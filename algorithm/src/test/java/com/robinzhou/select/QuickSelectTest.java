package com.robinzhou.select;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by N550 on 2015/7/15.
 */
public class QuickSelectTest {

    @Test
    public void testSelect() throws Exception {
        int[] array = {4, 65, 5, 96, 43, 13, 76, 49, 67, 34, 11, 5, 78, 65};
        int result = QuickSelect.select(array, 0, array.length - 1,6);
        Assert.assertEquals(34, result);
    }
}