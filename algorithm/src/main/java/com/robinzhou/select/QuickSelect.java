package com.robinzhou.select;

import com.robinzhou.sort.QuickSort;

/**
 * Created by N550 on 2015/7/15.
 */
public class QuickSelect {

    public static int select(int[] a, int p, int r, int i) {
        int q = QuickSort.randomPartition(a, p, r);
        int k = q - p + 1;
        if (k == i) {
            return a[q];
        } else if (k > i) {
            return select(a, p, q - 1, i);
        } else {
            return select(a, q + 1, r, i - k);
        }
    }
}
