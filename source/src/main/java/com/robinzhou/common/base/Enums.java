package com.robinzhou.common.base;

import com.google.common.base.Converter;
import com.google.common.base.Optional;

import java.io.Serializable;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;

import static com.robinzhou.common.base.Preconditions.checkNotNull;

/**
 * Created by robinzhou on 2015/9/23.
 */
public final class Enums {

    private static final Map<Class<? extends Enum<?>>, Map<String, WeakReference<? extends Enum<?>>>> enumConstantCache = new WeakHashMap<>();

    private Enums() {
    }

    public static Field getField(Enum<?> enumValue) {
        Class<?> clazz = enumValue.getDeclaringClass();
        try {
            return clazz.getDeclaredField(enumValue.name());
        } catch (NoSuchFieldException impossible) {
            throw new AssertionError(impossible);
        }
    }

    public static <T extends Enum<T>> Optional<T> getIfPresent(Class<T> enumClass, String value) {
        checkNotNull(enumClass);
        checkNotNull(value);
        return Platform.getEnumIfPresent(enumClass, value);
    }

    private static <T extends Enum<T>> Map<String, WeakReference<? extends Enum<?>>> populateCache(Class<T> enumClass) {
        Map<String, WeakReference<? extends Enum<?>>> result = new HashMap<>();
        for (T enumInstance : EnumSet.allOf(enumClass)) {
            result.put(enumInstance.name(), new WeakReference<Enum<?>>(enumInstance));
        }
        enumConstantCache.put(enumClass, result);
        return result;
    }

    static <T extends Enum<T>> Map<String, WeakReference<? extends Enum<?>>> getEnumConstants(Class<T> enumClass) {
        synchronized (enumConstantCache) {
            Map<String, WeakReference<? extends Enum<?>>> constants = enumConstantCache.get(enumClass);
            if (constants == null) {
                constants = populateCache(enumClass);
            }
            return constants;
        }
    }


    public static <T extends Enum<T>> Converter<String, T> stringConverter(Class<T> enumClass) {
        return new StringConverter(enumClass);
    }

    private static final class StringConverter<T extends Enum<T>> extends Converter<String, T> implements Serializable {

        private static final long serialVersionUID = 0;

        private final Class<T> enumClass;

        StringConverter(Class<T> enumClass) {
            this.enumClass = checkNotNull(enumClass);
        }

        @Override
        protected T doForward(String value) {
            return Enum.valueOf(enumClass, value);
        }

        @Override
        protected String doBackward(T enumValue) {
            return enumValue.name();
        }

        @Override
        public boolean equals(Object object) {
            if (object instanceof StringConverter) {
                StringConverter<?> that = (StringConverter<?>) object;
                return enumClass.equals(that.enumClass);
            }
            return false;
        }

        @Override
        public int hashCode() {
            return enumClass.hashCode();
        }

        @Override
        public String toString() {
            return "Enums.stringConverter(" + enumClass.getName() + ".class)";
        }
    }
}
