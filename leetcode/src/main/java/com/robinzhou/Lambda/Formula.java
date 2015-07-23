package com.robinzhou.Lambda;

/**
 * Created by N550 on 2015/7/23.
 */
public interface Formula {
    int a = 66;
    double calculate(int a);

    default double sqrt(int a) {
        return Math.sqrt(a);
    }
    static int get() {
        return a;
    }
}
