package com.hxoms.modules.omssmrperson.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hxoms.common.exception.CustomMessageException;
import com.hxoms.common.utils.Result;
import com.hxoms.modules.omssmrperson.entity.OmsSmrRecordInfo;
import com.hxoms.modules.omssmrperson.entity.OmsSmrRecordInfoVO;
import com.hxoms.modules.omssmrperson.mapper.OmsSmrRecordInfoMapper;
import com.hxoms.modules.omssmrperson.service.OmsSmrRecordInfoService;
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
public class OmsSmrRecordInfoServiceImpl extends ServiceImpl<OmsSmrRecordInfoMapper, OmsSmrRecordInfo> implements OmsSmrRecordInfoService {


    @Autowired
    private OmsSmrRecordInfoMapper smrRecordInfoMapper;

    @Override
    public IPage<OmsSmrRecordInfo> getSmrRecordInfoList(Page page, OmsSmrRecordInfo smrRecordInfo){
        return null;
    }

    @Override
    public Result getMatchingPerson(String importYear, String b0100) {
        if(StringUtils.isBlank(importYear) || StringUtils.isBlank(b0100)){
            return Result.error("汇总年份或汇总单位为空！");
        }
        List<OmsSmrRecordInfoVO> results = smrRecordInfoMapper.getMatchingPerson(importYear,b0100);
        return Result.success(results);
    }

    @Override
    public void exportMatchingPerson(String jsonParam,HttpServletResponse response) {
        OmsSmrRecordInfoVO bean = JSONObject.parseObject(jsonParam,OmsSmrRecordInfoVO.class);
        List<OmsSmrRecordInfoVO> list = smrRecordInfoMapper.getMatchingPerson(bean.getImportYear(),bean.getB0100());
        //创建HSSFWorkbook对象(excel的文档对象)
        HSSFWorkbook wb = new HSSFWorkbook();
        //创建文件样式对象
        HSSFCellStyle style = wb.createCellStyle();
        //获得字体对象
        HSSFFont font = wb.createFont();
        //建立新的sheet对象（excel的表单）
        HSSFSheet sheet=wb.createSheet("遗漏的省管干部");
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
        cell.setCellValue("遗漏的省管干部");

        //合并单元格CellRangeAddress构造参数依次表示起始行，截至行，起始列， 截至列
        sheet.addMergedRegion(new CellRangeAddress(0,1,0,14));
        //在sheet里创建第二行
        HSSFRow row2=sheet.createRow(2);
        row2.setRowStyle(style);
        //创建单元格并设置单元格内容
        row2.createCell(0).setCellValue("序号");
        row2.createCell(1).setCellValue("单位");
        row2.createCell(2).setCellValue("姓名");
        row2.createCell(3).setCellValue("性别");
        row2.createCell(4).setCellValue("出生年月");
        row2.createCell(5).setCellValue("民族");
        row2.createCell(6).setCellValue("政治面貌");
        row2.createCell(7).setCellValue("身份证号码");
        row2.createCell(8).setCellValue("涉密岗位");
        row2.createCell(9).setCellValue("职务职称");
        row2.createCell(10).setCellValue("涉密等级");
        row2.createCell(11).setCellValue("人员类型");
        row2.createCell(12).setCellValue("保密复审时间");
        row2.createCell(13).setCellValue("脱密期管理开始日期");
        row2.createCell(14).setCellValue("脱密期管理终止日期");
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
            row.createCell(5).setCellValue(list.get(i).getNation());
            row.createCell(6).setCellValue(list.get(i).getA0141());
            row.createCell(7).setCellValue(list.get(i).getIdCardNumber());
            row.createCell(8).setCellValue(list.get(i).getSecretRelatedPost());
            row.createCell(9).setCellValue(list.get(i).getPost());
            row.createCell(10).setCellValue(list.get(i).getSecretRelatedLevel());
            row.createCell(11).setCellValue(list.get(i).getPersonState());
            row.createCell(12).setCellValue(list.get(i).getSecretReviewDate());
            row.createCell(13).setCellValue(list.get(i).getStartDate());
            row.createCell(14).setCellValue(list.get(i).getFinishDate());
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
            response.setHeader(HttpHeaders.CONTENT_DISPOSITION, String.format("attachment; filename=\"%s\"", URLEncoder.encode("遗漏的省管干部"+date+".xls", "utf-8")));
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
    /**
     * @description:通过机构ID和导入年份删除已经导入过的数据
     * @author:杨波
     * @date:2020-09-22
     *  * @param b0100 机构ID
     *  @param importYear 导入年份
     * @return:
     **/
    @Override
    public void deleteByB0100AndYear(String b0100,String importYear){
        smrRecordInfoMapper.deleteByB0100AndYear(b0100,importYear);
    }
}
