package com.robinzhou.common.base;

import javax.annotation.Nullable;

/**
 * Created by robinzhou on 2015/9/11.
 */
public interface Function<F, T> {

    @Nullable
    T apply(@Nullable F object);



    @Override
    boolean equals(@Nullable Object object);
}
