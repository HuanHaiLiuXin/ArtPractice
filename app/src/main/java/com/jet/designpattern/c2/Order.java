package com.jet.designpattern.c2;

/**
 * 作者:幻海流心
 * GitHub:https://github.com/HuanHaiLiuXin
 * 邮箱:wall0920@163.com
 * 2018/6/29 16:47
 */
public class Order {
    private int orderId = 1000;
    private String orderName;
    static {
        System.out.println("Order:我是静态代码块1!");
    }
    {
        orderId = 1001;
        orderName = "AA";
        System.out.println("Order:我是非静态代码块1");
    }
    {
        orderId = 1002;
        orderName = "BB";
        System.out.println("Order:我是非静态代码块2");
    }

    @Override
    public String toString() {
        return "Order [orderId=" + orderId + ", orderName=" + orderName + "]";
    }

    public void doIt(){
        System.out.println("Order:doIt");
    }

    public static String s1 = "staticStringS1";
    public static void printS1(){
        System.out.println(s1);
    }

    public static class ChildClass{
        public static Order instance = new Order();
        static {
            System.out.println("ChildClass static");
        }
    }

    static {
        System.out.println("Order:我是静态代码块2!");
    }
    static {
        System.out.println("Order:我是静态代码块3!");
    }
    public static Order gainOrder(){
        return ChildClass.instance;
    }
}
