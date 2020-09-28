package com.hxoms.modules.omssmrperson.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hxoms.common.utils.Result;
import com.hxoms.modules.omssmrperson.entity.OmsSmrRecordInfo;

import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;

/**
 * 省国家保密局备案涉密人员管理
 * @author gaozhenyuan
 * @date 2020/5/12 16:28
 */
public interface OmsSmrRecordInfoService extends IService<OmsSmrRecordInfo>{
    //获取省国家保密局备案涉密人员列表
    IPage<OmsSmrRecordInfo> getSmrRecordInfoList(Page page, OmsSmrRecordInfo smrRecordInfo) throws ParseException;
    //获取遗漏的省管干部列表
    Result getMatchingPerson(String importYear, String b0100);
    //导出遗漏的省管干部列表
    void exportMatchingPerson(String importYear,String b0100,HttpServletResponse response);
    /**
    * @description:通过机构ID和导入年份删除已经导入过的数据
    * @author:杨波
    * @date:2020-09-22
    *  * @param b0100 机构ID
     *  @param importYear 导入年份
     * @return:
    **/
    void deleteByB0100AndYear(String b0100,String importYear);
}
