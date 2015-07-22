package com.robinzhou.devideandconquer;

/**
 * Created by N550 on 2015/7/8.
 */
public class QuickPow {

    public static int pow(int x, int n) {
        int result = 1;
        while (n > 0) {
            if (n % 2 == 1) {
                result *= x;
            }
            x *= x;
            n = n >> 1;
        }
        return result;
    }

    public static double myPow(double x, int n) {
        double result = 1;
        boolean neg = false;
        if (n < 0) {
            x = 1 / x;
            n = -n;
            neg = true;
        }
        while (n > 0) {
            if ((n & 1) == 1) {
                result *= x;
            }
            x *= x;
            n = n >> 1;
        }
        if (neg) {
            result = Math.round(result * 100000) / 100000.0;
        }
        return result;
    }
}
