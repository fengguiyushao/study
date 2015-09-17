package com.robinzhou.common.base;

import javax.annotation.Nullable;
import java.io.Serializable;
import java.util.Map;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by robinzhou on 2015/9/11.
 */
public class Functions {

    public static <E> Function<E, E> identity() {
        return (Function<E, E>) IdentityFunction.INSTANCE;
    }

    public static Function<Object, String> toStringFunction() {
        return ToStringFunction.INSTANCE;
    }

    public static <K, V> Function<K, V> forMap(Map<K, V> map) {
        return new FunctionForMapNoDefault(map);
    }

    public static <K, V> Function<K, V> forMap(Map<K, ? extends V> map, @Nullable V defaultValue) {
        return new ForMapWithDefault(map, defaultValue);
    }

    public static <A, B, C> Function<A, C> compose(Function<B, C> g, Function<A, ? extends B> f) {
        return new FunctionComposition<>(g, f);
    }

    public static  <E> Function<Object, E> consant(@Nullable E value) {
        return new ConstantFunction<E>(value);
    }

    private enum IdentityFunction implements Function<Object, Object> {
        INSTANCE;

        @Nullable
        @Override
        public Object apply(@Nullable Object o) {
            return o;
        }

        @Override
        public String toString() {
            return "Functions.identity()";
        }
    }

    private enum ToStringFunction implements Function<Object, String> {
        INSTANCE;

        @Override
        public String apply(Object object) {
            checkNotNull(object);
            return object.toString();
        }

        @Override
        public String toString() {
            return "Functions.toStringFunction()";
        }

    }

    private static class FunctionForMapNoDefault<K, V> implements Function<K, V>, Serializable {
        private static final long serialVersionUID = 0;
        final Map<K, V> map;

        FunctionForMapNoDefault(Map<K, V> map) {
            this.map = checkNotNull(map);
        }

        @Override
        public V apply(@Nullable K key) {
            V result = map.get(key);
            checkArgument(result != null || map.containsKey(key), "Key '%s' is not present in map", key);
            return result;
        }

        @Override
        public boolean equals(@Nullable Object o) {
            if (o instanceof FunctionForMapNoDefault) {
                FunctionForMapNoDefault<?, ?> other = (FunctionForMapNoDefault<?, ?>) o;
                return map.equals(other.map);
            }
            return false;
        }

        @Override
        public int hashCode() {
            return map.hashCode();
        }

        @Override
        public String toString() {
            return "Functions.forMao(" + map + ")";
        }
    }

    private static class ForMapWithDefault<K, V> implements Function<K, V>, Serializable {
        final Map<K, ? extends V> map;
        final V defaultValue;

        private ForMapWithDefault(Map<K, ? extends V> map, @Nullable V defaultValue) {
            this.map = checkNotNull(map);
            this.defaultValue = defaultValue;
        }


        @Override
        public V apply(@Nullable K key) {
            V result = map.get(key);
            return (result != null || map.containsKey(key)) ? result : defaultValue;
        }

        @Override
        public boolean equals(@Nullable Object o) {
            if (o instanceof ForMapWithDefault) {
                ForMapWithDefault<?, ?> other = (ForMapWithDefault<?, ?>) o;
                return map.equals(other.map) && Objects.equal(defaultValue, other.defaultValue);
            }
            return false;
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(map, defaultValue);
        }

        @Override
        public String toString() {
            return "Functions.formap(" + map + ", defaultValue" + defaultValue + ")";
        }
    }

    private static class FunctionComposition<A, B, C> implements Function<A, C>, Serializable {
        private static final long serialVersionUID = 0;
        private final Function<B, C> g;
        private final Function<A, ? extends B> f;

        private FunctionComposition(Function<B, C> g, Function<A, ? extends B> f) {
            this.g = checkNotNull(g);
            this.f = checkNotNull(f);
        }

        @Nullable
        public C apply(@Nullable A a) {
            return g.apply(f.apply(a));
        }

        @Override
        public boolean equals(@Nullable Object obj) {
            if (obj instanceof FunctionComposition) {
                FunctionComposition<?, ?, ?> other = (FunctionComposition<?, ?, ?>) obj;
                return f.equals(other.f) && g.equals(other.g);
            }
            return false;
        }

        @Override
        public int hashCode() {
            return f.hashCode() + g.hashCode();
        }

        @Override
        public String toString() {
            return g + "(" + f + ")";
        }
    }

    private static class ConstantFunction<E> implements Function<Object, E>, Serializable {

        private static final long serialVersionUID = 0l;
        private final E value;

        ConstantFunction(@Nullable E value) {
            this.value = value;
        }

        @Nullable
        @Override
        public E apply(@Nullable Object object) {
            return value;
        }

        @Override
        public boolean equals(@Nullable Object o) {
            if (o instanceof ConstantFunction) {
                ConstantFunction<?> other = (ConstantFunction<?>) o;
                return Objects.equal(value, other.value);
            }
            return false;
        }

        @Override
        public int hashCode() {
            return (value == null) ? 0 : value.hashCode();
        }

        @Override
        public String toString() {
            return "Functions.constant(" + value + ")";
        }
    }
}
