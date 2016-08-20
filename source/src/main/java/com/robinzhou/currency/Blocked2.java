package com.robinzhou.currency;

/**
 * Created by robinzhou on 2016/4/3.
 */
class Blocked2 implements Runnable {
    public void run() {
        BlockedMutex blocked = new BlockedMutex();
        System.out.println("Waiting for f() in BlockedMutex");
        blocked.f();
        System.out.println("Broken out of blocked call");
    }
}