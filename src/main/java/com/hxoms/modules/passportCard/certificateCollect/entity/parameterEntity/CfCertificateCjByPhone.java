package com.hxoms.modules.passportCard.certificateCollect.entity.parameterEntity;

import com.hxoms.modules.sysUser.entity.CfUser;

import java.util.List;

/**
 * @Desc：TODO
 * @Author: wangyunquan
 * @Date: 2020/8/12
 */
public class CfCertificateCjByPhone {
    //经办人信息
    private List<CfUser> handlerList;
    //单位人员
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
