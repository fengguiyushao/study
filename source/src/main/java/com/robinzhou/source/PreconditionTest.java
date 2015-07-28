package com.robinzhou.source;

import static com.google.common.base.Preconditions.*;

/**
 * Created by robinzhou on 2015/7/27.
 */
public class PreconditionTest {
    public static void main(String[] args) {
        checkArgument(1 == 1);

        checkState(1 == 1, "int not %s", "correct");

        checkNotNull(new PreconditionTest());
    }

}
