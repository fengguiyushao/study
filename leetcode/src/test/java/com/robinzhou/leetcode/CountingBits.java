package com.robinzhou.leetcode;

import java.util.Arrays;

/**
 * Created by robinzhou on 2016/6/1.
 */
public class CountingBits {

    public int[] countBits(int num) {
        int[] bits = new int[num + 1];
        for (int i = 1; i <= num; i++) {
            bits[i] = bits[i >> 1] + (i & 1);
        }
        return bits;
    }

    public static void main(String[] args) {
        CountingBits cb = new CountingBits();
        System.out.println(Arrays.toString(cb.countBits(5)));
    }
}
