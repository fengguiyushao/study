package com.robinzhou.sort;

/**
 * Created by N550 on 2015/7/8.
 */
public class HeapSort {

    public static int parent(int i) {
        return (i - 1) >> 1;
    }

    public static int left(int i) {
        return (i << 1) + 1;
    }

    public static int right(int i) {
        return (i << 1) + 2;
    }

    public static void maxHeapify(int[] a, int lastIndex, int i) {
        int left = left(i);
        int right = right(i);
        int max;
        if (left <= lastIndex && a[left] > a[i]) {
            max = left;
        } else {
            max = i;
        }
        if (right <= lastIndex && a[right] > a[max]) {
            max = right;
        }
        if (max != i) {
            int tmp = a[max];
            a[max] = a[i];
            a[i] = tmp;
            maxHeapify(a, lastIndex, max);
        }
    }

    public static void buildMaxHeap(int[] a) {
        int lastIndex = a.length - 1;
        for (int i = parent(lastIndex); i >= 0; i--) {
            maxHeapify(a, lastIndex, i);
        }
    }

    public static void sort(int[] a) {
        buildMaxHeap(a);
        for (int i = a.length - 1; i > 0; i--) {
            int tmp = a[i];
            a[i] = a[0];
            a[0] = tmp;
            maxHeapify(a, i - 1, 0);
        }
    }

}
