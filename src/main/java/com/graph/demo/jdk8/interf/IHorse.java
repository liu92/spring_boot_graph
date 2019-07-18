package com.graph.demo.jdk8.interf;

/**
 * 测试jdk 1.8 特性
 * 接口的默认方法和静态方法
 */
public interface IHorse {
    default void  run(){
        System.out.println("IHorse run");
    }
}
