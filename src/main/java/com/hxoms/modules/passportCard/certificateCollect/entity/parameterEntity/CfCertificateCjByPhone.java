package com.hxoms.modules.passportCard.certificateCollect.entity.parameterEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @Desc：TODO
 * @Author: wangyunquan
 * @Date: 2020/8/12
 */
@ApiModel(value = "经办人及催缴人员")
public class CfCertificateCjByPhone {
    //经办人信息
    @ApiModelProperty(value = "经办人")
    private List<HandlerInfo> handlerList;
    //单位人员
    @ApiModelProperty(value = "催缴人员集合")
    private List<CfCertificatePhoneCjInfo> cfCertificatePhoneCjInfoList;

    public List<HandlerInfo> getHandlerList() {
        return handlerList;
    }

    public void setHandlerList(List<HandlerInfo> handlerList) {
        this.handlerList = handlerList;
    }

    public List<CfCertificatePhoneCjInfo> getCfCertificatePhoneCjInfoList() {
        return cfCertificatePhoneCjInfoList;
    }

    public void setCfCertificatePhoneCjInfoList(List<CfCertificatePhoneCjInfo> cfCertificatePhoneCjInfoList) {
        this.cfCertificatePhoneCjInfoList = cfCertificatePhoneCjInfoList;
    }
}
