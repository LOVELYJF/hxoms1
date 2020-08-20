package com.hxoms.modules.passportCard.printGetQrCode.entity.parameterEntity;

import com.hxoms.modules.omsregcadre.entity.OmsRegProcpersoninfo;
import com.hxoms.modules.passportCard.initialise.entity.CfCertificate;

import java.util.List;

/**
 * @Desc：TODO
 * @Author: wangyunquan
 * @Date: 2020/8/20
 */
public class ReturnCertificateInfo {
    //证照信息
    private CfCertificate cfCertificate;
    //登记备案人员信息
    private List<OmsRegProcpersoninfo> omsRegProcpersoninfoList;

    public CfCertificate getCfCertificate() {
        return cfCertificate;
    }

    public void setCfCertificate(CfCertificate cfCertificate) {
        this.cfCertificate = cfCertificate;
    }

    public List<OmsRegProcpersoninfo> getOmsRegProcpersoninfoList() {
        return omsRegProcpersoninfoList;
    }

    public void setOmsRegProcpersoninfoList(List<OmsRegProcpersoninfo> omsRegProcpersoninfoList) {
        this.omsRegProcpersoninfoList = omsRegProcpersoninfoList;
    }
}
