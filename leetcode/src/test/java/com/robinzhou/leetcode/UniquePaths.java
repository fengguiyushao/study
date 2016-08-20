package com.robinzhou.leetcode;

/**
 * Created by robinzhou on 2016/6/1.
 */
public class UniquePaths {
    public int uniquePaths(int m, int n) {
        int[] p = new int[n];
        for (int i = 0; i < n; i++) {
            p[i] = 1;
        }

        while (--m > 0) {
            for (int i = 1; i < n; i++) {
                p[i] = p[i] + p[i - 1];
            }
        }

        return p[n - 1];
    }

    public static void main(String[] args) {
        UniquePaths up = new UniquePaths();
        System.out.println(up.uniquePaths(3, 7));
    }

}
