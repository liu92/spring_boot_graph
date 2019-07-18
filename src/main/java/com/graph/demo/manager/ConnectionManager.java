package com.graph.demo.manager;

import org.springframework.jdbc.support.JdbcUtils;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

/**
 * @ClassName ConnectionManager
 * @Description TODO
 * @Author liuwanlin
 * @Date 2019/7/9 9:38
 **/
public class ConnectionManager {
    private static String driverClass;
    private static String url;
    private static String user;
    private static String password;

    private Object s;

    static {
        try {
          Properties properties = new Properties();

            //构造输入流
            /**
             * 相对路径： . 这个代表当前目录。当前目录本质上是java命令运行的目录
             * java项目中：eclipse中,当前目录指向项目的跟目录
             * web项目中：当前目录只想%tomcat%/bin目录
             * 结论：
             * 1).在web项目中不能使用相对路径
             * 2).不能使用ServletContext读取文件（Java项目中无法使用，web项目可行）
             * 3).使用类路径的方式读取配置文件
             *      3.1获取类的对象
             *          Class clazz = 当前项目的类.class
             *
             *      3.2调用类的路径方法
             *          clazz.getResourceAsStream("/配置文件")
             *          这个斜杠代表类路径的根目录
             */
            //1)获取类的对象（使用当前类）
            Class clazz = ConnectionManager.class;
            //2)使用类路径的读取方法去读取文件
            /**
             * 这个斜杠：代表项目类路径的根目录。
             * 什么是类路径：查找类的目录/路径
             *      java项目下：类路径的根目录，指向项目的bin目录
             *      Web项目下：类路径的跟目录，指向WEB-INF/classes目录下
             * 只要把配置文件放在src目录的根目录下，那么这些文件就自动拷贝到项目的类路径跟目录下
             */
          InputStream in = clazz.getResourceAsStream("resource/config/config.properties");

          properties.load(in);
          driverClass = properties.getProperty("datasource.classname");
          url = properties.getProperty("datasource.url");
          user = properties.getProperty("datasource.user");
          password = properties.getProperty("datasource.password");
          System.out.println("url: "+url+" user: "+user+" password: "+password+" driverClass: "+driverClass);
          Class.forName(driverClass);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    /**
     *
     * @return
     */
    public static Connection getConnection(){
        try {
            Connection connection = DriverManager.getConnection(url,user,password);
            return connection;
        } catch (SQLException e) {
            e.printStackTrace();
            throw  new RuntimeException();
        }
    }

    public static void close(Statement statement, Connection connection){
        if(statement != null){
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException();
            }
        }
        if(connection != null){
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public  static  void  close(ResultSet rs, Statement stmt, Connection conn){
        if(rs!=null) {
            try {
                rs.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        if(stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }

        if(conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
    }

}
