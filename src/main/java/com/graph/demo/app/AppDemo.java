package com.graph.demo.app;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

/**
 * @ClassName AppDemo
 * @Description TODO
 * @Author liuwanlin
 * @Date 2019/5/29 15:06
 **/
public class AppDemo {
    public static void main(String[] args) {

        SparkSession sparkSession = SparkSession.builder().appName("spark-test").master("local[3]").getOrCreate();
        Dataset<Row> result = sparkSession.read().json("src/main/resources/employees.json");
        result.show();
        result.printSchema();
        sparkSession.stop();
    }
}
