package com.hxoms.modules.passportCard.initialise.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hxoms.common.hxannotation.ColumnAnnotation;
import com.hxoms.common.hxannotation.IdAnnotation;
import com.hxoms.common.hxannotation.TableAnnotation;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@TableAnnotation(TableName = "oms_cer_counter_number", TableDescription="证照柜台编号表")
@ApiModel(value = "证照柜台编号表")
public class OmsCerCounterNumber {
    @IdAnnotation
    @ColumnAnnotation(FieldName = "ID",   FieldDescription="主键")
    @ApiModelProperty(value="主键")
    private String id;

    @ColumnAnnotation(FieldName = "SURELY_UNIT",   FieldDescription="保管单位(0:干部监督处,1:省委统战部(台办))")
    @ApiModelProperty(value="保管单位(0:干部监督处,1:省委统战部(台办))")
    private String surelyUnit;

    @ColumnAnnotation(FieldName = "ZJLX",   FieldDescription="证件类型(1:护照,2:港澳通行证,4:台湾通行证)")
    @ApiModelProperty(value="证件类型(1:护照,2:港澳通行证,4:台湾通行证)")
    private Integer zjlx;

    @ColumnAnnotation(FieldName = "ZJXS",   FieldDescription="证件形式(0:本式证照,1:卡式证照)")
    @ApiModelProperty(value="证件形式(0:本式证照,1:卡式证照)")
    private String zjxs;

    @ColumnAnnotation(FieldName = "COUNTER_NUM",   FieldDescription="柜台编号")
    @ApiModelProperty(value="柜台编号")
    private Integer counterNum;

    @ColumnAnnotation(FieldName = "STATUS",   FieldDescription="状态(0:未使用,1:已使用)")
    @ApiModelProperty(value="状态(0:未使用,1:已使用)")
    private String status;

    @ColumnAnnotation(FieldName = "IS_LOCK",   FieldDescription="是否锁定(0:未锁定,1:锁定)")
    @ApiModelProperty(value="是否锁定(0:未锁定,1:锁定)")
    private String isLock;

    @ColumnAnnotation(FieldName = "CREATOR",   FieldDescription="创建人")
    @ApiModelProperty(value="创建人")
    private String creator;

    @ColumnAnnotation(FieldName = "CREATE_TIME",   FieldDescription="创建时间")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    @ApiModelProperty(value="创建时间")
    private Date createTime;

    @ColumnAnnotation(FieldName = "UPDATER",   FieldDescription="修改人")
    @ApiModelProperty(value="修改人")
    private String updater;

    @ColumnAnnotation(FieldName = "UPDATE_TIME",   FieldDescription="修改时间")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    @ApiModelProperty(value="修改时间")
    private Date updateTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getSurelyUnit() {
        return surelyUnit;
    }

    public void setSurelyUnit(String surelyUnit) {
        this.surelyUnit = surelyUnit == null ? null : surelyUnit.trim();
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

    public Integer getCounterNum() {
        return counterNum;
    }

    public void setCounterNum(Integer counterNum) {
        this.counterNum = counterNum;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getIsLock() {
        return isLock;
    }

    public void setIsLock(String isLock) {
        this.isLock = isLock == null ? null : isLock.trim();
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator == null ? null : creator.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUpdater() {
        return updater;
    }

    public void setUpdater(String updater) {
        this.updater = updater == null ? null : updater.trim();
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}