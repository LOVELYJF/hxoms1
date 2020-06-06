package com.hxoms.modules.omssmrperson.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hxoms.modules.omssmrperson.entity.OmsSmrCompare;

import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.util.List;

/**
 * 涉密人员身份证对照
 * @author gaozhenyuan
 * @date 2020/5/12 16:28
 */
public interface OmsSmrCompareService extends IService<OmsSmrCompare>{
    //获取涉密人员身份证对照列表
    IPage<OmsSmrCompare> getSmrCompareList(Page page, OmsSmrCompare smrCompare) throws ParseException;
    //添加涉密人员身份证对照信息
    Object insert(OmsSmrCompare smrCompare);
    //修改涉密人员身份证对照信息
    Object update(OmsSmrCompare smrCompare);
    //删除涉密人员身份证对照信息
    Object delete(String id);
    //获取身份证纠正列表
    List<OmsSmrCompare> getCompareIdCard();
    //导出身份证纠正列表
    boolean exportCompareIdCard(HttpServletResponse response);
}
