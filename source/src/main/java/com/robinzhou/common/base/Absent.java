package com.robinzhou.common.base;

import com.google.common.base.Function;
import com.google.common.base.Supplier;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.Set;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by robinzhou on 2015/9/8.
 */
final class Absent<T> extends Optional<T> {

    private static final long serialVersionUID = 0;

    private static Optional<Object> INSTANCE = new Absent<>();

    private Absent() {
    }

    static <T> Optional<T> withType() {
        return (Optional<T>) INSTANCE;
    }

    @Override
    public boolean isPresent() {
        return false;
    }

    @Override
    public T get() {
        throw new IllegalStateException("Optional.get() cannot be called on an absent value");
    }

    @Override
    public T or(T defaultValue) {
        return checkNotNull(defaultValue, "use Optional.orNull() instead of Optional.or(null)");
    }

    @Override
    public T or(Supplier<? extends T> supplier) {
        return checkNotNull(
                supplier.get(), "use Optional.orNull() instead of a Supplier that returns null");
    }

    @Override
    public Optional<T> or(Optional<? extends T> secondChoice) {
        return (Optional<T>) checkNotNull(secondChoice);
    }

    @Override
    public boolean equals(@Nullable Object object) {
        return this == object;
    }

    @Override
    public String toString() {
        return "Optional.absent()";
    }

    @Override
    public int hashCode() {
        return 0x79a31aac;
    }

    @Override
    public <V> Optional<V> transform(Function<? super T, V> function) {
        checkNotNull(function);
        return Optional.absent();
    }

    @Nullable
    @Override
    public T orNull() {
        return null;
    }

    @Override
    public Set<T> asSet() {
        return Collections.emptySet();
    }

    private Object readResolve() {
        return INSTANCE;
    }


}
