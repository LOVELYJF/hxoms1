package com.hxoms.modules.publicity.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
 * 功能描述: <br>
 * 〈因公出国境查询列表条件〉
 * @Param:
 * @Return:
 * @Author: 李逍遥
 * @Date: 2020/6/28 17:44
 */
public class OmsPubApplyQueryParam {
    /**页码*/
    private Integer pageNum;
    /**分页大小*/
    private Integer pageSize;
    /**申请状态集合 */
    private List<Integer> status;
    /** 姓名*/
    private String name;
    /** 出国时间*/
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date cgsj;
    /** 回国时间*/
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date hgsj;
    /**组团单位*/
    private String ztdw;
    /** 通知书文号*/
    private String pwh;
    /** 团组名称*/
    private String tzmc;
    /** 机构id */
    private String b0100;
    /** 对比标识*/
    private String isComparison;
    /** 备案id*/
    private String procpersonId;
    /** 申請id*/
    private String applyId;

    public String getPwh() {
        return pwh;
    }

    public void setPwh(String pwh) {
        this.pwh = pwh;
    }

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

    public List<Integer> getStatus() {
        return status;
    }

    public void setStatus(List<Integer> status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCgsj() {
        return cgsj;
    }

    public void setCgsj(Date cgsj) {
        this.cgsj = cgsj;
    }

    public Date getHgsj() {
        return hgsj;
    }

    public void setHgsj(Date hgsj) {
        this.hgsj = hgsj;
    }

    public String getZtdw() {
        return ztdw;
    }

    public void setZtdw(String ztdw) {
        this.ztdw = ztdw;
    }

    public String getTzmc() {
        return tzmc;
    }

    public void setTzmc(String tzmc) {
        this.tzmc = tzmc;
    }

    public String getB0100() {
        return b0100;
    }

    public void setB0100(String b0100) {
        this.b0100 = b0100;
    }

    public String getIsComparison() {
        return isComparison;
    }

    public void setIsComparison(String isComparison) {
        this.isComparison = isComparison;
    }

    public String getProcpersonId() {
        return procpersonId;
    }

    public void setProcpersonId(String procpersonId) {
        this.procpersonId = procpersonId;
    }

    public String getApplyId() {
        return applyId;
    }

    public void setApplyId(String applyId) {
        this.applyId = applyId;
    }
}
