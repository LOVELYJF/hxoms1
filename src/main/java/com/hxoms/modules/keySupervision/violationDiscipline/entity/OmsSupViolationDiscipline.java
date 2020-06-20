package com.hxoms.modules.keySupervision.violationDiscipline.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hxoms.common.hxannotation.ColumnAnnotation;
import com.hxoms.common.hxannotation.IdAnnotation;
import com.hxoms.common.hxannotation.TableAnnotation;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

@TableAnnotation(TableName = "oms_sup_violation_discipline", TableDescription="违反外事纪律人员信息")
public class OmsSupViolationDiscipline {
    @IdAnnotation
    @ColumnAnnotation(FieldName = "id",   FieldDescription="主键")
    private String id;

    @ColumnAnnotation(FieldName = "modify_user",   FieldDescription="修改用户")
    private String modifyUser;

    @ColumnAnnotation(FieldName = "modify_time",   FieldDescription="修改时间")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date modifyTime;

    @ColumnAnnotation(FieldName = "A0100",   FieldDescription="干部主键")
    private String a0100;

    @ColumnAnnotation(FieldName = "WORK_UNIT",   FieldDescription="工作单位")
    private String workUnit;

    @ColumnAnnotation(FieldName = "NAME",   FieldDescription="姓名")
    private String name;

    @ColumnAnnotation(FieldName = "PINYIN",   FieldDescription="拼音")
    private String pinyin;

    @ColumnAnnotation(FieldName = "VIOLATION_DIS_POST",   FieldDescription="违反时任职务")
    private String violationDisPost;

    @ColumnAnnotation(FieldName = "VIOLATION_DIS_TYPE",   FieldDescription="类型")
    private String violationDisType;

    @ColumnAnnotation(FieldName = "VIOLATION_DIS_TIME",   FieldDescription="违反时间")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date violationDisTime;

    @ColumnAnnotation(FieldName = "VIOLATION_TIME_START_QUERY",   FieldDescription="违反查询开始时间")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date violationTimeStartQuery;

    @ColumnAnnotation(FieldName = "VIOLATION_TIME_END_QUERY",   FieldDescription="违反查询结束时间")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date violationTimeEndQuery;

    @ColumnAnnotation(FieldName = "VIOLATION_DOCUMENT_NO",   FieldDescription="文书号")
    private String violationDocumentNo;

    @ColumnAnnotation(FieldName = "INFLUENCE_TIME",   FieldDescription="影响期")
    private String influenceTime;

    @ColumnAnnotation(FieldName = "VIOLATION_END_TIME",   FieldDescription="结束时间")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date violationEndTime;

    @ColumnAnnotation(FieldName = "DESCRIPTION",   FieldDescription="描述")
    private String description;

    @ColumnAnnotation(FieldName = "CREATE_USER",   FieldDescription="创建用户")
    private String createUser;

    @ColumnAnnotation(FieldName = "CREATE_TIME",   FieldDescription="创建时间")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date createTime;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
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

    public String getA0100() {
        return a0100;
    }

    public void setA0100(String a0100) {
        this.a0100 = a0100 == null ? null : a0100.trim();
    }

    public String getWorkUnit() {
        return workUnit;
    }

    public void setWorkUnit(String workUnit) {
        this.workUnit = workUnit == null ? null : workUnit.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin == null ? null : pinyin.trim();
    }

    public String getViolationDisPost() {
        return violationDisPost;
    }

    public void setViolationDisPost(String violationDisPost) {
        this.violationDisPost = violationDisPost == null ? null : violationDisPost.trim();
    }

    public String getViolationDisType() {
        return violationDisType;
    }

    public void setViolationDisType(String violationDisType) {
        this.violationDisType = violationDisType == null ? null : violationDisType.trim();
    }

    public Date getViolationDisTime() {
        return violationDisTime;
    }

    public void setViolationDisTime(Date violationDisTime) {
        this.violationDisTime = violationDisTime;
    }

    public Date getViolationTimeStartQuery() {
        return violationTimeStartQuery;
    }

    public void setViolationTimeStartQuery(Date violationTimeStartQuery) {
        this.violationTimeStartQuery = violationTimeStartQuery;
    }

    public Date getViolationTimeEndQuery() {
        return violationTimeEndQuery;
    }

    public void setViolationTimeEndQuery(Date violationTimeEndQuery) {
        this.violationTimeEndQuery = violationTimeEndQuery;
    }

    public String getViolationDocumentNo() {
        return violationDocumentNo;
    }

    public void setViolationDocumentNo(String violationDocumentNo) {
        this.violationDocumentNo = violationDocumentNo == null ? null : violationDocumentNo.trim();
    }

    public String getInfluenceTime() {
        return influenceTime;
    }

    public void setInfluenceTime(String influenceTime) {
        this.influenceTime = influenceTime == null ? null : influenceTime.trim();
    }

    public Date getViolationEndTime() {
        return violationEndTime;
    }

    public void setViolationEndTime(Date violationEndTime) {
        this.violationEndTime = violationEndTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser == null ? null : createUser.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}