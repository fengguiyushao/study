package com.robinzhou.leetcode;

/**
 * Created by N550 on 2015/7/16.
 */
public class PowerOfTwo {
    public static boolean isPowerOfTwo(int n) {
//        if (n == 0) return false;
//        while (true) {
//            if ((n & 1) == 1) {
//                if (n == 1) {
//                    return true;
//                } else {
//                    return false;
//                }
//            }
//            n = n >> 1;
//        }
        return (n > 0 && ((n & (n - 1)) == 0));
    }
}
