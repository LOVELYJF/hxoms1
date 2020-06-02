package com.hxoms.common.util.file;

import com.hxoms.common.utils.DomainObjectUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.Range;
import org.apache.poi.ooxml.POIXMLDocument;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

@Component
public class OmsFileUtils {
    Logger logger = LoggerFactory.getLogger(OmsFileUtils.class);

    @Value("${omsFile.baseDir}")
    private String baseDir;

    public String getBaseDir() {
        return baseDir;
    }

    /**
     * 复制整个文件夹的内容
     *
     * @param oldPath 主目录
     * @param newPath 复制结果目录
     */
    public void copyFolder(String oldPath, String newPath) {
        try {
            String oldPath1 = baseDir + File.separator + oldPath;
            String newPath1 = baseDir + File.separator + newPath;
            //如果文件夹不存在 则建立新文件夹
            (new File(newPath1)).mkdirs();
            File a = new File(oldPath1);
            String[] file = a.list();
            File temp;
            for (int i = 0; i < file.length; i++) {
                if (oldPath1.endsWith(File.separator)) {
                    temp = new File(oldPath1 + file[i]);
                } else {
                    temp = new File(oldPath1 + File.separator + file[i]);
                }
                String destPath = newPath1 + File.separator + temp.getName();
                if (temp.isFile() && !Files.exists(Paths.get(destPath))) {
                    Files.copy(temp.toPath(), Paths.get(destPath));
                } else if (temp.isDirectory()) {
                    //如果是子文件夹
                    copyFolder(oldPath + File.separator + file[i], newPath + File.separator + file[i]);
                }
            }
        } catch (Exception e) {
            System.out.println("复制整个文件夹内容操作出错");
            e.printStackTrace();
        }
    }

    /**
     * 替换关键词
     *
     * @param srcPath  模板路径
     * @param destPath 结果路径
     * @param map
     * @return
     */
    public static boolean replaceAndGenerateWord(String srcPath, String destPath, Map<String, String> map) {
        String[] sp = srcPath.split("\\.");
        String[] dp = destPath.split("\\.");
        // 判断文件有无扩展名
        if ((sp.length > 0) && (dp.length > 0)) {
            // 比较文件扩展名
            if (sp[sp.length - 1].equalsIgnoreCase("docx")
                    && (dp[dp.length - 1].equalsIgnoreCase("docx"))) {
                return replaceDocx(srcPath, destPath, map);
            } else if ((sp[sp.length - 1].equalsIgnoreCase("doc"))
                    && (dp[dp.length - 1].equalsIgnoreCase("doc"))) {
                // doc只能生成doc，如果生成docx会出错
                return replaceDoc(srcPath, destPath, map);
            } else if ((sp[sp.length - 1].equalsIgnoreCase("xlsx"))
                    && (dp[dp.length - 1].equalsIgnoreCase("xlsx"))) {
                return replaceXSSFWorkbook(srcPath, destPath, map);
            } else if ((sp[sp.length - 1].equalsIgnoreCase("xls"))
                    && (dp[dp.length - 1].equalsIgnoreCase("xls"))) {
                return replaceHSSFWorkbook(srcPath, destPath, map);
            }
        }
        return false;
    }

    /**
     * 下载文件
     *
     * @param filePath 文件路径
     * @param fileName 文件名字
     */
    public void downloadFile(String filePath, String fileName) {
        OutputStream out = null;
        FileInputStream in = null;
        try {
            in = new FileInputStream(filePath);
            HttpServletResponse response = DomainObjectUtil.getResponse();
            HttpServletRequest request = DomainObjectUtil.getRequest();
            response.reset();
            //浏览器设置
            String userAgent = request.getHeader("User-Agent");
            if (userAgent.contains("MSIE") || userAgent.contains("Trident")) {
                //IE浏览器处理
                fileName = java.net.URLEncoder.encode(fileName, "UTF-8");
            } else {
                // 非IE浏览器的处理：
                fileName = new String(fileName.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);
            }
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/octet-stream");
            //3.设置content-disposition响应头控制浏览器以下载的形式打开文件
            response.addHeader("Content-Disposition", "attachment;filename=" + fileName);
            //获取文件输入流
            int len = 0;
            byte[] buffer = new byte[1024];
            out = response.getOutputStream();
            while ((len = in.read(buffer)) > 0) {
                //将缓冲区的数据输出到客户端浏览器
                out.write(buffer, 0, len);
            }
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 替换docx关键词
     *
     * @param srcPath  模板路径
     * @param destPath 结果路径
     * @param map
     * @return
     */
    private static boolean replaceDocx(String srcPath, String destPath, Map<String, String> map) {
        try {
            XWPFDocument document = new XWPFDocument(POIXMLDocument.openPackage(srcPath));
            // 替换段落中的指定文字
            Iterator<XWPFParagraph> itPara = document.getParagraphsIterator();
            while (itPara.hasNext()) {
                XWPFParagraph paragraph = itPara.next();
                List<XWPFRun> runs = paragraph.getRuns();
                for (int i = 0; i < runs.size(); i++) {
                    String oneparaString = runs.get(i).getText(runs.get(i).getTextPosition());
                    if (oneparaString.contains("${")){
                        for (Map.Entry<String, String> entry : map.entrySet()) {
                            if (!StringUtils.isBlank(oneparaString)) {
                                oneparaString = oneparaString.replace(entry.getKey(), entry.getValue());
                            }
                        }
                    }
                    runs.get(i).setText(oneparaString, 0);
                }
            }

            // 替换表格中的指定文字
            Iterator<XWPFTable> itTable = document.getTablesIterator();
            while (itTable.hasNext()) {
                XWPFTable table = itTable.next();
                int rcount = table.getNumberOfRows();
                for (int i = 0; i < rcount; i++) {
                    XWPFTableRow row = table.getRow(i);
                    List<XWPFTableCell> cells = row.getTableCells();
                    for (XWPFTableCell cell : cells) {
                        String cellTextString = cell.getText();
                        if (cellTextString.contains("${")){
                            for (Map.Entry<String, String> e : map.entrySet()) {
                                if (cellTextString.contains(e.getKey())) {
                                    cellTextString = cellTextString.replace(e.getKey(), e.getValue());
                                }
                            }
                        }
                        cell.removeParagraph(0);
                        cell.setText(cellTextString);
                    }
                }
            }
            FileOutputStream outStream = outStream = new FileOutputStream(destPath);
            document.write(outStream);
            outStream.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 替换doc关键词
     *
     * @param srcPath  模板路径
     * @param destPath 结果路径
     * @param map
     * @return
     */
    private static boolean replaceDoc(String srcPath, String destPath, Map<String, String> map) {
        HWPFDocument document = null;
        try {
            document = new HWPFDocument(new FileInputStream(srcPath));
            Range range = document.getRange();
            for (Map.Entry<String, String> entry : map.entrySet()) {
                range.replaceText(entry.getKey(), entry.getValue());
            }
            FileOutputStream outStream = new FileOutputStream(destPath);
            document.write(outStream);
            outStream.close();
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 处理Excel2007+
     * @param srcPath  模板路径
     * @param destPath 结果路径
     * @param map
     */
    private static boolean replaceXSSFWorkbook(String srcPath, String destPath, Map<String, String> map) {
        try {
            FileInputStream input = new FileInputStream(new File(srcPath));
            Workbook wb = new XSSFWorkbook(OPCPackage.open(input));
            XSSFSheet sheet = (XSSFSheet) wb.getSheetAt(0);
            Iterator<?> rows = sheet.rowIterator();
            while (rows.hasNext()) {
                XSSFRow row = (XSSFRow) rows.next();
                if (row != null) {
                    int num = row.getLastCellNum();
                    for (int i = 0; i < num; i++) {
                        XSSFCell cell = row.getCell(i);
                        String value = cell.getStringCellValue();
                        if (!StringUtils.isBlank(value) && value.contains("${")) {
                            for (Map.Entry<String, String> entry : map.entrySet()) {
                                if (value.equals(entry.getKey())) {
                                    cell.setCellValue(entry.getValue());
                                    break;
                                }
                            }
                        }
                    }
                }
            }
            OutputStream out = new FileOutputStream(new File(destPath));
            wb.write(out);
            input.close();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return true;
    }

    /**
     * 处理excel2003
     *
     * @param srcPath  模板路径
     * @param destPath 结果路径
     * @param map
     */
    private static boolean replaceHSSFWorkbook(String srcPath, String destPath, Map<String, String> map) {
        try {
            POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(srcPath));
            Workbook wb = new HSSFWorkbook(fs);
            HSSFSheet sheet = (HSSFSheet) wb.getSheetAt(0);
            Iterator<?> rows = sheet.rowIterator();
            while (rows.hasNext()) {
                HSSFRow row = (HSSFRow) rows.next();
                if (row != null) {
                    int num = row.getLastCellNum();
                    for (int i = 0; i < num; i++) {
                        HSSFCell cell = row.getCell(i);
                        String value = cell.getStringCellValue();
                        if (!StringUtils.isBlank(value) && value.contains("}$")) {
                            for (Map.Entry<String, String> entry : map.entrySet()) {
                                if (value.equals(entry.getKey())) {
                                    cell.setCellValue(entry.getValue());
                                    break;
                                }
                            }
                        }
                    }
                }
            }
            OutputStream out = new FileOutputStream(new File(destPath));
            wb.write(out);
            fs.close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return true;
    }
}
