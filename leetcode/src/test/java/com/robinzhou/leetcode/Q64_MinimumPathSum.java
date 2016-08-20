package com.robinzhou.leetcode;

/**
 * Created by robinzhou on 2016/6/2.
 */
public class Q64_MinimumPathSum {

    public int minPathSum(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        int[] p = new int[n];

        p[0] = grid[0][0];

        for (int i = 1; i < n; i++) {
            p[i] = p[i - 1] + grid[0][i];
        }

        for (int i = 1; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (j == 0) {
                    p[j] += grid[i][j];
                } else {
                    p[j] = Math.min(p[j - 1] + grid[i][j], p[j] + grid[i][j]);
                }
            }
        }
        return p[n - 1];
    }

    public static void main(String[] args) {
        Q64_MinimumPathSum q = new Q64_MinimumPathSum();
        System.out.println(q.minPathSum(new int[][]{
                {1, 2, 3, 4, 5, 6, 7},
                {1, 2, 3, 4, 5, 6, 7},
                {1, 2, 3, 4, 5, 6, 7}}));
    }


}
