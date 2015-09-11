package com.robinzhou.common.base;

import com.google.common.base.Function;
import com.google.common.base.Supplier;
import com.google.common.collect.AbstractIterator;

import javax.annotation.Nullable;
import java.io.Serializable;
import java.util.Iterator;
import java.util.Set;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by robinzhou on 2015/9/8.
 */
public abstract class Optional<T> implements Serializable {

    private static final long serialVersionUID = 0;

    public static <T> Optional<T> absent() {
        return Absent.withType();
    }

    public static <T> Optional<T> of(T reference) {
        return new Present<>(checkNotNull(reference));
    }

    public static <T> Optional<T> fromNullable(@Nullable T nullableReference) {
        return (nullableReference == null) ? Optional.absent() : (new Present<>(nullableReference));
    }

    public abstract boolean isPresent();

    public abstract T get();

    public abstract T or(T defaultValue);

    public abstract T or(Supplier<? extends T> supplier);

    public abstract Optional<T> or(Optional<? extends T> secondChoice);

    public abstract <V> Optional<V> transform(Function<? super T, V> function);

    @Nullable
    public abstract T orNull();

    public abstract Set<T> asSet();

    @Override
    public abstract boolean equals(@Nullable Object object);

    @Override
    public abstract String toString();

    @Override
    public abstract int hashCode();

    public static <T> Iterable<T> presentInstances(Iterable<? extends Optional<? extends  T>> optionals) {
        checkNotNull(optionals);
        return () -> new AbstractIterator<T>() {
            private final Iterator<? extends Optional<? extends  T>> iterator = checkNotNull(optionals.iterator());
            @Override
            protected T computeNext() {
                while (iterator.hasNext()) {
                    Optional<? extends  T> optional = iterator.next();
                    if(optional.isPresent()) {
                        return optional.get();
                    }
                }
                return endOfData();
            }
        };
    }
}
