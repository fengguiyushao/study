package com.robinzhou.refactoring.firstsample;

public class Test {

    public static void main(String[] args) {
        A a = new A();
        System.out.println(B.class.isInstance(a));
        System.out.println(B.class.isInstance(new B()));
    }


    static class A {
        int a;
    }

    static class B extends A {

    }
}
