package com.jet.designpattern.c2;

/**
 * 作者:幻海流心
 * GitHub:https://github.com/HuanHaiLiuXin
 * 邮箱:wall0920@163.com
 * 2018/6/29 20:45
 */
public class Person {
    {
        System.out.println("person {}1");
    }
    static{
        System.out.println("person static");
    }
    {
        System.out.println("person {}2");
    }
    public Person(String str) {
        System.out.println("person "+str);
    }
}
