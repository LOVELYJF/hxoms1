package com.hxoms.modules.passportCard.omsCerApplyLendingLicense.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hxoms.common.hxannotation.ColumnAnnotation;
import com.hxoms.common.hxannotation.IdAnnotation;
import com.hxoms.common.hxannotation.TableAnnotation;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

@TableAnnotation(TableName = "oms_cer_cancellate_lending_apply", TableDescription="借出证照申请表")
@ApiModel(value = "借出证照申请表")
public class OmsCerCancellateLendingApply {
    @IdAnnotation
    @ColumnAnnotation(FieldName = "ID",   FieldDescription="主键")
    @ApiModelProperty(value="主键")
    private String id;

    @ColumnAnnotation(FieldName = "OMS_ID",   FieldDescription="登记备案主键")
    @ApiModelProperty(value="登记备案主键")
    private String omsId;

    @ColumnAnnotation(FieldName = "LENDING_CER_INFO",   FieldDescription="借出的证照信息")
    @ApiModelProperty(value="借出的证照信息")
    private String lendingCerInfo;

    @ColumnAnnotation(FieldName = "LENDING_REASON",   FieldDescription="借出原因")
    @ApiModelProperty(value="借出原因")
    private String lendingReason;

    @ColumnAnnotation(FieldName = "RETURN_TIME",   FieldDescription="归还时间")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    @ApiModelProperty(value="归还时间")
    private Date returnTime;

    @ColumnAnnotation(FieldName = "CREATE_TIME",   FieldDescription="创建时间")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    @ApiModelProperty(value="创建时间")
    private Date createTime;

    @ColumnAnnotation(FieldName = "CREATE_USER",   FieldDescription="创建人")
    @ApiModelProperty(value="创建人")
    private String createUser;

    @ColumnAnnotation(FieldName = "MODIFY_TIME",   FieldDescription="修改时间")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    @ApiModelProperty(value="修改时间")
    private Date modifyTime;

    @ColumnAnnotation(FieldName = "MODIFY_USER",   FieldDescription="修改人")
    @ApiModelProperty(value="修改人")
    private String modifyUser;

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

    public String getLendingCerInfo() {
        return lendingCerInfo;
    }

    public void setLendingCerInfo(String lendingCerInfo) {
        this.lendingCerInfo = lendingCerInfo == null ? null : lendingCerInfo.trim();
    }

    public String getLendingReason() {
        return lendingReason;
    }

    public void setLendingReason(String lendingReason) {
        this.lendingReason = lendingReason == null ? null : lendingReason.trim();
    }

    public Date getReturnTime() {
        return returnTime;
    }

    public void setReturnTime(Date returnTime) {
        this.returnTime = returnTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser == null ? null : createUser.trim();
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getModifyUser() {
        return modifyUser;
    }

    public void setModifyUser(String modifyUser) {
        this.modifyUser = modifyUser == null ? null : modifyUser.trim();
    }
}