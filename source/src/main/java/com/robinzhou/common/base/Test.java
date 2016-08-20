package com.robinzhou.common.base;

/**
 * Created by robinzhou on 2015/9/9.
 */
public class Test {
    int a = 0;

    public Test() {
        Thread t1 = new Thread(() -> {
            System.out.println(a);
        });
        t1.start();
        try {
            Thread.sleep(1000l);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        a = 10;
        System.out.println("test");
    }

    public static void main(String[] args) {
        new Test();
    }
}
