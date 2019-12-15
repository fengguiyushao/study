package com.robinzhou.simpleframework.util.bean;

import java.lang.reflect.Method;
import java.util.Objects;

public class Handler {
    private Class<?> controllerClass;

    private Method actionMethod;

    public Handler(Class<?> controllerClass, Method actionMethod) {
        this.controllerClass = controllerClass;
        this.actionMethod = actionMethod;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Handler)) return false;
        Handler handler = (Handler) o;
        return Objects.equals(controllerClass, handler.controllerClass) &&
                Objects.equals(actionMethod, handler.actionMethod);
    }

    @Override
    public int hashCode() {
        return Objects.hash(controllerClass, actionMethod);
    }

    public Class<?> getControllerClass() {
        return controllerClass;
    }

    public void setControllerClass(Class<?> controllerClass) {
        this.controllerClass = controllerClass;
    }

    public Method getActionMethod() {
        return actionMethod;
    }

    public void setActionMethod(Method actionMethod) {
        this.actionMethod = actionMethod;
    }
}
