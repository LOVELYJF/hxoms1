package com.hxoms.common.util;

import com.hxoms.common.exception.CustomMessageException;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * 导出Excel 工具类
 *
 * @author wuqingfan
 */
public class ExportExcelUtil {

//    private  final static String  DEFAULT_DATEFORMAT_SET = "yyyy-MM-dd hh:mm:ss";
    private  final static String  DEFAULT_DATEFORMAT_SET = "yyyy.MM.dd";
    private final static String DEFAULT_SHEET_NAME="导出报表";

    /**
     * 获取减去年的日期
     * @param subtrYear
     * @return
     */
    public static  String getDateStr(int subtrYear){
            Calendar cal = Calendar.getInstance();
            int nowYear = cal.get(Calendar.YEAR);
            int nowMonth=cal.get(Calendar.MONTH)+1;
            int nowDay=cal.get(Calendar.DATE);

            int lastYear = nowYear-subtrYear;
            if (subtrYear!=0&&nowMonth==2&&nowDay==29){
                nowDay=28;
            }
            StringBuffer bf=new StringBuffer();
            bf.append(lastYear);
            if (nowMonth<10){
                bf.append("0").append(nowMonth);
            }else {
                bf.append(nowMonth);
            }
            if (nowDay<10){
                bf.append("0").append(nowDay);
            }else {
                bf.append(nowDay);
            }
            return bf.toString();
    }

    /**
     * 有标题的导出
     * @param sheetName
     * @param headers
     * @param dataset
     * @param pattern
     * @param response
     */
    public static void exportExcel(String sheetName,String title,String[] headers, List<Object> dataset,String pattern,
                                   HttpServletResponse response) {
        if (headers.length<=0 || StringUtils.isEmpty(pattern))
            throw new CustomMessageException("操作失败");
        exportExcelFun(sheetName,title,headers,dataset,pattern,response);
    }

    /**
     * 无标题的导出
     * @param sheetName
     * @param headers
     * @param dataset
     * @param pattern
     * @param response
     */
    public static void exportNotTitleExcel(String sheetName,String[] headers, List<Object> dataset,String pattern,
                                   HttpServletResponse response) {
        if (headers.length<=0 || StringUtils.isEmpty(pattern))
        throw new CustomMessageException("操作失败");
        exportExcelNotTitleFun(sheetName,headers,dataset,pattern,response);
    }

    /**
     * 无标题的导出
     * @param headers
     * @param dataset
     * @param pattern
     * @param response
     */
    public static void exportNotTitleExcel(String[] headers, List<Object> dataset,String pattern,
                                   HttpServletResponse response) {
        if (headers.length<=0 || StringUtils.isEmpty(pattern))
            throw new CustomMessageException("操作失败");
        exportExcelNotTitleFun(DEFAULT_SHEET_NAME,headers,dataset,pattern,response);
    }

    /**
     * 无标题的导出
     * @param sheetName
     * @param headers
     * @param dataset
     * @param sheetName
     * @param response
     */
    public static void exportNotTitleExcel(String sheetName,String[] headers, List<Object> dataset,
                                           HttpServletResponse response) {
        if (headers.length<=0 || StringUtils.isEmpty(sheetName))
            throw new CustomMessageException("操作失败");
        exportExcelNotTitleFun(sheetName,headers,dataset,DEFAULT_DATEFORMAT_SET,response);
    }

    /**
     * 无标题的导出
     * @param headers
     * @param dataset
     * @param response
     */
    public static void exportNotTitleExcel(String[] headers, List<Object> dataset,
                                   HttpServletResponse response) {
        if (headers.length<=0)
            throw new CustomMessageException("操作失败");
        exportExcelNotTitleFun(DEFAULT_SHEET_NAME,headers,dataset,DEFAULT_DATEFORMAT_SET,response);
    }


    /**
     * 有标题的excel
     * <p>
     * 通用Excel导出方法,利用反射机制遍历对象的所有字段，将数据写入Excel文件中 <br>
     * 此版本生成2007以上版本的文件 (文件后缀：xlsx)
     * </p>
     *
     * @param title    表格标题名
     * @param headers  表格头部标题集合
     * @param dataset  需要显示的数据集合,集合中一定要放置符合JavaBean风格的类的对象。此方法支持的
     *                 JavaBean属性的数据类型有基本数据类型及String,Date
     * @param response 与输出设备关联的流对象，可以将EXCEL文档导出到本地文件或者网络中
     * @param pattern  如果有时间数据，设定输出格式。默认为"yyyy-MM-dd hh:mm:ss"
     */
    private static void exportExcelFun(String sheetName, String title, String[] headers, List<Object> dataset, String pattern,
                                      HttpServletResponse response) {

        /** 开始导出 */
        //创建HSSFWorkbook对象(excel的文档对象)
        HSSFWorkbook wb = new HSSFWorkbook();
        //创建文件样式对象
        HSSFCellStyle style = wb.createCellStyle();
        //获得字体对象
        HSSFFont font = wb.createFont();
        //建立新的sheet对象（excel的表单）
        HSSFSheet sheet = wb.createSheet(sheetName);/**导出EXCEL名称*/
        //在sheet里创建第一行，参数为行索引(excel的行)，可以是0～65535之间的任何一个
        HSSFRow row1 = sheet.createRow(0);
        //创建单元格（excel的单元格，参数为列索引，可以是0～255之间的任何一个
        HSSFCell cell = row1.createCell(0);

        //设置标题字体大小
        font.setFontHeightInPoints((short) 16);
        //加粗
        font.setBold(true);
        // 左右居中 
        style.setAlignment(HorizontalAlignment.CENTER);
        // 上下居中 
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setFont(font);
        cell.setCellStyle(style);
        //设置标题单元格内容
        cell.setCellValue(title);/**标题内容*/
        //合并单元格CellRangeAddress构造参数依次表示起始行，截至行，起始列， 截至列
        sheet.addMergedRegion(new CellRangeAddress(0, 1, 0, headers.length - 1));

        // 生成一个样式  标题样式
        HSSFCellStyle title_style = wb.createCellStyle();
        title_style.setAlignment(HorizontalAlignment.CENTER);//水平居中
        title_style.setVerticalAlignment(VerticalAlignment.CENTER);//垂直居中
        // 设置边框
        title_style.setBorderBottom(BorderStyle.THIN);
        title_style.setBorderLeft(BorderStyle.THIN);
        title_style.setBorderRight(BorderStyle.THIN);
        title_style.setBorderTop(BorderStyle.THIN);

        // 生成一个字体
        HSSFFont title_font = wb.createFont();
        title_font.setFontHeightInPoints((short) 14);
        title_font.setFontName("宋体");
        // 把字体 应用到当前样式
        title_style.setFont(title_font);
        // 自动换行
        title_style.setWrapText(true);

        //在sheet里创建第二行
        HSSFRow row2 = sheet.createRow(2);/**标题栏*/
        for (int t_num = 0; t_num < headers.length; t_num++) {
            int word_leg=headers[t_num].length();
            if (t_num==0){
                sheet.setColumnWidth(t_num, 10 * 256);
            }else if(word_leg>=5&&word_leg<=10){
                sheet.setColumnWidth(t_num, word_leg*3* 256);
            }else
            {
                sheet.setColumnWidth(t_num, 16* 256);
            }

            HSSFCell title_cell = row2.createCell(t_num);
            title_cell.setCellStyle(title_style);
            title_cell.setCellValue(headers[t_num]);
        }


        //创建文件样式对象
        HSSFCellStyle style1 = wb.createCellStyle();
        // 设置边框
        style1.setBorderBottom(BorderStyle.THIN);
        style1.setBorderLeft(BorderStyle.THIN);
        style1.setBorderRight(BorderStyle.THIN);
        style1.setBorderTop(BorderStyle.THIN);

        //获得字体对象
        HSSFFont font1 = wb.createFont();
        //设置单元格字体大小
        font1.setFontHeightInPoints((short) 13);
        //居左
        style1.setAlignment(HorizontalAlignment.LEFT);
        style1.setFont(font1);
        // 自动换行
//        style1.setWrapText(true);

        HSSFRow row = null;//内容区域

        // 遍历集合数据，产生数据行
        Iterator<Object> it = dataset.iterator();
        int index = 0;
        Object t;
        Field[] fields;
        Field field;
        HSSFRichTextString richString;
        Pattern p = Pattern.compile("^//d+(//.//d+)?$");
        Matcher matcher;
        String fieldName;
        String getMethodName;
        Class tCls;
        Method getMethod;
        Object value;
        String textValue;
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        while (it.hasNext()) {
            row = sheet.createRow(index + 3);//创建新的一行
            index++;
            row.createCell(0).setCellValue(index);//序号
            row.getCell(0).setCellStyle(style1);//设置单元格样式

            t = (Object) it.next();
            // 利用反射，根据JavaBean属性的先后顺序，动态调用getXxx()方法得到属性值
            fields = t.getClass().getDeclaredFields();
            for (int i = 0; i < fields.length; i++) {
                field = fields[i];
                fieldName = field.getName();

                getMethodName = "get" + fieldName.substring(0, 1).toUpperCase()
                        + fieldName.substring(1);
                try {
                    tCls = t.getClass();
                    getMethod = tCls.getMethod(getMethodName, new Class[]{});
                    value = getMethod.invoke(t, new Object[]{});
                    // 判断值的类型后进行强制类型转换
                    textValue = null;

                    if (value instanceof Integer) {
                        row.createCell(i + 1).setCellValue((Integer) value);
                    } else if (value instanceof Float) {
                        textValue = String.valueOf((Float) value);
                        row.createCell(i + 1).setCellValue(textValue);
                    } else if (value instanceof Double) {
                        textValue = String.valueOf((Double) value);
                        row.createCell(i + 1).setCellValue(textValue);
                    } else if (value instanceof Long) {
                        row.createCell(i + 1).setCellValue((Long) value);
                    }
                    if (value instanceof Boolean) {
                        textValue = "是";
                        if (!(Boolean) value) {
                            textValue = "否";
                        }
                    } else if (value instanceof Date) {
                        textValue = sdf.format((Date) value);
                    } else {
                        // 其它数据类型都当作字符串简单处理
                        if (value != null) {
                            textValue = value.toString();
                        }

                        //存空的单元格
                        row.createCell(i + 1).setCellValue("");
                    }
                    if (textValue != null) {
                        matcher = p.matcher(textValue);
                        if (matcher.matches()) {
                            // 是数字当作double处理
                            row.createCell(i + 1).setCellValue(Double.parseDouble(textValue));
                        } else {
                            row.createCell(i + 1).setCellValue(textValue);
                        }
                    }

                    row.getCell(i + 1).setCellStyle(style1);//设置单元格样式

                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    // 清理资源
                }

            }

        }

        //输出Excel文件
        OutputStream output = null;
        try {
            output = response.getOutputStream();
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "utf-8");
            wb.write(output);
            output.flush();
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    /**
     * 无标题的excel
     * <p>
     * 通用Excel导出方法,利用反射机制遍历对象的所有字段，将数据写入Excel文件中 <br>
     * 此版本生成2007以上版本的文件 (文件后缀：xlsx)
     * </p>
     *
     * @param headers  表格头部标题集合
     * @param dataset  需要显示的数据集合,集合中一定要放置符合JavaBean风格的类的对象。此方法支持的
     *                 JavaBean属性的数据类型有基本数据类型及String,Date
     * @param response 与输出设备关联的流对象，可以将EXCEL文档导出到本地文件或者网络中
     * @param pattern  如果有时间数据，设定输出格式。默认为"yyyy-MM-dd hh:mm:ss"
     */
    public static void exportExcelNotTitleFun(String sheetName, String[] headers, List<Object> dataset, String pattern,
                                              HttpServletResponse response) {
        /** 开始导出 */
        //创建HSSFWorkbook对象(excel的文档对象)
        HSSFWorkbook wb = new HSSFWorkbook();
        //创建文件样式对象
        HSSFCellStyle style = wb.createCellStyle();
        //建立新的sheet对象（excel的表单）
        HSSFSheet sheet = wb.createSheet(sheetName);/**导出EXCEL名称*/

        // 生成一个样式  标题样式
        HSSFCellStyle title_style = wb.createCellStyle();
        title_style.setAlignment(HorizontalAlignment.CENTER);//水平居中
        title_style.setVerticalAlignment(VerticalAlignment.CENTER);//垂直居中
        // 设置边框
        title_style.setBorderBottom(BorderStyle.THIN);
        title_style.setBorderLeft(BorderStyle.THIN);
        title_style.setBorderRight(BorderStyle.THIN);
        title_style.setBorderTop(BorderStyle.THIN);

        // 生成一个字体
        HSSFFont title_font = wb.createFont();
        title_font.setFontHeightInPoints((short) 14);
        title_font.setFontName("宋体");
        // 把字体 应用到当前样式
        title_style.setFont(title_font);
        // 自动换行
        title_style.setWrapText(true);

        //在sheet里创建第二行
        HSSFRow row2 = sheet.createRow(0);/**标题栏*/
        for (int t_num = 0; t_num < headers.length; t_num++) {
            int word_leg=headers[t_num].length();
            if (t_num==0){
                sheet.setColumnWidth(t_num, 10 * 256);
            }else if(word_leg>=5&&word_leg<=10){
                sheet.setColumnWidth(t_num, word_leg*3* 256);
            }else
            {
                sheet.setColumnWidth(t_num, 16* 256);
            }
            HSSFCell title_cell = row2.createCell(t_num);
            title_cell.setCellStyle(title_style);
            title_cell.setCellValue(headers[t_num]);
        }


        //创建文件样式对象
        HSSFCellStyle style1 = wb.createCellStyle();
        // 设置边框
        style1.setBorderBottom(BorderStyle.THIN);
        style1.setBorderLeft(BorderStyle.THIN);
        style1.setBorderRight(BorderStyle.THIN);
        style1.setBorderTop(BorderStyle.THIN);

        //获得字体对象
        HSSFFont font1 = wb.createFont();
        //设置单元格字体大小
        font1.setFontHeightInPoints((short) 13);
        //居左
        style1.setAlignment(HorizontalAlignment.LEFT);
        style1.setFont(font1);
        // 自动换行
//        style1.setWrapText(true);

        HSSFRow row = null;//内容区域

        // 遍历集合数据，产生数据行
        Iterator<Object> it = dataset.iterator();
        int index = 0;
        Object t;
        Field[] fields;
        Field field;
        HSSFRichTextString richString;
        Pattern p = Pattern.compile("^//d+(//.//d+)?$");
        Matcher matcher;
        String fieldName;
        String getMethodName;
        Class tCls;
        Method getMethod;
        Object value;
        String textValue;
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        while (it.hasNext()) {
            index++;
            row = sheet.createRow(index);//创建新的一行
            row.createCell(0).setCellValue(index);//序号
            row.getCell(0).setCellStyle(style1);//设置单元格样式

            t = (Object) it.next();
            // 利用反射，根据JavaBean属性的先后顺序，动态调用getXxx()方法得到属性值
            fields = t.getClass().getDeclaredFields();
            for (int i = 0; i < fields.length; i++) {
                field = fields[i];
                fieldName = field.getName();

                getMethodName = "get" + fieldName.substring(0, 1).toUpperCase()
                        + fieldName.substring(1);
                try {
                    tCls = t.getClass();
                    getMethod = tCls.getMethod(getMethodName, new Class[]{});
                    value = getMethod.invoke(t, new Object[]{});
                    // 判断值的类型后进行强制类型转换
                    textValue = null;

                    if (value instanceof Integer) {
                        row.createCell(i + 1).setCellValue((Integer) value);
                    } else if (value instanceof Float) {
                        textValue = String.valueOf((Float) value);
                        row.createCell(i + 1).setCellValue(textValue);
                    } else if (value instanceof Double) {
                        textValue = String.valueOf((Double) value);
                        row.createCell(i + 1).setCellValue(textValue);
                    } else if (value instanceof Long) {
                        row.createCell(i + 1).setCellValue((Long) value);
                    }
                    if (value instanceof Boolean) {
                        textValue = "是";
                        if (!(Boolean) value) {
                            textValue = "否";
                        }
                    } else if (value instanceof Date) {
                        textValue = sdf.format((Date) value);
                    } else {
                        // 其它数据类型都当作字符串简单处理
                        if (value != null) {
                            textValue = value.toString();
                        }

                        //存空的单元格
                        row.createCell(i + 1).setCellValue("");
                    }
                    if (textValue != null) {
                        matcher = p.matcher(textValue);
                        if (matcher.matches()) {
                            // 是数字当作double处理
                            row.createCell(i + 1).setCellValue(Double.parseDouble(textValue));
                        } else {
                            row.createCell(i + 1).setCellValue(textValue);
                        }
                    }

                    row.getCell(i + 1).setCellStyle(style1);//设置单元格样式

                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    // 清理资源
                }

            }

        }

        //输出Excel文件
        OutputStream output = null;
        try {
            output = response.getOutputStream();
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "utf-8");
            wb.write(output);
            output.flush();
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}