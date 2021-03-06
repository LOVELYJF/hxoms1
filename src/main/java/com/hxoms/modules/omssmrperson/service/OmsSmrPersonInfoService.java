package com.hxoms.modules.omssmrperson.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.hxoms.common.utils.Result;
import com.hxoms.modules.omsregcadre.entity.OmsRegProcpersoninfo;
import com.hxoms.modules.omssmrperson.entity.OmsSmrOldInfoVO;
import com.hxoms.modules.omssmrperson.entity.OmsSmrPersonInfo;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.InvocationTargetException;
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
    PageInfo<OmsRegProcpersoninfo> getSmrPersonInfo(Integer pageNum, Integer pageSize,String idList, OmsSmrPersonInfo smrPersonInfo) throws ParseException;
    //导入涉密人员信息
    Result insertSmrPersonInfo(String importYear, String b0100,List<OmsSmrOldInfoVO> smrPersonInfoList) throws ParseException, InvocationTargetException, IllegalAccessException, InstantiationException, ClassNotFoundException;
    //修改涉密人员信息
    Object updateSmrPersonInfo(OmsSmrPersonInfo smrPersonInfo);
    //删除涉密人员信息
    Object deleteSmrPersonInfo(String id);
    //上传涉密人员统计表
    Result uploadSmrExcel(MultipartFile file, String importYear, String b0100);
    //导出涉密人员信息列表
    void exportSmrPersonInfo(String idList, OmsSmrPersonInfo smrPersonInfo, HttpServletResponse response);
    //获取漏报涉密人员机构
    Result getFailReportOrg(String importYear);
    //导出漏报涉密人员机构
    void exportFailReportOrg(String jsonParam, HttpServletResponse response);
    //批量修改涉密人员信息（确认脱密期、涉密人员信息维护）
    boolean updateSmrPersonList(List<OmsSmrPersonInfo> smrPersonInfoList);
    //获取涉密人员信息维护列表
    Map<String, Object> getSmrMaintainList(OmsSmrPersonInfo smrPersonInfo);
}
