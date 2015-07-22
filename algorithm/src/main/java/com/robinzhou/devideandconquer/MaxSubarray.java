package com.robinzhou.devideandconquer;


/**
 * Created by N550 on 2015/7/8.
 */
public class MaxSubarray {


    public static Subarray findMaxCrossingSubarray(int[] a, int low, int mid, int high) {
        int maxLeft = 0, maxright = 0;
        int leftSum = Integer.MIN_VALUE;
        int sum = 0;
        for (int i = mid; i >= low; i--) {
            sum += a[i];
            if (sum > leftSum) {
                leftSum = sum;
                maxLeft = i;
            }
        }
        sum = 0;
        int rightSum = Integer.MIN_VALUE;
        for (int i = mid + 1; i <= high; i++) {
            sum += a[i];
            if (sum > rightSum) {
                rightSum = sum;
                maxright = i;
            }
        }
        return new Subarray(maxLeft, maxright, leftSum + rightSum);
    }

    public static Subarray findMaxSubarray(int[] a, int low, int high) {
        if (low == high) {
            return new Subarray(low, high, a[low]);
        }
        int mid = (low + high) >> 1;
        Subarray left = findMaxSubarray(a, low, mid);
        Subarray right = findMaxSubarray(a, mid + 1, high);
        Subarray cross = findMaxCrossingSubarray(a, low, mid, high);
        if (left.compareTo(right) > 0 && left.compareTo(right) > 0) {
            return left;
        } else if (right.compareTo(left) > 0 && right.compareTo(left) > 0) {
            return right;
        } else {
            return cross;
        }

    }

    public static class Subarray implements Comparable<Subarray> {
        int left;
        int right;
        int sum;

        public Subarray(int left, int right, int sum) {
            this.left = left;
            this.right = right;
            this.sum = sum;
        }

        @Override
        public int compareTo(Subarray o) {
            return sum - o.sum;
        }

        @Override
        public String toString() {
            return "left:" + left + ";right:" + right + ";sum:" + sum;
        }
    }
}
