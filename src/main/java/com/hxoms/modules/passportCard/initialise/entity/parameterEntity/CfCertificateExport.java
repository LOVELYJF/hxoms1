package com.hxoms.modules.passportCard.initialise.entity.parameterEntity;

import com.hxoms.modules.omsregcadre.entity.OmsEntryexitRecord;
import com.hxoms.modules.passportCard.initialise.entity.CfCertificate;
import com.hxoms.modules.passportCard.initialise.entity.OmsCerIssuePerson;

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
    //异常人员
    private List<OmsCerIssuePerson> omsCerIssuePersonList=new LinkedList<>();

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

    public List<OmsCerIssuePerson> getOmsCerIssuePersonList() {
        return omsCerIssuePersonList;
    }

    public void setOmsCerIssuePerson(OmsCerIssuePerson omsCerIssuePerson) {
        this.omsCerIssuePersonList.add(omsCerIssuePerson);
    }
}
