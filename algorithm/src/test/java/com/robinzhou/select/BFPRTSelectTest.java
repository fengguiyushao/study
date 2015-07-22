package com.robinzhou.select;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by N550 on 2015/7/15.
 */
public class BFPRTSelectTest {

    @Test
    public void testInsertSort() throws Exception {
        int[] array = {4, 65, 5, 96, 43, 13, 76, 49, 67, 34, 11, 5, 78, 65};
        BFPRTSelect.insertSort(array, 0, array.length - 1);
        Assert.assertArrayEquals(new int[]{4, 5, 5, 11, 13, 34, 43, 49, 65, 65, 67, 76, 78, 96}, array);
    }

    @Test
    public void testSelect() throws Exception {
        int[] array = {4, 65, 5, 96, 43, 13, 76, 49, 67, 34, 11, 5, 78, 65};
        int result = BFPRTSelect.select(array, 0, array.length - 1, 8);
        Assert.assertEquals(49, result);
    }
}