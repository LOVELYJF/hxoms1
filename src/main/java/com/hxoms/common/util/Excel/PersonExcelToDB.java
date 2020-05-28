package com.hxoms.common.util.Excel;

import com.hxoms.common.util.JDBC.JDBC;
import com.hxoms.common.utils.UUIDGenerator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

public class PersonExcelToDB {

    static List<Map<String, Object>> maps = new ArrayList<>();

    public static void main(String[] orgs) {
        /*findA01List();
        System.out.println(maps);*/
        String name = "安　          伟";
        String newName = name.replaceAll("　", "").replace(" ","");
        System.out.println(newName);
    }

    /**
     * 导入Excel的内容导入到数据库，因为数据较多，所以用JDBC
     *
     * @return
     */
    public static boolean ExcelToDB(InputStream ins, String dataSource, String importPerson) {

        boolean a = false;

        List<Map<String, Object>> list = readExcel(ins);

        if (list != null && list.size()>0){

            /**
             * 查询A01里面的数据，放在List<Map<String,Object>>  maps公有变量中，方便查询取值
             */
            findA01List();

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");

            Date data1 = new Date();

            String dateDB = formatter.format(data1);

            System.err.println("开始执行---------------------" + formatter.format(data1));

            if (list != null && list.size() > 0) {

                //连接数据库
                Connection conn = JDBC.getConnection();

                try {
                    conn.setAutoCommit(false);

                    PreparedStatement ps = null;

                    PreparedStatement ps2 = null;
                    /**
                     * ID 随机生成
                     * A0183身份证号码
                     * ZWQFJG签发机关
                     * QFRQ签发日期
                     * YXQZ有效期至
                     * DQZT当前状态，首次默认为取出状态
                     */
                    String sql = "insert into cf_certificate" +
                            "(ID,A0184,NAME,SEX,CSRQ,GJ,ZJLX,ZJHM,ZWCSDD,QFRQ,YXQZ,DQZT) " +
                            "values (?,?,?,?,?,?,?,?,?,?,?,?)";

                    String sql2 = "insert into oms_entryexit_record (ID,IMPORT_TIME,IMPORT_PERSON,OGA_MODE," +
                            "DATA_SOURCE,A0100,OGE_STATUS,NAME,SEX,BIRTH_DATE,NATIONALITY,ID_TYPE,ID_NUMBER," +
                            "DESTINATION,ENTRACE_EXIT,OGE_DATE,VALID_UNTIL,VISITING_TASKS)values(" +
                            "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

                    ps = conn.prepareStatement(sql);

                    ps2 = conn.prepareStatement(sql2);

                    for (int i = 0; i < list.size(); i++) {
                        //如果结果集包含"境"字，就添加到出国境记录表，如果不包含，就添加到证照表里面
                        if (list.get(i).get("IDCard").toString().contains("境")) {

                            String name = list.get(i).get("name").toString();
                            String sex  = list.get(i).get("sex").toString();
                            String csny = list.get(i).get("csny").toString();
                            String A0100 = findA0100ByName(maps,name,sex,csny.substring(0,6));
                            ps2.setString(1, UUIDGenerator.getPrimaryKey());

                            ps2.setString(2,dateDB);

                            ps2.setString(3,importPerson);

                            ps2.setString(4,"******");

                            ps2.setString(5,dataSource);

                            ps2.setString(6,A0100);

                            ps2.setInt(7,1);

                            ps2.setString(8, list.get(i).get("name").toString());

                            ps2.setString(9,list.get(i).get("sex").toString());

                            ps2.setString(10,list.get(i).get("csny").toString());

                            ps2.setString(11,list.get(i).get("gj").toString());

                            ps2.setInt(12,1);

                            ps2.setString(13,list.get(i).get("cardNum").toString());

                            ps2.setString(14,list.get(i).get("csd").toString());

                            ps2.setString(15,list.get(i).get("qfdw").toString());

                            ps2.setString(16,list.get(i).get("qfrq").toString());

                            ps2.setString(17,list.get(i).get("yxqz").toString());

                            ps2.setString(18,"出入境管理");

                            ps2.addBatch();

                        } else {

                            String sex = (String) list.get(i).get("sex");

                            int sexInt = 1;

                            if (sex.equals("男")) {

                                sexInt = 1;

                            } else {

                                sexInt = 2;

                            }

                            ps.setString(1, UUIDGenerator.getPrimaryKey());

                            ps.setString(2, (String) list.get(i).get("IDCard"));

                            ps.setString(3, (String) list.get(i).get("name"));

                            ps.setInt(4, sexInt);

                            ps.setString(5, (String) list.get(i).get("csny"));

                            ps.setString(6, (String) list.get(i).get("gj"));

                            ps.setString(7, (String) list.get(i).get("zjlx"));

                            ps.setString(8, (String) list.get(i).get("cardNum"));

                            ps.setString(9, (String) list.get(i).get("csd"));

                            ps.setString(10, (String) list.get(i).get("qfrq"));

                            ps.setString(11, (String) list.get(i).get("yxqz"));

                            ps.setInt(12, 1);

                            ps.addBatch();

                        }

                        if (i % 20 == 0) {

                            ps.executeBatch();

                            ps2.executeBatch();

                            conn.commit();

                            ps.clearBatch();

                            ps2.clearBatch();

                            a = true;
                        }
                    }

                    ps.executeBatch();

                    ps2.executeBatch();

                    conn.commit();

                    ps.clearBatch();

                    ps2.clearBatch();

                    a = true;

                } catch (SQLException e) {
                    e.printStackTrace();
                    e.getErrorCode();
                    e.getNextException();
                }
            } else {
                return false;
            }

            Date data2 = new Date();

            System.err.println("结束时间---------------------" + formatter.format(data2));

            System.out.println("总用时" + (data2.getTime() - data1.getTime()) / 1000 + "秒");

        }

        maps.clear();

        return a;

    }


    static String[] zd = {"IDCard", "name", "sex", "csny", "gj", "zjlx", "cardNum", "csd", "qfdw", "qfrq", "yxqz"};

    static String[] jl = {"status", "name", "sex", "csny", "gj", "zjlx", "cardNum", "toLocation", "crka", "crjrq", "crjsj"};

    /**
     * 导入Excel的内容导入到数据库，因为数据较多，所以用JDBC
     * 读取到Excel表格的数据
     *
     * @return
     */
    public static List<Map<String, Object>> readExcel(InputStream ins) {

        List<Map<String, Object>> list = new ArrayList<>();

        // 工作表
        Workbook workbook = null;

        try {

            workbook = WorkbookFactory.create(ins);

            // 表个数。
            int numberOfSheets = workbook.getNumberOfSheets();

            for (int i = 0; i < numberOfSheets; i++) {

                Sheet sheet = workbook.getSheetAt(i);
                // 行数。
                int rowNumbers = sheet.getLastRowNum() + 1;

                // Excel第一行。
                Row temp = sheet.getRow(0);

                if (temp == null) {
                    //跳出当次循环，继续下一次循环
                    continue;
                }

                //列数
                int cells = temp.getPhysicalNumberOfCells();

                Row rs = sheet.getRow(0);
                if (rs.getCell(0).toString().equals("证件出入境批量查询加过信息")){
                    return list;
                }
                for (int row = 0; row < rowNumbers; row++) {

                    Map<String, Object> map = new HashMap<String, Object>();

                    Row r = sheet.getRow(row);

                    if (r.getCell(2).toString() == null || "".equals(r.getCell(2).toString())) {
                        //跳出当次循环，继续下一次循环
                        continue;
                    }
                    if (r.getCell(2).toString().equals("公民身份号码")) {
                        continue;
                    }
                    if (r.getCell(2).toString().equals("出入境状态")) {
                        continue;
                    }
                    /*if (r.getCell(2).toString().contains("境")) {
                        continue;
                    }*/

                    for (int col = 2; col < cells; col++) {


                        map.put(zd[col - 2], r.getCell(col).toString());

                    }
                    list.add(map);
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return list;
    }

    /**
     * 查询人员的基本信息方便查询取值
     */
    public static void findA01List() {
        String sql = "SELECT a0100,a0101,A0107, A0184,(case WHEN " +
                "a0104=1 THEN '男' WHEN  a0104=2 THEN '女' END)" +
                "as sex FROM a01";


        try {
            Connection conn = JDBC.getConnection();

            conn.setAutoCommit(false);

            Statement stat = conn.createStatement();

            ResultSet rs = stat.executeQuery(sql);

            while (rs.next()) {
                Map<String, Object> dataMap = new HashMap<>();
                dataMap.put("A0100", rs.getString(1));
                dataMap.put("A0101", rs.getString(2).replace("　","").replace(" ",""));
                dataMap.put("A0107", rs.getString(3));
                dataMap.put("A0184", rs.getString(4));
                dataMap.put("sex", rs.getString(5));
                maps.add(dataMap);
            }
            /*stat.close();
            rs.close();
            conn.close();*/
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static String findA0100ByName(List<Map<String,Object>> list,String name,String sex,String A0107){

        String A0100 = null;

        for (int i= 0;i<list.size();i++) {
            if (list.get(i).get("A0101").toString().replace(" ","").equals(name) && list.get(i).get("sex").equals(sex) && list.get(i).get("A0107").equals(A0107)) {
                A0100 = list.get(i).get("A0100").toString();
            }
        }
        return A0100;

    }

}
