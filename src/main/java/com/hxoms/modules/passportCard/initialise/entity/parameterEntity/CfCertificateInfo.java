package com.hxoms.modules.passportCard.initialise.entity.parameterEntity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * @Desc：TODO
 * @Author: wangyunquan
 * @Date: 2020/8/5
 */
@ApiModel(value = "证照信息")
public class CfCertificateInfo {
    //主键
    @ApiModelProperty(value = "主键")
    private String id;
    //备案表id
    @ApiModelProperty(value = "备案表id")
    private String omsId;
    //单位编码
    @ApiModelProperty(value = "单位编码")
    private String rfB0000;
    //工作单位
    @ApiModelProperty(value = "工作单位")
    private String workUnit;
    //任职状态
    @ApiModelProperty(value = "任职状态")
    private String incumbencyStatus;
    //职务
    @ApiModelProperty(value = "职务")
    private String post;
    //姓名
    @ApiModelProperty(value = "姓名")
    private String name;
    //性别
    @ApiModelProperty(value = "性别")
    private String sex;
    //证照类型
    @ApiModelProperty(value = "证照类型编码")
    private Integer zjlx;
    //证照类型
    @ApiModelProperty(value = "证照类型名称")
    private String zjlxName;
    //证件号码
    @ApiModelProperty(value = "证件号码")
    private String zjhm;
    //有效期至
    @JsonFormat(pattern = "yyyy.MM.dd")
    @ApiModelProperty(value = "有效期至")
    private Date yxqz;
    //保管单位
    @ApiModelProperty(value = "保管单位")
    private String surelyUnit;
    //保管方式
    @ApiModelProperty(value = "保管方式")
    private String surelyWay;
    //机柜编号
    @ApiModelProperty(value = "机柜编号")
    private String cabinetNum;
    //位置
    @ApiModelProperty(value = "位置")
    private String place;
    //出生日期
    @JsonFormat(pattern = "yyyy.MM.dd")
    @ApiModelProperty(value = "出生日期")
    private Date csrq;
    //签发单位
    @ApiModelProperty(value = "签发单位")
    private String qfjg;
    //签发日期
    @JsonFormat(pattern = "yyyy.MM.dd")
    @ApiModelProperty(value = "签发日期")
    private  Date qfrq;
    //出生地
    @ApiModelProperty(value = "出生地")
    private String csdd;
    //存疑原因
    @ApiModelProperty(value = "存疑原因")
    private String exceptionMessage;
    //国籍
    @ApiModelProperty(value = "国籍")
    private String gj;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOmsId() {
        return omsId;
    }

    public void setOmsId(String omsId) {
        this.omsId = omsId;
    }

    public String getRfB0000() {
        return rfB0000;
    }

    public void setRfB0000(String rfB0000) {
        this.rfB0000 = rfB0000;
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

    public Integer getZjlx() {
        return zjlx;
    }

    public void setZjlx(Integer zjlx) {
        this.zjlx = zjlx;
    }

    public String getZjlxName() {
        return zjlxName;
    }

    public void setZjlxName(String zjlxName) {
        this.zjlxName = zjlxName;
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

    public String getExceptionMessage() {
        return exceptionMessage;
    }

    public void setExceptionMessage(String exceptionMessage) {
        this.exceptionMessage = exceptionMessage;
    }

    public String getGj() {
        return gj;
    }

    public void setGj(String gj) {
        this.gj = gj;
    }
}
