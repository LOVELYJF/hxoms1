package com.hxoms.modules.passportCard.counterGet.entity.parameterEntity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * @Desc：TODO
 * @Author: wangyunquan
 * @Date: 2020/8/18
 */
public class CerGetTaskInfo {
    //主键ID
    private String id;
    //证照表ID
    private String cerId;
    //工作单位
    private String workUnit;
    //姓名
    private String name;
    //性别
    private String sex;
    //出生日期
    @JsonFormat(pattern = "yyyy.MM.dd")
    private Date csrq;
    //职务（级）或职称
    private String post;
    //领取状态
    private String getStatus;
    //证照类型
    private int zjlx;
    //证件号码
    private String zjhm;
    //有效期至
    @JsonFormat(pattern = "yyyy.MM.dd")
    private Date yxqz;
    //证照状态
    private String cardStatus;
    //审批表
    private String spb;
    //保管方式
    private String surelyWay;
    //机柜
    private String cabinetNum;
    //位置
    private String place;
    //柜台编号
    private String counterNum;
    //来源
    private String dataSource;
    //任务产生日期
    @JsonFormat(pattern = "yyyy.MM.dd")
    private Date createTime;
    //领取时间
    @JsonFormat(pattern = "yyyy.MM.dd")
    private Date getTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCerId() {
        return cerId;
    }

    public void setCerId(String cerId) {
        this.cerId = cerId;
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

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Date getCsrq() {
        return csrq;
    }

    public void setCsrq(Date csrq) {
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

    public int getZjlx() {
        return zjlx;
    }

    public void setZjlx(int zjlx) {
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

    public String getCardStatus() {
        return cardStatus;
    }

    public void setCardStatus(String cardStatus) {
        this.cardStatus = cardStatus;
    }

    public String getSpb() {
        return spb;
    }

    public void setSpb(String spb) {
        this.spb = spb;
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

    public String getDataSource() {
        return dataSource;
    }

    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getGetTime() {
        return getTime;
    }

    public void setGetTime(Date getTime) {
        this.getTime = getTime;
    }
}
