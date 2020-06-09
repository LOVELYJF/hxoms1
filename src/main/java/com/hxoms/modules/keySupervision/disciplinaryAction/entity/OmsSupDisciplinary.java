package com.hxoms.modules.keySupervision.disciplinaryAction.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hxoms.common.hxannotation.ColumnAnnotation;
import com.hxoms.common.hxannotation.IdAnnotation;
import com.hxoms.common.hxannotation.TableAnnotation;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

@TableAnnotation(TableName = "oms_sup_disciplinary", TableDescription="处分信息表")
public class OmsSupDisciplinary {
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

    @ColumnAnnotation(FieldName = "DISCIPLINARY_POST",   FieldDescription="处分时职务")
    private String disciplinaryPost;

    @ColumnAnnotation(FieldName = "DISCIPLINARY_TIME",   FieldDescription="处分时间")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date disciplinaryTime;

    @ColumnAnnotation(FieldName = "DISCIPLINARY_DOCUMENT_NO",   FieldDescription="处分文书号")
    private String disciplinaryDocumentNo;

    @ColumnAnnotation(FieldName = "DISCIPLINARY_TYPE",   FieldDescription="处分类型")
    private String disciplinaryType;

    @ColumnAnnotation(FieldName = "DISCIPLINARY_END_TIME",   FieldDescription="处分结束时间")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date disciplinaryEndTime;

    @ColumnAnnotation(FieldName = "WHY_DISCIPLINARY",   FieldDescription="处分原因")
    private String whyDisciplinary;

    @ColumnAnnotation(FieldName = "INFLUENCE_TIME",   FieldDescription="影响期")
    private String influenceTime;

    @ColumnAnnotation(FieldName = "DISCIPLINARY_END_QUERY",   FieldDescription="处分结束时间查询")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date disciplinaryEndQuery;

    @ColumnAnnotation(FieldName = "DISCIPLINARY_START_QUERY",   FieldDescription="处分结束时间查询")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date disciplinaryStartQuery;


    @ColumnAnnotation(FieldName = "DC_STATUS",   FieldDescription="处分信息状态")
    private String dcStatus;

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

    public String getDisciplinaryPost() {
        return disciplinaryPost;
    }

    public void setDisciplinaryPost(String disciplinaryPost) {
        this.disciplinaryPost = disciplinaryPost == null ? null : disciplinaryPost.trim();
    }

    public Date getDisciplinaryTime() {
        return disciplinaryTime;
    }

    public void setDisciplinaryTime(Date disciplinaryTime) {
        this.disciplinaryTime = disciplinaryTime;
    }

    public String getDisciplinaryDocumentNo() {
        return disciplinaryDocumentNo;
    }

    public void setDisciplinaryDocumentNo(String disciplinaryDocumentNo) {
        this.disciplinaryDocumentNo = disciplinaryDocumentNo == null ? null : disciplinaryDocumentNo.trim();
    }

    public String getDisciplinaryType() {
        return disciplinaryType;
    }

    public void setDisciplinaryType(String disciplinaryType) {
        this.disciplinaryType = disciplinaryType == null ? null : disciplinaryType.trim();
    }

    public Date getDisciplinaryEndTime() {
        return disciplinaryEndTime;
    }

    public void setDisciplinaryEndTime(Date disciplinaryEndTime) {
        this.disciplinaryEndTime = disciplinaryEndTime;
    }

    public String getWhyDisciplinary() {
        return whyDisciplinary;
    }

    public void setWhyDisciplinary(String whyDisciplinary) {
        this.whyDisciplinary = whyDisciplinary == null ? null : whyDisciplinary.trim();
    }

    public String getInfluenceTime() {
        return influenceTime;
    }

    public void setInfluenceTime(String influenceTime) {
        this.influenceTime = influenceTime == null ? null : influenceTime.trim();
    }

    public Date getDisciplinaryEndQuery() {
        return disciplinaryEndQuery;
    }

    public void setDisciplinaryEndQuery(Date disciplinaryEndQuery) {
        this.disciplinaryEndQuery = disciplinaryEndQuery;
    }

    public Date getDisciplinaryStartQuery() {
        return disciplinaryStartQuery;
    }

    public void setDisciplinaryStartQuery(Date disciplinaryStartQuery) {
        this.disciplinaryStartQuery = disciplinaryStartQuery;
    }


    public String getDcStatus() {
        return dcStatus;
    }

    public void setDcStatus(String dcStatus) {
        this.dcStatus = dcStatus == null ? null : dcStatus.trim();
    }
}