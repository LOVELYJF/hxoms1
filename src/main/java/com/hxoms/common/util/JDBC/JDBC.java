package com.hxoms.common.util.JDBC;

import org.springframework.beans.factory.annotation.Value;

import java.sql.*;

public class JDBC {

    /*public static final String URL = "jdbc:mysql://127.0.0.1:3306/hxoms";

    public static final String USER = "root";

    public static final String PASSWORD = "12345abcde";*/

    public static final String URL = "jdbc:mysql://www.24hbs.com:3306/hxoms";

    public static final String USER = "hxoms";

    public static final String PASSWORD = "Hxyz-2020#";

    /*@Value("${spring.datasourece.druid.url}")
    private static  String URL;

    @Value("${spring.datasourece.druid.username}")
    public static String USER;


    @Value("${spring.datasourece.druid.password}")
    public static String PASSWORD;*/

    public static void main(String[] args) {
        try {
            //1.加载驱动程序
            //Class.forName("com.mysql.jdbc.Driver");
            //2. 获得数据库连接
            //Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            Connection conn = getConnection();
            //3.操作数据库，实现增删改查
            Statement stmt = conn.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private static Connection conn = null;
    static {
        try {
            //1.加载驱动程序
            Class.forName("com.mysql.jdbc.Driver");
            //2.获得数据库的连接
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //将获得的数据库与java的链接返回（返回的类型为Connection）
    public static Connection getConnection() {
        return conn;
    }

    //关闭数据库
    public static void closeConn(Connection conn){
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
