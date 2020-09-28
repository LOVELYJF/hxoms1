package com.hxoms.modules.omssmrperson.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hxoms.common.utils.Result;
import com.hxoms.modules.omssmrperson.entity.OmsSmrCompare;

import javax.servlet.http.HttpServletResponse;

/**
 * 涉密人员身份证对照
 * @author gaozhenyuan
 * @date 2020/5/12 16:28
 */
public interface OmsSmrCompareService extends IService<OmsSmrCompare>{
    //获取身份证纠正列表
    Result getCompareIdCard(String b0100);
    //导出身份证纠正列表
    void exportCompareIdCard(String b0100, HttpServletResponse response);
}
