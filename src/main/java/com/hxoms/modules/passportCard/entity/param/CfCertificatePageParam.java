package com.hxoms.modules.passportCard.entity.param;

import com.hxoms.modules.passportCard.entity.CfCertificate;

public class CfCertificatePageParam extends CfCertificate {

    /**页码*/
    private Integer pageNum;
    /**分页大小*/
    private Integer pageSize;

    private String incumbencyStatus;

    /**
     * 单位id
     */
    private String[] b0100s;

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
     * 单位父ID
     */
    private String b0100Pid;

    /**
     * 职务
     */
    private String duty;

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

    public String[] getB0100s() {
        return b0100s;
    }

    public void setB0100s(String[] b0100s) {
        this.b0100s = b0100s;
    }

    public String getB0100() {
        return b0100;
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
}