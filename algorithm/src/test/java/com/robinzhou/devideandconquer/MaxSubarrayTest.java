package com.robinzhou.devideandconquer;

import com.robinzhou.devideandconquer.MaxSubarray.Subarray;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by N550 on 2015/7/8.
 */
public class MaxSubarrayTest {

    @Test
    public void testFindMaxSubarray() throws Exception {
        int[] a = {100, 113, 110, 85, 105, 102, 86, 63, 81, 101, 94, 106, 101, 79, 94, 90, 97};
        int[] delta = new int[a.length];
        delta[0] = 0;
        for (int i = 1; i < a.length; i++) {
            delta[i] = a[i] - a[i - 1];
        }
        Subarray subarray = MaxSubarray.findMaxSubarray(delta, 0, delta.length - 1);
        Subarray result = new Subarray(8, 11, 43);
        Assert.assertEquals(result.toString(), subarray.toString());
    }
}