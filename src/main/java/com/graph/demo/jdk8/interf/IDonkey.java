package com.graph.demo.jdk8.interf;

/**
 * 测试jdk 1.8 特性
 * 接口的默认方法和静态方法
 */
public interface IDonkey {
    default void  run(){
        System.out.println("IDonkey run");
    }

    //还有一个特性是可以，接口中可以声明静态方法
    static void test(){
        System.out.println("static test");
    }
}
