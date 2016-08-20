package com.robinzhou.leetcode;

/**
 * Created by robinzhou on 2016/6/2.
 */
public class Q63_UniquePathsII {

    public int uniquePathsWithObstacles(int[][] obstacleGrid) {
        int m = obstacleGrid.length;
        int n = obstacleGrid[0].length;
        int[][] p = new int[m][n];
        p[0][0] = 1 - obstacleGrid[0][0];

        for (int i = 1; i < m; i++) {
            p[i][0] = obstacleGrid[i][0] == 1 ? 0 : p[i - 1][0];
        }

        for (int i = 1; i < n; i++) {
            p[0][i] = obstacleGrid[0][i] == 1 ? 0 : p[0][i - 1];
        }

        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                p[i][j] = obstacleGrid[i][j] == 1 ? 0 : p[i - 1][j] + p[i][j - 1];
            }
        }
        return p[m - 1][n - 1];
    }

    public static void main(String[] args) {
        Q63_UniquePathsII q = new Q63_UniquePathsII();
        System.out.println(q.uniquePathsWithObstacles(new int[][]{
                {0, 0, 0, 0, 0, 0, 0},
                {0, 0, 1, 0, 1, 0, 0},
                {0, 0, 0, 0, 0, 0, 0}}));
        System.out.println(q.uniquePathsWithObstacles(new int[][]{
                {1, 0}
        }));
    }
}
