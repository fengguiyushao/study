package com.robinzhou.select;

import com.robinzhou.sort.QuickSort;

/**
 * Created by N550 on 2015/7/15.
 */
public class BFPRTSelect {

    public static void insertSort(int[] a, int p, int r) {
        for (int i = p + 1; i <= r; i++) {
            int value = a[i];
            int j = i;
            while (j > p && a[j - 1] > value) {
                a[j] = a[j - 1];
                j--;
            }
            a[j] = value;
        }
    }

    public static int partition(int[] a, int p, int r, int k) {
        int tmp = a[k];
        a[k] = a[p];
        a[p] = tmp;
        return QuickSort.partition(a, p, r);
    }


    public static int selectIndex(int[] a, int p, int r, int k) {
        if (r - p < 5) {
            insertSort(a, p, r);
            return k + p - 1;
        }
        for (int i = 0; i < (r - p + 5) / 5; i++) {
            int s = p + 5 * i;
            int t = (s + 4) > r ? r : (s + 4);
            insertSort(a, s, t);
            int mid = (t + s) / 2;
            int tmp = a[p + i];
            a[p + i] = a[mid];
            a[mid] = tmp;
        }
        int m = selectIndex(a, p, p + (r - p + 5) / 5, (r - p + 10) / 10);
        int q = partition(a, p, r, m);
        int count = q - p + 1;
        if (count == k) {
            return q;
        } else if (count > k) {
            return selectIndex(a, p, q - 1, k);
        } else {
            return selectIndex(a, q + 1, r, k - count);
        }
    }

    public static int select(int[] a, int p, int r, int k) {
        int index = selectIndex(a, p, r, k);
        return a[index];
    }

}
