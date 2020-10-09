package com.hxoms.modules.leaderSupervision.until;

import com.github.pagehelper.util.StringUtil;
import com.hxoms.common.exception.CustomMessageException;
import com.hxoms.common.utils.BeanUtilSelf;
import com.hxoms.common.utils.Constants;
import com.hxoms.common.utils.StringUilt;
import com.hxoms.modules.leaderSupervision.Enum.BussinessApplyStatus;
import com.hxoms.modules.leaderSupervision.vo.LeaderSupervisionVo;
import com.hxoms.modules.leaderSupervision.vo.OmsJiweiOpinionVo;
import com.hxoms.modules.omsregcadre.entity.OmsRegProcbatch;
import com.hxoms.modules.omsregcadre.entity.OmsRegProcpersoninfo;
import com.hxoms.modules.omsregcadre.entity.OmsRegProcpersoninfoVO;
import com.hxoms.support.b01.entity.B01;
import io.swagger.models.auth.In;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.model.InternalSheet;
import org.apache.poi.hssf.record.ExtendedFormatRecord;
import org.apache.poi.hssf.record.aggregates.PageSettingsBlock;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.xhtmlrenderer.css.parser.property.PrimitivePropertyBuilders;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;


@Component
@PropertySource("classpath:/leaderSupervision.porperties")
public class LeaderSupervisionUntil {


    private static String leaderbatchPrefix;

    public static String leaderselectorFormatter;

    private static int leaderstepNum;

    public final static String prefixPdfStyle = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n" +
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
            "<body class=\"bg-white pb-3\"  style = \"font-family: SimSun;\">" +
            "<div id=\"footer\" style=\"\"/> ";

    public final static String suffixPdfStyle = "</body>\n" + "</html>";

    // 缓存 因私状态
    private static Map<String, String> mappri = new HashMap<String, String>();


    // 生成批次号的方法

    /***
     * HNSW-20200610001,HNSW-20200610002
     *
     */
    public static String factoryBatchCode(String maxBatchCode) {

        String batchCode = "";
        //如果当天 未建批次，或批次为空
        if (StringUilt.stringIsNullOrEmpty(maxBatchCode) || "wu".equals(maxBatchCode)) {

            batchCode += leaderbatchPrefix + "-" + selectorDateFormat(leaderselectorFormatter) + "001";

        } else {

            String stepNumstr = maxBatchCode.substring(maxBatchCode.length() - leaderstepNum);

            int curentbatchCode = Integer.valueOf(stepNumstr) + 1;

            String curentbatchCodestr = "";

            for (int i = String.valueOf(curentbatchCode).length(); i < leaderstepNum; i++) {

                curentbatchCodestr += 0;


            }


            batchCode += maxBatchCode.substring(0, maxBatchCode.length() - 3) + curentbatchCodestr + curentbatchCode;

        }


        return batchCode;
    }

    // 时间 选择器
    public static String selectorDateFormat(String selectorFormatter) {

        SimpleDateFormat formatter = null;

        formatter = new SimpleDateFormat(selectorFormatter);


        String batchSuffix = formatter.format(new Date());


        return batchSuffix;
    }

    // 统一判断参数 异常
    // 对必传的参数进行异常判断
    public static void throwableByParam(Object... objs) {

        if (objs != null && objs.length > 0) {

            for (Object obj : objs) {

                if (obj == null) {

                    throw new CustomMessageException("参数 不能 为空，请仔细检查");
                } else {


                    if (obj.getClass() == String.class) {

                        if (StringUilt.stringIsNullOrEmpty((String) obj)) {
                            throw new CustomMessageException("参数为空，请仔细检查");
                        }
                    } else if (obj instanceof LeaderSupervisionVo) {

                        if (((LeaderSupervisionVo) obj).getBussinessTypeAndIdVos() == null && ((LeaderSupervisionVo) obj).getBussinessTypeAndIdVos().size() <= 0) {

                            throw new CustomMessageException("参数为空，请仔细检查");

                        }

                    } else if (obj instanceof OmsJiweiOpinionVo) {

                        if (((OmsJiweiOpinionVo) obj).getBussinessTypeAndIdVos() == null && ((OmsJiweiOpinionVo) obj).getBussinessTypeAndIdVos().size() <= 0) {

                            throw new CustomMessageException("参数为空，请仔细检查");

                        }
                    } else if (obj instanceof List) {
                        if (((List) obj).size() <= 0) {

                            throw new CustomMessageException("参数为空，请仔细检查");
                        }

                    } else if (obj.getClass() == Date.class) {
                        if ((Date) obj == null) {

                            throw new CustomMessageException("受理时间为空，请仔细检查");
                        }
                    }

                }

            }
        } else {

            throw new CustomMessageException("所传的参数为空，请仔细检查");

        }

    }

    // 返回 申请业务 类型
    public static String selectorBussinessTypeByName(String bussinessName) {

        int i = 0;
        for (String bussinessType : Constants.oms_businessName) {

            if (bussinessType.contains(bussinessName)) {

                return Constants.oms_business[i];

            }

            i++;

        }

        return null;

    }


    // 根据数组的值 获取数组 下标

    public static Integer getIndexByArray(String[] array, String value) {

        if (null == array || array.length == 0 || StringUilt.stringIsNullOrEmpty(value))
            return null;

        for (int i = 0; i < array.length; i++) {

            if (array[i].equals(value)) {

                return i;
            }


        }

        return null;

    }

    public static String getBatchIdByBuessinessId(String buessinessId, String tableName) {


        return " select leader_batch_id as batchId from " + tableName + "where id =" + buessinessId;


    }

//    public Map getMapByLeaderSupervisionVo(LeaderSupervisionVo leaderSupervisionVo){
//
//        Map map = new HashMap();
//
//
//    }

    /**
     * 根据 纪委意见 修改流程状态
     **/

    public static String updateBussinessStatusByJiweiFlow(String busessId, String bussinesType, String leaderStatusName, String bussinessName) {

        String updateSql = "update " + bussinesType;

        String setSql = " set  ";

        String whereCondition = " where id = '" + busessId + "'";


        for (BussinessApplyStatus applyStatus : BussinessApplyStatus.values()) {

            if (bussinesType.indexOf(applyStatus.getTableName()) != -1) {

                if (!"干教".equals(bussinessName)) {

                    String status = applyStatus.getApplySatus();
                    // 干部监督处的状态
                    setSql += status + "=" + Constants.leader_business[LeaderSupervisionUntil.getIndexByArray(Constants.leader_businessName, leaderStatusName)];

                    break;
                } else if ("干教".equals(bussinessName)) {

                    String status = applyStatus.getApplySatus();
                    // 如果 是 干 教 流程 到纪委征求意见，状态置为 已办结
                    setSql += status + "=" + Constants.leader_business[Constants.leader_business.length - 1];

                    break;


                }


            }

        }

        return updateSql + setSql + whereCondition;


    }


    //导出 数据 类型 为 list<Map>

    public static HSSFWorkbook exportExcelByListMap(List listK, List listV, List<Map> dataList, String sheetName) {


        HSSFWorkbook wb = new HSSFWorkbook();
        // 设置 sheet 页
        HSSFSheet sheet0 = wb.createSheet(sheetName);

        List<HSSFSheet> sheetList = new ArrayList<HSSFSheet>();
        sheetList.add(sheet0);
        //设置标题样式
        HSSFCellStyle titleStyle = getTitleStyle(wb);
        //设置单元格样式
        HSSFCellStyle cellStyle = getCellStyle(wb);

        for (HSSFSheet sheet : sheetList) {


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
            for (int i = 0; i < dataList.size(); i++) {

                Row nextrow = sheet.createRow(i + 1); //第i行
                Cell cell2 = nextrow.createCell(0);
                cell2.setCellValue(String.valueOf(i + 1));


                Map temp = dataList.get(i);
                for (int j = 1; j < listK.size(); j++) {
                    cell2 = nextrow.createCell(j);
                    if (temp.get(listK.get(j)) != null) {

                        k = temp.get(listK.get(j)).toString();

                        s = (listK.get(j).toString()).toLowerCase();
                        if (s.indexOf("Time") != -1 || s.indexOf("Date") != -1) {
                            if (k.length() > 10) {
                                k = k.substring(0, 10);
                            }

                        }
                        // 如果 该列是 申请状态进行转化
                        if ("applystatus".equals(s)) {

                            k = getK(k, "因私");
                        }

                        cell2.setCellValue(k);
                        cell2.setCellStyle(cellStyle);
                        int currWidth = sheet.getColumnWidth(j);
                        autoSizeColumnOne(j, k, sheet, currWidth);
                    } else {
                        String m = "";
                        cell2.setCellValue(m);
                        cell2.setCellStyle(cellStyle);
                        int currWidth = sheet.getColumnWidth(j);
                        autoSizeColumnOne(j, m, sheet, currWidth);

                    }
                }
            }


        }

        return wb;
    }


    public static String getK(String k, String bussinessType) {


        if ("因私".equals(bussinessType)) {

            // 当两者 状态 不一致 的重新 把新的状态 写到 缓存中
            if (mappri.size() != (Constants.private_business.length + Constants.leader_business.length)) {
                mappri.clear();

                for (int i = 0; i < Constants.private_business.length; i++) {

                    //获取 状态  获取 状态 对应的值
                    mappri.put(String.valueOf(Constants.private_business[i]), Constants.private_businessName[i]);

                }

                for (int j = 0; j < Constants.leader_business.length; j++) {

                    mappri.put(String.valueOf(Constants.leader_business[j]), Constants.leader_businessName[j]);


                }

                return mappri.get(k);

            } else {


                return mappri.get(k);


            }


        }

        return null;

    }

    public static HSSFWorkbook exportRfInfoByListMap(List listK, List listE, List listV, List<OmsRegProcpersoninfoVO> dataList,
                                                     String sheetName1, String sheetName2, OmsRegProcbatch batchinfo,
                                                     B01 b01) throws IOException {


//        HSSFWorkbook wb = new HSSFWorkbook(new FileInputStream("E:\\work\\公司\\干部综合\\干综一体化\\原始需求\\出国境\\ZYF文件\\因私出国登记备案\\国家工作人员登记备案（报送单位填报）模板.xls"));
//        // 设置 sheet 页
//        HSSFSheet sheet0 = wb.getSheet(sheetName1); //wb.createSheet(sheetName1);
//        HSSFSheet sheet1 = wb.getSheet(sheetName2);

        HSSFWorkbook wb = new HSSFWorkbook();
        // 设置 sheet 页
        HSSFSheet sheet0 = wb.createSheet(sheetName1); //wb.createSheet(sheetName1);
        HSSFSheet sheet1 = wb.createSheet(sheetName2);

        //计算第一工作表首页可打印的行数，因为不知道如何获取页高，暂时写死
        int firtRows = 13;
        //计算第一工作表首页外其它页面能打印的行数
        int sechondRows = 22;
        //计算第一工作表页数
        int pages = 1;
        if (dataList.size() > firtRows) {
            double rows = dataList.size() - firtRows;
            pages += (int) Math.floor(rows / sechondRows);
        }
        List<Double> width1 = initialColumnWidth(wb);

        //设置标题样式
        HSSFCellStyle titleStyleOne = getTitlesStyleOne(wb);
        HSSFCellStyle titleStyleTwo = getTitlesStyleTwo(wb);
        HSSFCellStyle titleStyleThree = getTitlesStyleThree(wb);
        HSSFCellStyle titleStyleFour = getTitlesStyleFour(wb);

        //创建第一行
        Row row = sheet0.createRow(0);
        Cell cell1 = row.createCell(0);
        cell1.setCellStyle(titleStyleOne);
        cell1.setCellValue("国家工作人员登记备案表");
        row.setHeightInPoints(47.25f);

        //创建第二行
        Row row2 = sheet0.createRow(1);
        Cell cell2 = row2.createCell(0);
        cell2.setCellStyle(titleStyleTwo);
        cell2.setCellValue("报备单位名称（盖章）：" + batchinfo.getRfUnnit());
        row2.setHeightInPoints(39);

        CellRangeAddress regionNum1 = new CellRangeAddress(0, 0, 0, listV.size() - 1);
        CellRangeAddress regionNum2 = new CellRangeAddress(1, 1, 0, listV.size() - 1);
        sheet0.addMergedRegion(regionNum1);
        sheet0.addMergedRegion(regionNum2);

        //创建第三行
        Row rowNum3 = sheet0.createRow(2);
        rowNum3.setHeightInPoints(54);
        Cell cell = null;

        //创建标题
        for (int i = 0; i < listV.size(); i++) {

            cell = rowNum3.createCell(i);
            cell.setCellValue(listV.get(i).toString());
            cell.setCellStyle(titleStyleThree);
            //256*width+184为网友通过散列计算出相对准确的列宽设置函数，，
            //按照256*字符数与实际有很大偏差，并且不好找规律
            //通过程序读取客户给的样式表列宽，然后设置同样的值一样存在偏差
            sheet0.setColumnWidth(i, (int) (256 * width1.get(i)+ 184) );//width1.get(i)
        }

        //循环多少行
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        int offset = 3;
        for (int i = 0; i < dataList.size(); i++) {

            Row nextrow = sheet0.createRow(i + offset); //第i行
            nextrow.setHeightInPoints(25);

            CreateCell(dataList, nextrow, titleStyleFour, listK, b01, batchinfo, sdf, i);
            //打印第一页的页脚
            if (firtRows == i + 1) {
                writeFoot(wb, sheet0, batchinfo, i + 1, pages, dataList.size(), listV.size());
                offset += 2;
            }
        }
        if (dataList.size() <= firtRows) {
            writeFoot(wb, sheet0, batchinfo, dataList.size(), pages, dataList.size(), listV.size());
        }

        PageSetting(sheet0);

        //第二个sheet
        //创建第一行
        row = sheet1.createRow(0);
        row.setHeightInPoints(40);

        //创建标题
        for (int i = 0; i < listV.size(); i++) {
            cell = row.createCell(i);
            cell.setCellValue(listV.get(i).toString());
            cell.setCellStyle(titleStyleThree);
            //自动设置列宽
            sheet1.autoSizeColumn((short) i);
        }

        //循环多少行
        for (int i = 0; i < dataList.size(); i++) {

            Row nextrow = sheet1.createRow(i + 1); //第i行
            nextrow.setHeightInPoints(25);

            CreateCell(dataList, nextrow, titleStyleFour, listE, b01, batchinfo, sdf, i);
        }
        return wb;
    }

    private static void PageSetting(HSSFSheet sheet0) {
        //第一工作表打印设置
        HSSFPrintSetup printSetup = sheet0.getPrintSetup();
        printSetup.setScale((short) 88);//缩放
        printSetup.setVResolution((short) 600);//设置打印质量
        printSetup.setHResolution((short) 600);
        printSetup.setLandscape(true);//横向
        printSetup.setPaperSize(HSSFPrintSetup.A4_PAPERSIZE);
        printSetup.setHeaderMargin((double) 0.31496062992125984);
        printSetup.setFooterMargin((double) 0.31496062992125984);
        printSetup.setFitWidth((short) 1);
        printSetup.setFitHeight((short) 0);
        sheet0.setMargin(Sheet.LeftMargin, (double) 0.31496062992125984);
        sheet0.setMargin(Sheet.RightMargin, (double) 0.31496062992125984);
        sheet0.setMargin(Sheet.TopMargin, (double) 0.7480314960629921);
        sheet0.setMargin(Sheet.BottomMargin, (double) 0.7480314960629921);
    }

    private static List<Double> initialColumnWidth(HSSFWorkbook wb) {
        //sheet1列宽
        List<Double> width1 = new ArrayList<>();
        //excel中通列宽看到的值
        width1.add(4d);//序号
        width1.add(3.5d);//中文姓
        width1.add(4.63d);//中文名
        width1.add(3.25d);//性别
        width1.add(8d);//出生日期
        width1.add(16.13d);//身份证号
        width1.add(13d);//户口所在地
        width1.add(4d);//入库标识
        width1.add(18d);//工作单位
        width1.add(6.25d);//职务(级)或职称
        width1.add(14.63d);//人事主管单位
        width1.add(10d);//报送单位组织机构代码
        width1.add(14.63d);//报送单位名称
        width1.add(6d);//报送单位类别
        width1.add(8d);//报送单位联系人
        width1.add(8d);//联系电话
        width1.add(8.75d);//入库批号

        //实际发现宽度与客户的一致了，但像素少列宽的字符数，通过修改列默认样式，与客户一致来纠正，
        //通过客户提供的EXCEL文件的常规查看字体和字号来纠正
        wb.getFontAt(0).setFontName("宋体");
        wb.getFontAt(0).setFontHeightInPoints((short) 11);

        //从客户样式表通过程序读取的值
//        width1.add(1184d);//序号
//        width1.add(1056d);//中文姓
//        width1.add(1344d);//中文名
//        width1.add(992d);//性别
//        width1.add(2208d);//出生日期
//        width1.add(4288d);//身份证号
//        width1.add(3488d);//户口所在地
//        width1.add(1184d);//入库标识
//        width1.add(4768d);//工作单位
//        width1.add(1760d);//职务(级)或职称
//        width1.add(3904d);//人事主管单位
//        width1.add(2720d);//报送单位组织机构代码
//        width1.add(3904d);//报送单位名称
//        width1.add(1696d);//报送单位类别
//        width1.add(2208d);//报送单位联系人
//        width1.add(2400d);//联系电话
//        width1.add(2400d);//入库批号

        return width1;
    }

    private static void CreateCell(List<OmsRegProcpersoninfoVO> dataList,
                                   Row nextrow, HSSFCellStyle titleStyleFour,
                                   List listK, B01 b01,
                                   OmsRegProcbatch batchinfo,
                                   SimpleDateFormat sdf,
                                   int i) {
        Cell cell2 = nextrow.createCell(0);
        cell2.setCellValue(String.valueOf(i + 1));
        cell2.setCellStyle(titleStyleFour);
        OmsRegProcpersoninfo temp = dataList.get(i);
        for (int j = 1; j < listK.size(); j++) {
            if (listK.get(j) == null) continue;
            Cell cell3 = nextrow.createCell(j);
            cell3.setCellStyle(titleStyleFour);
            Object value = BeanUtilSelf.getFieldValueByFieldName(listK.get(j).toString(), temp);
            if (j < 11 && value != null) {
                String k = value.toString();

                if (value instanceof Date) {
                    k = sdf.format((Date) value);
                }
                cell3.setCellValue(k);
            } else {
                SetSubmitInfo(cell3, b01, batchinfo, j);
            }
        }
    }

    private static void SetSubmitInfo(Cell cell, B01 b01, OmsRegProcbatch batchinfo, int j) {
        String m = "";
        if (j == 11) {
            m = b01.getOrganization_code();
        } else if (j == 12) {
            m = batchinfo.getSubmitUname();
        } else if (j == 13) {
            m = "10";
        } else if (j == 14) {
            m = batchinfo.getSubmitUcontacts();
        } else if (j == 15) {
            m = batchinfo.getSubmitPhone();
        } else if (j == 16) {
            m = batchinfo.getBatchNo();
        }
        cell.setCellValue(m);
    }

    private static void SetMergeCellBorder(CellRangeAddress mergeCell, Sheet sheet) {
        RegionUtil.setBorderBottom(BorderStyle.THIN, mergeCell, sheet);
        RegionUtil.setBorderLeft(BorderStyle.THIN, mergeCell, sheet);
        RegionUtil.setBorderRight(BorderStyle.THIN, mergeCell, sheet);
        RegionUtil.setBorderTop(BorderStyle.THIN, mergeCell, sheet);
    }

    private static void writeFoot(HSSFWorkbook wb, HSSFSheet sheet0, OmsRegProcbatch batchinfo,
                                  int i, int pages, int personCount, int columnCount) {

        CellRangeAddress regionNum3 = new CellRangeAddress(i + 3, i + 3, 0, columnCount - 1);
        CellRangeAddress regionNum4 = new CellRangeAddress(i + 4, i + 4, 0, columnCount - 1);
        sheet0.addMergedRegion(regionNum3);
        sheet0.addMergedRegion(regionNum4);

        HSSFCellStyle titleStyleFive = getTitlesStyleFive(wb);

        Row nextrow1 = sheet0.createRow(i + 3); //第i行
        nextrow1.setHeightInPoints(30);
        Cell celladdOther1 = nextrow1.createCell(0);
        celladdOther1.setCellValue("本次备案共 " + pages + " 页 " + personCount + " 人             此为第 1 页                           报送时间 ：     年   月   日\t\t\t\t\t\t\t\t\t\t\t\t\t\n");
        celladdOther1.setCellStyle(titleStyleFive);

        Row nextrow2 = sheet0.createRow(i + 4); //第i行
        nextrow2.setHeightInPoints(28.5f);
        Cell celladdOther2 = nextrow2.createCell(0);
        celladdOther2.setCellValue("备案单位负责人：                   联系人：" + batchinfo.getRfUcontacts() + "     联系电话：" + batchinfo.getRfUphone());
        celladdOther2.setCellStyle(titleStyleFive);
    }

    // 设置列宽方法1
    public void autoSizeColumn(List listV, HSSFSheet sheet) {
        for (int i = 0; i < listV.size(); i++) {

            sheet.autoSizeColumn((short) i); //调整第i列宽度
        }
    }
    // 设置列宽方法2

    /**
     * 注意汉字*512
     * 数字*256
     **/
    public static void autoSizeColumnOne(int j, String k, HSSFSheet sheet, int currWidth) {

        if (StringUtil.isEmpty(k)) {
            sheet.setColumnWidth(j, 512 * 4);
        } else {
            //再次设置列宽  只增 不减
            if (k.length() * 512 > currWidth) {

                sheet.setColumnWidth(j, k.length() * 256); //调整第i列宽度
            }

        }
        sheet.autoSizeColumn((short) j);
    }

    //设置标题表单样式

    public static HSSFCellStyle getTitleStyle(HSSFWorkbook workbook) {


        HSSFCellStyle style = workbook.createCellStyle();


        style.setAlignment(HorizontalAlignment.CENTER); //居中

        //设置字体
        HSSFFont font = workbook.createFont();
        font.setFontName("仿宋_GB2312");
        font.setBold(true);//字体加粗

        style.setFont(font);

        return style;
    }

    public static HSSFCellStyle getStyle(HSSFWorkbook workbook, String fontName,
                                         short fontSize, boolean bold, HorizontalAlignment ha,
                                         VerticalAlignment va) {
        HSSFFont font = workbook.createFont();
        HSSFCellStyle style = workbook.createCellStyle();
        font.setFontHeightInPoints(fontSize);// 字号
        font.setBold(bold);//字体加粗
        font.setFontName(fontName);
        style.setFont(font);
        style.setAlignment(ha);// 水平居中
        style.setVerticalAlignment(va);//垂直居中
        return style;
    }

    public static void setStyleBorder(HSSFCellStyle style) {
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
    }

    //设置备案表标题表单样式
    public static HSSFCellStyle getTitlesStyleOne(HSSFWorkbook workbook) {
        return getStyle(workbook, "宋体", (short) 18, true, HorizontalAlignment.CENTER, VerticalAlignment.CENTER);
    }

    //设置备案表表单样式
    public static HSSFCellStyle getTitlesStyleTwo(HSSFWorkbook workbook) {
        return getStyle(workbook, "宋体", (short) 11, false, HorizontalAlignment.LEFT, VerticalAlignment.CENTER);
    }

    //设置备案表表头样式
    public static HSSFCellStyle getTitlesStyleThree(HSSFWorkbook workbook) {
        HSSFCellStyle style = getStyle(workbook, "宋体", (short) 11, true, HorizontalAlignment.CENTER, VerticalAlignment.CENTER);
        setStyleBorder(style);
        style.setWrapText(true);
        return style;
    }


    //设置备案表表头样式
    public static HSSFCellStyle getTitlesStyleFour(HSSFWorkbook workbook) {
        HSSFCellStyle style = getStyle(workbook, "宋体", (short) 10, false, HorizontalAlignment.CENTER, VerticalAlignment.CENTER);
        setStyleBorder(style);
        style.setWrapText(true);
        return style;
    }


    //设置备案表表头样式
    public static HSSFCellStyle getTitlesStyleFive(HSSFWorkbook workbook) {
        HSSFCellStyle style = getStyle(workbook, "宋体", (short) 11, false, HorizontalAlignment.LEFT, VerticalAlignment.CENTER);
        return style;
    }


    //设置单元格样式
    public static HSSFCellStyle getCellStyle(HSSFWorkbook workbook) {

        HSSFCellStyle style = workbook.createCellStyle();

        style.setAlignment(HorizontalAlignment.LEFT); //居左

        //设置字体
        HSSFFont font = workbook.createFont();
        font.setFontName("仿宋_GB2312");
        font.setBold(true);//字体加粗
        return style;
    }


    //文件上传工具类服务方法
    public static void uploadFile(byte[] file, String filePath, String fileName) {

        File targetFile = new File(filePath);
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }
        try {
            FileOutputStream out = new FileOutputStream(filePath + fileName);
            out.write(file);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();

        } finally {


        }
    }


    /**
     * @Desc: 文件下载
     */
    public static byte[] downloadFile(String filedDownPath) throws IOException {
        byte[] fileDateByte = null;
        File file = new File(FilenameUtils.normalize(filedDownPath));
        if (!file.exists() || !file.isFile()) {
            throw new CustomMessageException("文件未生成！");
        }
        ByteArrayOutputStream outStream = null;
        FileInputStream in = null;
        try {
            outStream = new ByteArrayOutputStream();
            in = new FileInputStream(file);
            int len;
            byte[] buf = new byte[1024];
            while ((len = in.read(buf)) > 0) {
                outStream.write(buf, 0, len);
            }
            fileDateByte = outStream.toByteArray();
        } catch (IOException e) {
            throw new CustomMessageException(e);
        } finally {
            //关闭流
            if (in != null) {
                in.close();
            }
            if (outStream != null) {
                outStream.close();
            }
        }
        return fileDateByte;
    }

    // 自定义删除 相似 文件
    public static void deleteFileById(String commentStr, String fileDirectoryPaht) {

        // String commentStr = "蒋超良备案表"+".pdf";

        long maxNum = 0; // 找到一个 最大随机数 (最大的 表示 最新的 不用删)

        List<String> deletePaths;  // 需要删除的集合

        File file = new File(FilenameUtils.normalize(fileDirectoryPaht));
//      File file = new File(FilenameUtils.normalize("D:\\oms\\attachment\\static"));

        List<String> filepaths = new ArrayList<>();

        if (file.isDirectory()) {
            File[] files = file.listFiles();

            if (files != null && files.length > 0) {

                for (int i = 0; i < files.length; i++) {

                    File f = files[i];
                    if (f.getName().contains(commentStr)) {

                        String numstr = f.getName().replaceAll(commentStr, "").trim();

                        long num = Long.parseLong(numstr);
                        if (num > maxNum) {

                            maxNum = num;
                        }
                        filepaths.add(f.getName());
                    }
                }
            }
        }

        final long maxnum1 = maxNum;


        if (filepaths != null && filepaths.size() == 1) {


        } else {

            deletePaths = filepaths.stream().filter((String s) -> Long.valueOf(s.replaceAll(commentStr, "").trim()) < maxnum1).collect(Collectors.toList());

            if (deletePaths != null && deletePaths.size() > 0) {

                for (int i = 0; i < deletePaths.size(); i++) {

                    File file1 = new File(fileDirectoryPaht + File.separator + deletePaths.get(i));

                    file1.delete();

                }


            }

        }


    }


    public static String getUpdateStatusByJieWei(String busessId, String bussinesType, String leaderStatusName, String bussinessName) {

        String updateSql = "update " + bussinesType;

        String setSql = " set  ";

        String whereCondition = " where id = '" + busessId + "'";


        for (BussinessApplyStatus applyStatus : BussinessApplyStatus.values()) {

            if (bussinesType.indexOf(applyStatus.getTableName()) != -1) {

                String status = applyStatus.getApplySatus();

                if ("干教".equals(bussinessName)) {
                    // todo 干教 流程 到征求纪委意见 就走完了 将状态 置为 已完结

                    setSql += status + "=" + Constants.leader_business[Constants.leader_business.length - 1];


                } else {

                    // 干部监督处的状态
                    setSql += status + "=" + Constants.leader_business[LeaderSupervisionUntil.getIndexByArray(Constants.leader_businessName, leaderStatusName)];


                }

                // 干部监督处的状态
//                setSql+= status + "=" + Constants.leader_business[LeaderSupervisionUntil.getIndexByArray(Constants.leader_businessName,leaderStatusName)];

                break;


            }

        }

        return updateSql + setSql + whereCondition;


    }


    public static void main(String[] args) {


//        deleteFileById();


    }


    public static String getLeaderbatchPrefix() {
        return leaderbatchPrefix;
    }

    @Value("${batch.prefix}")
    public void setLeaderbatchPrefix(String batchPrefix) {
        LeaderSupervisionUntil.leaderbatchPrefix = batchPrefix;
    }

    public static String getLeaderselectorFormatter() {
        return leaderselectorFormatter;
    }

    @Value("${selectordate.formatter}")
    public void setLeaderselectorFormatter(String selectorFormatter) {
        LeaderSupervisionUntil.leaderselectorFormatter = selectorFormatter;
    }

    public static int getLeaderstepNum() {
        return leaderstepNum;
    }

    @Value("${step.length}")
    public void setLeaderstepNum(int stepNum) {
        LeaderSupervisionUntil.leaderstepNum = stepNum;
    }
}
