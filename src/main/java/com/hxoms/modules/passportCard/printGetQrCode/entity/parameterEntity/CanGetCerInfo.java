package com.hxoms.modules.passportCard.printGetQrCode.entity.parameterEntity;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.xml.crypto.Data;

/**
 * @Desc：TODO
 * @Author: wangyunquan
 * @Date: 2020/8/20
 */
public class CanGetCerInfo {
    //领取表id
    private String id;
    //工作单位
    private String workUnit;
    //姓名
    private String name;
    //出生日期
    @JsonFormat(pattern = "yyyy.MM.dd")
    private Data csrq;
    //职务
    private String post;
    //状态
    private String getStatus;
    //证件类型
    private Integer zjlx;
    //证件号码
    private String zjhm;
    //有效期至
    @JsonFormat(pattern = "yyyy.MM.dd")
    private Data yxqz;
    //审批表
    private String spb;
    //来源
    private String dataSource;
    //业务日期
    @JsonFormat(pattern = "yyyy.MM.dd")
    private Data happenDate;
    //生成日期
    @JsonFormat(pattern = "yyyy.MM.dd")
    private Data creatTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getWorkUnit() {
        return workUnit;
    }

    public void setWorkUnit(String workUnit) {
        this.workUnit = workUnit;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Data getCsrq() {
        return csrq;
    }

    public void setCsrq(Data csrq) {
        this.csrq = csrq;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getGetStatus() {
        return getStatus;
    }

    public void setGetStatus(String getStatus) {
        this.getStatus = getStatus;
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

    public Data getYxqz() {
        return yxqz;
    }

    public void setYxqz(Data yxqz) {
        this.yxqz = yxqz;
    }

    public String getSpb() {
        return spb;
    }

    public void setSpb(String spb) {
        this.spb = spb;
    }

    public String getDataSource() {
        return dataSource;
    }

    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
    }

    public Data getHappenDate() {
        return happenDate;
    }

    public void setHappenDate(Data happenDate) {
        this.happenDate = happenDate;
    }

    public Data getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(Data creatTime) {
        this.creatTime = creatTime;
    }
}
