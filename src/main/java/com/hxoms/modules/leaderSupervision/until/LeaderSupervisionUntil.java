package com.hxoms.modules.leaderSupervision.until;

import com.github.pagehelper.util.StringUtil;
import com.hxoms.common.exception.CustomMessageException;
import com.hxoms.common.utils.Constants;
import com.hxoms.common.utils.StringUilt;
import com.hxoms.general.select.entity.SqlVo;
import com.hxoms.general.select.mapper.SelectMapper;
import com.hxoms.modules.leaderSupervision.Enum.BussinessApplyStatus;
import com.hxoms.modules.leaderSupervision.vo.BussinessTypeAndIdVo;
import com.hxoms.modules.leaderSupervision.vo.LeaderSupervisionVo;
import com.hxoms.modules.leaderSupervision.vo.OmsJiweiOpinionVo;
import com.hxoms.modules.omsregcadre.entity.OmsRegProcbatch;
import io.swagger.models.auth.In;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;


@Component
@PropertySource("classpath:/leaderSupervision.porperties")
public class LeaderSupervisionUntil {



    private static String leaderbatchPrefix;

    public static String   leaderselectorFormatter;

    private static int  leaderstepNum;

    public final static String prefixPdfStyle="<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n" +
            "<html lang=\"en\" xmlns=\"http://www.w3.org/1999/xhtml\">\n" +
            "<head>\n" +
            "  <meta charset=\"UTF-8\"/>\n" +
            "  <title>表格样式</title>\n" +
            "  <meta name=\"viewport\" content=\"initial-scale=1, maximum-scale=1, user-scalable=no, width=device-width\"/>\n" +
            "  <link rel=\"stylesheet\" href=\"https://static.loyalvalleycapital.com/web/css/frame.css\"/>\n" +
            "  <style>\n" +
            "  table.table-separate th{\n" +
            "    font-weight:bold;\n" +
            "    font-size:14px;\n" +
            "    border-top:1px solid #F3EDE9 !important;\n" +
            "  }\n" +
            "  table.table-separate td{\n" +
            "    padding: 13px 0;\n" +
            "    font-weight:100;\n" +
            "  }\n" +
            "  .table-separate td.tit{\n" +
            "    background-color: #f4f9fe;\n" +
            "    font-weight:normal;\n" +
            "    padding:22px 0;\n" +
            "    width:15%;\n" +
            "  }\n" +
            "  .table-separate td.cont{\n" +
            "    text-align: left;\n" +
            "    padding:16px 22px;\n" +
            "    width:85%;\n" +
            "    line-height:175%;\n" +
            "  }\n" +
            "  .table-separate.no-border th{\n" +
            "    border:none;\n" +
            "    text-align: left;\n" +
            "  }\n" +
            "  .table-separate.no-border td{\n" +
            "    text-align: left;\n" +
            "    border:none;\n" +
            "  }\n" +
            " \n" +
            "\ttable {\n" +
            "\t\t\tborder-collapse: collapse;\n" +
            "\t\t\ttable-layout: fixed;\n" +
            "\t\t\tword-break:break-all;\n" +
            "\t\t\tfont-size: 10px;\n" +
            "\t\t\twidth: 100%;\n" +
            "\t\t\ttext-align: center;\n" +
            "\t}\n" +
            "\ttd {\n" +
            "\t\tword-break:break-all;\n" +
            "\t\tword-wrap : break-word;\n" +
            "\t}\n" +
            "\t@page {\n" +
            "\t\tsize:210mm 297mm;//纸张大小A4\n" +
            "\t\tmargin: 0.25in;\n" +
            "\t\t-fs-flow-bottom: \"footer\";\n" +
            "\t\t-fs-flow-left: \"left\";\n" +
            "\t\t-fs-flow-right: \"right\";\n" +
            "\t\tpadding: 1em;\n" +
            "\t\t}\n" +
            "\t\t#footer {\n" +
            "\t\tfont-size: 90%; font-style: italic;\n" +
            "\t\tposition: absolute; top: 0; left: 0;\n" +
            "\t\t-fs-move-to-flow: \"footer\";\n" +
            "\t\t}\n" +
            "\t\t#pagenumber:before {\n" +
            "\t\tcontent: counter(page);\n" +
            "\t\t}\n" +
            "\t\t#pagecount:before {content: counter(pages);\n" +
            "\t\t}\n" +
            "</style>\n" +
            "</head>\n" +
            "<body class=\"bg-white pb-3\"  style = \"font-family: SimSun;\">"+
            "<div id=\"footer\" style=\"\"/> ";

    public final static String suffixPdfStyle="</body>\n" + "</html>";

    // 缓存 因私状态
    private static Map<String,String> mappri = new HashMap<String,String>();





    // 生成批次号的方法
    /***
     * HNSW-20200610001,HNSW-20200610002
     *
     */
    public static   String factoryBatchCode(String maxBatchCode){

         String batchCode="";
         //如果当天 未建批次，或批次为空
         if(StringUilt.stringIsNullOrEmpty(maxBatchCode) ||"wu".equals(maxBatchCode)){

             batchCode+=leaderbatchPrefix+"-"+selectorDateFormat(leaderselectorFormatter)+"001";

         }else{

             String stepNumstr = maxBatchCode.substring(maxBatchCode.length()-leaderstepNum);

             int curentbatchCode = Integer.valueOf(stepNumstr)+1;

             String curentbatchCodestr ="";

            for(int i=String.valueOf(curentbatchCode).length();i<leaderstepNum;i++){

                curentbatchCodestr+=0;


            }


             batchCode+=maxBatchCode.substring(0,maxBatchCode.length()-3)+curentbatchCodestr+curentbatchCode;

         }






        return batchCode;
    }

    // 时间 选择器
    public  static String selectorDateFormat(String selectorFormatter){

        SimpleDateFormat formatter = null;

        formatter = new SimpleDateFormat(selectorFormatter);



        String batchSuffix = formatter.format(new Date());


        return  batchSuffix;
    }

    // 统一判断参数 异常
    // 对必传的参数进行异常判断
    public static void  throwableByParam(Object ... objs){

        if(objs!=null && objs.length>0){

            for(Object obj : objs){

                if(obj == null){

                    throw new CustomMessageException("参数 不能 为空，请仔细检查");
                }else{



                    if(obj.getClass()==String.class){

                        if(StringUilt.stringIsNullOrEmpty((String)obj)){
                            throw new CustomMessageException("参数为空，请仔细检查");
                        }
                    }else if(obj instanceof LeaderSupervisionVo){

                        if(((LeaderSupervisionVo) obj).getBussinessTypeAndIdVos()==null && ((LeaderSupervisionVo) obj).getBussinessTypeAndIdVos().size()<=0){

                            throw new CustomMessageException("参数为空，请仔细检查");

                        }

                    }else if(obj instanceof OmsJiweiOpinionVo){

                        if(((OmsJiweiOpinionVo) obj).getBussinessTypeAndIdVos()==null && ((OmsJiweiOpinionVo) obj).getBussinessTypeAndIdVos().size()<=0){

                            throw new CustomMessageException("参数为空，请仔细检查");

                        }
                    }

                    else if(obj instanceof List ){
                        if(((List)obj).size()<=0){

                            throw new CustomMessageException("参数为空，请仔细检查");
                        }

                    }else if(obj.getClass()==Date.class){
                        if((Date)obj ==null){

                            throw new CustomMessageException("受理时间为空，请仔细检查");
                        }
                    }

                }

            }
        }else{

            throw new CustomMessageException("所传的参数为空，请仔细检查");

        }

    }

    // 返回 申请业务 类型
    public  static String   selectorBussinessTypeByName(String bussinessName){

        int i =0;
        for(String bussinessType: Constants.oms_businessName){

            if(bussinessType.contains(bussinessName)){

                return Constants.oms_business[i];

            }

            i++;

        }

       return null;

    }


    // 根据数组的值 获取数组 下标

    public static   Integer getIndexByArray(String[] array,String value){

        if (null == array || array.length==0 || StringUilt.stringIsNullOrEmpty(value))
            return null;

        for(int i=0;i<array.length;i++){

            if(array[i].equals(value)){

                return i;
            }


        }

        return null;

    }

    public static String  getBatchIdByBuessinessId(String buessinessId,String tableName){


      return " select leader_batch_id as batchId from " +  tableName  + "where id =" +buessinessId;


    }

//    public Map getMapByLeaderSupervisionVo(LeaderSupervisionVo leaderSupervisionVo){
//
//        Map map = new HashMap();
//
//
//    }




    //导出 数据 类型 为 list<Map>

    public static HSSFWorkbook exportExcelByListMap(List listK,List listV,List<Map> dataList,String sheetName){


        HSSFWorkbook wb = new HSSFWorkbook();
        // 设置 sheet 页
        HSSFSheet sheet0 = wb.createSheet(sheetName);

        List<HSSFSheet> sheetList=new ArrayList<HSSFSheet>();
        sheetList.add(sheet0);
        //设置标题样式
        HSSFCellStyle titleStyle=  getTitleStyle(wb);
        //设置单元格样式
        HSSFCellStyle   cellStyle= getCellStyle(wb);

        for (HSSFSheet sheet:sheetList) {


            //创建第一行
            Row row = sheet.createRow(0);
            Cell cell = null;

            //创建标题
            for (int i = 0; i < listV.size(); i++) {
                cell = row.createCell(i);
                cell.setCellValue(listV.get(i).toString());

                cell.setCellStyle(titleStyle);

                //自动设置列宽
                sheet.setColumnWidth(i, 512 * 4);
            }

            //填充内容

            //绘制内容
            String k;
            String s;

            //循环多少行
            for(int i=0;i<dataList.size();i++){

                Row nextrow = sheet.createRow(i+1); //第i行
                Cell cell2  =nextrow.createCell(0);
                cell2.setCellValue(String.valueOf(i+1));



                Map temp = dataList.get(i);
                for (int j = 1; j < listK.size(); j++) {
                    cell2  =nextrow.createCell(j);
                    if (temp.get(listK.get(j)) != null) {

                        k = temp.get(listK.get(j)).toString();

                        s = (listK.get(j).toString()).toLowerCase();
                        if (s.indexOf("Time") != -1 || s.indexOf("Date") != -1 ) {
                            if (k.length() > 10) {
                                k = k.substring(0, 10);
                            }

                        }
                        // 如果 该列是 申请状态进行转化
                        if("applystatus".equals(s)){

                            k = getK(k,"因私");
                        }

                        cell2.setCellValue(k);
                        cell2.setCellStyle(cellStyle);
                        int currWidth = sheet.getColumnWidth(j);
                        autoSizeColumnOne(j, k, sheet, currWidth);
                    }else {
                        String m = "";
                        cell2.setCellValue(m);
                        cell2.setCellStyle(cellStyle);
                        int currWidth = sheet.getColumnWidth(j);
                        autoSizeColumnOne(j,m,sheet,currWidth);

                    }
                }
            }


        }

        return  wb;
    }


    public static String getK(String k,String bussinessType){



        if("因私".equals(bussinessType)){

            // 当两者 状态 不一致 的重新 把新的状态 写到 缓存中
            if(mappri.size()!=(Constants.private_business.length+Constants.leader_business.length)){
                mappri.clear();

                for(int i=0;i<Constants.private_business.length;i++){

                    //获取 状态  获取 状态 对应的值
                    mappri.put(String.valueOf(Constants.private_business[i]),Constants.private_businessName[i]);

                }

                for(int j=0;j<Constants.leader_business.length;j++){

                    mappri.put(String.valueOf(Constants.leader_business[j]),Constants.leader_businessName[j]);



                }

                return mappri.get(k);

            }else{


                return mappri.get(k);


            }




        }

        return null;

    }

    public static HSSFWorkbook exportRfInfoByListMap(List listK, List listV, List<Map> dataList, String sheetName1, String sheetName2, OmsRegProcbatch batchinfo) {
        HSSFWorkbook wb = new HSSFWorkbook();
        // 设置 sheet 页
        HSSFSheet sheet0 = wb.createSheet(sheetName1);
        HSSFSheet sheet1 = wb.createSheet(sheetName2);

        List<HSSFSheet> sheetList=new ArrayList<HSSFSheet>();
        sheetList.add(sheet0);
        sheetList.add(sheet1);
        //设置标题样式
        HSSFCellStyle titleStyle=  getTitleStyle(wb);
        //设置单元格样式
        HSSFCellStyle cellStyle= getCellStyle(wb);
        int f=0;
        for (HSSFSheet sheet:sheetList) {
            f++;
            if (f==1){
                //创建第一行
                Row row = sheet.createRow(0);
                row.setHeight((short)250);
                Cell cell1 = row.createCell(0);
                cell1.setCellValue("国家工作人员登记备案表");
                cell1.setCellStyle(titleStyle);
                //创建第二行
                Row row2 = sheet.createRow(1);
                row.setHeight((short)250);
                Cell cell2 = row2.createCell(0);
                cell2.setCellValue("报备单位名称（盖章）：");
                cell2.setCellStyle(cellStyle);


                CellRangeAddress regionNum1 = new CellRangeAddress(0,0 , 0, listV.size()-1);
                CellRangeAddress regionNum2 = new CellRangeAddress(1,1 , 0, listV.size()-1);
                sheet.addMergedRegion(regionNum1);
                sheet.addMergedRegion(regionNum2);
                //创建第三行
                Row rowNum3 = sheet.createRow(2);
                Cell cell = null;

                //创建标题
                for (int i = 2; i < listV.size(); i++) {

                    cell = rowNum3.createCell(i);
                    cell.setCellValue(listV.get(i).toString());

                    cell.setCellStyle(titleStyle);

                    //自动设置列宽
                    sheet.setColumnWidth(i, 512 * 4);
                }
                //填充内容

                //绘制内容
                String k;
                String s;

                //循环多少行
                for(int i=0;i<dataList.size();i++){

                    Row nextrow = sheet.createRow(i+3); //第i行
                    Cell cell3  =nextrow.createCell(0);
                    cell3.setCellValue(String.valueOf(i+1));



                    Map temp = dataList.get(i);
                    for (int j = 1; j < listK.size(); j++) {
                        cell3  =nextrow.createCell(j);
                        if (temp.get(listK.get(j)) != null) {

                            k = temp.get(listK.get(j)).toString();

                            s = (listK.get(j).toString()).toLowerCase();
                            if (s.indexOf("Time") != -1 || s.indexOf("Date") != -1 ) {
                                if (k.length() > 10) {
                                    k = k.substring(0, 10);
                                }

                            }
                            cell3.setCellValue(k);
                            cell3.setCellStyle(cellStyle);
                            int currWidth = sheet.getColumnWidth(j);
                            autoSizeColumnOne(j, k, sheet, currWidth);
                        }else {
                            String m = "";
                            cell3.setCellValue(m);
                            cell3.setCellStyle(cellStyle);
                            int currWidth = sheet.getColumnWidth(j);
                            autoSizeColumnOne(j,m,sheet,currWidth);

                        }
                    }
                }

                CellRangeAddress regionNum3 = new CellRangeAddress(dataList.size()+3,dataList.size()+3 , 0, listV.size()-1);
                CellRangeAddress regionNum4 = new CellRangeAddress(dataList.size()+3+1,dataList.size()+3+1 , 0, listV.size()-1);
                sheet.addMergedRegion(regionNum3);
                sheet.addMergedRegion(regionNum4);
                Row nextrow1 = sheet.createRow(dataList.size()+3); //第i行
                Cell celladdOther1 =  nextrow1.createCell(0);
                celladdOther1.setCellValue("本次备案共  页 人（首页填写）             此为第  页                              报送时间（出入境管理部门填写） ：年月日\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\n");
                celladdOther1.setCellStyle(cellStyle);
                Row nextrow2 = sheet.createRow(dataList.size()+3+1); //第i行
                Cell celladdOther2 =  nextrow2.createCell(0);
                celladdOther2.setCellValue("备案单位负责人（首页填写）：                   联系人：                        联系电话：\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\nj");
                celladdOther2.setCellStyle(cellStyle);
            }
            if (f==2){
                //创建第一行
                Row row = sheet.createRow(0);
                Cell cell = null;

                //创建标题
                for (int i = 0; i < listV.size(); i++) {
                    cell = row.createCell(i);
                    cell.setCellValue(listV.get(i).toString());

                    cell.setCellStyle(titleStyle);

                    //自动设置列宽
                    sheet.setColumnWidth(i, 512 * 4);
                }

                //填充内容

                //绘制内容
                String k;
                String s;

                //循环多少行
                for(int i=0;i<dataList.size();i++){

                    Row nextrow = sheet.createRow(i+1); //第i行
                    Cell cell2  =nextrow.createCell(0);
                    cell2.setCellValue(String.valueOf(i+1));



                    Map temp = dataList.get(i);
                    for (int j = 1; j < listK.size(); j++) {
                        cell2  =nextrow.createCell(j);
                        if (temp.get(listK.get(j)) != null) {

                            k = temp.get(listK.get(j)).toString();

                            s = (listK.get(j).toString()).toLowerCase();
                            if (s.indexOf("Time") != -1 || s.indexOf("Date") != -1 ) {
                                if (k.length() > 10) {
                                    k = k.substring(0, 10);
                                }

                            }
                            cell2.setCellValue(k);
                            cell2.setCellStyle(cellStyle);
                            int currWidth = sheet.getColumnWidth(j);
                            autoSizeColumnOne(j, k, sheet, currWidth);
                        }else {
                            String m = "";
                            cell2.setCellValue(m);
                            cell2.setCellStyle(cellStyle);
                            int currWidth = sheet.getColumnWidth(j);
                            autoSizeColumnOne(j,m,sheet,currWidth);

                        }
                    }
                }
            }
        }
        return  wb;
    }


    // 设置列宽方法1
    public void autoSizeColumn(List listV,HSSFSheet sheet){
        for(int i=0;i<listV.size();i++){

            sheet.autoSizeColumn((short)i); //调整第i列宽度
        }
    }
    // 设置列宽方法2
    /**
     * 注意汉字*512
     * 数字*256
     * **/
    public static void autoSizeColumnOne(int j, String k,HSSFSheet sheet,int currWidth){


        if(StringUtil.isEmpty(k)){
            sheet.setColumnWidth(j,512*8);
        }else{
            //再次设置列宽  只增 不减
            if(k.length()*512>currWidth){

                sheet.setColumnWidth(j,k.length()*512); //调整第i列宽度
            }

        }

//           sheet.autoSizeColumn((short)j);

    }

    //设置标题表单样式

    public static HSSFCellStyle getTitleStyle(HSSFWorkbook workbook){



        HSSFCellStyle style = workbook.createCellStyle();


        style.setAlignment(HorizontalAlignment.CENTER); //居中

        //设置字体
        HSSFFont font = workbook.createFont();
        font.setFontName("仿宋_GB2312");
        font.setBold(true);//字体加粗

        style.setFont(font);

        return  style;
    }
    //设置单元格样式
    public static HSSFCellStyle getCellStyle(HSSFWorkbook workbook){
        
        HSSFCellStyle style = workbook.createCellStyle();

        style.setAlignment(HorizontalAlignment.LEFT); //居左

        //设置字体
        HSSFFont font = workbook.createFont();
        font.setFontName("仿宋_GB2312");
        font.setBold(true);//字体加粗
        return  style;
    }



    //文件上传工具类服务方法
    public static void uploadFile(byte[] file, String filePath, String fileName) {

        File targetFile = new File(filePath);
        if(!targetFile.exists()){
            targetFile.mkdirs();
        }
        try {
            FileOutputStream out = new FileOutputStream(filePath + fileName);
            out.write(file);
            out.flush();
            out.close();
        } catch (Exception e){
          e.printStackTrace();

        }finally {


        }
    }


    /**
     * @Desc: 文件下载
     *
     */
    public static byte[] downloadFile(String filedDownPath) throws IOException {
        byte[] fileDateByte=null;
        File file=new File(FilenameUtils.normalize(filedDownPath));
        if(!file.exists()||!file.isFile()){
            throw new CustomMessageException("文件未生成！");
        }
        ByteArrayOutputStream outStream=null;
        FileInputStream in=null;
        try {
            outStream = new ByteArrayOutputStream();
            in = new FileInputStream(file);
            int len;
            byte[] buf = new byte[1024];
            while ((len = in.read(buf)) > 0) {
                outStream.write(buf, 0, len);
            }
            fileDateByte=outStream.toByteArray();
        } catch (IOException e) {
            throw new CustomMessageException(e);
        } finally {
            //关闭流
            if(in!=null){
                in.close();
            }
            if(outStream!=null){
                outStream.close();
            }
        }
        return fileDateByte;
    }

    // 自定义删除 相似 文件
    public static void deleteFileById(String commentStr,String fileDirectoryPaht){

     // String commentStr = "蒋超良备案表"+".pdf";

      long maxNum = 0; // 找到一个 最大随机数 (最大的 表示 最新的 不用删)

      List<String> deletePaths ;  // 需要删除的集合

      File file = new File(FilenameUtils.normalize(fileDirectoryPaht));
//      File file = new File(FilenameUtils.normalize("D:\\oms\\attachment\\static"));

      List<String> filepaths = new ArrayList<>();

      if(file.isDirectory()){
        File[] files =  file.listFiles();

        if(files!=null && files.length>0){

            for(int i=0;i<files.length;i++){

                File f = files[i];
                if(f.getName().contains(commentStr)){

                 String numstr =    f.getName().replaceAll(commentStr,"").trim();

                   long num =Long.parseLong(numstr);
                   if(num >maxNum){

                       maxNum =num;
                   }
                    filepaths.add(f.getName());
                }
            }
        }
      }

      final long maxnum1 =maxNum;


      if(filepaths!=null && filepaths.size()==1){


      }else{

          deletePaths=  filepaths.stream().filter((String s) -> Long.valueOf(s.replaceAll(commentStr,"").trim())<maxnum1 ).collect(Collectors.toList());

          if(deletePaths!=null&& deletePaths.size()>0){

              for(int i=0;i<deletePaths.size();i++){

                  File file1 = new File(fileDirectoryPaht+File.separator+deletePaths.get(i));

                  file1.delete();

              }


          }

      }



    }


    public static String getUpdateStatusByJieWei(String busessId,String bussinesType,String leaderStatusName,String bussinessName){

        String updateSql = "update "+bussinesType;

        String setSql = " set  " ;

        String whereCondition = " where id = '" + busessId+"'";


        for(BussinessApplyStatus applyStatus  : BussinessApplyStatus.values()){

            if(bussinesType.indexOf(applyStatus.getTableName())!=-1){

                String status =  applyStatus.getApplySatus();

                if("干教".equals(bussinessName)){
                    // todo 干教 流程 到征求纪委意见 就走完了 将状态 置为 已完结

                    setSql+= status + "=" + Constants.leader_business[Constants.leader_business.length-1];


                }else{

                    // 干部监督处的状态
                    setSql+= status + "=" + Constants.leader_business[LeaderSupervisionUntil.getIndexByArray(Constants.leader_businessName,leaderStatusName)];


                }

                // 干部监督处的状态
//                setSql+= status + "=" + Constants.leader_business[LeaderSupervisionUntil.getIndexByArray(Constants.leader_businessName,leaderStatusName)];

                break;


            }

        }

        return  updateSql+setSql+whereCondition;


    }





    public static void main(String[] args){


//        deleteFileById();


    }











    public static String getLeaderbatchPrefix() {
        return leaderbatchPrefix;
    }
    @Value("${batch.prefix}")
    public  void setLeaderbatchPrefix(String batchPrefix) {
        LeaderSupervisionUntil.leaderbatchPrefix = batchPrefix;
    }

    public static  String getLeaderselectorFormatter() {
        return leaderselectorFormatter;
    }
    @Value("${selectordate.formatter}")
    public  void setLeaderselectorFormatter(String selectorFormatter) {
        LeaderSupervisionUntil.leaderselectorFormatter = selectorFormatter;
    }

    public static int getLeaderstepNum() {
        return leaderstepNum;
    }
    @Value("${step.length}")
    public  void setLeaderstepNum(int stepNum) {
        LeaderSupervisionUntil.leaderstepNum = stepNum;
    }
}
