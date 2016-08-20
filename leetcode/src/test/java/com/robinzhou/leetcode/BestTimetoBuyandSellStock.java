package com.robinzhou.leetcode;

/**
 * Created by robinzhou on 2016/5/30.
 */
public class BestTimetoBuyandSellStock {

    public int maxProfit(int[] prices) {
        if (prices.length == 0) {
            return 0;
        }
        int best = 0;
        int min = prices[0];
        for (int i = 1, n = prices.length; i < n; i++) {
            int temp = prices[i] - min;
            best = temp > best ? temp : best;
            min = prices[i] < min ? prices[i] : min;
        }
        return best;
    }

    public static void main(String[] args) {
        BestTimetoBuyandSellStock b = new BestTimetoBuyandSellStock();
        int[] prices = new int[0];
        System.out.println(b.maxProfit(prices));
    }
}
