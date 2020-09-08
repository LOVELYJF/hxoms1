package com.hxoms.modules.omsregcadre.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.hxoms.modules.keySupervision.suspendApproval.entity.OmsSupSuspendUnit;
import com.hxoms.modules.omsregcadre.entity.OmsEntryexitRecord;
import com.hxoms.modules.omsregcadre.entity.OmsEntryexitRecordModel;
import com.hxoms.modules.omsregcadre.entity.OmsEntryexitRecordVO;
import com.hxoms.modules.omsregcadre.entity.paramentity.OmsEntryexitRecordIPagParam;
import com.hxoms.modules.passportCard.omsCerCancellateLicense.entity.OmsCerCancellateLicense;
import com.hxoms.modules.privateabroad.entity.OmsPriApply;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface OmsEntryexitRecordService extends IService<OmsEntryexitRecord> {

    PageInfo<OmsEntryexitRecord> getEntryexitRecordinfo(OmsEntryexitRecordIPagParam entryexitRecordIPagParam);

    Map<String, Object> queryPriApplyList(String omsId);

    Object batchPriApplyList(List<String> omsIds);

    Object insertEntryexitRecord(OmsEntryexitRecord entryexitRecord);

    Object updateEntryexitRecord(OmsEntryexitRecord entryexitRecord);

    Object deleteEntryexitRecord(String id);

    Map<String, Object> selectCompareInfo(OmsPriApply priapply, OmsEntryexitRecord outinfo, OmsEntryexitRecord joininfo);

    Object clockGoAbroadApply(OmsSupSuspendUnit supSuspendUnit);

    int cancelCompareInfo(String id);/**
     * @description: 填写有关情况报告时进行比对，检查出入境记录是否合规
     * @author:李姣姣
     * @date:2020-08-19
     *  * @param apply 因私出国申请
     * @return:void
     **/
    void verifySituationReport(OmsPriApply apply);
    /**
     * @description: 单人出入境记录比对
     * @author:李姣姣
     * @date:2020-08-20
     *  * @param reg 登记备案人员信息
     * @return:void
     **/
    void entryexitRecordCompare(String omsId);
    /**
     * @description:出入境记录检查
     * @author:李姣姣
     * @date:2020-08-19
     * @param applyDate 申请出国境日期
     * @param applyID 出国境申请主键
     * @param oldExit 被比较出境时间
     * @param oldEntry 被比较入境时间
     * @param oldDestination 被比较目的地
     * @param newExit 用于比较的出境时间
     * @param newEntry 用于比较的入境时间
     * @param newDestination 用于比较的目的地
     * @param sensitiveCountry 禁止性、限制性、敏感性国家或地区
     * @param zzlist 证照信息
     * @return:java.lang.String
     **/
    String EntryexitRecordChecking(Date applyDate, String applyID,
                                   Date oldExit, Date oldEntry, String oldDestination,
                                   Date newExit, Date newEntry, String newDestination,
                                   Map<String, String> sensitiveCountry,
                                   List<OmsCerCancellateLicense> zzlist);


    Map<String, Object> queryCompresultByYear(OmsEntryexitRecordIPagParam entryexitRecordIPagParam);


    List<OmsEntryexitRecordVO> queryExceptionPriApplyList(String omsId);

    PageInfo<OmsEntryexitRecordVO> getExceptionRecord(OmsEntryexitRecordIPagParam entryexitRecordIPagParam);

    List<OmsEntryexitRecordModel> newexitRecordsList(List<String> ids);
}
