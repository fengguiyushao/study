package com.robinzhou.jvm;

import java.io.IOException;

/**
 * Created by robinzhou on 2016/12/31.
 */
public class DeadLock {

    public static void main(String[] args) throws InterruptedException, IOException {
        for (int i = 0; i < 100; i++) {
            new Thread(new SyncAddRunable(1, 2)).start();
            new Thread(new SyncAddRunable(2, 1)).start();
        }
    }

    static class SyncAddRunable implements Runnable {

        int a, b;

        public SyncAddRunable(int a, int b) {
            this.a = a;
            this.b = b;
        }

        @Override
        public void run() {
            synchronized (Integer.valueOf(a)) {
                synchronized (Integer.valueOf(b)) {
                    System.out.println(a + b);
                }
            }
        }
    }
}
