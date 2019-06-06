package com.graph.demo.app;

import com.datastax.spark.connector.japi.rdd.CassandraJavaRDD;
import org.apache.spark.SparkConf;
import org.apache.spark.SparkContext;
import static com.datastax.spark.connector.japi.CassandraJavaUtil.javaFunctions;

/**
 * @ClassName: Demo
 * @Description:
 * @Author: lin
 * @Date: 2019/6/5 0:36
 * History:
 * @<version> 1.0
 */
public class Demo {
    public static void main(String[] args) {
        SparkConf conf= new SparkConf(true).set("spark.cassandra.connection.host", "192.168.199.117");
        SparkContext sc = new SparkContext( conf);
        CassandraJavaRDD rdd = javaFunctions(sc).cassandraTable("test", "words");
        CassandraJavaRDD result = rdd.where("word in ('foo', 'fo2') and count > 1 and count < 8");
        result.foreach(row -> System.out.println(row));

    }
}
