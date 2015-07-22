package com.robinzhou.devideandconquer;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by N550 on 2015/7/8.
 */
public class QuickPowTest {

    @Test
    public void testPow() throws Exception {
        Assert.assertEquals(1, QuickPow.pow(2, 0));
        Assert.assertEquals(2, QuickPow.pow(2, 1));
        Assert.assertEquals(32, QuickPow.pow(2, 5));
        Assert.assertEquals(1024, QuickPow.pow(2, 10));
        Assert.assertEquals(32768, QuickPow.pow(2, 15));

        Assert.assertEquals(0.00003,QuickPow.myPow(34.00515, -3),0.0);

    }
}