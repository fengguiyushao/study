package com.robinzhou.simpleframework.util.helper;

public class HelperLoader {

    public static void init() {
        Class<?>[] classList = {ClassHelper.class, BeanHelper.class, IocHelper.class, ControllerHelper.class};
    }
}
