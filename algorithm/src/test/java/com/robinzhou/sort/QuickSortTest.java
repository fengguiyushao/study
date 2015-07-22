package com.robinzhou.sort;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by N550 on 2015/7/9.
 */
public class QuickSortTest {

    @Test
    public void testSort() throws Exception {
        int[] array = {4, 65, 5, 96, 43, 13, 76, 49, 67, 34, 11, 5, 78, 65};
        QuickSort.sort(array, 0, array.length - 1);
        Assert.assertArrayEquals(new int[]{4, 5, 5, 11, 13, 34, 43, 49, 65, 65, 67, 76, 78, 96}, array);
    }
}