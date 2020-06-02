package com.hxoms.modules.keySupervision.dismissed.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hxoms.common.hxannotation.ColumnAnnotation;
import com.hxoms.common.hxannotation.IdAnnotation;
import com.hxoms.common.hxannotation.TableAnnotation;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

@TableAnnotation(TableName = "oms_sup_dismissed", TableDescription="免职撤职人员信息")
public class OmsSupDismissed {
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

    @ColumnAnnotation(FieldName = "POST",   FieldDescription="职务")
    private String post;

    @ColumnAnnotation(FieldName = "DISMISSED_TIME",   FieldDescription="免职时间")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date dismissedTime;

    @ColumnAnnotation(FieldName = "DOCUMENT_NO",   FieldDescription="文书号")
    private String documentNo;

    @ColumnAnnotation(FieldName = "REASON",   FieldDescription="原因")
    private String reason;

    @ColumnAnnotation(FieldName = "DISMISSED_TIME_START_QUERY",   FieldDescription="免职查询开始时间")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date dismissedTimeStartQuery;

    @ColumnAnnotation(FieldName = "DISMISSED_TIME_END_QUERY",   FieldDescription="免职查询结束时间")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date dismissedTimeEndQuery;

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

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post == null ? null : post.trim();
    }

    public Date getDismissedTime() {
        return dismissedTime;
    }

    public void setDismissedTime(Date dismissedTime) {
        this.dismissedTime = dismissedTime;
    }

    public String getDocumentNo() {
        return documentNo;
    }

    public void setDocumentNo(String documentNo) {
        this.documentNo = documentNo == null ? null : documentNo.trim();
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason == null ? null : reason.trim();
    }

    public Date getDismissedTimeStartQuery() {
        return dismissedTimeStartQuery;
    }

    public void setDismissedTimeStartQuery(Date dismissedTimeStartQuery) {
        this.dismissedTimeStartQuery = dismissedTimeStartQuery;
    }

    public Date getDismissedTimeEndQuery() {
        return dismissedTimeEndQuery;
    }

    public void setDismissedTimeEndQuery(Date dismissedTimeEndQuery) {
        this.dismissedTimeEndQuery = dismissedTimeEndQuery;
    }
}