package com.ley.java.concurrent;

public class A {
    String name = "a";

    String go() {
        return "- function in A";
    }
}

class B extends A {

    String name = "b";

    String go() {
        return "- function in B";
    }

    public static void main(String[] args) {
        A a = new B();
        System.out.println(a.name + a.go());
    }
}
