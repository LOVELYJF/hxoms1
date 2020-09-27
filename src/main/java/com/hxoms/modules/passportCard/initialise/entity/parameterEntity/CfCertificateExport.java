package com.hxoms.modules.passportCard.initialise.entity.parameterEntity;

import com.hxoms.modules.omsregcadre.entity.OmsEntryexitRecord;
import com.hxoms.modules.omsregcadre.entity.OmsRegProcpersoninfo;
import com.hxoms.modules.passportCard.certificateCollect.entity.CfCertificateCollection;
import com.hxoms.modules.passportCard.initialise.entity.CfCertificate;
import com.hxoms.modules.passportCard.initialise.entity.OmsCerExitEntryImportManage;
import com.hxoms.modules.passportCard.initialise.entity.OmsCerImportManage;

import java.util.LinkedList;
import java.util.List;

/**
 * @Desc：TODO
 * @Author: wangyunquan
 * @Date: 2020/7/27
 */
public class CfCertificateExport {
    //证照信息集合
    private List<CfCertificate> cfCertificateList=new LinkedList<>();
    //出入境记录集合
    private List<OmsEntryexitRecord> omsEntryexitRecordList=new LinkedList<>();
    //证照导入管理集合
    private List<OmsCerImportManage> omsCerImportManagesList=new LinkedList<>();
    //出入境记录导入管理集合
    private List<OmsCerExitEntryImportManage> omsCerExitEntryImportManageList=new LinkedList<>();
    //证照信息更新集合
    private List<CfCertificate> cfCertificateUpdateList=new LinkedList<>();
    //登记备案表
    private List<OmsRegProcpersoninfo> omsRegProcpersoninfoList=new LinkedList<>();
    //自动解除催缴任务集合
    private List<CfCertificateCollection> cfCertificateCollectionList=new LinkedList<>();

    public List<CfCertificate> getCfCertificateList() {
        return cfCertificateList;
    }

    public void setCfCertificate(CfCertificate cfCertificate) {
        this.cfCertificateList.add(cfCertificate);
    }

    public List<OmsEntryexitRecord> getOmsEntryexitRecordList() {
        return omsEntryexitRecordList;
    }

    public void setOmsEntryexitRecord(OmsEntryexitRecord omsEntryexitRecord) {
        this.omsEntryexitRecordList.add(omsEntryexitRecord);
    }

    public List<OmsCerImportManage> getOmsCerImportManagesList() {
        return omsCerImportManagesList;
    }

    public void setOmsCerImportManages(OmsCerImportManage omsCerImportManage) {
        this.omsCerImportManagesList.add(omsCerImportManage);
    }

    public List<OmsCerExitEntryImportManage> getOmsCerExitEntryImportManageList() {
        return omsCerExitEntryImportManageList;
    }

    public void setOmsCerExitEntryImportManage(OmsCerExitEntryImportManage omsCerExitEntryImportManage) {
        this.omsCerExitEntryImportManageList.add(omsCerExitEntryImportManage);
    }

    public List<CfCertificate> getCfCertificateUpdateList() {
        return cfCertificateUpdateList;
    }

    public void setCfCertificateUpdate(CfCertificate cfCertificate) {
        this.cfCertificateUpdateList.add(cfCertificate);
    }

    public List<OmsRegProcpersoninfo> getOmsRegProcpersoninfoList() {
        return omsRegProcpersoninfoList;
    }

    public void setOmsRegProcpersoninfo(OmsRegProcpersoninfo omsRegProcpersoninfo) {
        this.omsRegProcpersoninfoList.add(omsRegProcpersoninfo);
    }

    public List<CfCertificateCollection> getCfCertificateCollectionList() {
        return cfCertificateCollectionList;
    }

    public void setCfCertificateCollection(CfCertificateCollection cfCertificateCollection) {
        this.cfCertificateCollectionList.add(cfCertificateCollection);
    }

    public void clear() {
        cfCertificateList.clear();
        omsEntryexitRecordList.clear();
        omsCerImportManagesList.clear();
        omsCerExitEntryImportManageList.clear();
        cfCertificateUpdateList.clear();
        omsRegProcpersoninfoList.clear();
        cfCertificateCollectionList.clear();
    }
}
