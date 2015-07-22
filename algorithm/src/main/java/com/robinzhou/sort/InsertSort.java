package com.robinzhou.sort;

/**
 * Created by N550 on 2015/7/7.
 */
public class InsertSort {

    public static void sort(int[] array) {
        for (int i = 1, length = array.length; i < length; i++) {
            int key = array[i];
            int j = i - 1;
            while (j >= 0 && array[j] > key) {
                array[j + 1] = array[j];
                j--;
            }
            array[j + 1] = key;
        }
    }

}
