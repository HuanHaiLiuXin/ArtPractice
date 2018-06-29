package com.jet.designpattern.c2;

/**
 * 作者:幻海流心
 * GitHub:https://github.com/HuanHaiLiuXin
 * 邮箱:wall0920@163.com
 * 2018/6/29 20:46
 */
public class MyClass extends Test {
    public MyClass() {
        System.out.println("myclass constructor");
    }
    {
        System.out.println("myclass {}1");
    }
    Person person = new Person("MyClass");
    static{
        System.out.println("myclass static");
    }
    {
        System.out.println("myclass {}2");
    }


}