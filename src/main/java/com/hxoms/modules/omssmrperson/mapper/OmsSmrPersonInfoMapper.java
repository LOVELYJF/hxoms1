package com.hxoms.modules.omssmrperson.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hxoms.modules.omsregcadre.entity.OmsRegProcpersoninfo;
import com.hxoms.modules.omssmrperson.entity.OmsSmrPersonInfo;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 涉密人员信息管理
 * @author gaozhenyuan
 * @date 2020/5/12 16:28
 */
@Repository
public interface OmsSmrPersonInfoMapper  extends BaseMapper<OmsSmrPersonInfo> {
    //获取涉密人员信息列表
    List<OmsSmrPersonInfo> selectSmrPersonInfo(Map<String,Object> param);
    //批量添加涉密人员信息
    int insertPersonList(List<OmsSmrPersonInfo> list);
    //判断导入的涉密人员信息能否匹配
    OmsRegProcpersoninfo getMatchingDate(String workUnit, String idCardNumber);
    //获取漏报涉密人员单位
    List<OmsSmrPersonInfo> getFailReportOrg(String importYear);
    //获取涉密人员信息维护列表
    List<OmsSmrPersonInfo> getSmrMaintainList(Map<String, Object> param);
    //批量修改涉密人员信息（确认脱密期、涉密人员信息维护）
    int updateSmrPersonList(List<OmsSmrPersonInfo> smrPersonInfoList);
}