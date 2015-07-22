package com.robinzhou.sort;

/**
 * Created by N550 on 2015/7/13.
 */
public class CountSort {

    public static int[] sort(int[] a) {
        int[] b = new int[a.length];
        int min = a[0], max = a[0];
        for (int x : a) {
            if (x > max) {
                max = x;
            }
            if (x < min) {
                min = x;
            }
        }
        int[] c = new int[max - min + 1];
        for (int x : a) {
            c[x - min]++;
        }
        for (int i = 1; i < c.length; i++) {
            c[i] += c[i - 1];
        }
        for (int i = a.length - 1; i >= 0; i--) {
            b[--c[a[i] - min]] = a[i];
        }
        return b;
    }
}
