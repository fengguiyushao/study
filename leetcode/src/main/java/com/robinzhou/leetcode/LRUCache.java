package com.robinzhou.leetcode;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by robinzhou on 2017/3/2.
 */
public class LRUCache extends LinkedHashMap<Integer, Integer> {
    private final int MAX_CHCHE_SIZE;

    public LRUCache(int cacheSize) {
        super(cacheSize);
        MAX_CHCHE_SIZE = cacheSize;
    }

    public static void main(String[] args) {
        LRUCache cache = new LRUCache(2);
        cache.put(2, 1);
        cache.put(1, 1);
        cache.put(2, 3);
        cache.put(4, 1);
        System.out.println(cache.get(1));       // returns 1
        System.out.println(cache.get(2));       // returns -1 (not found)
    }

    public Integer get(Integer key) {
        Integer value = super.get(key);
        if (value != null) {
            super.remove(key);
            super.put(key, value);
        }
        return value == null ? -1 : value;
    }

    public Integer put(Integer key, Integer value) {
        super.remove(key);
        super.put(key, value);
        return null;
    }

    protected boolean removeEldestEntry(Map.Entry eldest) {
        return size() > MAX_CHCHE_SIZE;
    }


}
