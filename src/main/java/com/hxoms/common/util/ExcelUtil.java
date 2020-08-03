package com.hxoms.common.util;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ooxml.POIXMLDocument;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
     * 导出2003版本以下的Excel
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
     * 导出2007版本以上的Excel
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

    /**
     * 设置样式
     * @param workbook
     * @param cell
     * @param rowno 行号
     */
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

    /**
     * 解析2007Excel 版本以上
     * @param path 文件绝对路径
     * @return
     */
    public static String[][] read07Excel(String path) {
        String[][] values = null;
        OPCPackage opcPackage = null;
        Workbook workbook = null;
        try {
            opcPackage = POIXMLDocument.openPackage(path);
            workbook = new XSSFWorkbook(opcPackage);
            if (workbook == null) return null;

            Sheet sheet = workbook.getSheetAt(0);
            if (sheet == null) return null;

            Row row = sheet.getRow(0);
            if (row == null) return null;

            int physicalNumberOfRows = sheet.getPhysicalNumberOfRows();
            int physicalNumberOfCells = row.getPhysicalNumberOfCells();
            values = new String[physicalNumberOfRows][physicalNumberOfCells];

            for (int i = 0; i < physicalNumberOfRows; i++) {
                row = sheet.getRow(i);
                if (row == null) continue;

                for (int j = 0; j < physicalNumberOfCells; j++) {
                    Cell cell = row.getCell(j);
                    if (cell == null) continue;

                    CellType cellType = cell.getCellType();
                    switch (cellType) {
                        case _NONE:
                            values[i][j] = "";
                            break;
                        case STRING:
                            values[i][j] = cell.getStringCellValue();
                            break;
                        case BLANK:
                            values[i][j] = "";
                            break;
                        case BOOLEAN:
                            boolean booleanCellValue = cell.getBooleanCellValue();
                            values[i][j] = String.valueOf(booleanCellValue);
                            break;
                        case ERROR:
                            values[i][j] = "";
                            break;
                        case NUMERIC:
                            if (HSSFDateUtil.isCellDateFormatted(cell)) {
                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                                String format = simpleDateFormat.format(cell.getDateCellValue());
                                values[i][j] = format;
                            } else {
                                double numericCellValue = cell.getNumericCellValue();
                                values[i][j] = String.valueOf(numericCellValue);
                            }
                            break;
                        default:
                            break;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return values;
    }

    /**
     * 解析2003Excel版本以下
     * @param path 文件绝对路径
     * @return
     */
    public static String[][] read03Excel(String path) {
        FileInputStream fileInputStream = null;
        String[][] values = null;
        try {
            fileInputStream = new FileInputStream(path);
            Workbook workbook = new HSSFWorkbook(fileInputStream);

            Sheet sheetAt = workbook.getSheetAt(0);
            if (sheetAt == null) return null;

            Row row = sheetAt.getRow(0);
            if (row == null) return null;

            int physicalNumberOfRows = sheetAt.getPhysicalNumberOfRows();
            int physicalNumberOfCells = row.getPhysicalNumberOfCells();
            values = new String[physicalNumberOfRows][physicalNumberOfCells];

            for (int i = 0; i < physicalNumberOfRows; i++) {
                row = sheetAt.getRow(i);
                if (row == null) continue;

                for (int j = 0; j < physicalNumberOfCells; j++) {
                    Cell cell = row.getCell(j);
                    if (cell == null) continue;

                    CellType cellType = cell.getCellType();
                    switch (cellType) {
                        case _NONE:
                            values[i][j] = "";
                            break;
                        case BLANK:
                            values[i][j] = "";
                            break;
                        case ERROR:
                            values[i][j] = "";
                            break;
                        case BOOLEAN:
                            values[i][j] = String.valueOf(cell.getBooleanCellValue());
                            break;
                        case STRING:
                            values[i][j] = cell.getStringCellValue();
                            break;
                        case NUMERIC:
                            if (HSSFDateUtil.isCellDateFormatted(cell)) {
                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                                String format = simpleDateFormat.format(cell.getDateCellValue());
                                values[i][j] = format;
                            } else {
                                double numericCellValue = cell.getNumericCellValue();
                                values[i][j] = String.valueOf(numericCellValue);
                            }
                        default:break;
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.err.println("文个路径不对");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if(fileInputStream!=null){
                    fileInputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return values;
    }




    public static void main(String[] args) {

        String path = "D:/2020-07-03.xlsx";
        String[][] values = read07Excel(path);

    }

}

