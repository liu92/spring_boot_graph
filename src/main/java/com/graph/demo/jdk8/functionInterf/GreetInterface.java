package com.graph.demo.jdk8.functionInterf;

/**
 * jdk1.8 最大的变化是引入了函数式思想。
 * 函数式思想 也就是说函数可以作为另一个函数的参数，
 * 函数式接口，要求接口中有且仅有一个抽象方法，因此经常使用的Runnable, Callable就是典型的函数式接口。
 * 可以使用@FunctionalInterface注解，声明一个接口是函数式接口。如果一个接口满足函数式接口的定义，
 * 会默认转换成函数式接口。但是，最好是使用@FunctionalInterface注解显式声明。这是因为函数式接口比较脆弱，
 * 如果开发人员无意间新增了其他方法，就破坏了函数式接口的要求，如果使用注解@FunctionalInterface，
 * 开发人员就会知道当前接口是函数式接口，就不会无意间破坏该接口
 */
@FunctionalInterface
public interface GreetInterface {
    //如果不加这个方法 ，注解就会报错
    void message(String message);

    //void test(); 函数式接口中 不能有多个抽象方法，但是可以有多个默认的方法
    //该接口只有一个抽象方法，并且使用注解显式声明。但是，函数式接口要求只有一个抽象方法却可以拥有若干个默认方法的（实例方法）

    default void run(){
        System.out.println("run");
    }

    default void po(){
        System.out.println("po");
    }

}
