package com.hxoms.modules.dataCapture.extracttable;

import com.hxoms.modules.dataCapture.entity.DataSource;
import com.hxoms.modules.dataCapture.entity.DynamicData;
import com.hxoms.modules.dataCapture.sqlconfig.Datasourceinfo;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.util.List;
import java.util.Map;
@Component
public class ExtractData {


    // ++++++++++++++++++++++++++++++++++ a01

    public List<Map<String,Object>>  getTargetA01(DataSource dataSource,Object... values){


        Connection connection = Datasourceinfo.getConnection(dataSource);
        List<Map<String, Object>> targetlist = Datasourceinfo.executeQueryList(connection, DynamicData.orcalSqlA01,values);

        System.out.println(targetlist.size());

        return  targetlist;
    }

    public int  getTargetA01Count(DataSource dataSource,Object... values){

        String   a01Countsql = "select count(1) as num  from a01";

        Connection connection = Datasourceinfo.getConnection(dataSource);
        List<Map<String, Object>> targetlist = Datasourceinfo.executeQueryList(connection,a01Countsql,values);

        if(targetlist!=null && targetlist.size() ==1){

           if(targetlist.get(0).get("num").getClass() ==Integer.class) {

               return (Integer) targetlist.get(0).get("num");
           }

        }

        return 0;

    }

    // ++++++++++++++++++++++++++++++++++ a02

    public List<Map<String,Object>>  getTargetA02(DataSource dataSource,Object... values){


        Connection connection = Datasourceinfo.getConnection(dataSource);
        List<Map<String, Object>> targetlist = Datasourceinfo.executeQueryList(connection, DynamicData.orcalSqlA02,values);

        System.out.println(targetlist.size());

        return  targetlist;
    }

    public int  getTargetA02Count(DataSource dataSource,Object... values){

        String   a02CountSql = "select count(1) as num  from a02";

        Connection connection = Datasourceinfo.getConnection(dataSource);
        List<Map<String, Object>> targetlist = Datasourceinfo.executeQueryList(connection,a02CountSql,values);

        if(targetlist!=null && targetlist.size() ==1){

            if(targetlist.get(0).get("num").getClass() ==Integer.class) {

                return (Integer) targetlist.get(0).get("num");
            }

        }

        return 0;

    }

    // ++++++++++++++++++++++++++++++++++ a05

    public List<Map<String,Object>>  getTargetA05(DataSource dataSource,Object... values){


        Connection connection = Datasourceinfo.getConnection(dataSource);
        List<Map<String, Object>> targetlist = Datasourceinfo.executeQueryList(connection, DynamicData.orcalSqlA05,values);

        System.out.println(targetlist.size());

        return  targetlist;
    }

    public int  getTargetA05Count(DataSource dataSource,Object... values){

        String   a05CountSql = "select count(1) as num  from a05";

        Connection connection = Datasourceinfo.getConnection(dataSource);
        List<Map<String, Object>> targetlist = Datasourceinfo.executeQueryList(connection,a05CountSql,values);

        if(targetlist!=null && targetlist.size() ==1){

            if(targetlist.get(0).get("num").getClass() ==Integer.class) {

                return (Integer) targetlist.get(0).get("num");
            }

        }

        return 0;

    }

    // ++++++++++++++++++++++++++++++++++ a06

    public List<Map<String,Object>>  getTargetA06(DataSource dataSource,Object... values){


        Connection connection = Datasourceinfo.getConnection(dataSource);
        List<Map<String, Object>> targetlist = Datasourceinfo.executeQueryList(connection, DynamicData.orcalSqlA06,values);

        System.out.println(targetlist.size());

        return  targetlist;
    }

    public int  getTargetA06Count(DataSource dataSource,Object... values){

        String   a06CountSql = "select count(1) as num  from a06";

        Connection connection = Datasourceinfo.getConnection(dataSource);
        List<Map<String, Object>> targetlist = Datasourceinfo.executeQueryList(connection,a06CountSql,values);

        if(targetlist!=null && targetlist.size() ==1){

            if(targetlist.get(0).get("num").getClass() ==Integer.class) {

                return (Integer) targetlist.get(0).get("num");
            }

        }

        return 0;

    }

    // ++++++++++++++++++++++++++++++++++ a08

    public List<Map<String,Object>>  getTargetA08(DataSource dataSource,Object... values){


        Connection connection = Datasourceinfo.getConnection(dataSource);
        List<Map<String, Object>> targetlist = Datasourceinfo.executeQueryList(connection, DynamicData.orcalSqlA08,values);

        System.out.println(targetlist.size());

        return  targetlist;
    }

    public int  getTargetA08Count(DataSource dataSource,Object... values){

        String   a08CountSql = "select count(1) as num  from a08";

        Connection connection = Datasourceinfo.getConnection(dataSource);
        List<Map<String, Object>> targetlist = Datasourceinfo.executeQueryList(connection,a08CountSql,values);

        if(targetlist!=null && targetlist.size() ==1){

            if(targetlist.get(0).get("num").getClass() ==Integer.class) {

                return (Integer) targetlist.get(0).get("num");
            }

        }

        return 0;

    }



    // ++++++++++++++++++++++++++++++++++ a14

    public List<Map<String,Object>>  getTargetA14(DataSource dataSource,Object... values){


        Connection connection = Datasourceinfo.getConnection(dataSource);
        List<Map<String, Object>> targetlist = Datasourceinfo.executeQueryList(connection, DynamicData.orcalSqlA14,values);

        System.out.println(targetlist.size());

        return  targetlist;
    }

    public int  getTargetA14Count(DataSource dataSource,Object... values){

        String   a14CountSql = "select count(1) as num  from a14";

        Connection connection = Datasourceinfo.getConnection(dataSource);
        List<Map<String, Object>> targetlist = Datasourceinfo.executeQueryList(connection,a14CountSql,values);

        if(targetlist!=null && targetlist.size() ==1){

            if(targetlist.get(0).get("num").getClass() ==Integer.class) {

                return (Integer) targetlist.get(0).get("num");
            }

        }

        return 0;

    }



    // ++++++++++++++++++++++++++++++++++ a15

    public List<Map<String,Object>>  getTargetA15(DataSource dataSource,Object... values){


        Connection connection = Datasourceinfo.getConnection(dataSource);
        List<Map<String, Object>> targetlist = Datasourceinfo.executeQueryList(connection, DynamicData.orcalSqlA15,values);

        System.out.println(targetlist.size());

        return  targetlist;
    }

    public int  getTargetA15Count(DataSource dataSource,Object... values){

        String   a15CountSql = "select count(1) as num  from a15";

        Connection connection = Datasourceinfo.getConnection(dataSource);
        List<Map<String, Object>> targetlist = Datasourceinfo.executeQueryList(connection,a15CountSql,values);

        if(targetlist!=null && targetlist.size() ==1){

            if(targetlist.get(0).get("num").getClass() ==Integer.class) {

                return (Integer) targetlist.get(0).get("num");
            }

        }

        return 0;

    }



    // ++++++++++++++++++++++++++++++++++ a17

    public List<Map<String,Object>>  getTargetA17(DataSource dataSource,Object... values){


        Connection connection = Datasourceinfo.getConnection(dataSource);
        List<Map<String, Object>> targetlist = Datasourceinfo.executeQueryList(connection, DynamicData.orcalSqlA17,values);

        System.out.println(targetlist.size());

        return  targetlist;
    }

    public int  getTargetA17Count(DataSource dataSource,Object... values){

        String   a17CountSql = "select count(1) as num  from a17";

        Connection connection = Datasourceinfo.getConnection(dataSource);
        List<Map<String, Object>> targetlist = Datasourceinfo.executeQueryList(connection,a17CountSql,values);

        if(targetlist!=null && targetlist.size() ==1){

            if(targetlist.get(0).get("num").getClass() ==Integer.class) {

                return (Integer) targetlist.get(0).get("num");
            }

        }

        return 0;

    }



    // ++++++++++++++++++++++++++++++++++ a36

    public List<Map<String,Object>>  getTargetA36(DataSource dataSource,Object... values){


        Connection connection = Datasourceinfo.getConnection(dataSource);
        List<Map<String, Object>> targetlist = Datasourceinfo.executeQueryList(connection, DynamicData.orcalSqlA36,values);

        System.out.println(targetlist.size());

        return  targetlist;
    }

    public int  getTargetA36Count(DataSource dataSource,Object... values){

        String   a36CountSql = "select count(1) as num  from a36";

        Connection connection = Datasourceinfo.getConnection(dataSource);
        List<Map<String, Object>> targetlist = Datasourceinfo.executeQueryList(connection,a36CountSql,values);

        if(targetlist!=null && targetlist.size() ==1){

            if(targetlist.get(0).get("num").getClass() ==Integer.class) {

                return (Integer) targetlist.get(0).get("num");
            }

        }

        return 0;

    }


    public List<Map<String,Object>>  getMasterA01(){

        DataSource dataSource = new DataSource();
        dataSource.setDatabasetype("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3307/hxoim512?useUnicode=true&allowMultiQueries=true&characterEncoding=utf8");
        dataSource.setUserName("root");
        dataSource.setPassWord("123456");
        Connection connection = Datasourceinfo.getConnection(dataSource);
        List<Map<String, Object>> masterlist = Datasourceinfo.executeQueryList(connection, DynamicData.mysqlSqlA01,new Object[]{0,1500});

        System.out.println(masterlist.size());

        return  masterlist;

    }
}
