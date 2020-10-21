package com.hxoms.modules.omsregcadre.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import java.util.Date;

public class CfCertificateHistoryRecordModel {

    @ExcelProperty(value = "姓名", index = 0)
    private String name;

    @ExcelProperty(value = "性别", index = 1)
    private String sex;

    @ExcelProperty(value = "单位", index = 2)
    private String workUnit;

    @ExcelProperty(value = "任职状态", index = 3)
    private String incumbencyStatus;

    @ExcelProperty(value = "职务", index = 4)
    private String post;

    @ExcelProperty(value = "证件类型", index = 5)
    private String zjlxName;

    @ExcelProperty(value = "证件号码", index = 6)
    private String zjhm;

    @ExcelProperty(value = "有效期至", index = 7)
    private String yxqz;

    @ExcelProperty(value = "管理单位", index = 8)
    private String surelyUnit;

    @ExcelProperty(value = "出生日期", index = 9)
    private Date csrq;

    @ExcelProperty(value = "签发单位", index = 10)
    private String qfjg;

    @ExcelProperty(value = "签发日期", index = 11)
    private String qfrq;

    @ExcelProperty(value = "出生地点", index = 12)
    private String csdd;

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

    public String getYxqz() {
        return yxqz;
    }

    public void setYxqz(String yxqz) {
        this.yxqz = yxqz;
    }

    public String getSurelyUnit() {
        return surelyUnit;
    }

    public void setSurelyUnit(String surelyUnit) {
        this.surelyUnit = surelyUnit;
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

    public String getQfrq() {
        return qfrq;
    }

    public void setQfrq(String qfrq) {
        this.qfrq = qfrq;
    }

    public String getCsdd() {
        return csdd;
    }

    public void setCsdd(String csdd) {
        this.csdd = csdd;
    }
}
