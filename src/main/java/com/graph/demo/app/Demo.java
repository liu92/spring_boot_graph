package com.graph.demo.app;

import com.datastax.spark.connector.japi.rdd.CassandraJavaRDD;
import org.apache.spark.SparkConf;
import org.apache.spark.SparkContext;
import org.apache.spark.sql.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

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
//                .set("spark.cassandra.connection.host", "192.168.199.117")
//                .setMaster("local[12]").setAppName("searchTest");
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

        // 新建一个Spark上下文对象，第一个参数是将要连接到的Cluster地址，
        // 这里我们仅仅使用localhost来运行，所以可以简单设置为local[*]，*为1，2，3之类的数字。第二个只是一个显示参数，可以随意。
        SparkSession spark = SparkSession.builder()
                .appName("searchTest")
                .config("spark.cassandra.connection.host", "192.168.199.117")
                .master("local[1]")
                .getOrCreate();
        User user = new User();
        user.setName("driver");
        user.setComment("解决驱动问题");

        List<User> users = Arrays.asList(user);
        Dataset<User> dataset = spark.createDataset(users, Encoders.bean(User.class));
        dataset.write()
               .format("org.apache.spark.sql.cassandra")
               .options(new HashMap<String, String>() {
                   {
                       put("keyspace", "jgex");
                       put("table", "user");
                   }
               }).mode(SaveMode.Append).save();
            dataset.show();
        System.out.println("Cassandra Save success!");

        //read 读取数据
        Dataset<Row> ds = spark.read()
                .format("org.apache.spark.sql.cassandra")
                .options(new HashMap<String, String>(){
                    {
                        put("keyspace", "jgex");
                        put("table", "user");
                    }
                }).load();
        ds.show();
        System.out.println("Cassandra Read success!");

        ds.createOrReplaceTempView("dsv");
        spark.stop();
    }
}
