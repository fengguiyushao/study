package com.robinzhou.jvm;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by robinzhou on 2016/12/31.
 */
public class HeapOOM {

    static class OOMObject {
        public byte[] placeHolder = new byte[100 * 1024];

    }

    public static void main(String[] args) throws InterruptedException {
        List<OOMObject> list = new ArrayList<OOMObject>();

        for (int i = 0; i < 10000; i++) {
            list.add(new OOMObject());
        }

        while (true) {
            Thread.sleep(2000);
        }
    }
}
