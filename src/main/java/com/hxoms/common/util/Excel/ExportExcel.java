package com.hxoms.common.util.Excel;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExportExcel {

    public HSSFWorkbook generateExcel() {
        return new HSSFWorkbook();
    }

    private HttpServletResponse response;
    //显示的导出表的标题
    private String title;
    //导出表的列名
    private String[] rowName;

    private List<Object[]> dataList = new ArrayList<Object[]>();

    /**
     * 构造方法，传入要导出的数据
     *
     * @param title    是表格的第一行的大标题名字
     * @param rowName  是每一列的头标题
     * @param dataList 是要显示在excel表格的封装好的list类型的数据
     * @param resp
     */
    public ExportExcel(String title, String[] rowName, List<Object[]> dataList, HttpServletResponse resp) {
        this.dataList = dataList;
        this.rowName = rowName;
        this.title = title;
        this.response = resp;
    }

    public static XSSFWorkbook exportExcel(List<Map<String, Object>> list, EntityExcel entityExcel) throws UnsupportedEncodingException {
        /**String startTime = request.getParameter("startTime");
         String endTime = request.getParameter("endTime");
         int consumesType = Integer.parseInt(request.getParameter("consumesType"));
         int conPaymentStatus =Integer.parseInt(request.getParameter("conPaymentStatus"));*/

        /**VipConsumesExample example = new VipConsumesExample();
         if(consumesType!=0 && conPaymentStatus!=0){
         example.createCriteria().andTimeBetween(startTime, endTime).andConsumeTypeEqualTo(consumesType).andStatusEqualTo(conPaymentStatus);
         }else if(consumesType ==0 && conPaymentStatus!=0) {
         example.createCriteria().andTimeBetween(startTime, endTime).andStatusEqualTo(conPaymentStatus);
         }else if(consumesType!=0 && conPaymentStatus==0){
         example.createCriteria().andTimeBetween(startTime, endTime).andConsumeTypeEqualTo(consumesType);
         }else {
         example.createCriteria().andTimeBetween(startTime, endTime);
         }
         list = this.vipConsumesDao.selectByExample(example);*/

        String fileName = entityExcel.getFileName();
        String title = entityExcel.getTitle();
        String[] key = entityExcel.getKey();
        String[] rowName = entityExcel.getRowName();
        //判断标题
        if (key.length < 0 && rowName.length < 0) {
            System.err.println("数据格式不匹配");
        }

        //二、 数据转成excel
        //request.setCharacterEncoding("UTF-8");
        //response.setCharacterEncoding("UTF-8");
        //response.setContentType("application/x-download");

        fileName = URLEncoder.encode(fileName, "UTF-8");
        //response.addHeader("Content-Disposition", "attachment;filename=" + fileName+".xlsx");
        // 第一步：定义一个新的工作簿
        XSSFWorkbook wb = new XSSFWorkbook();
        // 第二步：创建一个Sheet页
        XSSFSheet sheet = wb.createSheet(entityExcel.getTitle());
        sheet.setDefaultRowHeight((short) (2 * 256));//设置行高
        sheet.setColumnWidth(0, 4000);//设置列宽
        sheet.setColumnWidth(1, 5500);
        sheet.setColumnWidth(2, 5500);
        sheet.setColumnWidth(3, 5500);
        sheet.setColumnWidth(11, 3000);
        sheet.setColumnWidth(12, 3000);
        sheet.setColumnWidth(13, 3000);
        XSSFFont font = wb.createFont();
        font.setFontName("宋体");
        font.setFontHeightInPoints((short) 16);

        /**
         * 创建第一条标题title
         * 标题格式  未定义
         *    HSSFCellStyle columnTopStyle = this.getColumnTopStyle(workbook);//获取列头样式对象
         *    HSSFCellStyle style = this.getStyle(workbook);//单元格样式对象
         *
         */


        XSSFCellStyle alignStyle = wb.createCellStyle();

        //内容居中
        alignStyle.setAlignment(HorizontalAlignment.CENTER);
        alignStyle.setFillForegroundColor((short) 13);
        //合并单元格
        CellRangeAddress cellRangeAddress = new CellRangeAddress(0, 0, 0, key.length - 1);
        sheet.addMergedRegion(cellRangeAddress);
        XSSFRow row = sheet.createRow(0);
        XSSFCell cell = row.createCell(0);
        cell.setCellStyle(alignStyle);
        cell.setCellValue(title);

        //创建第二条，每一列的标题row
        XSSFRow rowTitle = sheet.createRow(1);

        for (int i = 0; i < rowName.length; i++) {
            XSSFCell cellTitle = rowTitle.createCell(i);
            cellTitle.setCellValue(rowName[i]);
        }

        XSSFRow rows;
        XSSFCell cells;
        for (int i = 0; i < list.size(); i++) {

            // 第三步：在这个sheet页里创建一行
            rows = sheet.createRow(i + 2);
            // 第四步：在该行创建一个单元格i
            for (int j = 0; j < key.length; j++) {
                cells = rows.createCell(j);
                // 第五步：在该单元格里设置值
                cells.setCellValue(list.get(i).get(key[j]).toString());
            }

        }
        return wb;
        /**File fileExcel = new File(filePath);
         if(!fileExcel.exists()){
         fileExcel.createNewFile();
         }
         FileOutputStream fileOutputStream = new FileOutputStream(fileExcel);
         wb.write(fileOutputStream);
         fileOutputStream.close();*/
    }

    public static void main(String[] args) throws IOException {
        String filePath = "D://test11.xlsx";
        File fileExcel = new File(filePath);
        if (!fileExcel.exists()) {
            fileExcel.createNewFile();
        }
        EntityExcel entityExcel = new EntityExcel();
        entityExcel.setTitle("这是新的文件名字");
        entityExcel.setFileName("新的文件");
        String[] rowName = {"姓名", "性别", "年龄"};
        String[] key = {"name", "sex", "age"};
        entityExcel.setRowName(rowName);
        entityExcel.setKey(key);
        List<Map<String, Object>> maps = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("name", i + "name");
            map.put("sex", i + "sex");
            map.put("age", i + "age");
            maps.add(map);
        }
        File file = new File(filePath);
        try {
           XSSFWorkbook wb =  exportExcel(maps, entityExcel);
             FileOutputStream fileOutputStream = new FileOutputStream(file);
            wb.write(fileOutputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
