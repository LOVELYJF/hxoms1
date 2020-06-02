package com.hxoms.modules.dataCapture.sqlconfig;

import com.hxoms.modules.dataCapture.entity.DataSource;
import com.hxoms.modules.dataCapture.entity.DynamicData;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Datasourceinfo {


    // 写一个返回连接的方法
    public static Connection getConnection(DataSource dataSource) {
        try {
            Class.forName(dataSource.getDatabasetype());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        Connection connection = null;
        try {
            connection = DriverManager.getConnection(dataSource.getUrl(), dataSource.getUserName(), dataSource.getPassWord());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }


    public static void close(ResultSet rs, Statement ps, Connection ct) {
        if (rs != null) {
            try {
                rs.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            rs = null;
        }
        if (ps != null) {
            try {
                ps.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            ps = null;
        }
        if (ct != null) {
            try {
                ct.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            ct = null;
        }
    }

    public static List<Map<String, Object>> executeQueryList(Connection connection,String sql, Object[] parameters) {
        List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {


            // 创建ps对象，得到sql语句对象
            preparedStatement = connection.prepareStatement(sql);
            // 如果parameters不为null，才赋值
            if (parameters != null && parameters.length>0) {
                for (int i = 0; i < parameters.length; i++) {
                    if(parameters[i].getClass()==String.class){
                        preparedStatement.setString(i + 1, (String)parameters[i]);
                    }else if(parameters[i].getClass()==Integer.class){

                        preparedStatement.setInt(i + 1, (Integer) parameters[i]);
                    }



                }
            }
            resultSet = preparedStatement.executeQuery();
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = resultSet.getMetaData().getColumnCount();
            while (resultSet.next()) {
                //System.out.println("开始循环：" + ((new SimpleDateFormat("yyyy.MM.dd hh:mm:ss.SSS")).format(new Date())));
                Map<String, Object> mapData = new LinkedHashMap<String, Object>();
                for (int i = 1; i <= columnCount; i++) {
                    String columnName = metaData.getColumnName(i).toLowerCase();
                    String columnTypeName = metaData.getColumnTypeName(i);
                    Object columnValue =null;
                    if("DATE".equals(columnTypeName)){
                        columnValue = resultSet.getDate(i);
                    }
                    else if("NUMBER".equals(columnTypeName)){
                        columnValue=resultSet.getInt(i);
                    }
                    else {
                        columnValue = resultSet.getString(i);
                    }

                    mapData.put(columnName, columnValue);
                }
                mapList.add(mapData);
            }
        } catch (Exception e) {
            e.printStackTrace();

            throw new RuntimeException(e.getMessage());
        } finally {
            close(resultSet, preparedStatement, connection);
        }
        return mapList;
    }






    public static void main(String[] args) {

//        #      username: hxoms
//#      password: Hxyz-2020#
//#      url: jdbc:mysql://www.24hbs.cn:3306/hxoms?useUnicode=true&allowMultiQueries=true&characterEncoding=utf8
//        username: root
//        #      username: HXOMS
//#      password: 12345abcde
//#      url: jdbc:dm://www.24hbs.com:5236/HXOMS
//#      driver-class-name: dm.jdbc.driver.DmDriver

        DataSource dataSource = new DataSource();
        dataSource.setDatabasetype("dm.jdbc.driver.DmDriver");
        dataSource.setUrl("jdbc:dm://www.24hbs.com:5236/HXOMS");
        dataSource.setUserName("HXOMS");
        dataSource.setPassWord("12345abcde");
        Connection connection =  getConnection(dataSource);
        List<Map<String, Object>> list = executeQueryList(connection,DynamicData.mysqlSqlA01,new Object[]{0,1500});

        System.out.println(list.size());


        DataSource dataSource1 = new DataSource();
        dataSource1.setDatabasetype("oracle.jdbc.driver.OracleDriver");
        dataSource1.setUrl("jdbc:oracle:thin:@127.0.0.1:1521:orcl");
        dataSource1.setUserName("newhxdba");
        dataSource1.setPassWord("123456");
        Connection connection1 =  getConnection(dataSource1);
        List<Map<String, Object>> list1 = executeQueryList(connection1, DynamicData.orcalSqlA01,new Object[]{1500,0});

        System.out.println(list1.size());

    }

}
