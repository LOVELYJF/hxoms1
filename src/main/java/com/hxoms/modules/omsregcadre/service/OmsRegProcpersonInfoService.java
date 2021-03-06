package com.hxoms.modules.omsregcadre.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.hxoms.common.tree.Tree;
import com.hxoms.common.utils.Result;
import com.hxoms.modules.omsregcadre.entity.*;
import com.hxoms.modules.omsregcadre.entity.paramentity.OmsRegProcpersoninfoIPagParam;
import com.hxoms.modules.omsregcadre.entity.paramentity.OmsRegYearCheckIPagParam;
import com.hxoms.support.leaderInfo.entity.A01;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface OmsRegProcpersonInfoService extends IService<OmsRegProcpersoninfo> {



    Object insertRpinfo(OmsRegProcpersoninfo orpInfo);

    Object updateRpinfo(OmsRegProcpersoninfo orpInfo);

    Object deleteRpinfo(String id);

    String insertOmsRegGongAn(List<OmsRegProcpersoninfo> list);

    Result mergeDataGBandGA(String idStr);

    PageInfo<OmsRegProcpersoninfo> getProvinceCadreRegInfo(OmsRegProcpersoninfoIPagParam personInfoIPagParam);

    Result extractRegPersonInfo() throws ParseException;

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

    void SplitName(OmsRegProcpersoninfo orpInfo,String name);

    List<Map> selectRegInfoListById(String idStr);

    HashMap<String, OmsRegProcpersoninfo> CacheRegProcpersonInfo(HashMap<String, OmsRegProcpersoninfo> nameAndIDCard);

    HashMap<String, List<Map<String, Object>>> CachePost(List<A01> a01list);

    HashMap<String, Map> CachePost();
}
