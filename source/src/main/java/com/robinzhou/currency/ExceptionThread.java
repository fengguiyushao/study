package com.robinzhou.currency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by robinzhou on 2016/4/3.
 */
public class ExceptionThread implements Runnable {
    public void run() {
        throw new RuntimeException();
    }
    public static void main(String[] args) {
        ExecutorService exec = Executors.newCachedThreadPool();
        exec.execute(new ExceptionThread());
    }
}