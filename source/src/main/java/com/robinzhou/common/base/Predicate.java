package com.robinzhou.common.base;

import javax.annotation.Nullable;

/**
 * Created by robinzhou on 2015/9/15.
 */
public interface Predicate<T> {

    boolean apply(@Nullable T input);

    @Override
    boolean equals(@Nullable Object object);
}
