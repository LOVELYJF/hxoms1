package com.hxoms.modules.privateabroad.entity.paramentity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @desc: 因私出国审批表回收列表参数
 * @author: lijing
 * @date: 2020-06-15
 */
public class OmsPriApprovalReturnIPageParam {
    /**页码*/
    private Integer pageNum;
    /**分页大小*/
    private Integer pageSize;
    //回收开始时间
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date returnStartTime;
    //回收结束时间
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date returnStopTime;
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
    //回收人（姓名）
    private String returnUser;

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

    public Date getReturnStartTime() {
        return returnStartTime;
    }

    public void setReturnStartTime(Date returnStartTime) {
        this.returnStartTime = returnStartTime;
    }

    public Date getReturnStopTime() {
        return returnStopTime;
    }

    public void setReturnStopTime(Date returnStopTime) {
        this.returnStopTime = returnStopTime;
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

    public String getReturnUser() {
        return returnUser;
    }

    public void setReturnUser(String returnUser) {
        this.returnUser = returnUser;
    }
}
