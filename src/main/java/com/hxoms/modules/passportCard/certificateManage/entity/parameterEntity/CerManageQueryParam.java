package com.hxoms.modules.passportCard.certificateManage.entity.parameterEntity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @Desc：TODO
 * @Author: wangyunquan
 * @Date: 2020/8/13
 */
public class CerManageQueryParam {
    //姓名
    @ApiModelProperty(value="姓名")
    private String name;
    //机构
    @ApiModelProperty(value = "机构")
    private String rfB0000;
    //证件类型
    @ApiModelProperty(value = "证件类型(1:护照,2:港澳通行证,4:台湾通行证)")
    private Integer zjlx;
    //证件号码
    @ApiModelProperty(value = "证件号码")
    private String zjhm;
    //有效期至
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @ApiModelProperty(value="有效期至")
    private Date yxqz;
    //保管状态
    @ApiModelProperty(value = "保管状态(0:正常保管,1:已取出,2:未上缴)")
    private String saveStatus;
    //证照状态
    @ApiModelProperty(value = "证照状态(0:正常,1:过期,2:注销,3:验证失败,4:已验证,5:待验证,6:借出,7:待领取,8:已领取)")
    private String cardStatus;
    //证件样式
    @ApiModelProperty(value="证件形式(0:本式证照,1:卡式证照)")
    private String zjxs;
    //芯片类型
    @ApiModelProperty(value="芯片类型(0:自带,1:粘贴)")
    private String xplx;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRfB0000() {
        return rfB0000;
    }

    public void setRfB0000(String rfB0000) {
        this.rfB0000 = rfB0000;
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

    public Date getYxqz() {
        return yxqz;
    }

    public void setYxqz(Date yxqz) {
        this.yxqz = yxqz;
    }

    public String getSaveStatus() {
        return saveStatus;
    }

    public void setSaveStatus(String saveStatus) {
        this.saveStatus = saveStatus;
    }

    public String getCardStatus() {
        return cardStatus;
    }

    public void setCardStatus(String cardStatus) {
        this.cardStatus = cardStatus;
    }

    public String getZjxs() {
        return zjxs;
    }

    public void setZjxs(String zjxs) {
        this.zjxs = zjxs;
    }

    public String getXplx() {
        return xplx;
    }

    public void setXplx(String xplx) {
        this.xplx = xplx;
    }
}
