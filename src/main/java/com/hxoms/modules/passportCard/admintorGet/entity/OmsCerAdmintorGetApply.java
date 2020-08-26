package com.hxoms.modules.passportCard.admintorGet.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hxoms.common.hxannotation.ColumnAnnotation;
import com.hxoms.common.hxannotation.IdAnnotation;
import com.hxoms.common.hxannotation.TableAnnotation;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@TableAnnotation(TableName = "oms_cer_admintor_get_apply", TableDescription="管理员取证申请表")
@ApiModel(value = "管理员取证申请表")
public class OmsCerAdmintorGetApply {
    @IdAnnotation
    @ColumnAnnotation(FieldName = "ID",   FieldDescription="主键")
    @ApiModelProperty(value="主键")
    private String id;

    @ColumnAnnotation(FieldName = "OMS_ID",   FieldDescription="备案信息表ID")
    @ApiModelProperty(value="备案信息表ID")
    private String omsId;

    @ColumnAnnotation(FieldName = "CER_ID",   FieldDescription="证照领取表ID")
    @ApiModelProperty(value="证照领取表ID")
    private String cerId;

    @ColumnAnnotation(FieldName = "NAME",   FieldDescription="姓名")
    @ApiModelProperty(value="姓名")
    private String name;

    @ColumnAnnotation(FieldName = "RF_B0000",   FieldDescription="机构")
    @ApiModelProperty(value="机构")
    private String rfB0000;

    @ColumnAnnotation(FieldName = "ZJLX",   FieldDescription="证件类型(1:护照,2:港澳通行证,4:台湾通行证)")
    @ApiModelProperty(value="证件类型(1:护照,2:港澳通行证,4:台湾通行证)")
    private Integer zjlx;

    @ColumnAnnotation(FieldName = "ZJHM",   FieldDescription="证件号码")
    @ApiModelProperty(value="证件号码")
    private String zjhm;

    @ColumnAnnotation(FieldName = "GET_CAUSE",   FieldDescription="取证原因")
    @ApiModelProperty(value="取证原因")
    private String getCause;

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

    public String getOmsId() {
        return omsId;
    }

    public void setOmsId(String omsId) {
        this.omsId = omsId == null ? null : omsId.trim();
    }

    public String getCerId() {
        return cerId;
    }

    public void setCerId(String cerId) {
        this.cerId = cerId == null ? null : cerId.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getRfB0000() {
        return rfB0000;
    }

    public void setRfB0000(String rfB0000) {
        this.rfB0000 = rfB0000 == null ? null : rfB0000.trim();
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

    public String getGetCause() {
        return getCause;
    }

    public void setGetCause(String getCause) {
        this.getCause = getCause == null ? null : getCause.trim();
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