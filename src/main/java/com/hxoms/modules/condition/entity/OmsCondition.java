package com.hxoms.modules.condition.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hxoms.common.hxannotation.ColumnAnnotation;
import com.hxoms.common.hxannotation.IdAnnotation;
import com.hxoms.common.hxannotation.TableAnnotation;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@TableAnnotation(TableName = "oms_condition", TableDescription="约束条件")
public class OmsCondition {
    @IdAnnotation
    @ColumnAnnotation(FieldName = "ID",   FieldDescription="主键")
    private String id;

    @ColumnAnnotation(FieldName = "NAME",   FieldDescription="约束条件名称")
    private String name;

    @ColumnAnnotation(FieldName = "CONDITION_TYPE",   FieldDescription="条件类型1--因公条件,2--因私条件，3延期回国 后续可以增加。。。")
    private String conditionType;

    @ColumnAnnotation(FieldName = "CHECK_TYPE",   FieldDescription="1--保存前校验;2--保存后校验")
    private Integer checkType;

    @ColumnAnnotation(FieldName = "SQL_CONTENT",   FieldDescription="生成的sql条件")
    private String sqlContent;

    @ColumnAnnotation(FieldName = "DESCRIPTION",   FieldDescription="条件的中文描述")
    private String description;

    @ColumnAnnotation(FieldName = "CARE_TYPE",   FieldDescription="约束类型(1约束  2提醒)")
    private String careType;

    @ColumnAnnotation(FieldName = "REMIND_PERSON_TYPE",   FieldDescription="提醒人员类别（1经办人、2组团单位、4审批单位，8干部监督处）")
    private String remindPersonType;

    @ColumnAnnotation(FieldName = "SETTING_TYPE",   FieldDescription="配置类型（1非配置 2可配置 3开关）")
    private String settingType;

    @ColumnAnnotation(FieldName = "SETTING_CODE",   FieldDescription="配置编码")
    private String settingCode;

    @ColumnAnnotation(FieldName = "REMIND_CONTENT",   FieldDescription="提醒内容")
    private String remindContent;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getConditionType() {
        return conditionType;
    }

    public void setConditionType(String conditionType) {
        this.conditionType = conditionType == null ? null : conditionType.trim();
    }

    public Integer getCheckType() {
        return checkType;
    }

    public void setCheckType(Integer checkType) {
        this.checkType = checkType;
    }

    public String getSqlContent() {
        return sqlContent;
    }

    public void setSqlContent(String sqlContent) {
        this.sqlContent = sqlContent == null ? null : sqlContent.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public String getCareType() {
        return careType;
    }

    public void setCareType(String careType) {
        this.careType = careType == null ? null : careType.trim();
    }

    public String getRemindPersonType() {
        return remindPersonType;
    }

    public void setRemindPersonType(String remindPersonType) {
        this.remindPersonType = remindPersonType == null ? null : remindPersonType.trim();
    }

    public String getSettingType() {
        return settingType;
    }

    public void setSettingType(String settingType) {
        this.settingType = settingType == null ? null : settingType.trim();
    }

    public String getSettingCode() {
        return settingCode;
    }

    public void setSettingCode(String settingCode) {
        this.settingCode = settingCode == null ? null : settingCode.trim();
    }

    public String getRemindContent() {
        return remindContent;
    }

    public void setRemindContent(String remindContent) {
        this.remindContent = remindContent == null ? null : remindContent.trim();
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