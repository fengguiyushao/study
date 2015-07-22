package com.robinzhou.sort;

/**
 * Created by N550 on 2015/7/13.
 */
public class RadixSort {

    public static void sort(int[] a, int d) {
        int m = 1;
        int n = 1;
        while (m <= d) {
            countSort(a, n);
            m++;
            n *= 10;
        }
    }


    public static void countSort(int[] a, int n) {
        int[] b = new int[a.length];
        int[] c = new int[10];
        for (int x : a) {
            int digit = (x / n) % 10;
            c[digit]++;
        }
        for (int i = 1; i < c.length; i++) {
            c[i] += c[i - 1];
        }
        for (int i = a.length - 1; i >= 0; i--) {
            int digit = (a[i] / n) % 10;
            b[--c[digit]] = a[i];
        }
        System.arraycopy(b, 0, a, 0, a.length);
    }
}
