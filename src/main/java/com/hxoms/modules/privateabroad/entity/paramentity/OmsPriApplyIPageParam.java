package com.hxoms.modules.privateabroad.entity.paramentity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
 * @desc: 因私出国境申请列表查询条件
 * @author: lijing
 * @date: 2020-05-14
 */
@ApiModel(value = "因私出国境申请列表查询条件")
public class OmsPriApplyIPageParam {
    /**页码*/
    @ApiModelProperty(value = "页码")
    private Integer pageNum;
    /**分页大小*/
    @ApiModelProperty(value = "分页大小")
    private Integer pageSize;
    //申请开始时间
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    @ApiModelProperty(value = "申请开始时间")
    private Date applyStartTime;
    //申请结束时间
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    @ApiModelProperty(value = "申请结束时间")
    private Date applyStopTime;
    //出国时间（开始）
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    @ApiModelProperty(value = "出国时间（开始）")
    private Date abroadStartTime;
    //出国时间（结束）
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    @ApiModelProperty(value = "出国时间（结束）")
    private Date abroadEndTime;
    //申请人(姓名或者拼音)
    @ApiModelProperty(value = "申请人(姓名或者拼音)")
    private String name;
    //经办人（姓名或者拼音）
    @ApiModelProperty(value = "经办人（姓名或者拼音）")
    private String createUser;
    //状态
    private Integer[] applyStatus;
    @ApiModelProperty(value = "状态")
    private String applyStatusString;
    //机构id
    @ApiModelProperty(value = "机构id")
    private String b0100;
    //备案id
    @ApiModelProperty(value = "备案id")
    private String procpersonId;
    @ApiModelProperty(value = "比对结果")
    private String isComparison;
    @ApiModelProperty(value = "是否出入境")
    private String isAbroad;

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

    public String getIsAbroad() {
        return isAbroad;
    }

    public void setIsAbroad(String isAbroad) {
        this.isAbroad = isAbroad;
    }
}
