package com.hxoms.modules.omsregcadre.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.hxoms.common.tree.Tree;
import com.hxoms.modules.omsregcadre.entity.*;
import com.hxoms.modules.omsregcadre.entity.paramentity.OmsRegProcpersoninfoIPagParam;
import com.hxoms.modules.omsregcadre.entity.paramentity.OmsRegYearCheckIPagParam;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface OmsRegProcpersonInfoService extends IService<OmsRegProcpersoninfo> {



    Object insertRpinfo(OmsRegProcpersoninfo orpInfo);

    Object updateRpinfo(OmsRegProcpersoninfo orpInfo);

    Object deleteRpinfo(String id);

    int insertOmsRegGongAn(List<OmsRegProcpersoninfo> list);

    int mergeDataGBandGA(String idStr);

    PageInfo<OmsRegProcpersoninfo> getProvinceCadreRegInfo(OmsRegProcpersoninfoIPagParam personInfoIPagParam);

    Object extractRegPersonInfo() throws ParseException;

    PageInfo<OmsRegProcpersoninfo> getRegPersonInfoList(OmsRegProcpersoninfoIPagParam personInfoIPagParam);

    PageInfo<OmsRegProcbatchPerson> selectPersonByBatchNo(String batchNo,Integer pageNum,Integer pageSize);

    int checkUploadRegRecord(List<OmsRegProcpersoninfo> list);

    List<String> queryYearList();

    PageInfo<OmsRegYearcheckinfo> queryYearCheckList(OmsRegYearCheckIPagParam regYearCheckIPagParam);

    Object selectPersonAndAllowRevoke(OmsRegProcpersoninfo msRegProcpersonInfo);

    Object selectInfoByA0100(String a0100);

    /**
     * <b>查询登记备案库中的人员信息（出生日期）</b>
     * @param a0100
     * @author luoshuai
     * @return
     */
    public Date getOmsRegProcpersonBirthDate(String a0100);

    PageInfo<OmsRegProcpersoninfo> getInitialReginfo(OmsRegProcpersoninfoIPagParam personInfoIPagParam) throws ParseException;

    Map<String, Object> selectStatisticsCount();

    List<OmsRegProcpersoninfo> selectMergeList(String sortType);

    List<OmsRegProcpersoninfo> selectListById(String idStr);

    int updateRegProcpersoninfo(String idStr);

    List<Map<String, Object>> selectGZPostTree();

    List<Tree> selectCGJPostTree(String dictCode);

    int insertBaseInfoConfig(List<OmsBaseinfoConfig> list);

    int deleteBaseInfoConfig(List<String> Ids);

    List<ExcelCheckModelORPinfo> selectCheckModelList(String year);
}
