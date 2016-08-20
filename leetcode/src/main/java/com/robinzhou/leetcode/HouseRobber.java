package com.robinzhou.leetcode;

/**
 * Created by robinzhou on 2016/5/26.
 */
public class HouseRobber {
    public int rob(int[] num) {
        int n = num.length;
        if(n == 0) {
            return 0;
        }
        int[] d = new int[num.length];
        d[0] = num[0];
        for (int i = 1; i < n; i++) {
            if (i == 1) {
                d[i] = (num[0] >= num[1]) ? num[0] : num[1];
            } else {
                d[i] = (d[i - 2] + num[i] >= d[i - 1]) ? d[i - 2] + num[i] : d[i - 1];
            }
        }
        return d[n - 1];

//        int prevNo = 0;
//        int prevYes = 0;
//        for (int n : num) {
//            int temp = prevNo;
//            prevNo = Math.max(prevNo, prevYes);
//            prevYes = n + temp;
//        }
//        return Math.max(prevNo, prevYes);
    }
}
