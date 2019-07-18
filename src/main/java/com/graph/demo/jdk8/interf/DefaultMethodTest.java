package com.graph.demo.jdk8.interf;

/**
 * @ClassName DefaultMethodTest
 * @Description TODO
 * @Author liuwanlin
 * @Date 2019/7/18 13:10
 **/
public class DefaultMethodTest implements IDonkey,IHorse {

    public static void main(String[] args) {
        DefaultMethodTest defaultMethodTest = new DefaultMethodTest();
        IDonkey.test();
    }

    //重写方法，如果不重写，并且不绑定方法 不指定以哪个接口中的默认方法为准，那么在运行时候会报错
    @Override
    public void run() {
        IHorse.super.run();
    }
}
