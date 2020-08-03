package com.hxoms.common.util;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class ExcelUtil {

    /**
     * 下载Excel
     *
     * @param workbook
     * @param response
     */
    public static void download(Workbook workbook, HttpServletResponse response) {

        DateFormat dateFormat = DateFormat.getDateTimeInstance();
        String fileName = dateFormat.format(new Date()).replace("-", "").replace(":", "").replace(" ", "");
        System.out.println(workbook.getClass() + "-----" + fileName);

        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/vnd.ms-excel");

        Class<? extends Workbook> workbookClass = workbook.getClass();
        String simpleName = workbookClass.getSimpleName();

        ServletOutputStream outputStream = null;
        BufferedOutputStream bufferedOutputStream = null;
        try {
            fileName = URLEncoder.encode(fileName, "UTF-8");
            if ("HSSFWorkbook".equals(simpleName)) {
                fileName = fileName + ".xls";
            }
            if ("XSSFWorkbook".equals(simpleName)) {
                fileName = fileName + ".xlsx";
            }

            response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
            outputStream = response.getOutputStream();
            bufferedOutputStream = new BufferedOutputStream(outputStream);
            workbook.write(bufferedOutputStream);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (bufferedOutputStream != null) {
                    bufferedOutputStream.close();
                }
                if (outputStream != null) {
                    outputStream.close();
                }
                if (workbook != null) {
                    workbook.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 数据封装
     *
     * @param data        Excel数据
     * @param columnValue Excel表头标题
     * @return
     */
    public static String[][] envelopedData(List<Map<String, Object>> data, List<Map<String, String>> columnValue) {

        String[][] values = null;

        if (columnValue != null && columnValue.size() > 0) {

            if (data != null && data.size() > 0) {
                values = new String[data.size() + 1][columnValue.size()];
            } else {
                values = new String[0][columnValue.size()];
            }

            for (int i = 0; i < columnValue.size(); i++) {
                Map<String, String> stringStringMap = columnValue.get(i);
                for (String key : stringStringMap.keySet()) {
                    values[0][i] = stringStringMap.get(key) == null ? "" : stringStringMap.get(key).trim();
                }
            }

            if (data != null && data.size() > 0) {
                for (int i = 0; i < data.size(); i++) {

                    Map<String, Object> stringObjectMap = data.get(i);

                    for (int j = 0; j < columnValue.size(); j++) {
                        Map<String, String> stringStringMap = columnValue.get(j);
                        String columnKey = "";
                        for (String key : stringStringMap.keySet()) {
                            columnKey = key.toUpperCase();
                        }

                        for (String key : stringObjectMap.keySet()) {
                            if (columnKey.equals(key.toUpperCase())) {
                                values[i + 1][j] = stringObjectMap.get(key) == null ? "" : stringObjectMap.get(key).toString().trim();
                            }
                        }
                    }
                }
            }
        }
        return values;
    }

    /**
     * 导出2003版Excel
     *
     * @param data        导出的数据
     * @param columnValue 表头标题 传入方如：[{"字段":"中文描述"}] 字段必须和查询出来的字段一至，字段不区分大小写
     */
    public static Workbook writer03Excel(List<Map<String, Object>> data, List<Map<String, String>> columnValue) {
        //数据封装
        String[][] values = envelopedData(data, columnValue);

        Workbook workbook = null;

        if (values != null && values.length > 0) {
            //1、创建一个工作薄
            workbook = new HSSFWorkbook();
            //2、创建sheet
            Sheet sheet = workbook.createSheet();

            for (int i = 0; i < values.length; i++) {
                String[] value = values[i];
                Row row = sheet.createRow(i);

                if (value != null && value.length > 0) {
                    for (int j = 0; j < value.length; j++) {
                        Cell cell = row.createCell(j);
                        cell.setCellValue(value[j] == null ? "" : value[j].trim());
                    }
                }
            }
        }
        return workbook;
    }

    /**
     * 导出2007版Excel
     *
     * @param data        导出的数据
     * @param columnValue 表头标题 传入方如：[{"字段":"中文描述"}] 字段必须和查询出来的字段一至，字段不区分大小写
     */
    public static Workbook writer07Excel(List<Map<String, Object>> data, List<Map<String, String>> columnValue) {
        //数据封装
        String[][] values = envelopedData(data, columnValue);
        //1、创建一个工作薄
        Workbook workbook = null;

        if (values != null && values.length > 0) {
            workbook = new XSSFWorkbook();
            //2、创建sheet
            Sheet sheet = workbook.createSheet();
            //冻结第一行
            sheet.createFreezePane(0, 1, 0, 1);

            for (int i = 0; i < values.length; i++) {
                String[] value = values[i];
                Row row = sheet.createRow(i);
                if (value != null && value.length > 0) {
                    for (int j = 0; j < value.length; j++) {
                        Cell cell = row.createCell(j);
                        //标题样式设置
                        setCellStyle(workbook, cell, i);

                        cell.setCellValue(value[j] == null ? "" : value[j].trim());
                    }
                }
            }
        }
        return workbook;
    }

    public static void setCellStyle(Workbook workbook, Cell cell, int rowno) {
        CellStyle cellStyle = workbook.createCellStyle();
        Font font = workbook.createFont();
        if (rowno == 0) {
            font.setBold(true);
            cellStyle.setFont(font);

            cellStyle.setAlignment(HorizontalAlignment.CENTER);
            cell.setCellStyle(cellStyle);
        }
    }
}

