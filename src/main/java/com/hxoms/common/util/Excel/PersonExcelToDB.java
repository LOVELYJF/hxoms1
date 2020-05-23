package com.hxoms.common.util.Excel;

import com.hxoms.common.util.JDBC.JDBC;
import com.hxoms.common.utils.UUIDGenerator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

public class PersonExcelToDB {

    public static void main(String[] orgs) {

        boolean f = ExcelToDB();

        System.out.println(f);

    }

    /**
     * 导入Excel的内容导入到数据库，因为数据较多，所以用JDBC
     *
     * @return
     */
    public static boolean ExcelToDB() {
        SimpleDateFormat formatter  = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");

        Date data1 = new Date();
        //formatter.format(new Date());
        System.err.println("开始执行---------------------"+formatter.format(data1));

        boolean a = false;

        List<Map<String, Object>> list = readExcel("D:\\tests.xls");

        //连接数据库
        Connection conn = JDBC.getConnection();


        try {
            conn.setAutoCommit(false);

            PreparedStatement ps = null;

            String sql = "insert into cf_certificate" +
                    "(ID,A0184,NAME,SEX,CSRQ,GJ,ZJLX,ZJHM,ZWCSDD,QFRQ,YXQZ,DQZT) " +
                    "values (?,?,?,?,?,?,?,?,?,?,?,?)";

            ps = conn.prepareStatement(sql);

            for (int i = 0; i<list.size(); i++) {
                String sex = (String)list.get(i).get("sex");
                int sexInt = 1;
                if (sex.equals("男")){
                    sexInt = 1;
                }else{
                    sexInt = 2;
                }
                ps.setString(1, UUIDGenerator.getPrimaryKey());
                ps.setString(2, (String)list.get(i).get("IDCard"));
                ps.setString(3, (String)list.get(i).get("name"));
                ps.setInt(4,sexInt);
                ps.setString(5, (String)list.get(i).get("csny"));
                ps.setString(6, (String)list.get(i).get("gj"));
                ps.setString(7, (String)list.get(i).get("zjlx"));
                ps.setString(8, (String)list.get(i).get("cardNum"));
                ps.setString(9,(String)list.get(i).get("csd"));
                ps.setString(10,(String)list.get(i).get("qfrq"));
                ps.setString(11,(String)list.get(i).get("yxqz"));
                ps.setInt(12,1);

                ps.addBatch();

                if (i % 20 == 0){
                    ps.executeBatch();
                    conn.commit();
                    ps.clearBatch();
                    a = true;
                }

            }
            ps.executeBatch();
            conn.commit();
            ps.clearBatch();
            a = true;

        } catch (SQLException e) {
            e.printStackTrace();
            e.getErrorCode();
            e.getNextException();
        }
        //formatter.format(new Date());
        Date data2 = new Date();
        System.err.println("结束时间---------------------"+formatter.format(data2));


        System.out.println("总用时"+(data2.getTime()-data1.getTime())/1000+"秒");
        return a;
    }


    static String[] zd = {"IDCard", "name", "sex", "csny", "gj", "zjlx", "cardNum", "csd", "qfdw", "qfrq", "yxqz"};

    static String[] jl = {"status", "name", "sex", "csny", "gj", "zjlx", "cardNum", "toLocation", "crka", "crjrq", "crjsj"};

    /**
     * 导入Excel的内容导入到数据库，因为数据较多，所以用JDBC
     * 读取到Excel表格的数据
     *
     * @param path
     * @return
     */
    public static List<Map<String, Object>> readExcel(String path) {

        List<Map<String, Object>> list = new ArrayList<>();

        //Map<String, Object> map = new HashMap<String, Object>();
        //连接数据库
            /*Connection conn = JDBC.getConnection();

            conn.setAutoCommit(false);

            PreparedStatement ps = null;*/

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

        //ps = conn.prepareStatement(sql);

        //Map<String, Object> map = new HashMap<String, Object>();

        File xlsFile = new File(path);

        // 工作表
        Workbook workbook = null;

        try {

            workbook = WorkbookFactory.create(xlsFile);

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
                    if (r.getCell(2).toString().contains("境")) {
                        continue;
                    }

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
}
