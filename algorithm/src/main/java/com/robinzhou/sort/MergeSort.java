package com.robinzhou.sort;

/**
 * Created by N550 on 2015/7/7.
 */
public class MergeSort {

    public static void sort(int[] a) {
        sort(a, 0, a.length - 1);
    }

    public static void sort(int[] a, int p, int r) {
        if (p >= r) {
            return;
        }
        int q = (p + r) >> 1;
        sort(a, p, q);
        sort(a, q + 1, r);
        merge(a, p, q, r);
    }

    public static void merge(int[] a, int p, int q, int r) {
        int n1 = q - p + 1;
        int n2 = r - q;
        int[] left = new int[n1 + 1];
        int[] right = new int[n2 + 1];

        System.arraycopy(a, p, left, 0, n1);
        left[n1] = Integer.MAX_VALUE;
        System.arraycopy(a, q + 1, right, 0, n2);
        right[n2] = Integer.MAX_VALUE;

        int i = 0, j = 0, k = p;
        while (k <= r) {
            if (left[i] < right[j]) {
                a[k] = left[i];
                i++;
            } else {
                a[k] = right[j];
                j++;
            }
            k++;
        }

    }
}
