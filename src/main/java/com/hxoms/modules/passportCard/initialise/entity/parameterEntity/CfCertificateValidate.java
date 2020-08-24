package com.hxoms.modules.passportCard.initialise.entity.parameterEntity;

import com.hxoms.modules.omsregcadre.entity.OmsRegProcpersoninfo;
import com.hxoms.modules.passportCard.initialise.entity.CfCertificate;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @Desc：TODO
 * @Author: wangyunquan
 * @Date: 2020/8/4
 */
@ApiModel(value = "验证证照返回信息")
public class CfCertificateValidate {
    //证照信息
    @ApiModelProperty(value = "证照信息")
    private CfCertificate cfCertificate;
    //登记备案人员信息
    @ApiModelProperty(value = "登记备案人员信息")
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
