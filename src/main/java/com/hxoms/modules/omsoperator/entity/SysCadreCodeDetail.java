package com.hxoms.modules.omsoperator.entity;

import java.util.Date;

public class SysCadreCodeDetail {
	private String id;

    private String codeid;

    private String code;

    private String name;

    private String pid;

    private String remark;

    private Integer uSortno;

    private String uCreator;

    private Date uCreatedate;

    private String uCreatororgid;

    private String uLastmodifieder;

    private Date uLastmodifieddate;

    private String uLastmodifiederorgid;

    private Boolean uIsvalid;

    private String uAreacode;

    private Boolean uIssystem;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCodeid() {
        return codeid;
    }

    public void setCodeid(String codeid) {
        this.codeid = codeid;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getuSortno() {
        return uSortno;
    }

    public void setuSortno(Integer uSortno) {
        this.uSortno = uSortno;
    }

    public String getuCreator() {
        return uCreator;
    }

    public void setuCreator(String uCreator) {
        this.uCreator = uCreator;
    }

    public Date getuCreatedate() {
        return uCreatedate;
    }

    public void setuCreatedate(Date uCreatedate) {
        this.uCreatedate = uCreatedate;
    }

    public String getuCreatororgid() {
        return uCreatororgid;
    }

    public void setuCreatororgid(String uCreatororgid) {
        this.uCreatororgid = uCreatororgid;
    }

    public String getuLastmodifieder() {
        return uLastmodifieder;
    }

    public void setuLastmodifieder(String uLastmodifieder) {
        this.uLastmodifieder = uLastmodifieder;
    }

    public Date getuLastmodifieddate() {
        return uLastmodifieddate;
    }

    public void setuLastmodifieddate(Date uLastmodifieddate) {
        this.uLastmodifieddate = uLastmodifieddate;
    }

    public String getuLastmodifiederorgid() {
        return uLastmodifiederorgid;
    }

    public void setuLastmodifiederorgid(String uLastmodifiederorgid) {
        this.uLastmodifiederorgid = uLastmodifiederorgid;
    }

    public Boolean getuIsvalid() {
        return uIsvalid;
    }

    public void setuIsvalid(Boolean uIsvalid) {
        this.uIsvalid = uIsvalid;
    }

    public String getuAreacode() {
        return uAreacode;
    }

    public void setuAreacode(String uAreacode) {
        this.uAreacode = uAreacode;
    }

    public Boolean getuIssystem() {
        return uIssystem;
    }

    public void setuIssystem(Boolean uIssystem) {
        this.uIssystem = uIssystem;
    }
}