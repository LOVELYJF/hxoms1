package com.hxoms.modules.omssmrperson.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.hxoms.modules.omssmrperson.entity.OmsSmrOldInfo;
import com.hxoms.modules.omssmrperson.entity.OmsSmrOldInfoVO;

import java.text.ParseException;
import java.util.Map;

/**
 * 涉密人员原涉密信息管理
 * @author gaozhenyuan
 * @date 2020/5/12 16:28
 */
public interface OmsSmrOldInfoService extends IService<OmsSmrOldInfo>{
    //获取涉密人员原涉密信息列表
    PageInfo<OmsSmrOldInfoVO> getSmrOldInfoById(Integer pageNum, Integer pageSize, String id) throws ParseException;
    //添加涉密人员原涉密信息
    Object insert(OmsSmrOldInfo smrOldInfo);
    //修改涉密人员原涉密信息
    Object update(OmsSmrOldInfo smrOldInfo);
    //删除涉密人员原涉密信息
    Object delete(String id);
    //获取脱密期确认列表
    Map<String, Object> getConfirmPeriodList();
}
