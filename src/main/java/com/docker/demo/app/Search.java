package com.docker.demo.app;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * @ClassName: Search
 * @Description:
 * @Author: lin
 * @Date: 2019/6/5 0:26
 * History:
 * @<version> 1.0
 */
public class Search {
    public static void main(String[] args) {
        SparkSession sparkSession = creat();

        User u = new User();

        u.setUserName("xxl");
        u.setComment("nice-job");

        //write 查询数据
        List<User> aa = Arrays.asList(u);
        Dataset<User> ds1 = sparkSession.createDataset(aa, Encoders.bean(User.class));
        ds1.write()
                .format("org.apache.spark.sql.cassandra")
                .options(new HashMap<String, String>() {
                    {
                        put("keyspace", "busuanzi_org");
                        put("table", "top_n_url");
                    }
                }).mode("append").save();
        ds1.show();
        System.out.println("Cassandra Save success!");

        //read 读取数据
        Dataset<Row> ds = sparkSession.read()
                .format("org.apache.spark.sql.cassandra")
                .options(new HashMap<String, String>(){
                    {
                        put("keyspace", "busuanzi_org");
                        put("table", "top_n_url");
                    }
                }).load();
        ds.show();
        System.out.println("Cassandra Read success!");

        ds.createOrReplaceTempView("dsv");

        //distinct 去重
        ds.select("username", "projects", "comment").distinct().show();
        System.out.println("Spark distinct success!");
        sparkSession.stop();

    }
    
    
    public static SparkSession creat(){
        SparkSession sparkSession = SparkSession.builder()
                .appName("Java Spark SQL basic example")
                //            .config("spark.some.config.option", "some-value")
                .config("spark.cassandra.connection.host", "192.168.199.117")
//                .config("spark.cassandra.auth.username", "bu")
//                .config("spark.cassandra.auth.password", "busuan ")
                .config("spark.cassandra.connection.port", "9042")
                .getOrCreate();
        return  sparkSession;
    }
}
