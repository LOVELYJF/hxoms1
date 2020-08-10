package com.hxoms.modules.leaderSupervision.until;

import com.github.pagehelper.util.StringUtil;
import com.hxoms.common.exception.CustomMessageException;
import com.hxoms.common.utils.Constants;
import com.hxoms.common.utils.StringUilt;
import com.hxoms.modules.leaderSupervision.vo.LeaderSupervisionVo;
import com.hxoms.modules.leaderSupervision.vo.OmsJiweiOpinionVo;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.*;


@Component
@PropertySource("classpath:/leaderSupervision.porperties")
public class LeaderSupervisionUntil {



    private static String leaderbatchPrefix;

    public static String   leaderselectorFormatter;

    private static int  leaderstepNum;








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

        style.setFont(font);

        return  style;
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
