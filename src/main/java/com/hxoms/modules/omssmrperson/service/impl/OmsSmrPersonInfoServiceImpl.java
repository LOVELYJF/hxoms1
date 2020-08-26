package com.hxoms.modules.omssmrperson.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import com.hxoms.common.OmsCommonUtil;
import com.hxoms.common.exception.CustomMessageException;
import com.hxoms.common.utils.UUIDGenerator;
import com.hxoms.common.utils.UserInfoUtil;
import com.hxoms.modules.omsregcadre.entity.OmsRegProcpersoninfo;
import com.hxoms.modules.omsregcadre.mapper.OmsRegProcpersoninfoMapper;
import com.hxoms.modules.omssmrperson.entity.OmsSmrPersonInfo;
import com.hxoms.modules.omssmrperson.entity.OmsSmrRecordInfo;
import com.hxoms.modules.omssmrperson.mapper.OmsSmrCompareMapper;
import com.hxoms.modules.omssmrperson.mapper.OmsSmrPersonInfoMapper;
import com.hxoms.modules.omssmrperson.mapper.OmsSmrRecordInfoMapper;
import com.hxoms.modules.omssmrperson.service.OmsSmrPersonInfoService;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class OmsSmrPersonInfoServiceImpl extends ServiceImpl<OmsSmrPersonInfoMapper,OmsSmrPersonInfo> implements OmsSmrPersonInfoService {


    @Autowired
    private OmsSmrPersonInfoMapper smrPersonInfoMapper;
    @Autowired
    private OmsSmrRecordInfoMapper smrRecordInfoMapper;
    @Autowired
    private OmsSmrCompareMapper smrCompareMapper;

    private OmsRegProcpersoninfoMapper regProcpersonInfoMapper;

    /** 获取涉密人员信息列表 */
    @Override
    @Transactional(rollbackFor=Exception.class)
    public Map<String, Object> getSmrPersonInfo(Integer pageNum, Integer pageSize,  List<String> idList,OmsSmrPersonInfo omsSmrPersonInfo) throws ParseException {
        Map<String, Object> resultMap = new LinkedHashMap<>();
        Map<String,Object> param = new HashMap<>();
        if(!StringUtils.isEmpty(omsSmrPersonInfo.getName())){
            if(isPinyin(omsSmrPersonInfo.getName())){
                param.put("namePy","%"+omsSmrPersonInfo.getName()+"%");
            }else{
                param.put("name","%"+omsSmrPersonInfo.getName()+"%");
            }
        }

        if(!StringUtils.isEmpty(omsSmrPersonInfo.getB0100())){
            param.put("b0100",omsSmrPersonInfo.getB0100());
        }

        if(!StringUtils.isEmpty(omsSmrPersonInfo.getSecretRelatedLevel())){
            param.put("level",omsSmrPersonInfo.getSecretRelatedLevel());
        }

        if(!StringUtils.isEmpty(omsSmrPersonInfo.getSecretRelatedPost())){
            param.put("post",omsSmrPersonInfo.getSecretRelatedPost());
        }

        if(!StringUtils.isEmpty(omsSmrPersonInfo.getPersonState())){
            param.put("personState",omsSmrPersonInfo.getPersonState());
        }
        if(idList.size()>0){
            if(!"-1".equals(idList.get(0))) {
                param.put("idList", idList);
            }
        }
        List<OmsSmrPersonInfo> list = smrPersonInfoMapper.selectSmrPersonInfo(param);
        PageInfo pageInfo = new PageInfo(list);
        resultMap.put("pageInfo", pageInfo);
        return resultMap;
    }

    /** 导入涉密人员信息 */
    @Override
    public Object insertSmrPersonInfo(String importYear, String b0100,List<OmsSmrPersonInfo> smrPersonInfoList) {
        //插入涉密人员基本信息
        boolean a = smrPersonInfoMapper.insertPersonList(smrPersonInfoList) > 0 ? true : false;
        if(a){
            //封装涉密人员备案信息
            List<OmsSmrRecordInfo> srrList = new ArrayList<>();
            for(int i=0; i<smrPersonInfoList.size(); i++){
                OmsSmrRecordInfo smrRecordInfo = new OmsSmrRecordInfo();
                smrRecordInfo.setId(UUIDGenerator.getPrimaryKey());
                smrRecordInfo.setB0100(b0100);
                smrRecordInfo.setName(smrPersonInfoList.get(i).getName());
                smrRecordInfo.setSex(smrPersonInfoList.get(i).getSex());
                smrRecordInfo.setBirthDate(smrPersonInfoList.get(i).getBirthDay());
                smrRecordInfo.setIdCardNumber(smrPersonInfoList.get(i).getIdCardNumber());
                smrRecordInfo.setNation(smrPersonInfoList.get(i).getNation());
                smrRecordInfo.setA0141(smrPersonInfoList.get(i).getA0141());
                smrRecordInfo.setPost(smrPersonInfoList.get(i).getPost());
                smrRecordInfo.setSecretRelatedPost(smrPersonInfoList.get(i).getSecretRelatedPost());
                smrRecordInfo.setSecretRelatedLevel(smrPersonInfoList.get(i).getSecretRelatedLevel());
                smrRecordInfo.setStartDate(smrPersonInfoList.get(i).getStartDate());
                smrRecordInfo.setFinishDate(smrPersonInfoList.get(i).getFinishDate());
                smrRecordInfo.setImportUserId(smrPersonInfoList.get(i).getUpdateUserId());
                smrRecordInfo.setImportDate(new Date());
                smrRecordInfo.setImportYear(importYear);
                String isMatching = getIsMatching(b0100,smrPersonInfoList.get(i).getIdCardNumber());
                smrRecordInfo.setIsMatching(isMatching);
                srrList.add(smrRecordInfo);
            }
            return smrRecordInfoMapper.insertRecordList(srrList) > 0;
        }
        return false;

    }

    /** 修改涉密人员信息 */
    @Override
    public Object updateSmrPersonInfo(OmsSmrPersonInfo smrPersonInfo) {
        return baseMapper.updateById(smrPersonInfo);
    }

    /** 删除涉密人员信息 */
    @Override
    public Object deleteSmrPersonInfo(String id) {
        return baseMapper.deleteById(id);
    }

    /** 导入涉密人员信息列表 */
   /* @Override
    public String uploadSmrExcel(String filePath, String importYear, String b0100, String userId) {
        String msg = "";
        List<Map<String, Object>> list = readExcel(filePath);
        List<OmsSmrPersonInfo> srpList = new ArrayList<OmsSmrPersonInfo>();
        List<OmsSmrRecordInfo> srrList = new ArrayList<OmsSmrRecordInfo>();
        if(list.size() == 0){
            msg = "选择的涉密人员统计表格式不正确，请下载模板后，按模板格式调整！";
            return msg;
        }
        for(int i = 0; i <= list.size(); i++){
            OmsSmrPersonInfo smrPersonInfo = new OmsSmrPersonInfo();
            OmsSmrRecordInfo smrRecordInfo = new OmsSmrRecordInfo();
            Map<String, Object> map = new HashMap<>();
            map.put("name",list.get(i).get("name"));
            map.put("birthDay",list.get(i).get("birthDay"));
            List<OmsRegProcpersoninfo> rpList = regProcpersonInfoMapper.selectA0100ByMap(map);
            String idCardNum = list.get(i).get("idCardNumber").toString();//身份证号
            String srLevel = list.get(i).get("secretRelatedLevel").toString();//涉密等级
            if(StringUtils.isNotBlank(idCardNum)){
                if(idCardNum.length() != 18){
                    msg = "姓名："+map.get("name")+",性别:"+map.get("sex")+",出生年月:"+map.get("birthDay");
                    msg += ",身份证号码:"+idCardNum+",职务（级）:"+map.get("post")+",其身份证号码格式不正确;";
                }
            }
            //封装涉密人员基本信息
            smrPersonInfo.setId(UUIDGenerator.getPrimaryKey());
            smrPersonInfo.setA0100(rpList.get(0).getA0100());
            smrPersonInfo.setB0100(b0100);
            smrPersonInfo.setIdCardNumber(idCardNum);
            smrPersonInfo.setA0141(list.get(i).get("a0141").toString());
            smrPersonInfo.setPost(list.get(i).get("post").toString());
            smrPersonInfo.setPersonState(list.get(i).get("personState").toString());
            smrPersonInfo.setSecretRelatedPost(list.get(i).get("secretRelatedPost").toString());
            if(StringUtils.isNotBlank(srLevel)){
                if(!"核心".equals(srLevel) && !"重要".equals(srLevel) && !"一般".equals(srLevel)){
                    msg = "姓名："+map.get("name")+",性别:"+map.get("sex")+",出生年月:"+map.get("birthDay");
                    msg += ",身份证号码:"+idCardNum+",职务（级）:"+map.get("post")+",其涉密等级格式不正确;";
                }
            }
            smrPersonInfo.setSecretRelatedLevel(srLevel);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd ");
            if(!"".equals(list.get(i).get("secretReviewDate").toString())){
                try{
                    Date srDate = simpleDateFormat.parse(list.get(i).get("secretReviewDate").toString());
                    smrPersonInfo.setSecretReviewDate(srDate);
                    smrRecordInfo.setSecretReviewDate(srDate);
                } catch(ParseException px) {
                    px.printStackTrace();
                }
            }
            Date startDate = formatDate(list.get(i).get("startDate").toString());
            if(startDate == null){
                msg = "姓名："+map.get("name")+",性别:"+map.get("sex")+",出生年月:"+map.get("birthDay");
                msg += ",身份证号码:"+idCardNum+",职务（级）:"+map.get("post")+",其脱密期管理开始日期格式不正确;";
            }else{
                smrPersonInfo.setStartDate(startDate);
            }
            Date finishDate = formatDate(list.get(i).get("finishDate").toString());
            if(finishDate == null){
                msg = "姓名："+map.get("name")+",性别:"+map.get("sex")+",出生年月:"+map.get("birthDay");
                msg += ",身份证号码:"+idCardNum+",职务（级）:"+map.get("post")+",其脱密期管理结束日期格式不正确;";
            }else{
                smrPersonInfo.setFinishDate(finishDate);
            }
            smrPersonInfo.setUpdateUserId(userId);
            smrPersonInfo.setUpdateTime(new Date());

            srpList.add(smrPersonInfo);

            //封装涉密人员备案信息
            smrRecordInfo.setId(UUIDGenerator.getPrimaryKey());
            smrRecordInfo.setB0100(b0100);
            smrRecordInfo.setName(list.get(i).get("name").toString());
            smrRecordInfo.setSex(list.get(i).get("sex").toString());
            smrRecordInfo.setBirthDate(list.get(i).get("birthDay").toString());
            smrRecordInfo.setIdCardNumber(idCardNum);
            smrRecordInfo.setNation(list.get(i).get("nation").toString());
            smrRecordInfo.setA0141(list.get(i).get("a0141").toString());
            smrRecordInfo.setPost(list.get(i).get("post").toString());
            smrRecordInfo.setSecretRelatedPost(list.get(i).get("secretRelatedPost").toString());
            smrRecordInfo.setSecretRelatedLevel(srLevel);
            smrRecordInfo.setStartDate(startDate);
            smrRecordInfo.setFinishDate(finishDate);
            smrRecordInfo.setImportUserId(userId);
            smrRecordInfo.setImportDate(new Date());
            smrRecordInfo.setImportYear(importYear);
            String isMatching = getIsMatching(b0100,idCardNum) + "";
            smrRecordInfo.setIsMatching(isMatching);

            srrList.add(smrRecordInfo);
        }
        smrPersonInfoMapper.insertPersonList(srpList);
        smrRecordInfoMapper.insertRecordList(srrList);
        return msg;
    }*/
    @Override
    public Map<String, Object> uploadSmrExcel(MultipartFile file, String importYear, String b0100) {
        Map<String, Object> resultMap = new HashMap<>();
        String msg = "";
        int errOrgNum = 0;
        String userId = UserInfoUtil.getUserId();
        OmsSmrPersonInfo smrPersonInfo = new OmsSmrPersonInfo();
        List<Map<String, Object>> list = readExcel(file);
        List<OmsSmrPersonInfo> srpList = new ArrayList<OmsSmrPersonInfo>();
        if(list.size() == 0){
            msg = "选择的涉密人员统计表格式不正确，请下载模板后，按模板格式调整！";
            srpList.add(smrPersonInfo);
        }else{
            for(int i = 0; i <= list.size(); i++){
                Map<String, Object> map = new HashMap<>();
                String idCardNum = list.get(i).get("idCardNumber").toString();//身份证号
                if(StringUtils.isNotBlank(idCardNum)){
                    if(idCardNum.length() != 18){
                        msg = "姓名："+list.get(i).get("name")+",性别:"+list.get(i).get("sex")+",出生年月:"+list.get(i).get("birthDay");
                        msg += ",身份证号码:"+idCardNum+",职务（级）:"+list.get(i).get("post")+",其身份证号码格式不正确;";
                    }
                }
                map.put("b0100",b0100);
                map.put("idCardNum",idCardNum);
                OmsRegProcpersoninfo rppInfo = regProcpersonInfoMapper.selectRegIdByMap(map);
                //判断所选机构和人员机构是否一致
                if(!b0100.equals(rppInfo.getRfB0000())){
                    errOrgNum += 1;
                }
                String srLevel = list.get(i).get("secretRelatedLevel").toString();//涉密等级
                //封装涉密人员基本信息
                smrPersonInfo.setId(UUIDGenerator.getPrimaryKey());
                smrPersonInfo.setA0100(rppInfo.getId());
                smrPersonInfo.setB0100(b0100);
                smrPersonInfo.setIdCardNumber(idCardNum);
                smrPersonInfo.setA0141(list.get(i).get("a0141").toString());
                smrPersonInfo.setPost(list.get(i).get("post").toString());
                smrPersonInfo.setPersonState(list.get(i).get("personState").toString());
                smrPersonInfo.setSecretRelatedPost(list.get(i).get("secretRelatedPost").toString());
                if(StringUtils.isNotBlank(srLevel)){
                    if(!"核心".equals(srLevel) && !"重要".equals(srLevel) && !"一般".equals(srLevel)){
                        msg = "姓名："+list.get(i).get("name")+",性别:"+list.get(i).get("sex")+",出生年月:"+list.get(i).get("birthDay");
                        msg += ",身份证号码:"+idCardNum+",职务（级）:"+list.get(i).get("post")+",其涉密等级格式不正确;";
                    }
                }
                smrPersonInfo.setSecretRelatedLevel(srLevel);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd ");
                if(!"".equals(list.get(i).get("secretReviewDate").toString())){
                    try{
                        Date srDate = simpleDateFormat.parse(list.get(i).get("secretReviewDate").toString());
                        smrPersonInfo.setSecretReviewDate(srDate);
                    } catch(ParseException px) {
                        px.printStackTrace();
                    }
                }
                Date startDate = formatDate(list.get(i).get("startDate").toString());
                if(startDate == null){
                    msg = "姓名："+list.get(i).get("name")+",性别:"+list.get(i).get("sex")+",出生年月:"+list.get(i).get("birthDay");
                    msg += ",身份证号码:"+idCardNum+",职务（级）:"+list.get(i).get("post")+",其脱密期管理开始日期格式不正确;";
                }else{
                    smrPersonInfo.setStartDate(startDate);
                }
                Date finishDate = formatDate(list.get(i).get("finishDate").toString());
                if(finishDate == null){
                    msg = "姓名："+list.get(i).get("name")+",性别:"+list.get(i).get("sex")+",出生年月:"+list.get(i).get("birthDay");
                    msg += ",身份证号码:"+idCardNum+",职务（级）:"+list.get(i).get("post")+",其脱密期管理结束日期格式不正确;";
                }else{
                    smrPersonInfo.setFinishDate(finishDate);
                }
                smrPersonInfo.setUpdateUserId(userId);
                smrPersonInfo.setUpdateTime(new Date());
                smrPersonInfo.setMsg(msg);

                srpList.add(smrPersonInfo);
            }
        }
        if(errOrgNum > 0 && errOrgNum/srpList.size() > 0.5){
            msg = "所导入涉密人员单位与选择的单位不一致率大于50%，请确认所选单位是否正确！";
        }
        resultMap.put("msg",msg);
        resultMap.put("resultList",srpList);
        return resultMap;
    }

    /** 导出涉密人员信息 */
    @Override
    public boolean exportSmrPersonInfo(List<String> idList, OmsSmrPersonInfo smrPersonInfo, HttpServletResponse response) {
        //根据条件查询数据
        Map<String,Object> param = new HashMap<>();
        if(!StringUtils.isEmpty(smrPersonInfo.getName())){
            if(isPinyin(smrPersonInfo.getName())){
                param.put("namePy","%"+smrPersonInfo.getName()+"%");
            }else{
                param.put("name","%"+smrPersonInfo.getName()+"%");
            }
        }

        if(!StringUtils.isEmpty(smrPersonInfo.getB0100())){
            param.put("b0100",smrPersonInfo.getB0100());
        }

        if(!StringUtils.isEmpty(smrPersonInfo.getSecretRelatedLevel())){
            param.put("level",smrPersonInfo.getSecretRelatedLevel());
        }

        if(!StringUtils.isEmpty(smrPersonInfo.getSecretRelatedPost())){
            param.put("post",smrPersonInfo.getSecretRelatedPost());
        }

        if(!StringUtils.isEmpty(smrPersonInfo.getPersonState())){
            param.put("personState",smrPersonInfo.getPersonState());
        }
        if(idList.size()>0){
            if(!"-1".equals(idList.get(0))) {
                param.put("idList", idList);
            }
        }
        List<OmsSmrPersonInfo> list = smrPersonInfoMapper.selectSmrPersonInfo(param);

        //导出
        if(list.size() < 1){
            throw new CustomMessageException("操作失败");
        }
        //创建HSSFWorkbook对象(excel的文档对象)
        HSSFWorkbook wb = new HSSFWorkbook();
        //创建文件样式对象
        HSSFCellStyle style = wb.createCellStyle();
        //获得字体对象
        HSSFFont font = wb.createFont();
        //建立新的sheet对象（excel的表单）
        HSSFSheet sheet=wb.createSheet("涉密人员信息表");
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
        cell.setCellValue("涉密人员信息表");

        //合并单元格CellRangeAddress构造参数依次表示起始行，截至行，起始列， 截至列
        sheet.addMergedRegion(new CellRangeAddress(0,1,0,14));
        //在sheet里创建第二行
        HSSFRow row2=sheet.createRow(1);
        //创建单元格并设置单元格内容
        row2.createCell(0).setCellValue("序号");
        row2.createCell(1).setCellValue("单位");
        row2.createCell(2).setCellValue("姓名");
        row2.createCell(3).setCellValue("性别");
        row2.createCell(4).setCellValue("职务职级");
        row2.createCell(5).setCellValue("政治面貌");
        row2.createCell(6).setCellValue("在职状态");
        row2.createCell(7).setCellValue("最高等级定级单位");
        row2.createCell(8).setCellValue("最高涉密等级");
        row2.createCell(9).setCellValue("最长脱密结束日期");
        row2.createCell(10).setCellValue("涉密岗位");
        row2.createCell(11).setCellValue("涉密等级");
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
            row = sheet.createRow(i + 2);
            row.createCell(0).setCellValue(i + 1);
            row.createCell(1).setCellValue(list.get(i).getB0101());
            row.createCell(2).setCellValue(list.get(i).getName());
            row.createCell(3).setCellValue(list.get(i).getSex());
            row.createCell(4).setCellValue(list.get(i).getPost());
            row.createCell(5).setCellValue(list.get(i).getA0141());
            row.createCell(6).setCellValue(list.get(i).getPersonState());
            row.createCell(7).setCellValue(list.get(i).getMaxSecretRelatedOrg());
            row.createCell(8).setCellValue(list.get(i).getMaxSecretRelatedLevel());
            row.createCell(9).setCellValue(list.get(i).getMaxFinishDate());
            row.createCell(10).setCellValue(list.get(i).getSecretRelatedPost());
            row.createCell(11).setCellValue(list.get(i).getSecretRelatedLevel());
            row.createCell(12).setCellValue(list.get(i).getSecretReviewDate());
            row.createCell(13).setCellValue(list.get(i).getStartDate());
            row.createCell(14).setCellValue(list.get(i).getFinishDate());
            //设置单元格字体大小
            for(int j = 0;j < 8;j++){
                row.getCell(j).setCellStyle(style1);
            }
        }

        //输出Excel文件
        OutputStream output= null;
        try {
            output = response.getOutputStream();
            response.reset();
            response.setCharacterEncoding("UTF-8");
            response.setHeader("Content-disposition", "attachment; " +
                    "filename=" + new String( "涉密人员信息表.xls".getBytes("gb2312"), "ISO8859-1" ));
            response.setContentType("application/msexcel");
            wb.write(output);
            output.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /** 获取漏报涉密人员机构 */
    @Override
    public List<String> getFailReportOrg() {
        return smrPersonInfoMapper.getFailReportOrg();
    }

    /** 导出漏报涉密人员机构 */
    @Override
    public boolean exportFailReportOrg(HttpServletResponse response) {
        List<String> list = getFailReportOrg();
        if(list.size() < 1 || list == null){
            throw new CustomMessageException("没有可以导出的数据");
        }
        //创建HSSFWorkbook对象(excel的文档对象)
        HSSFWorkbook wb = new HSSFWorkbook();
        //创建文件样式对象
        HSSFCellStyle style = wb.createCellStyle();
        //获得字体对象
        HSSFFont font = wb.createFont();
        //建立新的sheet对象（excel的表单）
        HSSFSheet sheet=wb.createSheet("漏报涉密人员机构");
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
        cell.setCellValue("漏报涉密人员机构");

        //合并单元格CellRangeAddress构造参数依次表示起始行，截至行，起始列， 截至列
        sheet.addMergedRegion(new CellRangeAddress(0,1,0,1));
        //在sheet里创建第二行
        HSSFRow row2=sheet.createRow(1);
        //创建单元格并设置单元格内容
        row2.createCell(0).setCellValue("序号");
        row2.createCell(1).setCellValue("单位");
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
            row = sheet.createRow(i + 2);
            row.createCell(0).setCellValue(i + 1);
            row.createCell(1).setCellValue(list.get(i));
            //设置单元格字体大小
            for(int j = 0;j < 8;j++){
                row.getCell(j).setCellStyle(style1);
            }
        }

        //输出Excel文件
        OutputStream output= null;
        try {
            output = response.getOutputStream();
            response.reset();
            response.setCharacterEncoding("UTF-8");
            response.setHeader("Content-disposition", "attachment; " +
                    "filename=" + new String( "漏报涉密人员机构.xls".getBytes("gb2312"), "ISO8859-1" ));
            response.setContentType("application/msexcel");
            wb.write(output);
            output.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /** 获取差异数据列表 */
    @Override
    public List<OmsSmrPersonInfo> getDifferentData() {
        return smrPersonInfoMapper.getDifferentData();
    }

    /** 导出差异数据列表 */
    @Override
    public boolean exportDifferentData(HttpServletResponse response) {
        List<OmsSmrPersonInfo> list = getDifferentData();
        if(list.size() < 1 || list == null){
            throw new CustomMessageException("操作失败");
        }
        //创建HSSFWorkbook对象(excel的文档对象)
        HSSFWorkbook wb = new HSSFWorkbook();
        //创建文件样式对象
        HSSFCellStyle style = wb.createCellStyle();
        //获得字体对象
        HSSFFont font = wb.createFont();
        //建立新的sheet对象（excel的表单）
        HSSFSheet sheet=wb.createSheet("差异数据");
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
        cell.setCellValue("差异数据");

        //合并单元格CellRangeAddress构造参数依次表示起始行，截至行，起始列， 截至列
        sheet.addMergedRegion(new CellRangeAddress(0,1,0,8));
        //在sheet里创建第二行
        HSSFRow row2=sheet.createRow(1);
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
        for(int i = 0; i < list.size(); i++){
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
            for(int j = 0;j < 8;j++){
                row.getCell(j).setCellStyle(style1);
            }
        }

        //输出Excel文件
        OutputStream output= null;
        try {
            output = response.getOutputStream();
            response.reset();
            response.setCharacterEncoding("UTF-8");
            response.setHeader("Content-disposition", "attachment; " +
                    "filename=" + new String( "差异数据.xls".getBytes("gb2312"), "ISO8859-1" ));
            response.setContentType("application/msexcel");
            wb.write(output);
            output.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /** 获取脱密期确认列表 */
    @Override
    public Map<String, Object> getConfirmPeriodList() {
        Map<String, Object> resultMap = new LinkedHashMap<>();
        List<OmsSmrPersonInfo> list = smrPersonInfoMapper.getConfirmPeriodList();
        resultMap.put("result", list);
        return resultMap;
    }

    /** 批量修改涉密人员信息（确认脱密期、涉密人员信息维护） */
    @Override
    public boolean updateSmrPersonList(List<OmsSmrPersonInfo> smrPersonInfoList) {
        boolean b = false;
        if(smrPersonInfoMapper.updateSmrPersonList(smrPersonInfoList) > 0){
            b = true;
        }
        return b;
    }

    /** 获取涉密人员信息维护列表 */
    @Override
    public Map<String, Object> getSmrMaintainList(OmsSmrPersonInfo smrPersonInfo) {
        Map<String, Object> resultMap = new LinkedHashMap<>();
        Map<String,Object> param = new HashMap<>();
        if(!StringUtils.isEmpty(smrPersonInfo.getName())){
            if(isPinyin(smrPersonInfo.getName())){
                param.put("namePy","%"+smrPersonInfo.getName()+"%");
            }else{
                param.put("name","%"+smrPersonInfo.getName()+"%");
            }
        }
        if(!StringUtils.isEmpty(smrPersonInfo.getSecretRelatedPost())){
            param.put("secretRelatedPost",smrPersonInfo.getSecretRelatedPost());
        }
        param.put("orgId",UserInfoUtil.getUserInfo().getOrgId());
        List<OmsSmrPersonInfo> list = smrPersonInfoMapper.getSmrMaintainList(param);
        resultMap.put("result", list);
        return resultMap;
    }

    /**
     * 读取到Excel表格的数据(导入用)
     * @return List<OmsSmrPersonInfo>
     */
    public static List<Map<String, Object>> readExcel(MultipartFile file) {
        List<Map<String, Object>> list = new ArrayList<>();
        HSSFWorkbook workbook = null;
        try {
            // 读取Excel文件
            POIFSFileSystem inputStream = new POIFSFileSystem(file.getInputStream());
            //InputStream inputStream = new FileInputStream(filePath);
            workbook = new HSSFWorkbook(inputStream);
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 循环工作表
        for (int numSheet = 0; numSheet < workbook.getNumberOfSheets(); numSheet++) {
            HSSFSheet hssfSheet = workbook.getSheetAt(numSheet);
            if (hssfSheet == null) {
                continue;
            }
            //判断导入模板是否正确
            if(!"姓名".equals(hssfSheet.getRow(2).getCell(1).toString())){
                return list;
            }
            if(!"身份证号码".equals(hssfSheet.getRow(2).getCell(6).toString())){
                return list;
            }
            if(!"涉密岗位".equals(hssfSheet.getRow(2).getCell(7).toString())){
                return list;
            }
            if(!"涉密等级".equals(hssfSheet.getRow(2).getCell(9).toString())){
                return list;
            }
            if(!"保密复审时间".equals(hssfSheet.getRow(2).getCell(12).toString())){
                return list;
            }
            if(!"脱密期管理开始日期".equals(hssfSheet.getRow(2).getCell(18).toString())){
                return list;
            }
            if(!"脱密期管理终止日期".equals(hssfSheet.getRow(2).getCell(19).toString())){
                return list;
            }
            // 循环行
            for (int rowNum = 3; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
                HSSFRow hssfRow = hssfSheet.getRow(rowNum);
                if (hssfRow == null) {
                    continue;
                }
                // 将单元格中的内容存入集合
                Map<String, Object> map = new HashMap<String, Object>();
                //姓名
                HSSFCell cell = hssfRow.getCell(1);
                if (cell == null) {
                    continue;
                }
                map.put("name",cell.getStringCellValue());
                //性别
                cell = hssfRow.getCell(2);
                if(cell == null){
                    continue;
                }
                map.put("sex",cell.getStringCellValue());
                //出生年月
                cell = hssfRow.getCell(3);
                if(cell == null){
                    continue;
                }
                map.put("birthDay",cell.getStringCellValue());
                //民族
                cell = hssfRow.getCell(3);
                if(cell == null){
                    continue;
                }
                map.put("nation",cell.getStringCellValue());
                //政治面貌
                cell = hssfRow.getCell(5);
                if(cell == null){
                    continue;
                }
                map.put("a0141",cell.getStringCellValue());
                //身份证号
                cell = hssfRow.getCell(6);
                if (cell == null) {
                    continue;
                }
                map.put("idCardNumber",cell.getStringCellValue());
                //涉密岗位
                cell = hssfRow.getCell(7);
                if (cell == null) {
                    continue;
                }
                map.put("secretRelatedPost",cell.getStringCellValue());
                //职务（级）或职称
                cell = hssfRow.getCell(8);
                if (cell == null) {
                    continue;
                }
                map.put("post",cell.getStringCellValue());
                //涉密等级
                cell = hssfRow.getCell(9);
                if (cell == null) {
                    continue;
                }
                map.put("secretRelatedLevel",OmsCommonUtil.toSecretLevelStatus(cell.getStringCellValue()));
                //人员类型
                cell = hssfRow.getCell(11);
                if (cell == null) {
                    continue;
                }
                map.put("personState",cell.getStringCellValue());
                //保密复审时间
                cell = hssfRow.getCell(12);
                if (cell == null) {
                    continue;
                }
                map.put("secretReviewDate",cell.getStringCellValue());
                //脱密期管理开始日期
                cell = hssfRow.getCell(18);
                if (cell == null) {
                    continue;
                }
                map.put("startDate", cell.getStringCellValue());
                //脱密期管理终止日期
                cell = hssfRow.getCell(19);
                if (cell == null) {
                    continue;
                }
                map.put("finishDate", cell.getStringCellValue());

                list.add(map);
            }
        }
        return list;
    }

    /**
     * 判断是否是姓名是否为拼音
     * @param keyword
     */
    public boolean isPinyin(String keyword) {
        int len = keyword.toLowerCase().length();
        for (int i = 0; i < len; i++) {
            char ch = keyword.charAt(i);
            if (ch >= 'a' && ch <= 'z') {
                continue;
            }
            return false;
        }
        return true;
    }

    /**
     * 格式化日期为'/'
     * @param date
     * @return Date
     */
    public static Date formatDate(String date){
        //转换日期格式为我们需要的格式
        String srDateNew = "";
        if(date.contains("-")) {
            srDateNew = date.replaceAll("-", "/");
        }
        if(date.contains(".")) {
            srDateNew = date.replaceAll(".", "/");
        }
        if(date.contains("年")) {
            srDateNew = date.replaceAll("年", "/");
            if(date.contains("月")) {
                srDateNew = date.replaceAll("月", "/");
                if(date.contains("日")) {
                    srDateNew = date.replaceAll("日", "/");
                }
            }
        }
        //格式化日期
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd ");
        Date newDate = null;
        try {
            if(!"".equals(srDateNew)){
                newDate = simpleDateFormat.parse(srDateNew);
            }else{
                newDate = simpleDateFormat.parse(date);
            }
        } catch(ParseException px) {
            px.printStackTrace();
        }
        return newDate;
    }

    /**
     * 判断是否匹配导入数据
     * @param workUnit,idCardNumber
     * @return String
     */
    private String getIsMatching(String workUnit,String idCardNumber){
        boolean result1 = smrPersonInfoMapper.getMatchingDate(workUnit,idCardNumber) == null ? true : false;
        if(result1){
            return "1";
        }
        boolean result2 = smrCompareMapper.getMatchingDate(workUnit,idCardNumber) == null ? true : false;
        if(result2){
            return "1";
        }
        return "0";
    }

}
