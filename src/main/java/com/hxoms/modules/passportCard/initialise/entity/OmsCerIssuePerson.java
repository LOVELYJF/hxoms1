package com.hxoms.modules.passportCard.initialise.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hxoms.common.hxannotation.ColumnAnnotation;
import com.hxoms.common.hxannotation.IdAnnotation;
import com.hxoms.common.hxannotation.TableAnnotation;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

@TableAnnotation(TableName = "oms_cer_issue_person", TableDescription="证照异常人员记录表")
public class OmsCerIssuePerson {
    @IdAnnotation
    @ColumnAnnotation(FieldName = "ID",   FieldDescription="主键")
    private String id;

    @ColumnAnnotation(FieldName = "ID_NO",   FieldDescription="身份证号")
    private String idNo;

    @ColumnAnnotation(FieldName = "NAME",   FieldDescription="姓名")
    private String name;

    @ColumnAnnotation(FieldName = "A0100S",   FieldDescription="人员主键")
    private String a0100s;

    @ColumnAnnotation(FieldName = "DESCRIPTION",   FieldDescription="描述")
    private String description;

    @ColumnAnnotation(FieldName = "IMPORT_PERSON",   FieldDescription="导入人")
    private String importPerson;

    @ColumnAnnotation(FieldName = "IMPORT_TIME",   FieldDescription="导入时间")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date importTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo == null ? null : idNo.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getA0100s() {
        return a0100s;
    }

    public void setA0100s(String a0100s) {
        this.a0100s = a0100s == null ? null : a0100s.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public String getImportPerson() {
        return importPerson;
    }

    public void setImportPerson(String importPerson) {
        this.importPerson = importPerson == null ? null : importPerson.trim();
    }

    public Date getImportTime() {
        return importTime;
    }

    public void setImportTime(Date importTime) {
        this.importTime = importTime;
    }
}