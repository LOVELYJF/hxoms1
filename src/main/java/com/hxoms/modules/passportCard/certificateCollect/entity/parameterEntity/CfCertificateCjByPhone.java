package com.hxoms.modules.passportCard.certificateCollect.entity.parameterEntity;

import com.hxoms.modules.sysUser.entity.CfUser;
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
    private List<CfUser> handlerList;
    //单位人员
    @ApiModelProperty(value = "催缴人员集合")
    private List<CfCertificateCjInfo> cfCertificateCjInfoList;

    public List<CfUser> getHandlerList() {
        return handlerList;
    }

    public void setHandlerList(List<CfUser> handlerList) {
        this.handlerList = handlerList;
    }

    public List<CfCertificateCjInfo> getCfCertificateCjInfoList() {
        return cfCertificateCjInfoList;
    }

    public void setCfCertificateCjInfoList(List<CfCertificateCjInfo> cfCertificateCjInfoList) {
        this.cfCertificateCjInfoList = cfCertificateCjInfoList;
    }
}
