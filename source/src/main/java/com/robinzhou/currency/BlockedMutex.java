package com.robinzhou.currency;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by robinzhou on 2016/4/3.
 */
class BlockedMutex {
    private Lock lock = new ReentrantLock();
    public BlockedMutex() {
// Acquire it right away, to demonstrate interruption
// of a task blocked on a ReentrantLock:
        lock.lock();
    }
    public void f() {
        try {
// This will never be available to a second task
            lock.lockInterruptibly(); // Special call
            System.out.println("lock acquired in f()");
        } catch(InterruptedException e) {
            System.out.println("Interrupted from lock acquisition in f()");
        }
    }
}