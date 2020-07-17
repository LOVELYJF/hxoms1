package com.hxoms.modules.privateabroad.entity.paramentity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
 * @desc: 因私出国境申请列表查询条件
 * @author: lijing
 * @date: 2020-05-14
 */
public class OmsPriApplyIPageParam {
    /**页码*/
    private Integer pageNum;
    /**分页大小*/
    private Integer pageSize;
    //申请开始时间
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date applyStartTime;
    //申请结束时间
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date applyStopTime;
    //出国时间（开始）
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date abroadStartTime;
    //出国时间（结束）
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date abroadEndTime;
    //申请人(姓名或者拼音)
    private String name;
    //经办人（姓名或者拼音）
    private String createUser;
    //状态
    private Integer[] applyStatus;
    private String applyStatusString;
    //机构id
    private String b0100;

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

    public Date getApplyStartTime() {
        return applyStartTime;
    }

    public void setApplyStartTime(Date applyStartTime) {
        this.applyStartTime = applyStartTime;
    }

    public Date getApplyStopTime() {
        return applyStopTime;
    }

    public void setApplyStopTime(Date applyStopTime) {
        this.applyStopTime = applyStopTime;
    }

    public Date getAbroadStartTime() {
        return abroadStartTime;
    }

    public void setAbroadStartTime(Date abroadStartTime) {
        this.abroadStartTime = abroadStartTime;
    }

    public Date getAbroadEndTime() {
        return abroadEndTime;
    }

    public void setAbroadEndTime(Date abroadEndTime) {
        this.abroadEndTime = abroadEndTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public Integer[] getApplyStatus() {
        return applyStatus;
    }

    public void setApplyStatus(Integer[] applyStatus) {
        this.applyStatus = applyStatus;
    }

    public String getApplyStatusString() {
        return applyStatusString;
    }

    public void setApplyStatusString(String applyStatusString) {
        this.applyStatusString = applyStatusString;
    }

    public String getB0100() {
        return b0100;
    }

    public void setB0100(String b0100) {
        this.b0100 = b0100;
    }
}
