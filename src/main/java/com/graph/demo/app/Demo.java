package com.graph.demo.app;

import com.datastax.spark.connector.japi.rdd.CassandraJavaRDD;
import org.apache.spark.SparkConf;
import org.apache.spark.SparkContext;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

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
//        SparkConf conf= new SparkConf(true)
//                .set("spark.cassandra.connection.host", "192.168.199.117").setMaster("local[12]").setAppName("searchTest");
//        SparkContext sc = new SparkContext( conf);
//        CassandraJavaRDD rdd = javaFunctions(sc).cassandraTable("test", "edgestore");
//        CassandraJavaRDD result = rdd.where("age >20 ");
//
//        result.foreach(row -> System.out.println(row));

//        SparkSession spark = SparkSession.builder()
//                .master("local[1]")
//                .appName("searchTest")
//                .config("spark.cassandra.connection.host", "192.168.199.117")
//                .getOrCreate();
//
//        Dataset<Row> load =
//                spark.read()
//                        .format("org.apache.spark.sql.cassandra")
//                        .option("keyspace", "test").option("table", "edgestore").load();
//
//        load.show(5,false);
    }
}
