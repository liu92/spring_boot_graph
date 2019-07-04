package com.graph.demo.example;

/**
 * @ClassName Test
 * @Description TODO
 * @Author liuwanlin
 * @Date 2019/7/3 13:51
 **/
public class Test {
    static abstract class Human{

    }

    static class Man extends Human{

    }

    static class Woman extends Human{

    }

    public void sayHello(Human guy){
        System.out.println("hello,guy!");
    }

    public void sayHello(Man guy){
        System.out.println("hello,gentleman!");
    }

    public void sayHello(Woman guy){
        System.out.println("hello,lady!");
    }

    public static void main(String[] args) {
        Human man = new Man();
        Human woman = new Woman();
        Test sr = new Test();
        sr.sayHello(man);
        sr.sayHello(woman);
    }
}
