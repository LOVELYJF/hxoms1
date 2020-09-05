package com.hxoms.modules.passportCard.printGetQrCode.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hxoms.common.hxannotation.ColumnAnnotation;
import com.hxoms.common.hxannotation.IdAnnotation;
import com.hxoms.common.hxannotation.TableAnnotation;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@TableAnnotation(TableName = "oms_cer_print_qr_code", TableDescription="证照打印二维码表")
@ApiModel(value = "证照打印二维码表")
public class OmsCerPrintQrCode {
    @IdAnnotation
    @ColumnAnnotation(FieldName = "ID",   FieldDescription="主键")
    @ApiModelProperty(value="主键")
    private String id;

    @ColumnAnnotation(FieldName = "QR_CODE_ID",   FieldDescription="二维码ID")
    @ApiModelProperty(value="二维码ID")
    private String qrCodeId;

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

    @ColumnAnnotation(FieldName = "QR_URL",   FieldDescription="二维码访问URL")
    @ApiModelProperty(value="二维码访问URL")
    private String qrUrl;

    @ColumnAnnotation(FieldName = "OPERATOR",   FieldDescription="操作人")
    @ApiModelProperty(value="操作人")
    private String operator;

    @ColumnAnnotation(FieldName = "OPERATE_TIME",   FieldDescription="操作时间")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    @ApiModelProperty(value="操作时间")
    private Date operateTime;

    @ColumnAnnotation(FieldName = "QR_CODE",   FieldDescription="二维码(BASE64数据)")
    @ApiModelProperty(value="二维码(BASE64数据)")
    private String qrCode;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getQrCodeId() {
        return qrCodeId;
    }

    public void setQrCodeId(String qrCodeId) {
        this.qrCodeId = qrCodeId == null ? null : qrCodeId.trim();
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

    public String getQrUrl() {
        return qrUrl;
    }

    public void setQrUrl(String qrUrl) {
        this.qrUrl = qrUrl == null ? null : qrUrl.trim();
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

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode == null ? null : qrCode.trim();
    }
}