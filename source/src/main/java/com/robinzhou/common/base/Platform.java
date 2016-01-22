package com.robinzhou.common.base;

import com.google.common.base.Optional;

import java.lang.ref.WeakReference;

/**
 * Created by robinzhou on 2015/9/17.
 */
final class Platform {
    private Platform() {
    }

    /**
     * Calls {@link System#nanoTime()}.
     */
    static long systemNanoTime() {
        return System.nanoTime();
    }


    static <T extends Enum<T>> Optional<T> getEnumIfPresent(Class<T> enumClass, String value) {
        WeakReference<? extends Enum<?>> ref = Enums.getEnumConstants(enumClass).get(value);
        return ref == null
                ? Optional.<T>absent()
                : Optional.of(enumClass.cast(ref.get()));
    }
}
