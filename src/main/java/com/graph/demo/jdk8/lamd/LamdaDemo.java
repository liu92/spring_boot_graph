package com.graph.demo.jdk8.lamd;

import java.util.function.Function;

/**
 * @ClassName LamdaDemo
 * @Description TODO
 * @Author liuwanlin
 * @Date 2019/7/15 14:54
 **/
public class LamdaDemo {
    public static void main(String[] args) {
        String hello = "hello lambda ";
        Function<String, Void> func = (name)->{
            System.out.println(hello + name);
            return  null;
        };
        func.apply("ce");
    }
}
