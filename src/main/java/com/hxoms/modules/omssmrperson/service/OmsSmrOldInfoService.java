package com.hxoms.modules.omssmrperson.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hxoms.modules.omssmrperson.entity.OmsSmrOldInfo;

import java.text.ParseException;

/**
 * 涉密人员原涉密信息管理
 * @author gaozhenyuan
 * @date 2020/5/12 16:28
 */
public interface OmsSmrOldInfoService extends IService<OmsSmrOldInfo>{
    //获取涉密人员原涉密信息列表
    IPage<OmsSmrOldInfo> getSmrOldInfoById(String id) throws ParseException;
    //添加涉密人员原涉密信息
    Object insert(OmsSmrOldInfo smrOldInfo);
    //修改涉密人员原涉密信息
    Object update(OmsSmrOldInfo smrOldInfo);
    //删除涉密人员原涉密信息
    Object delete(String id);
}
