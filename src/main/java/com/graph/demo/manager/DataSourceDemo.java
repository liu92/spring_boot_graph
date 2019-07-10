package com.graph.demo.manager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @ClassName DataSourceDemo
 * @Description TODO
 * @Author liuwanlin
 * @Date 2019/7/9 10:28
 **/
public class DataSourceDemo {
    private static Connection conn = null;
    private static Statement stmt = null;
    private static ResultSet res = null;
    public static void main(String[] args) throws SQLException {
        String name = "sd";
        int age = 23;
        conn = ConnectionManager.getConnection();
        stmt = conn.createStatement();
        String sql = "SELECT * FROM c_user WHERE name =' "+name+" ' AND age=' "+age+" '";
        res = stmt.executeQuery(sql);
        if(res.next()){
            System.out.println("查询成功");
        }else {
            System.out.println("查询失败");
        }
    }
}
