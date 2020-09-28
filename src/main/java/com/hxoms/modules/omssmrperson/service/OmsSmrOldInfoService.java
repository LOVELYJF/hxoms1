package com.hxoms.modules.omssmrperson.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.hxoms.common.utils.Result;
import com.hxoms.modules.omssmrperson.entity.OmsSmrOldInfo;
import com.hxoms.modules.omssmrperson.entity.OmsSmrOldInfoVO;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

/**
 * 涉密人员原涉密信息管理
 * @author gaozhenyuan
 * @date 2020/5/12 16:28
 */
public interface OmsSmrOldInfoService extends IService<OmsSmrOldInfo>{
    //获取涉密人员原涉密信息列表
    PageInfo<OmsSmrOldInfoVO> getSmrOldInfoById(Integer pageNum, Integer pageSize, String id) throws ParseException;
    //获取脱密期确认列表
    Map<String, Object> getConfirmPeriodList();
    //获取涉密人员信息维护列表
    Map<String, Object> getSmrMaintainList();
    //更新涉密信息
    Result updateSmrOldInfo(List<OmsSmrOldInfoVO> smrOldInfos);
    //获取差异数据列表
    Result getDifferentData(String importYear, String b0100);
    //导出差异数据列表
    void exportDifferentData();
    //差异数据纠正确认
    Result updateDifferentData(List<OmsSmrOldInfoVO> smrOldInfos);
}
