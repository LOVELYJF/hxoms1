package com.hxoms.modules.omssmrperson.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hxoms.modules.omssmrperson.entity.OmsSmrRecordInfo;

import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.util.List;

/**
 * 省国家保密局备案涉密人员管理
 * @author gaozhenyuan
 * @date 2020/5/12 16:28
 */
public interface OmsSmrRecordInfoService extends IService<OmsSmrRecordInfo>{
    //获取省国家保密局备案涉密人员列表
    IPage<OmsSmrRecordInfo> getSmrRecordInfoList(Page page, OmsSmrRecordInfo smrRecordInfo) throws ParseException;
    //获取已匹配人员列表
    List<OmsSmrRecordInfo> getMatchingPerson();
    //导出已匹配人员列表
    boolean exportMatchingPerson(HttpServletResponse response);
}
