package com.hxoms.modules.selfestimate.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hxoms.common.hxannotation.ColumnAnnotation;
import com.hxoms.common.hxannotation.IdAnnotation;
import com.hxoms.common.hxannotation.TableAnnotation;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

@ApiModel(value = "自评项目结果")
@TableAnnotation(TableName = "oms_selfestimate_resultitem", TableDescription="自评项目结果")
public class OmsSelfestimateResultitem {
    @IdAnnotation
    @ColumnAnnotation(FieldName = "ID",   FieldDescription="主键")
    @ApiModelProperty(value = "主键")
    private String id;

    @ColumnAnnotation(FieldName = "SELFFILE_ID",   FieldDescription="自评材料清单ID")
    @ApiModelProperty(value = "自评材料清单ID")
    private String selffileId;

    @ColumnAnnotation(FieldName = "APPLY_ID",   FieldDescription="出国申请ID")
    @ApiModelProperty(value = "出国申请ID")
    private String applyId;

    @ColumnAnnotation(FieldName = "CHECK_ID",   FieldDescription="检查项ID")
    @ApiModelProperty(value = "检查项ID")
    private String checkId;

    @ColumnAnnotation(FieldName = "CHECK_RESULT",   FieldDescription="检查结果（1通过、0不通过）")
    @ApiModelProperty(value = "检查结果")
    private Integer checkResult;

    @ColumnAnnotation(FieldName = "DESCRIPTION",   FieldDescription="说明")
    @ApiModelProperty(value = "说明")
    private String description;

    @ColumnAnnotation(FieldName = "PERSON_TYPE",   FieldDescription="处理人类型(经办人  干部监督处)")
    @ApiModelProperty(value = "处理人类型")
    private String personType;

    @ColumnAnnotation(FieldName = "CREATE_TIME",   FieldDescription="创建时间")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date createTime;

    @ColumnAnnotation(FieldName = "CREATE_USER",   FieldDescription="创建人")
    private String createUser;

    @ColumnAnnotation(FieldName = "MODIFY_TIME",   FieldDescription="修改时间")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date modifyTime;

    @ColumnAnnotation(FieldName = "MODIFY_USER",   FieldDescription="修改人")
    private String modifyUser;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getSelffileId() {
        return selffileId;
    }

    public void setSelffileId(String selffileId) {
        this.selffileId = selffileId == null ? null : selffileId.trim();
    }

    public String getApplyId() {
        return applyId;
    }

    public void setApplyId(String applyId) {
        this.applyId = applyId == null ? null : applyId.trim();
    }

    public String getCheckId() {
        return checkId;
    }

    public void setCheckId(String checkId) {
        this.checkId = checkId == null ? null : checkId.trim();
    }

    public Integer getCheckResult() {
        return checkResult;
    }

    public void setCheckResult(Integer checkResult) {
        this.checkResult = checkResult;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public String getPersonType() {
        return personType;
    }

    public void setPersonType(String personType) {
        this.personType = personType == null ? null : personType.trim();
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