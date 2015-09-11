package com.robinzhou.common.base;

import com.google.common.base.MoreObjects;

import javax.annotation.Nullable;
import java.util.Arrays;

/**
 * Created by robinzhou on 2015/9/10.
 */
public class Objects {

    private Objects() {
    }

    public static boolean equal(@Nullable Object a, @Nullable Object b) {
        return a == b || (a != null && a.equals(b));
    }

    public static int hashCode(@Nullable Object... objects) {
        return Arrays.hashCode(objects);
    }

    public static <T> T firstNonNull(@Nullable T first, @Nullable T second) {
        return MoreObjects.firstNonNull(first, second);
    }
}
