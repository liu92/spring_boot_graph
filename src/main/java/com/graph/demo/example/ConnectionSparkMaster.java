package com.graph.demo.example;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;

/**
 * 连接 spark 集群测试
 * @ClassName: ConnectionSparkMaster
 * @Description:
 * @Author: lin
 * @Date: 2019/5/30 22:38
 * History:
 * @<version> 1.0
 */
public class ConnectionSparkMaster {

//    public static final String MASTER = "spark://192.168.199.117:7077";
public static final String MASTER = "spark://192.168.199.117:7077";
    public static void main(String[] args) {
        SparkConf sparkConf = new SparkConf().setAppName("demo").setMaster(MASTER);

        JavaSparkContext sc = new JavaSparkContext(sparkConf);

        System.out.println(sc);
        sc.stop();
    }
}
