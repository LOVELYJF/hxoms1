package com.hxoms.modules.omssmrperson.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hxoms.modules.omsregcadre.entity.OmsRegProcpersonInfo;
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
    OmsRegProcpersonInfo getMatchingDate(String workUnit, String idCardNumber);
    //获取漏报涉密人员单位
    List<String> getFailReportOrg();
    //获取差异数据列表
    List<OmsSmrPersonInfo> getDifferentData();
}