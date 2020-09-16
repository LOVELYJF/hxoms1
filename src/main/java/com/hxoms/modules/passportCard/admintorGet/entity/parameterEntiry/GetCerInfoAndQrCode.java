package com.hxoms.modules.passportCard.admintorGet.entity.parameterEntiry;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @Desc：TODO
 * @Author: wangyunquan
 * @Date: 2020/9/15
 */
@ApiModel(value = "领取证照信息及二维码")
public class GetCerInfoAndQrCode {
    //领取证照信息
    @ApiModelProperty(value = "领取证照信息")
    private List<CanGetCerInfo> canGetCerInfoList;
    //二维码BASE64字符
    @ApiModelProperty(value = "二维码BASE64字符")
    private String qrCode;

    public List<CanGetCerInfo> getCanGetCerInfoList() {
        return canGetCerInfoList;
    }

    public void setCanGetCerInfoList(List<CanGetCerInfo> canGetCerInfoList) {
        this.canGetCerInfoList = canGetCerInfoList;
    }

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }
}
