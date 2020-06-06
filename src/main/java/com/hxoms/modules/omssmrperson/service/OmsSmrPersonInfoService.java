package com.hxoms.modules.omssmrperson.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hxoms.modules.omssmrperson.entity.OmsSmrPersonInfo;

import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

/**
 * 涉密人员信息管理
 * @author gaozhenyuan
 * @date 2020/5/12 16:28
 */
public interface OmsSmrPersonInfoService extends IService<OmsSmrPersonInfo>{
    //获取涉密人员信息列表
    Map<String, Object> getSmrPersonInfo(Integer pageNum, Integer pageSize, List<String> idList, OmsSmrPersonInfo smrPersonInfo) throws ParseException;
    //修改涉密人员信息
    Object updateSmrPersonInfo(OmsSmrPersonInfo smrPersonInfo);
    //删除涉密人员信息
    Object deleteSmrPersonInfo(String id);
    //导入涉密人员统计表
    String uploadSmrExcel(String filePath, String importYear, String b0100, String userId);
    //导出涉密人员信息列表
    boolean exportSmrPersonInfo(List<OmsSmrPersonInfo> list, HttpServletResponse response);
    //获取漏报涉密人员机构
    List<String> getFailReportOrg();
    //导出漏报涉密人员机构
    boolean exportFailReportOrg(HttpServletResponse response);
    //获取差异数据列表
    List<OmsSmrPersonInfo> getDifferentData();
    //导出差异数据列表
    boolean exportDifferentData(HttpServletResponse response);
}
