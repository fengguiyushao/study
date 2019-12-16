package com.robinzhou.simpleframework.bean;

import org.apache.commons.lang3.math.NumberUtils;

import java.util.Map;

public class Param {
    private Map<String, Object> paramMap;

    public Param(Map<String, Object> paramMap) {
        this.paramMap = paramMap;
    }

    public long getLong(String name) {
        return NumberUtils.toLong(paramMap.get(name).toString());
    }

    public Map<String, Object> getMap() {
        return paramMap;
    }
}
