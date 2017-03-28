package com.robinzhou.leetcode;

/**
 * Created by N550 on 2015/7/16.
 */
public class PowerOfTwo {
    public static void main(String[] args) {
        System.out.println(titleToNumber("AB"));
    }

    public static int titleToNumber(String s) {
        char[] chars = s.toCharArray();
        int sum = 0;
        for (int i = 0; i < chars.length; i++) {
            sum *= 26;
            sum += chars[i] - 'A' + 1;
        }
        return sum;
    }
}
