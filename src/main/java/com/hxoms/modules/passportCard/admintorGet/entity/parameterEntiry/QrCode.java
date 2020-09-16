package com.hxoms.modules.passportCard.admintorGet.entity.parameterEntiry;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Desc：TODO
 * @Author: wangyunquan
 * @Date: 2020/9/4
 */
@ApiModel(value = "二维码")
public class QrCode {
    //二维码
    @ApiModelProperty(value = "二维码")
    private String QrCodeStr;

    public String getQrCodeStr() {
        return QrCodeStr;
    }

    public void setQrCodeStr(String qrCodeStr) {
        QrCodeStr = qrCodeStr;
    }
}
