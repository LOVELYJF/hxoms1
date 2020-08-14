package com.hxoms.modules.passportCard.certificateManage.entity.parameterEntity;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @Desc：TODO
 * @Author: wangyunquan
 * @Date: 2020/8/13
 */
public class CerManageQueryParam {
    //姓名
    private String name;
    //机构
    private String rfB0000;
    //证件类型
    private Integer zjlx;
    //证件号码
    private String zjhm;
    //有效期至
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date yxqz;
    //保管状态
    private String saveStatus;
    //证照状态
    private String cardStatus;
    //证件样式
    private String zjxs;
    //芯片类型
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
