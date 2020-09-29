package com.hxoms.modules.omssmrperson.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import com.hxoms.common.exception.CustomMessageException;
import com.hxoms.common.utils.*;
import com.hxoms.modules.omsregcadre.entity.OmsRegProcpersoninfo;
import com.hxoms.modules.omsregcadre.service.OmsRegProcpersonInfoService;
import com.hxoms.modules.omssmrperson.entity.*;
import com.hxoms.modules.omssmrperson.mapper.OmsSmrOldInfoMapper;
import com.hxoms.modules.omssmrperson.service.OmsSmrCompareService;
import com.hxoms.modules.omssmrperson.service.OmsSmrOldInfoService;
import com.hxoms.modules.omssmrperson.service.OmsSmrRecordInfoService;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.swing.text.html.parser.Entity;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class OmsSmrOldInfoServiceImpl extends ServiceImpl<OmsSmrOldInfoMapper, OmsSmrOldInfo> implements OmsSmrOldInfoService {

    @Autowired
    OmsSmrOldInfoMapper smrOldInfoMapper;
    @Autowired
    OmsRegProcpersonInfoService regProcpersonInfoService;
    @Autowired
    OmsSmrCompareService smrCompareService;
    @Autowired
    OmsSmrRecordInfoService smrRecordInfoService;

    @Override
    public PageInfo<OmsSmrOldInfoVO> getSmrOldInfoById(Integer pageNum, Integer pageSize, String id) {
        List<OmsSmrOldInfoVO> resultList = baseMapper.getSmrOldInfoList(id);
        PageUtil.pageHelp(pageNum, pageSize);
        PageInfo<OmsSmrOldInfoVO> pageInfo = new PageInfo(resultList);
        return pageInfo;
    }


    /**
     * 获取脱密期确认列表
     */
    @Override
    public Map<String, Object> getConfirmPeriodList() {
        Map<String, Object> resultMap = new LinkedHashMap<>();
        List<OmsSmrOldInfoVO> list = smrOldInfoMapper.getConfirmPeriodList(UserInfoUtil.getUserInfo().getOrgId(), "", "");
        resultMap.put("result", list);
        return resultMap;
    }

    @Override
    public Map<String, Object> getSmrMaintainList() {
        Map<String, Object> resultMap = new LinkedHashMap<>();
        List<OmsSmrOldInfoVO> list = smrOldInfoMapper.getSmrMaintainList(UserInfoUtil.getUserInfo().getOrgId(), "", "");
        resultMap.put("result", list);
        return resultMap;
    }

    @Override
    public Result updateSmrOldInfo(List<OmsSmrOldInfoVO> smrOldInfos) {
        Calendar calc = Calendar.getInstance();
        List<OmsSmrOldInfo> updates = new ArrayList<>();
        String staffs = "";
        for (OmsSmrOldInfoVO smrOldInfo : smrOldInfos
        ) {
            if(smrOldInfo.getStartDate()!=null&&smrOldInfo.getQrStartDate()!=null){
                calc.setTime(smrOldInfo.getQrStartDate());
                calc.add(Calendar.DATE, 40);
                if (calc.getTime().before(smrOldInfo.getStartDate())) {
                    staffs += smrOldInfo.getA0101() + ",";
                } else {
                    smrOldInfo.setSfqr("1");
                    updates.add(smrOldInfo);
                }
            }
            else{
                updates.add(smrOldInfo);
            }
        }
        if (updates.size() > 0)
            this.updateBatchById(updates);

        if (!"".equals(staffs))
            return Result.error("以下人员的确定脱密开始时间早于脱密时间40天以上，系统不于保存:" + staffs);

        return Result.success("保存成功！");
    }

    /**
     * 获取差异数据列表
     */
    @Override
    public Result getDifferentData(String importYear, String b0100) {
        if(StringUtils.isBlank(importYear) || StringUtils.isBlank(b0100)){
            return Result.error("汇总年份或单位为空！");
        }
        List<OmsSmrOldInfoVO> result = smrOldInfoMapper.getDifferentData(importYear,b0100);
        return Result.success(result);
    }

    /**
     * 导出差异数据列表
     */
    @Override
    public void exportDifferentData(String importYear, String b0100, HttpServletResponse response) {
        List<OmsSmrOldInfoVO> list =  smrOldInfoMapper.getDifferentData(importYear,b0100);
        if (list.size() < 1 || list == null) {
            throw new CustomMessageException("操作失败");
        }
        //创建HSSFWorkbook对象(excel的文档对象)
        HSSFWorkbook wb = new HSSFWorkbook();
        //创建文件样式对象
        HSSFCellStyle style = wb.createCellStyle();
        //获得字体对象
        HSSFFont font = wb.createFont();
        //建立新的sheet对象（excel的表单）
        HSSFSheet sheet = wb.createSheet("差异数据");
        //在sheet里创建第一行，参数为行索引(excel的行)，可以是0～65535之间的任何一个
        HSSFRow row1 = sheet.createRow(0);
        //创建单元格（excel的单元格，参数为列索引，可以是0～255之间的任何一个
        HSSFCell cell = row1.createCell(0);

        //设置标题字体大小
        font.setFontHeightInPoints((short) 16);
        font.setBold(true); //加粗
        style.setAlignment(HorizontalAlignment.CENTER);// 左右居中   
        style.setVerticalAlignment(VerticalAlignment.CENTER);// 上下居中   
        style.setFont(font);
        cell.setCellStyle(style);
        //设置标题单元格内容
        cell.setCellValue("差异数据");

        //合并单元格CellRangeAddress构造参数依次表示起始行，截至行，起始列， 截至列
        sheet.addMergedRegion(new CellRangeAddress(0, 1, 0, 8));
        //在sheet里创建第二行
        HSSFRow row2 = sheet.createRow(1);
        //创建单元格并设置单元格内容
        row2.createCell(0).setCellValue("序号");
        row2.createCell(1).setCellValue("数据来源");
        row2.createCell(2).setCellValue("单位");
        row2.createCell(3).setCellValue("姓名");
        row2.createCell(4).setCellValue("性别");
        row2.createCell(5).setCellValue("出生年月");
        row2.createCell(6).setCellValue("职务(级)");
        row2.createCell(7).setCellValue("身份证号");
        row2.createCell(8).setCellValue("备注");
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
        for (int i = 0; i < list.size(); i++) {
            row = sheet.createRow(i + 2);
            row.createCell(0).setCellValue(i + 1);
            row.createCell(1).setCellValue(list.get(i).getDataSource());
            row.createCell(2).setCellValue(list.get(i).getB0101());
            row.createCell(3).setCellValue(list.get(i).getName());
            row.createCell(4).setCellValue(list.get(i).getSex());
            row.createCell(5).setCellValue(list.get(i).getBirthDay());
            row.createCell(6).setCellValue(list.get(i).getPost());
            row.createCell(7).setCellValue(list.get(i).getIdCardNumber());
            row.createCell(8).setCellValue(list.get(i).getMsg());
            //设置单元格字体大小
            for (int j = 0; j < 8; j++) {
                row.getCell(j).setCellStyle(style1);
            }
        }

        //输出Excel文件
        try{
            String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/vnd.ms-excel");
            response.setHeader(HttpHeaders.CONTENT_DISPOSITION, String.format("attachment; filename=\"%s\"", URLEncoder.encode("差异数据"+date+".xls", "utf-8")));
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

    @Override
    public Result updateDifferentData(List<OmsSmrOldInfoVO> smrOldInfos) {
        if(smrOldInfos == null)
            return Result.error("未勾选数据！");

        int size = smrOldInfos.size();//勾选数量
        if(size != 2)
            return Result.error("请勾选两条对应数据进行确认！");

        //定义一些后面需要的参数
        boolean flag = false;
        OmsSmrOldInfoVO smrOldInfoVOXt = new OmsSmrOldInfoVO();
        OmsSmrOldInfoVO smrOldInfoVOBmj = new OmsSmrOldInfoVO();
        OmsSmrOldInfoVO smrOldInfoVO1 = smrOldInfos.get(0);
        OmsSmrOldInfoVO smrOldInfoVO2 = smrOldInfos.get(1);

        if(StringUilt.equalsWithNull(smrOldInfoVO1.getDataSource(),"系统")
                && StringUilt.equalsWithNull(smrOldInfoVO2.getDataSource(),"保密局")){

            smrOldInfoVOXt = smrOldInfoVO1;
            smrOldInfoVOBmj = smrOldInfoVO2;

        }else if(StringUilt.equalsWithNull(smrOldInfoVO1.getDataSource(),"保密局")
                && StringUilt.equalsWithNull(smrOldInfoVO2.getDataSource(),"系统")){

            smrOldInfoVOXt = smrOldInfoVO2;
            smrOldInfoVOBmj = smrOldInfoVO1;
        }else{
            return Result.error("所勾选的数据无法合并，请再次确认");
        }

        //合并数据
        flag = updateSmrPersonInfo(smrOldInfoVOXt,smrOldInfoVOBmj);
        if (flag)
            return Result.success();
        else
            return Result.error("操作失败");
    }

    private boolean updateSmrPersonInfo(OmsSmrOldInfoVO smrOldInfoVOXt, OmsSmrOldInfoVO smrOldInfoVOBmj){
        //将保密局人员的涉密信息更新到系统人员的涉密信息表中
        OmsRegProcpersoninfo regProcpersoninfo = new OmsRegProcpersoninfo();
        regProcpersoninfo.setId(smrOldInfoVOXt.getId());
        regProcpersoninfo.setSecretLevel(smrOldInfoVOBmj.getSecretRelatedLevel());
        regProcpersoninfo.setSecretPost(smrOldInfoVOBmj.getSecretRelatedPost());
        regProcpersoninfo.setDecryptStartdate(smrOldInfoVOBmj.getStartDate());
        regProcpersoninfo.setDecryptEnddate(smrOldInfoVOBmj.getFinishDate());
        regProcpersoninfo.setModifyUser(UserInfoUtil.getUserId());
        regProcpersoninfo.setModifyTime(new Date());
        //更新备案表数据
        boolean flag = regProcpersonInfoService.updateById(regProcpersoninfo);

        if(flag){
            //将两者的身份证对照关系存入保密局涉密人员身份证对照表中
            OmsSmrCompare smrCompare = new OmsSmrCompare();
            smrCompare.setId(UUIDGenerator.getPrimaryKey());
            smrCompare.setB0100(smrOldInfoVOXt.getB0100());
            smrCompare.setName(smrOldInfoVOXt.getName());
            smrCompare.setSex(smrOldInfoVOXt.getSex());
            smrCompare.setBirthDate(smrOldInfoVOXt.getBirthDay());
            smrCompare.setPost(smrOldInfoVOXt.getPost());
            smrCompare.setIdCardNumberError(smrOldInfoVOBmj.getIdCardNumber());
            smrCompare.setIdCardNumberRight(smrOldInfoVOXt.getIdCardNumber());
            //插入身份证对照表
            flag = smrCompareService.save(smrCompare);
        }

        if(flag){
            //将省国家保密局备案涉密人员表对应数据更改为已匹配以确保差异数据纠正数据中不会再次出现该数据
            OmsSmrRecordInfo smrRecordInfo = new OmsSmrRecordInfo();
            smrRecordInfo.setId(smrOldInfoVOBmj.getId());
            smrRecordInfo.setIsMatching("1");
            //更新省国家保密局备案涉密人员表
            flag = smrRecordInfoService.updateById(smrRecordInfo);
        }

        return flag;
    }

    public void exportSmrExcel(String[] headers, List<String> exports, String title, HttpServletResponse response){
        int numHl = headers.length;
        int numEs = exports.size();

        //创建HSSFWorkbook对象(excel的文档对象)
        HSSFWorkbook wb = new HSSFWorkbook();
        //创建文件样式对象
        HSSFCellStyle style = wb.createCellStyle();
        //获得字体对象
        HSSFFont font = wb.createFont();
        //建立新的sheet对象（excel的表单）
        HSSFSheet sheet = wb.createSheet(title);
        //在sheet里创建第一行，参数为行索引(excel的行)，可以是0～65535之间的任何一个
        HSSFRow row1 = sheet.createRow(0);
        //创建单元格（excel的单元格，参数为列索引，可以是0～255之间的任何一个
        HSSFCell cell = row1.createCell(0);

        //设置标题字体大小
        font.setFontHeightInPoints((short) 16);
        font.setBold(true); //加粗
        style.setAlignment(HorizontalAlignment.CENTER);// 左右居中   
        style.setVerticalAlignment(VerticalAlignment.CENTER);// 上下居中   
        style.setFont(font);
        cell.setCellStyle(style);
        //设置标题单元格内容
        cell.setCellValue(title);

        //合并单元格CellRangeAddress构造参数依次表示起始行，截至行，起始列， 截至列
        sheet.addMergedRegion(new CellRangeAddress(0, 1, 0, numHl-1));
        //在sheet里创建第二行
        HSSFRow row2 = sheet.createRow(2);
        //创建单元格并设置单元格内容
        for (int i = 0; i < numHl; i++) {
            row2.createCell(i).setCellValue(headers[i]);
        }
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
        for (int i = 0; i < numEs; i++) {
            row = sheet.createRow(i + 3);
            row.createCell(i).setCellValue(exports.get(i));
            //设置单元格字体大小
            for (int j = 0; j < 8; j++) {
                row.getCell(j).setCellStyle(style1);
            }
        }

        //输出Excel文件
        try{
            String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/vnd.ms-excel");
            response.setHeader(HttpHeaders.CONTENT_DISPOSITION,
                    String.format("attachment; filename=\"%s\"", URLEncoder.encode(title + date + ".xls", "utf-8")));
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
