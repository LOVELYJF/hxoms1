package com.hxoms.modules.keySupervision.caseInfo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hxoms.common.hxannotation.ColumnAnnotation;
import com.hxoms.common.hxannotation.IdAnnotation;
import com.hxoms.common.hxannotation.TableAnnotation;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

@TableAnnotation(TableName = "oms_sup_case_info", TableDescription="立案信息表")
public class OmsSupCaseInfo {
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

    @ColumnAnnotation(FieldName = "CASE_POST",   FieldDescription="立案时职务")
    private String casePost;

    @ColumnAnnotation(FieldName = "CASE_TIME",   FieldDescription="立案时间")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date caseTime;

    @ColumnAnnotation(FieldName = "CASE_TIME_END",   FieldDescription="立案查询结束时间")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date caseTimeEnd;

    @ColumnAnnotation(FieldName = "CASE_TIME_START",   FieldDescription="立案查询开始时间")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date caseTimeStart;

    @ColumnAnnotation(FieldName = "CASE_DOCUMENT_NO",   FieldDescription="立案文书号")
    private String caseDocumentNo;

    @ColumnAnnotation(FieldName = "DISCIPLINARY_ACTION",   FieldDescription="是否受处分")
    private String disciplinaryAction;

    @ColumnAnnotation(FieldName = "WHY_CASE",   FieldDescription="立案原因")
    private String whyCase;

    @ColumnAnnotation(FieldName = "DISCIPLINARY_ACTION_TYPE",   FieldDescription="处分类型")
    private String disciplinaryActionType;

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

    public String getCasePost() {
        return casePost;
    }

    public void setCasePost(String casePost) {
        this.casePost = casePost == null ? null : casePost.trim();
    }

    public Date getCaseTime() {
        return caseTime;
    }

    public void setCaseTime(Date caseTime) {
        this.caseTime = caseTime;
    }

    public Date getCaseTimeEnd() {
        return caseTimeEnd;
    }

    public void setCaseTimeEnd(Date caseTimeEnd) {
        this.caseTimeEnd = caseTimeEnd;
    }

    public Date getCaseTimeStart() {
        return caseTimeStart;
    }

    public void setCaseTimeStart(Date caseTimeStart) {
        this.caseTimeStart = caseTimeStart;
    }

    public String getCaseDocumentNo() {
        return caseDocumentNo;
    }

    public void setCaseDocumentNo(String caseDocumentNo) {
        this.caseDocumentNo = caseDocumentNo == null ? null : caseDocumentNo.trim();
    }

    public String getDisciplinaryAction() {
        return disciplinaryAction;
    }

    public void setDisciplinaryAction(String disciplinaryAction) {
        this.disciplinaryAction = disciplinaryAction == null ? null : disciplinaryAction.trim();
    }

    public String getWhyCase() {
        return whyCase;
    }

    public void setWhyCase(String whyCase) {
        this.whyCase = whyCase == null ? null : whyCase.trim();
    }

    public String getDisciplinaryActionType() {
        return disciplinaryActionType;
    }

    public void setDisciplinaryActionType(String disciplinaryActionType) {
        this.disciplinaryActionType = disciplinaryActionType == null ? null : disciplinaryActionType.trim();
    }
}