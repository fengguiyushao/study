package com.robinzhou.sort;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by N550 on 2015/7/8.
 */
public class PriorityQueueTest {

    @Test
    public void testInsert() throws Exception {
        int[] array = {65, 4, 96, 5, 43, 13, 76, 49, 67, 34, 11, 5, 78, 65};
        PriorityQueue queue = new PriorityQueue(array);

        Assert.assertEquals(96, queue.heapExtractMax());
        Assert.assertEquals(78, queue.heapMaximum());
        queue.heapIncreaseKey(12, 100);
        Assert.assertEquals(100, queue.heapMaximum());
        queue.insert(103);
        Assert.assertEquals(103, queue.heapMaximum());
        queue.insert(106);
        Assert.assertEquals(106, queue.heapMaximum());
    }
}