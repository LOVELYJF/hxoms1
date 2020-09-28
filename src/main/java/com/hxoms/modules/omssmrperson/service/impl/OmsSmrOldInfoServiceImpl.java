package com.hxoms.modules.omssmrperson.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import com.hxoms.common.exception.CustomMessageException;
import com.hxoms.common.utils.PageUtil;
import com.hxoms.common.utils.Result;
import com.hxoms.common.utils.UserInfoUtil;
import com.hxoms.modules.omssmrperson.entity.OmsSmrOldInfo;
import com.hxoms.modules.omssmrperson.entity.OmsSmrOldInfoVO;
import com.hxoms.modules.omssmrperson.entity.OmsSmrPersonInfo;
import com.hxoms.modules.omssmrperson.mapper.OmsSmrOldInfoMapper;
import com.hxoms.modules.omssmrperson.service.OmsSmrOldInfoService;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;

@Service
public class OmsSmrOldInfoServiceImpl extends ServiceImpl<OmsSmrOldInfoMapper, OmsSmrOldInfo> implements OmsSmrOldInfoService {

    @Autowired
    OmsSmrOldInfoMapper smrOldInfoMapper;

    @Override
    public PageInfo<OmsSmrOldInfoVO> getSmrOldInfoById(Integer pageNum, Integer pageSize, String id) {
        List<OmsSmrOldInfoVO> resultList = baseMapper.getSmrOldInfoList(id);
        PageUtil.pageHelp(pageNum, pageSize);
        PageInfo<OmsSmrOldInfoVO> pageInfo = new PageInfo(resultList);
        return pageInfo;
    }

    @Override
    public Object insert(OmsSmrOldInfo smrOldInfo) {
        return baseMapper.insert(smrOldInfo);
    }

    @Override
    public Object update(OmsSmrOldInfo smrOldInfo) {
        return null;
    }

    @Override
    public Object delete(String id) {
        return null;
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
            Result.error("汇总年份或单位为空！");
        }
        List<OmsSmrOldInfoVO> result = smrOldInfoMapper.getDifferentData(importYear,b0100);
        return Result.success(result);
    }

    /**
     * 导出差异数据列表
     */
    @Override
    public void exportDifferentData() {
        List<OmsSmrPersonInfo> list = null;
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
            row.createCell(8).setCellValue(list.get(i).getRemark());
            //设置单元格字体大小
            for (int j = 0; j < 8; j++) {
                row.getCell(j).setCellStyle(style1);
            }
        }

        //输出Excel文件
        OutputStream output = null;
        try {
            HttpServletResponse response = null;
            output = response.getOutputStream();
            response.reset();
            response.setCharacterEncoding("UTF-8");
            response.setHeader("Content-disposition", "attachment; " +
                    "filename=" + new String("差异数据.xls".getBytes("gb2312"), "ISO8859-1"));
            response.setContentType("application/msexcel");
            wb.write(output);
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
