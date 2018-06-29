package com.jet.designpattern.c2;

/**
 * 作者:幻海流心
 * GitHub:https://github.com/HuanHaiLiuXin
 * 邮箱:wall0920@163.com
 * 2018/6/29 20:45
 */
public class Test {
    public Test() {
        System.out.println("test constructor");
    }
    {
        System.out.println("test {}1");
    }
    Person person = new Person("Test");
    static{
        System.out.println("test static");
    }
    {
        System.out.println("test {}2");
    }
    public static void main(String[] args) {
        new MyClass();
    }
}
