package com.hxoms.modules.passportCard.deviceInteraction.entity.parameterEntiry;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Desc：TODO
 * @Author: wangyunquan
 * @Date: 2020/10/13
 */
@ApiModel(value = "入柜证件的简单信息")
public class SimpCerInfo {
    @ApiModelProperty(value = "证件号码")
    private String zjhm;
    @ApiModelProperty(value = "出生日期")
    private String csrq;
    @ApiModelProperty(value = "有效期至")
    private String yxqz;

    public String getZjhm() {
        return zjhm;
    }

    public void setZjhm(String zjhm) {
        this.zjhm = zjhm;
    }

    public String getCsrq() {
        return csrq;
    }

    public void setCsrq(String csrq) {
        this.csrq = csrq;
    }

    public String getYxqz() {
        return yxqz;
    }

    public void setYxqz(String yxqz) {
        this.yxqz = yxqz;
    }
}
