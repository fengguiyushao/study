package com.robinzhou.sort;

/**
 * Created by N550 on 2015/7/9.
 */
public class QuickSort {

    public static int randomPartition(int[] a, int p, int r) {
        int i = (int) (p + Math.random() * (r - p + 1));
        int tmp = a[i];
        a[i] = a[p];
        a[p] = tmp;
        return partition(a, p, r);
    }

    public static int partition(int[] a, int p, int r) {
        int x = a[p];
        int i = p;
        for (int j = p + 1; j <= r; j++) {
            if (a[j] < x) {
                int tmp = a[++i];
                a[i] = a[j];
                a[j] = tmp;
            }
        }
        a[p] = a[i];
        a[i] = x;
        return i;
    }

    public static void sort(int[] a, int p, int r) {
        if (p < r) {
            int q = randomPartition(a, p, r);
            sort(a, p, q - 1);
            sort(a, q + 1, r);
        }
    }
}
