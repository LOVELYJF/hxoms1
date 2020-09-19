package com.hxoms.modules.passportCard.exitEntryManage.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hxoms.common.hxannotation.ColumnAnnotation;
import com.hxoms.common.hxannotation.IdAnnotation;
import com.hxoms.common.hxannotation.TableAnnotation;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@TableAnnotation(TableName = "oms_cer_exit_entry_repertory", TableDescription="证照出入库表")
@ApiModel(value = "证照出入库表")
public class OmsCerExitEntryRepertory {
    @IdAnnotation
    @ColumnAnnotation(FieldName = "ID",   FieldDescription="主键")
    @ApiModelProperty(value="主键")
    private String id;

    @ColumnAnnotation(FieldName = "CER_ID",   FieldDescription="证照管理表ID")
    @ApiModelProperty(value="证照管理表ID")
    private String cerId;

    @ColumnAnnotation(FieldName = "GET_ID",   FieldDescription="证照领取表ID")
    @ApiModelProperty(value="证照领取表ID")
    private String getId;

    @ColumnAnnotation(FieldName = "NAME",   FieldDescription="姓名")
    @ApiModelProperty(value="姓名")
    private String name;

    @ColumnAnnotation(FieldName = "ZJLX",   FieldDescription="证件类型(1:护照,2:港澳通行证,4:台湾通行证)")
    @ApiModelProperty(value="证件类型(1:护照,2:港澳通行证,4:台湾通行证)")
    private Integer zjlx;

    @ColumnAnnotation(FieldName = "ZJHM",   FieldDescription="证件号码")
    @ApiModelProperty(value="证件号码")
    private String zjhm;

    @ColumnAnnotation(FieldName = "STATUS",   FieldDescription="出入库状态(0:出库,1:入库)")
    @ApiModelProperty(value="出入库状态(0:出库,1:入库)")
    private String status;

    @ColumnAnnotation(FieldName = "MODE",   FieldDescription="存取方式(0:证照机,1:柜台)")
    @ApiModelProperty(value="存取方式(0:证照机,1:柜台)")
    private String mode;

    @ColumnAnnotation(FieldName = "CABINET_NUM",   FieldDescription="机柜编号")
    @ApiModelProperty(value="机柜编号")
    private String cabinetNum;

    @ColumnAnnotation(FieldName = "PLACE",   FieldDescription="机柜位置")
    @ApiModelProperty(value="机柜位置")
    private String place;

    @ColumnAnnotation(FieldName = "COUNTER_NUM",   FieldDescription="柜台编号")
    @ApiModelProperty(value="柜台编号")
    private Integer counterNum;

    @ColumnAnnotation(FieldName = "OPERATOR",   FieldDescription="操作人")
    @ApiModelProperty(value="操作人")
    private String operator;

    @ColumnAnnotation(FieldName = "OPERATE_TIME",   FieldDescription="操作时间")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    @ApiModelProperty(value="操作时间")
    private Date operateTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getCerId() {
        return cerId;
    }

    public void setCerId(String cerId) {
        this.cerId = cerId == null ? null : cerId.trim();
    }

    public String getGetId() {
        return getId;
    }

    public void setGetId(String getId) {
        this.getId = getId == null ? null : getId.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getZjlx() {
        return zjlx;
    }

    public void setZjlx(Integer zjlx) {
        this.zjlx = zjlx;
    }

    public String getZjhm() {
        return zjhm;
    }

    public void setZjhm(String zjhm) {
        this.zjhm = zjhm == null ? null : zjhm.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode == null ? null : mode.trim();
    }

    public String getCabinetNum() {
        return cabinetNum;
    }

    public void setCabinetNum(String cabinetNum) {
        this.cabinetNum = cabinetNum == null ? null : cabinetNum.trim();
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place == null ? null : place.trim();
    }

    public Integer getCounterNum() {
        return counterNum;
    }

    public void setCounterNum(Integer counterNum) {
        this.counterNum = counterNum;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator == null ? null : operator.trim();
    }

    public Date getOperateTime() {
        return operateTime;
    }

    public void setOperateTime(Date operateTime) {
        this.operateTime = operateTime;
    }
}