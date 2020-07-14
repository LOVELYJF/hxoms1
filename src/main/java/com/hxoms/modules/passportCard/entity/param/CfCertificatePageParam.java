package com.hxoms.modules.passportCard.entity.param;

import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

public class CfCertificatePageParam {

    /**页码*/
    @ApiModelProperty("分页开始")
    private Integer pageNum;
    /**分页大小*/
    @ApiModelProperty("分页结束")
    private Integer pageSize;

    private String incumbencyStatus;

    private String b0100[];

    private String id;

    private String omsId;

    private String name;

    private String py;

    private String a0184;

    private Date csrq;

    private  Integer sex;

    private String a0100;

    private String gj;

    private String zwcsdd;

    private String ywcsdd;

    private String zjhm;
    @ApiModelProperty("证照类型：1护照 3港澳通行证 4台湾通行证")
    private Integer zjlx;

    private String zjxs;

    private String location;

    private  String gjdw;

    private String zwqfdd;

    private String ywqfdd;

    private String zwqfjg;

    private String ywqfjg;

    private Date qfrq;

    private Date yxqz;

    private Date zjsjrq;

    private String manageUnit;

    private String cabinetNum;

    private  String storagePlace;

    private String saveStatus;

    private Integer dqzt;

    private Integer isLock;

    private String exceptionMessage;

    private Integer isCabinet;

    private Date updateTime;

    private Integer isValid;
    @ApiModelProperty("-1已过期数量  181半年内过期数量")
    private Integer dayqty;

    public Integer getDayqty() {
        return dayqty;
    }

    public void setDayqty(Integer dayqty) {
        this.dayqty = dayqty;
    }

    public Integer getIsValid() {
        return isValid;
    }

    public void setIsValid(Integer isValid) {
        this.isValid = isValid;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getGjdw() {
        return gjdw;
    }

    public void setGjdw(String gjdw) {
        this.gjdw = gjdw;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getA0100() {
        return a0100;
    }

    public void setA0100(String a0100) {
        this.a0100 = a0100 == null ? null : a0100.trim();
    }

    public String getGj() {
        return gj;
    }

    public void setGj(String gj) {
        this.gj = gj == null ? null : gj.trim();
    }

    public String getZwcsdd() {
        return zwcsdd;
    }

    public void setZwcsdd(String zwcsdd) {
        this.zwcsdd = zwcsdd == null ? null : zwcsdd.trim();
    }

    public String getYwcsdd() {
        return ywcsdd;
    }

    public void setYwcsdd(String ywcsdd) {
        this.ywcsdd = ywcsdd == null ? null : ywcsdd.trim();
    }

    public String getZjhm() {
        return zjhm;
    }

    public void setZjhm(String zjhm) {
        this.zjhm = zjhm == null ? null : zjhm.trim();
    }

    public Integer getZjlx() {
        return zjlx;
    }

    public void setZjlx(Integer zjlx) {
        this.zjlx = zjlx;
    }

    public String getZjxs() {
        return zjxs;
    }

    public void setZjxs(String zjxs) {
        this.zjxs = zjxs == null ? null : zjxs.trim();
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location == null ? null : location.trim();
    }

    public String getZwqfdd() {
        return zwqfdd;
    }

    public void setZwqfdd(String zwqfdd) {
        this.zwqfdd = zwqfdd == null ? null : zwqfdd.trim();
    }

    public String getYwqfdd() {
        return ywqfdd;
    }

    public void setYwqfdd(String ywqfdd) {
        this.ywqfdd = ywqfdd == null ? null : ywqfdd.trim();
    }

    public String getZwqfjg() {
        return zwqfjg;
    }

    public void setZwqfjg(String zwqfjg) {
        this.zwqfjg = zwqfjg == null ? null : zwqfjg.trim();
    }

    public String getYwqfjg() {
        return ywqfjg;
    }

    public void setYwqfjg(String ywqfjg) {
        this.ywqfjg = ywqfjg == null ? null : ywqfjg.trim();
    }

    public Date getQfrq() {
        return qfrq;
    }

    public void setQfrq(Date qfrq) {
        this.qfrq = qfrq;
    }

    public Date getYxqz() {
        return yxqz;
    }

    public void setYxqz(Date yxqz) {
        this.yxqz = yxqz;
    }

    public Date getZjsjrq() {
        return zjsjrq;
    }

    public void setZjsjrq(Date zjsjrq) {
        this.zjsjrq = zjsjrq;
    }

    public Integer getDqzt() {
        return dqzt;
    }

    public void setDqzt(Integer dqzt) {
        this.dqzt = dqzt;
    }

    public Integer getIsLock() {
        return isLock;
    }

    public void setIsLock(Integer isLock) {
        this.isLock = isLock;
    }

    public String getA0184() {
        return a0184;
    }

    public void setA0184(String a0184) {
        this.a0184 = a0184;
    }

    public String getManageUnit() {
        return manageUnit;
    }

    public void setManageUnit(String manageUnit) {
        this.manageUnit = manageUnit;
    }

    public String getStoragePlace() {
        return storagePlace;
    }

    public void setStoragePlace(String storagePlace) {
        this.storagePlace = storagePlace;
    }

    public String getCabinetNum() {
        return cabinetNum;
    }

    public void setCabinetNum(String cabinetNum) {
        this.cabinetNum = cabinetNum;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getPy() {
        return py;
    }

    public void setPy(String py) {
        this.py = py;
    }

    public String getOmsId() {
        return omsId;
    }

    public void setOmsId(String omsId) {
        this.omsId = omsId;
    }

    public Date getCsrq() {
        return csrq;
    }

    public void setCsrq(Date csrq) {
        this.csrq = csrq;
    }

    public String getSaveStatus() {
        return saveStatus;
    }

    public void setSaveStatus(String saveStatus) {
        this.saveStatus = saveStatus;
    }

    public String getExceptionMessage() {
        return exceptionMessage;
    }

    public void setExceptionMessage(String exceptionMessage) {
        this.exceptionMessage = exceptionMessage;
    }

    public Integer getIsCabinet() {
        return isCabinet;
    }

    public void setIsCabinet(Integer isCabinet) {
        this.isCabinet = isCabinet;
    }

    public String[] getB0100() {
        return b0100;
    }

    public void setB0100(String[] b0100) {
        this.b0100 = b0100;
    }

   /*   private String b0100[];

    public String[] getB0100() {
        return b0100;
    }

    public void setB0100(String[] b0100) {
        this.b0100 = b0100;
    }*/

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getIncumbencyStatus() {
        return incumbencyStatus;
    }

    public void setIncumbencyStatus(String incumbencyStatus) {
        this.incumbencyStatus = incumbencyStatus;
    }

}