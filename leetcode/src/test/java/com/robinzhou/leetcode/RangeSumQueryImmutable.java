package com.robinzhou.leetcode;

/**
 * Created by robinzhou on 2016/5/31.
 */
public class RangeSumQueryImmutable {

    public static class NumArray {
        int[][] sum;
        int[] nums;

        public NumArray(int[] nums) {
            int n = nums.length;
            this.nums = nums;
            sum = new int[n][n];
        }

        public int sumRange(int i, int j) {
            int k = j;
            while (sum[i][k] == 0 && k > i) {
                k--;
            }

            if (k == i) {
                sum[i][i] = nums[i];
            }

            while (++k <= j) {
                sum[i][k] = sum[i][k-1] + nums[k];
            }
            return sum[i][j];
        }
    }

    public static void main(String[] args) {
        NumArray numArray = new NumArray(new int[]{-2, 0, 3, -5, 2, -1});
        System.out.println(numArray.sumRange(0, 2));
        System.out.println(numArray.sumRange(2, 5));
        System.out.println(numArray.sumRange(0, 5));
    }


}
