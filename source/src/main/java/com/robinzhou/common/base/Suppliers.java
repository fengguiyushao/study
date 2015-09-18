package com.robinzhou.common.base;

import javax.annotation.Nullable;
import java.io.Serializable;
import java.util.concurrent.TimeUnit;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by robinzhou on 2015/9/17.
 */
public final class Suppliers {
    private Suppliers() {
    }

    public static <F, T> Supplier<T> compose(Function<? super F, T> function, Supplier<F> supplier) {
        return new SupplierComposition<>(checkNotNull(function), checkNotNull(supplier));
    }

    public static <T> Supplier memoizeWithExpiration(Supplier<T> delegate) {
        return (delegate instanceof MemoizingSupplier) ? delegate : new MemoizingSupplier<>(checkNotNull(delegate));
    }

    public static <T> Supplier memoizeWithExpiration(Supplier<T> delegate, long duration, TimeUnit unit) {
        return new ExpiringMemoizingSupplier<>(delegate, duration, unit);
    }

    public static <T> Supplier<T> ofInstance(T instance) {
        return new SupplierOfInstance(instance);
    }

    public static <T> Supplier<T> synchronizedSupplier(Supplier<T> delegate) {
        return new ThreadSafeSupplier<>(checkNotNull(delegate));
    }

    public static <T> Function<Supplier<T>, T> supplierFunction() {
        SupplierFunction<T> sf = (SupplierFunction<T>) SupplierFunctionImpl.INSTANCE;
        return sf;
    }

    private enum SupplierFunctionImpl implements SupplierFunction<Object> {
        INSTANCE;

        @Override
        public Object apply(Supplier<Object> input) {
            return input.get();
        }

        @Override
        public String toString() {
            return "Suppliers.supplierFunction()";
        }
    }

    private interface SupplierFunction<T> extends Function<Supplier<T>, T> {
    }

    private static class SupplierComposition<F, T> implements Supplier<T>, Serializable {

        private static final long serialVersionUID = 0;

        final Function<? super F, T> function;
        final Supplier<F> supplier;

        SupplierComposition(Function<? super F, T> function, Supplier<F> supplier) {
            this.function = function;
            this.supplier = supplier;
        }

        @Override
        public T get() {
            return function.apply(supplier.get());
        }

        @Override
        public boolean equals(@Nullable Object obj) {
            if (obj instanceof SupplierComposition) {
                SupplierComposition<?, ?> that = (SupplierComposition<?, ?>) obj;
                return function.equals(that.function) && supplier.equals(that.supplier);
            }
            return false;
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(function, supplier);
        }

        @Override
        public String toString() {
            return "Suppliers.compose(" + function + ", " + supplier + ")";
        }
    }

    static class MemoizingSupplier<T> implements Supplier<T>, Serializable {

        private static final long serialVersionUID = 0l;

        final Supplier<T> delegate;
        transient volatile boolean initialized;

        transient T value;

        MemoizingSupplier(Supplier<T> delegate) {
            this.delegate = delegate;
        }

        @Override
        public T get() {
            if (!initialized) {
                synchronized (this) {
                    if (!initialized) {
                        T t = delegate.get();
                        value = t;
                        initialized = true;
                        return t;
                    }
                }
            }
            return value;
        }

        @Override
        public String toString() {
            return "Suppliers.memoizeWithExpiration(" + delegate + ")";
        }
    }

    static class ExpiringMemoizingSupplier<T> implements Supplier<T>, Serializable {

        private static final long serialVersionUID = 0;

        final Supplier<T> delegate;
        final long durationNanos;
        transient volatile T value;
        transient volatile long expirationNanos;

        ExpiringMemoizingSupplier(Supplier<T> delegate, long duration, TimeUnit unit) {
            this.delegate = checkNotNull(delegate);
            this.durationNanos = unit.toNanos(duration);
            checkArgument(duration > 0);
        }

        @Override
        public T get() {
            long nanos = expirationNanos;
            long now = Platform.systemNanoTime();
            if (expirationNanos == 0 || now - nanos >= 0) {
                synchronized (this) {
                    if (nanos == expirationNanos) {
                        T t = delegate.get();
                        value = t;
                        nanos = now + durationNanos;
                        expirationNanos = (nanos == 0) ? 1 : nanos;
                        return t;
                    }
                }
            }
            return value;
        }

        @Override
        public String toString() {
            return "Suppliers.memoizeWithExpiring(" + delegate + ", " + durationNanos + ", NANOS)";
        }
    }

    private static class SupplierOfInstance<T> implements Supplier<T>, Serializable {
        private static final long serialVersionUID = 0;

        final T instance;

        SupplierOfInstance(@Nullable T instance) {
            this.instance = instance;
        }

        @Override
        public T get() {
            return instance;
        }

        @Override
        public boolean equals(@Nullable Object obj) {
            if (obj instanceof SupplierOfInstance) {
                SupplierOfInstance<?> that = (SupplierOfInstance<?>) obj;
                return instance.equals(that.instance);
            }
            return false;
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(instance);
        }

        @Override
        public String toString() {
            return "Suppliers.ofInstance(" + instance + ")";
        }
    }

    private static class ThreadSafeSupplier<T> implements Supplier<T>, Serializable {
        private static final long serialVersionUID = 0;

        final Supplier<T> delegate;

        ThreadSafeSupplier(Supplier<T> delegate) {
            this.delegate = delegate;
        }

        @Override
        public T get() {
            synchronized (delegate) {
                return delegate.get();
            }
        }

        @Override
        public String toString() {
            return "Suppliers.synchronizedSupplier(" + delegate + ")";
        }

    }

}
