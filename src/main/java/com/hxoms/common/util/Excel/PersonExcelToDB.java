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

    static List<Map<String, Object>> cfMaps = new ArrayList<>();

    static List<Map<String, Object>> ExcelMap = new ArrayList<>();

    static List<Map<String,Object>> oerMap = new ArrayList<>();

    static String[] zd = {"IDCard", "name", "sex", "csny", "gj", "zjlx", "cardNum", "csd", "qfdw", "qfrq", "yxqz"};

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
             * 查询oms_reg_procpersoninfo（登记备案表）里面的数据，放在List<Map<String,Object>>  maps公有变量中，方便查询取值
             */
            findOrpList();

            findCfList();

            findOerList();

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");
            SimpleDateFormat formatterNow = new SimpleDateFormat("yyyyMMdd");
            Date data1 = new Date();

            String timeNow = formatterNow.format(data1);

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
                            "(ID,A0184,NAME,SEX,CSRQ,GJ,ZJLX,ZJHM,ZWCSDD,QFRQ,YXQZ,DQZT,ZWQFJG,OMS_ID) " +
                            "values (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

                    String sql2 = "insert into oms_entryexit_record (ID,IMPORT_TIME,IMPORT_PERSON,OGA_MODE," +
                            "DATA_SOURCE,OMS_ID,OGE_STATUS,NAME,SEX,BIRTH_DATE,NATIONALITY,ID_TYPE,ID_NUMBER," +
                            "DESTINATION,ENTRACE_EXIT,OGE_DATE,VALID_UNTIL,PRIAPPLY_ID)values(" +
                            "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

                    ps = conn.prepareStatement(sql);

                    ps2 = conn.prepareStatement(sql2);

                    for (int i = 0; i < list.size(); i++) {
                        String name = list.get(i).get("name").toString().replaceAll(" ","").replaceAll("    ","");
                        String sex  = list.get(i).get("sex").toString();
                        String csny = list.get(i).get("csny").toString();
                        String cjsj = list.get(i).get("qfrq").toString();
                        String cardNum = list.get(i).get("cardNum").toString();
                        String omsId = findOmsIdByName(maps,name,sex,csny);
                            //如果结果集包含"境"字，就添加到出国境记录表，如果不包含，就添加到证照表里面
                            if (list.get(i).get("IDCard").toString().contains("境")) {
                                //判断出入境记录的证件是否为过期
                                if(ExcelMap(ExcelMap,cardNum) == 0){
                                    //判断是否导入过，如果导入过就不导入
                                    if(ExcelComparisonList(oerMap,cjsj,cardNum)==0){

                                        ps2.setString(1, UUIDGenerator.getPrimaryKey());

                                        ps2.setString(2,dateDB);

                                        ps2.setString(3,importPerson);

                                        ps2.setString(4,"******");

                                        ps2.setString(5,dataSource);

                                        ps2.setString(6,omsId);

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

                                        ps2.setString(17,findYxqzByNum(ExcelMap,list.get(i).get("cardNum").toString()));

                                        ps2.setString(18,"没有因私出国的ID");

                                        ps2.addBatch();
                                    }
                                }
                            }else{
                                //判断护照是否过期，如果超过当前时间，证明户名已经过期
                                if(Integer.parseInt(list.get(i).get("yxqz").toString())<Integer.parseInt(timeNow)){
                                    Map<String, Object> map = new HashMap<>();
                                    map.put("cardNum",list.get(i).get("cardNum"));
                                    map.put("name",list.get(i).get("name"));
                                    ExcelMap.add(map);
                                }else {

                                    if(!ExcelComparisonList(cfMaps, list.get(i).get("cardNum").toString())){

                                        int sexInt = 1;

                                        if (sex.equals("男")) {

                                            sexInt = 1;

                                        } else {

                                            sexInt = 2;

                                        }

                                        String zjlx = (String) list.get(i).get("zjlx");
                                        int zjlxInt = 1;
                                        if (zjlx.contains("护照")){
                                            zjlxInt = 1;
                                        }else if(zjlx.contains("港澳")){
                                            zjlxInt = 2;
                                        }else if(zjlx.contains("台湾")){
                                            zjlxInt = 4;
                                        }
                                        ps.setString(1, UUIDGenerator.getPrimaryKey());

                                        ps.setString(2, (String) list.get(i).get("IDCard"));

                                        ps.setString(3, (String) list.get(i).get("name"));

                                        ps.setInt(4, sexInt);

                                        ps.setString(5, (String) list.get(i).get("csny"));

                                        ps.setString(6, (String) list.get(i).get("gj"));

                                        ps.setInt(7, zjlxInt);

                                        ps.setString(8, (String) list.get(i).get("cardNum"));

                                        ps.setString(9, (String) list.get(i).get("csd"));

                                        ps.setString(10, (String) list.get(i).get("qfrq"));

                                        ps.setString(11, (String) list.get(i).get("yxqz"));

                                        ps.setInt(12, 1);

                                        ps.setString(13,(String)list.get(i).get("qfdw"));

                                        ps.setString(14,omsId);

                                        ps.addBatch();

                                        }
                                    }
                                }

                        if (i % 1000 == 0) {

                            ps.executeBatch();

                            ps2.executeBatch();

                            conn.commit();

                            ps.clearBatch();

                            ps2.clearBatch();

                        }
                    }

                    ps.executeBatch();

                    ps2.executeBatch();

                    conn.commit();

                    ps.clearBatch();

                    ps2.clearBatch();

                    maps.clear();    cfMaps.clear();   ExcelMap.clear();  oerMap.clear();

                    a = true;

                } catch (SQLException e) {
                    maps.clear();    cfMaps.clear();   ExcelMap.clear();  oerMap.clear();
                    e.printStackTrace();
                    e.getErrorCode();
                    e.getNextException();
                }
                Date data2 = new Date();

                System.err.println("结束时间---------------------" + formatter.format(data2));

                System.err.println("总用时" + (data2.getTime() - data1.getTime()) / 1000 + "秒");
            } else {
                return a;
            }

        }
        maps.clear();
        return a;

    }

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

                    Map<String, Object> map = new HashMap<>();

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
    public static void findOrpList() {

        String sql = "select id,Replace(CONCAT(SURNAME,name),' ','') as name,Replace(BIRTH_DATE,'-','') as birthDate,idnumber,CASE SEX WHEN 1 then '男' when 2 then '女' end as SEX FROM oms_reg_procpersoninfo";

        //String sqlCf = "select A0184,NAME,CASE SEX WHEN 1 THEN '男' WHEN 2 THEN '女' END AS SEX,CSRQ,ZJHM FROM cf_certificate";

        try {
            Connection conn = JDBC.getConnection();

            conn.setAutoCommit(false);

            Statement stat = conn.createStatement();

            ResultSet rs = stat.executeQuery(sql);

            while (rs.next()) {
                Map<String, Object> dataMap = new HashMap<>();
                dataMap.put("a0100", rs.getString(1));
                dataMap.put("a0101", rs.getString(2).replace("　","").replace(" ",""));
                dataMap.put("a0107", rs.getString(3));
                dataMap.put("a0184", rs.getString(4));
                dataMap.put("sex", rs.getString(5));
                maps.add(dataMap);
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * 查询数据库里已有的护照信息
     */
    public static void findCfList(){

        String sqlCf = "select A0184,NAME,CASE SEX WHEN 1 THEN '男' WHEN 2 THEN '女' END AS SEX,CSRQ,ZJHM FROM cf_certificate";

        try {
            Connection conn = JDBC.getConnection();

            conn.setAutoCommit(false);

            Statement statCf = conn.createStatement();

            ResultSet rsCf = statCf.executeQuery(sqlCf);

            while (rsCf.next()) {
                Map<String, Object> dataMapCf = new HashMap<>();
                dataMapCf.put("a0184", rsCf.getString(1));
                dataMapCf.put("name", rsCf.getString(2).replace("　","").replace(" ",""));
                dataMapCf.put("sex", rsCf.getString(3));
                dataMapCf.put("csrq", rsCf.getString(4));
                dataMapCf.put("cardNum", rsCf.getString(5));
                cfMaps.add(dataMapCf);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void findOerList(){

        String sqlCf = "SELECT ID_NUMBER,OGE_DATE from oms_entryexit_record";

        try {
            Connection conn = JDBC.getConnection();

            conn.setAutoCommit(false);

            Statement statOer = conn.createStatement();

            ResultSet rsOer = statOer.executeQuery(sqlCf);

            while (rsOer.next()) {
                Map<String, Object> dataMapOer = new HashMap<>();
                dataMapOer.put("cardNum", rsOer.getString(1));
                dataMapOer.put("ogeDate", rsOer.getString(2).replace("　","").replace(" ",""));
                oerMap.add(dataMapOer);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据证件号码查找证件有效期
     * @param list
     * @param cardNum
     * @return
     */
    public static String findYxqzByNum(List<Map<String,Object>> list,String cardNum){

        String yxqz = null;

        if(list.size()>0){
            for(int i= 0;i<list.size();i++){
                if(list.get(i).get("cardNum").toString().equals(cardNum)){
                    yxqz = list.get(i).get("yxqz").toString();
                    break;
                }
            }
        }
        return yxqz;
    }

    /**
     * 查询证件信息表里面所有的信息，导入Excel表格的时候判断是否已经导入过
     */
    public static boolean ExcelComparisonList(List<Map<String,Object>> list,String cardNum){

        boolean flag = false;

        if(list.size() >0){
            for (int i= 0;i<list.size();i++){
                if (list.get(i).get("cardNum").toString().equals(cardNum)){
                    flag = true;
                    break;
                }else{
                    flag = false;
                }
            }
        }

        return flag;

    }

    /**
     * 判断导入的记录表的证照是否过期
     * @param list
     * @param cardNum
     * @return
     */
    public static int ExcelMap(List<Map<String,Object>> list,String cardNum){

        int flag = 0;
        if(list.size()>0){
            for (int i= 0;i<list.size();i++){
                if(list.get(i).get("cardNum").toString().equals(cardNum)){
                    flag = 1;
                    break;
                }
            }
        }
        return flag;
    }

    /**
     * 根据处境日期和证件号码判断是否导入过
     * @param list
     * @param ogeDate
     * @param cardNum
     * @return
     */
    public static int ExcelComparisonList(List<Map<String,Object>> list,String ogeDate,String cardNum){

        int flag=0;

        if (list.size()>0){
            for (int i =0;i<list.size();i++){
                String ogeDatelist = list.get(i).get("ogeDate").toString().replaceAll("-","");
                String cardNumList= list.get(i).get("cardNum").toString();
                if (ogeDatelist.equals(ogeDate) && cardNumList.equals(cardNum)){
                    flag = 1;
                    break;
                }
            }
        }

        return flag;
    }

    public static String findOmsIdByName(List<Map<String,Object>> list,String name,String sex,String a0107){

        String omsId = null;

        for (int i= 0;i<list.size();i++) {
            if (list.get(i).get("a0101").toString().replace(" ","").equals(name) && list.get(i).get("sex").equals(sex) && list.get(i).get("a0107").equals(a0107)) {
                omsId = list.get(i).get("a0100").toString();
                break;
            }
        }
        return omsId;

    }

}
