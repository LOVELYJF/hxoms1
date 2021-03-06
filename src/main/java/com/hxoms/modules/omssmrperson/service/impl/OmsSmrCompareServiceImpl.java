package com.hxoms.modules.omssmrperson.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hxoms.common.exception.CustomMessageException;
import com.hxoms.common.utils.Result;
import com.hxoms.modules.omssmrperson.entity.OmsSmrCompare;
import com.hxoms.modules.omssmrperson.entity.OmsSmrCompareVO;
import com.hxoms.modules.omssmrperson.mapper.OmsSmrCompareMapper;
import com.hxoms.modules.omssmrperson.service.OmsSmrCompareService;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class OmsSmrCompareServiceImpl extends ServiceImpl<OmsSmrCompareMapper, OmsSmrCompare> implements OmsSmrCompareService {


    @Autowired
    private OmsSmrCompareMapper smrCompareMapper;

    @Override
    public Result getCompareIdCard(String b0100) {
        if(StringUtils.isBlank(b0100)){
            return Result.error("请先选择单位后查询！");
        }
        List<OmsSmrCompareVO> results = smrCompareMapper.getCompareIdCard(b0100);
        return Result.success(results);
    }

    @Override
    public void exportCompareIdCard(String jsonParam, HttpServletResponse response) {
        OmsSmrCompareVO bean = JSONObject.parseObject(jsonParam,OmsSmrCompareVO.class);
        List<OmsSmrCompareVO> list = smrCompareMapper.getCompareIdCard(bean.getB0100());
        //创建HSSFWorkbook对象(excel的文档对象)
        HSSFWorkbook wb = new HSSFWorkbook();
        //创建文件样式对象
        HSSFCellStyle style = wb.createCellStyle();
        //获得字体对象
        HSSFFont font = wb.createFont();
        //建立新的sheet对象（excel的表单）
        HSSFSheet sheet=wb.createSheet("身份证纠正表");
        //在sheet里创建第一行，参数为行索引(excel的行)，可以是0～65535之间的任何一个
        HSSFRow row1=sheet.createRow(0);
        //创建单元格（excel的单元格，参数为列索引，可以是0～255之间的任何一个
        HSSFCell cell=row1.createCell(0);

        //设置标题字体大小
        font.setFontHeightInPoints((short) 16);
        font.setBold(true); //加粗
        style.setAlignment(HorizontalAlignment.CENTER);// 左右居中   
        style.setVerticalAlignment(VerticalAlignment.CENTER);// 上下居中   
        style.setFont(font);
        cell.setCellStyle(style);
        //设置标题单元格内容
        cell.setCellValue("身份证纠正表");

        //合并单元格CellRangeAddress构造参数依次表示起始行，截至行，起始列， 截至列
        sheet.addMergedRegion(new CellRangeAddress(0,1,0,7));
        //在sheet里创建第二行
        HSSFRow row2=sheet.createRow(2);
        //创建单元格并设置单元格内容
        row2.createCell(0).setCellValue("序号");
        row2.createCell(1).setCellValue("单位");
        row2.createCell(2).setCellValue("姓名");
        row2.createCell(3).setCellValue("性别");
        row2.createCell(4).setCellValue("出生年月");
        row2.createCell(5).setCellValue("职务（级）");
        row2.createCell(6).setCellValue("保密局使用的身份证号");
        row2.createCell(7).setCellValue("正确身份证号");
        //在sheet里添加数据

        //创建文件样式对象
        HSSFCellStyle style1 = wb.createCellStyle();
        //获得字体对象
        HSSFFont font1 = wb.createFont();
        //设置单元格字体大小
        font1.setFontHeightInPoints((short) 13);
        style1.setAlignment(HorizontalAlignment.LEFT);// 居左  
        style1.setFont(font1);

        HSSFRow row = null;
        for(int i = 0; i < list.size(); i++){
            row = sheet.createRow(i + 3);
            row.createCell(0).setCellValue(i + 1);
            row.createCell(1).setCellValue(list.get(i).getB0100());
            row.createCell(2).setCellValue(list.get(i).getName());
            row.createCell(3).setCellValue(list.get(i).getSex());
            row.createCell(4).setCellValue(list.get(i).getBirthDate());
            row.createCell(5).setCellValue(list.get(i).getPost());
            row.createCell(6).setCellValue(list.get(i).getIdCardNumberError());
            row.createCell(7).setCellValue(list.get(i).getIdCardNumberRight());
            //设置单元格字体大小
            for(int j = 0;j < 8;j++){
                row.getCell(j).setCellStyle(style1);
            }
        }

        //输出Excel文件
        try{
            String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/vnd.ms-excel");
            response.setHeader(HttpHeaders.CONTENT_DISPOSITION, String.format("attachment; filename=\"%s\"", URLEncoder.encode("身份证纠正表"+date+".xls", "utf-8")));
            ServletOutputStream out = response.getOutputStream();
            wb.write(out);
            out.flush();
            out.close();
        }
        catch (IOException e){
            e.printStackTrace();
            throw new CustomMessageException("导出失败，原因："+e.getMessage());
        }

    }
}
