package com.robinzhou.simpleframework.util.helper;

import com.robinzhou.simpleframework.util.ReflectionUtil;
import com.robinzhou.simpleframework.util.annotation.Inject;
import org.apache.commons.lang3.ArrayUtils;

import java.lang.reflect.Field;
import java.util.Map;

public class IocHelper {

    static {
        Map<Class<?>, Object> beanMap = BeanHelper.getBeanMap();
        for (Map.Entry<Class<?>, Object> beanEntry : beanMap.entrySet()) {
            Class<?> beanClass = beanEntry.getKey();
            Object beanInstance = beanEntry.getValue();

            Field[] beanFields = beanClass.getDeclaredFields();
            if(ArrayUtils.isNotEmpty(beanFields)) {
                for (Field beanField : beanFields) {
                    if(beanField.isAnnotationPresent(Inject.class)) {
                        Class<?> beanFieldClass = beanField.getType();
                        Object beanFieldInstance = beanMap.get(beanFieldClass);
                        if(beanFieldInstance != null) {
                            ReflectionUtil.setField(beanInstance, beanField, beanFieldInstance);
                        }
                    }
                }
            }
        }
    }
}
