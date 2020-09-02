package com.hxoms.modules.passportCard.omsCerCancellateLicense.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hxoms.common.hxannotation.ColumnAnnotation;
import com.hxoms.common.hxannotation.IdAnnotation;
import com.hxoms.common.hxannotation.TableAnnotation;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

@TableAnnotation(TableName = "oms_cer_cancellate_apply", TableDescription="注销证照申请表")
@ApiModel(value = "注销证照申请表")
public class OmsCerCancellateApply {
    @IdAnnotation
    @ColumnAnnotation(FieldName = "ID",   FieldDescription="注销证照申请表")
    @ApiModelProperty(value="注销证照申请表")
    private String id;

    @ColumnAnnotation(FieldName = "OMS_ID",   FieldDescription="备案人员信息")
    @ApiModelProperty(value="备案人员信息")
    private String omsId;

    @ColumnAnnotation(FieldName = "APPLY_CER_INFO",   FieldDescription="申请证照信息")
    @ApiModelProperty(value="申请证照信息")
    private String applyCerInfo;

    @ColumnAnnotation(FieldName = "APPEND_PLACE",   FieldDescription="发生地")
    @ApiModelProperty(value="发生地")
    private String appendPlace;

    @ColumnAnnotation(FieldName = "ZXYY",   FieldDescription="注销原因")
    @ApiModelProperty(value="注销原因")
    private String zxyy;

    @ColumnAnnotation(FieldName = "CREATE_TIME",   FieldDescription="创建时间")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    @ApiModelProperty(value="创建时间")
    private Date createTime;

    @ColumnAnnotation(FieldName = "CRREATE_USER",   FieldDescription="创建人")
    @ApiModelProperty(value="创建人")
    private String crreateUser;

    @ColumnAnnotation(FieldName = "MODIFY_USER",   FieldDescription="修改人")
    @ApiModelProperty(value="修改人")
    private String modifyUser;

    @ColumnAnnotation(FieldName = "MODIFY_TIME",   FieldDescription="修改时间")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    @ApiModelProperty(value="修改时间")
    private Date modifyTime;

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

    public String getApplyCerInfo() {
        return applyCerInfo;
    }

    public void setApplyCerInfo(String applyCerInfo) {
        this.applyCerInfo = applyCerInfo == null ? null : applyCerInfo.trim();
    }

    public String getAppendPlace() {
        return appendPlace;
    }

    public void setAppendPlace(String appendPlace) {
        this.appendPlace = appendPlace == null ? null : appendPlace.trim();
    }

    public String getZxyy() {
        return zxyy;
    }

    public void setZxyy(String zxyy) {
        this.zxyy = zxyy == null ? null : zxyy.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCrreateUser() {
        return crreateUser;
    }

    public void setCrreateUser(String crreateUser) {
        this.crreateUser = crreateUser == null ? null : crreateUser.trim();
    }

    public String getModifyUser() {
        return modifyUser;
    }

    public void setModifyUser(String modifyUser) {
        this.modifyUser = modifyUser == null ? null : modifyUser.trim();
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }
}