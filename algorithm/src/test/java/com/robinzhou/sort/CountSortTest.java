package com.robinzhou.sort;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by N550 on 2015/7/13.
 */
public class CountSortTest {

    @Test
    public void testSort() throws Exception {
        int[] array = {1,3,5,3,2,5,7,1,9,6,4,8,5,6,4};
        int[] sortedArray = CountSort.sort(array);
        Assert.assertArrayEquals(new int[]{1,1,2,3,3,4,4,5,5,5,6,6,7,8,9}, sortedArray);
    }
}