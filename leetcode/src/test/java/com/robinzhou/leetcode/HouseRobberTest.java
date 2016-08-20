package com.robinzhou.leetcode;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by robinzhou on 2016/5/26.
 */
public class HouseRobberTest {

    @Test
    public void testRob() throws Exception {
        HouseRobber hr = new HouseRobber();
        System.out.println(hr.rob(new int[]{2, 4, 6, 2, 5, 6}));
    }
}