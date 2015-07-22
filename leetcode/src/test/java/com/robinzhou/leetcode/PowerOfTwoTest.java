package com.robinzhou.leetcode;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by N550 on 2015/7/16.
 */
public class PowerOfTwoTest {

    @Test
    public void testIsPowerOfTwo() throws Exception {
        Assert.assertEquals(true,PowerOfTwo.isPowerOfTwo(1));
        Assert.assertEquals(true,PowerOfTwo.isPowerOfTwo(8));
        Assert.assertEquals(false,PowerOfTwo.isPowerOfTwo(3));
        Assert.assertEquals(false,PowerOfTwo.isPowerOfTwo(7));
        Assert.assertEquals(false,PowerOfTwo.isPowerOfTwo(0));
    }
}