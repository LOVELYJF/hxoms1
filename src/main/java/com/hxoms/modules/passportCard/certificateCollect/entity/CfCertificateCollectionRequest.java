package com.hxoms.modules.passportCard.certificateCollect.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hxoms.common.hxannotation.ColumnAnnotation;
import com.hxoms.common.hxannotation.IdAnnotation;
import com.hxoms.common.hxannotation.TableAnnotation;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@TableAnnotation(TableName = "cf_certificate_collection_request", TableDescription="证照催缴子表")
@ApiModel(value = "证照催缴子表")
public class CfCertificateCollectionRequest {
    @IdAnnotation
    @ColumnAnnotation(FieldName = "ID",   FieldDescription="主键")
    @ApiModelProperty(value="主键")
    private String id;

    @ColumnAnnotation(FieldName = "CER_CJ_ID",   FieldDescription="证照催缴表ID")
    @ApiModelProperty(value="证照催缴表ID")
    private String cerCjId;

    @ColumnAnnotation(FieldName = "ZJLX",   FieldDescription="证件类型(1:护照,2:港澳通行证,4:台湾通行证)")
    @ApiModelProperty(value="证件类型(1:护照,2:港澳通行证,4:台湾通行证)")
    private Integer zjlx;

    @ColumnAnnotation(FieldName = "ZJHM",   FieldDescription="证件号码")
    @ApiModelProperty(value="证件号码")
    private String zjhm;

    @ColumnAnnotation(FieldName = "CJ_WAY",   FieldDescription="催缴方式(0:电话催缴,1:短信催缴)")
    @ApiModelProperty(value="催缴方式(0:电话催缴,1:短信催缴)")
    private String cjWay;

    @ColumnAnnotation(FieldName = "CJ_MESSAGE",   FieldDescription="催缴内容")
    @ApiModelProperty(value="催缴内容")
    private String cjMessage;

    @ColumnAnnotation(FieldName = "CJ_RESULT",   FieldDescription="催缴结果")
    @ApiModelProperty(value="催缴结果")
    private String cjResult;

    @ColumnAnnotation(FieldName = "USER_ID",   FieldDescription="经办人用户ID")
    @ApiModelProperty(value="经办人用户ID")
    private String userId;

    @ColumnAnnotation(FieldName = "NAME",   FieldDescription="经办人姓名")
    @ApiModelProperty(value="经办人姓名")
    private String name;

    @ColumnAnnotation(FieldName = "PHONE",   FieldDescription="经办人电话")
    @ApiModelProperty(value="经办人电话")
    private String phone;

    @ColumnAnnotation(FieldName = "CJ_PERSON",   FieldDescription="催缴人")
    @ApiModelProperty(value="催缴人")
    private String cjPerson;

    @ColumnAnnotation(FieldName = "CJ_TIME",   FieldDescription="催缴时间")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    @ApiModelProperty(value="催缴时间")
    private Date cjTime;

    @ColumnAnnotation(FieldName = "UPDATOR",   FieldDescription="修改人")
    @ApiModelProperty(value="修改人")
    private String updator;

    @ColumnAnnotation(FieldName = "UPDATETIME",   FieldDescription="修改时间")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    @ApiModelProperty(value="修改时间")
    private Date updatetime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getCerCjId() {
        return cerCjId;
    }

    public void setCerCjId(String cerCjId) {
        this.cerCjId = cerCjId == null ? null : cerCjId.trim();
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

    public String getCjWay() {
        return cjWay;
    }

    public void setCjWay(String cjWay) {
        this.cjWay = cjWay == null ? null : cjWay.trim();
    }

    public String getCjMessage() {
        return cjMessage;
    }

    public void setCjMessage(String cjMessage) {
        this.cjMessage = cjMessage == null ? null : cjMessage.trim();
    }

    public String getCjResult() {
        return cjResult;
    }

    public void setCjResult(String cjResult) {
        this.cjResult = cjResult == null ? null : cjResult.trim();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getCjPerson() {
        return cjPerson;
    }

    public void setCjPerson(String cjPerson) {
        this.cjPerson = cjPerson == null ? null : cjPerson.trim();
    }

    public Date getCjTime() {
        return cjTime;
    }

    public void setCjTime(Date cjTime) {
        this.cjTime = cjTime;
    }

    public String getUpdator() {
        return updator;
    }

    public void setUpdator(String updator) {
        this.updator = updator == null ? null : updator.trim();
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }
}