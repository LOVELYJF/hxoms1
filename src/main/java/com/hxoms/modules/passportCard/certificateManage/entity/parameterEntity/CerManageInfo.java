package com.hxoms.modules.passportCard.certificateManage.entity.parameterEntity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * @Desc：TODO
 * @Author: wangyunquan
 * @Date: 2020/8/13
 */
public class CerManageInfo {
    //主键
    private String id;
    //姓名
    private String name;
    //性别
    private String sex;
    //单位
    private String workUnit;
    //任职状态
    private String incumbencyStatus;
    //职务
    private String post;
    //证照类型
    private Integer zjlx;
    //芯片类型
    private String xplx;
    //证照形式
    private String zjxs;
    //证件号码
    private String zjhm;
    //有效期至
    @JsonFormat(pattern = "yyyy.MM.dd")
    private Date yxqz;
    //管理单位
    private String surelyUnit;
    //证照状态
    private String cardStatus;
    //保管状态
    private String saveStatus;
    //保管方式
    private String surelyWay;
    //机柜
    private String cabinetNum;
    //位置
    private String place;
    //柜台编号
    private String counterNum;
    //出生日期
    @JsonFormat(pattern = "yyyy.MM.dd")
    private Date csrq;
    //签发单位
    private String qfjg;
    //签发日期
    @JsonFormat(pattern = "yyyy.MM.dd")
    private Date qfrq;
    //出生地点
    private String csdd;
    //存疑信息
    private String exceprionMessqge;

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

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getWorkUnit() {
        return workUnit;
    }

    public void setWorkUnit(String workUnit) {
        this.workUnit = workUnit;
    }

    public String getIncumbencyStatus() {
        return incumbencyStatus;
    }

    public void setIncumbencyStatus(String incumbencyStatus) {
        this.incumbencyStatus = incumbencyStatus;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public Integer getZjlx() {
        return zjlx;
    }

    public void setZjlx(Integer zjlx) {
        this.zjlx = zjlx;
    }

    public String getXplx() {
        return xplx;
    }

    public void setXplx(String xplx) {
        this.xplx = xplx;
    }

    public String getZjxs() {
        return zjxs;
    }

    public void setZjxs(String zjxs) {
        this.zjxs = zjxs;
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

    public String getSurelyUnit() {
        return surelyUnit;
    }

    public void setSurelyUnit(String surelyUnit) {
        this.surelyUnit = surelyUnit;
    }

    public String getCardStatus() {
        return cardStatus;
    }

    public void setCardStatus(String cardStatus) {
        this.cardStatus = cardStatus;
    }

    public String getSaveStatus() {
        return saveStatus;
    }

    public void setSaveStatus(String saveStatus) {
        this.saveStatus = saveStatus;
    }

    public String getSurelyWay() {
        return surelyWay;
    }

    public void setSurelyWay(String surelyWay) {
        this.surelyWay = surelyWay;
    }

    public String getCabinetNum() {
        return cabinetNum;
    }

    public void setCabinetNum(String cabinetNum) {
        this.cabinetNum = cabinetNum;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getCounterNum() {
        return counterNum;
    }

    public void setCounterNum(String counterNum) {
        this.counterNum = counterNum;
    }

    public Date getCsrq() {
        return csrq;
    }

    public void setCsrq(Date csrq) {
        this.csrq = csrq;
    }

    public String getQfjg() {
        return qfjg;
    }

    public void setQfjg(String qfjg) {
        this.qfjg = qfjg;
    }

    public Date getQfrq() {
        return qfrq;
    }

    public void setQfrq(Date qfrq) {
        this.qfrq = qfrq;
    }

    public String getCsdd() {
        return csdd;
    }

    public void setCsdd(String csdd) {
        this.csdd = csdd;
    }

    public String getExceprionMessqge() {
        return exceprionMessqge;
    }

    public void setExceprionMessqge(String exceprionMessqge) {
        this.exceprionMessqge = exceprionMessqge;
    }
}
