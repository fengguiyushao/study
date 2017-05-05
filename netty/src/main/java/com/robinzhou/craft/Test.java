package com.robinzhou.craft;

/**
 * Created by robinzhou on 2017/5/5.
 */
public class Test {
    public static void main(String[] args) {
        int a = 3, b = 5;
        a = a ^ b;
        b = a ^ b;
        a = a ^ b;
        System.out.println(a);
        System.out.println(b);
    }
}
