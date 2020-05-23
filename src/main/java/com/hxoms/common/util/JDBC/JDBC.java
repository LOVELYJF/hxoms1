package com.hxoms.common.util.JDBC;

import java.sql.*;

public class JDBC {

    //public static final String URL = "jdbc:mysql://www.24hbs.cn:3306/hxoms";

   // public static final String USER = "hxoms";

    //public static final String PASSWORD = "Hxyz-2020#";

    public static final String URL = "jdbc:mysql://localhost:3306/hxoms";

    public static final String USER = "root";

    public static final String PASSWORD = "12345abcde";

    public static void main(String[] args) {
        try {
            //1.加载驱动程序
            //Class.forName("com.mysql.jdbc.Driver");
            //2. 获得数据库连接
            //Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            Connection conn = getConnection();
            //3.操作数据库，实现增删改查
            Statement stmt = conn.createStatement();
            String sql = "select * from cf_certificate";
            String sql2 = "insert into cf_certificate(ID,A0184)VALUES(?,?)";
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
