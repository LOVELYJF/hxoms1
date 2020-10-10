package com.hxoms.modules.passportCard.deviceInteraction.entity.parameterEntiry;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Desc：TODO
 * @Author: wangyunquan
 * @Date: 2020/10/10
 */
@ApiModel(value = "证件领取返回信息")
public class CerGetInfo {
    @ApiModelProperty(value = "领取id")
    private String id;
    @ApiModelProperty(value = "姓名")
    private String name;
    @ApiModelProperty(value = "证件类型（1：护照，2：港澳通行证，4：台湾通行证)")
    private Integer zjlx;
    @ApiModelProperty(value = "证件号码")
    private String zjhm;
    @ApiModelProperty(value = "出生日期")
    private String csrq;
    @ApiModelProperty(value = "有效期至")
    private String yxqz;
    @ApiModelProperty(value = "领取状态（0：未领取，1：领取，2：取消领取）")
    private String getStatus;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getZjlx() {
        return zjlx;
    }

    public void setZjlx(Integer zjlx) {
        this.zjlx = zjlx;
    }

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

    public String getGetStatus() {
        return getStatus;
    }

    public void setGetStatus(String getStatus) {
        this.getStatus = getStatus;
    }
}
