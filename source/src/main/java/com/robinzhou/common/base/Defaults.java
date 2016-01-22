package com.robinzhou.common.base;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by robinzhou on 2015/9/21.
 */
public final class Defaults {
    private static final Map<Class<?>, Object> DEFAULTS;

    static {
        Map<Class<?>, Object> map = new HashMap<Class<?>, Object>();
        put(map, boolean.class, false);
        put(map, char.class, '\0');
        put(map, byte.class, (byte) 0);
        put(map, short.class, (short) 0);
        put(map, int.class, 0);
        put(map, long.class, 0L);
        put(map, float.class, 0f);
        put(map, double.class, 0d);
        DEFAULTS = Collections.unmodifiableMap(map);
    }

    private Defaults() {
    }

    private static <T> void put(Map<Class<?>, Object> map, Class<T> type, T value) {
        map.put(type, value);
    }

    public static <T> T defaultValue(Class<T> type) {
        T t = (T) DEFAULTS.get(checkNotNull(type));
        return t;
    }
}
