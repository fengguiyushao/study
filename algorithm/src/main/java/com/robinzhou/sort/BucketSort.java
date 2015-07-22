package com.robinzhou.sort;

/**
 * Created by N550 on 2015/7/13.
 */
public class BucketSort {


    public static void sort(int[] a) {
        Integer[][] buckets = new Integer[10][a.length];
        for (int x : a) {
            int digit = x / 10;
            for (int i = 0, length = a.length; i < length; i++) {
                if (buckets[digit][i] == null) {
                    buckets[digit][i] = x;
                    break;
                }
            }
        }

        for (int i = 0; i < buckets.length; i++) {
            for (int j = 1; j < a.length; j++) {
                if (buckets[i][j] == null) {
                    break;
                }
                int value = buckets[i][j];
                int pos = j;
                if (pos > 0 && buckets[i][pos - 1] > value) {
                    buckets[i][pos] = buckets[i][pos - 1];
                    pos--;
                }
                buckets[i][pos] = value;
            }

        }

        for (int i = 0, index = 0; i < buckets.length; i++) {
            for (int j = 0; j < a.length; j++) {
                if (buckets[i][j] == null) {
                    break;
                }
                a[index++] = buckets[i][j];
            }
        }

    }

    /**
     * 用数组进行桶排序，用CountSort思想，不用插入桶
     *
     * @param a
     */
    public static void sort2(int[] a) {
        int[] b = new int[a.length];
        int[] c = new int[10];
        int[] s = new int[10];
        for (int i = 0, length = a.length; i < length; i++) {
            int digit = a[i] / 10;
            c[digit]++;
        }
        for (int i = 1, length = c.length; i < length; i++) {
            s[i] = s[i - 1] + c[i - 1];
        }
        for (int i = 0, length = a.length; i < length; i++) {
            int digit = a[i] / 10;
            b[s[digit]++] = a[i];
        }
        for (int i = 0, length = s.length; i < length; i++) {
            QuickSort.sort(b, s[i] - c[i], s[i] - 1);
        }
        System.arraycopy(b, 0, a, 0, a.length);
    }
}
