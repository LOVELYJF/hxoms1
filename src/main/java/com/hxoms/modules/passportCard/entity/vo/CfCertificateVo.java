package com.hxoms.modules.passportCard.entity.vo;

import com.hxoms.modules.passportCard.entity.CfCertificate;

import java.util.List;

public class CfCertificateVo extends CfCertificate {

    /**
     * 单位ID
     */
    private String b0100;
    /**
     * 单位
     */
    private String b0101;

    /**
     * 职位
     */
    private String a0215a;

    /**
     * 在职状态
     */
    private String incumbencyStatus;
    /**
     * 单位父ID
     */
    private String b0100Pid;

    /**
     * 职务
     */
    private String duty;


    /**
     * 用于分页
     */
    private int pageSize;

    /**
     * 用于分页
     */
    private int pageNum;

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNum() {
        return pageNum;
    }


    public void setB0100(String b0100) {
        this.b0100 = b0100;
    }

    public String getB0101() {
        return b0101;
    }

    public void setB0101(String b0101) {
        this.b0101 = b0101;
    }

    public String getA0215a() {
        return a0215a;
    }

    public void setA0215a(String a0215a) {
        this.a0215a = a0215a;
    }

    public String getIncumbencyStatus() {
        return incumbencyStatus;
    }

    public void setIncumbencyStatus(String incumbencyStatus) {
        this.incumbencyStatus = incumbencyStatus;
    }

    public String getB0100Pid() {
        return b0100Pid;
    }

    public void setB0100Pid(String b0100Pid) {
        this.b0100Pid = b0100Pid;
    }

    public String getDuty() {
        return duty;
    }

    public void setDuty(String duty) {
        this.duty = duty;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

}
