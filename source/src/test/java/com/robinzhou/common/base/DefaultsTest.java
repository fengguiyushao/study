package com.robinzhou.common.base;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;

/**
 * Created by robinzhou on 2015/9/21.
 */
public class DefaultsTest {
    @Test
    public void testGetDefaultValue() {
        assertEquals(false, Defaults.defaultValue(boolean.class).booleanValue());
        assertEquals('\0', Defaults.defaultValue(char.class).charValue());
        assertEquals(0, Defaults.defaultValue(byte.class).byteValue());
        assertEquals(0, Defaults.defaultValue(short.class).shortValue());
        assertEquals(0, Defaults.defaultValue(int.class).intValue());
        assertEquals(0, Defaults.defaultValue(long.class).longValue());
        assertEquals(0.0f, Defaults.defaultValue(float.class).floatValue(), 0f);
        assertEquals(0.0d, Defaults.defaultValue(double.class).doubleValue(), 0.0d);
        assertNull(Defaults.defaultValue(void.class));
        assertNull(Defaults.defaultValue(String.class));
    }
}
