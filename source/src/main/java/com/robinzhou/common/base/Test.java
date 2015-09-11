package com.robinzhou.common.base;

/**
 * Created by robinzhou on 2015/9/9.
 */
public class Test {

    public static int count = 0;
    private int id = count++;

    @Override
    public String toString() {
        return id + "";
    }

    public static void main(String[] args) {
        System.out.println(new Test());
        System.out.println(new Test());
    }
}
