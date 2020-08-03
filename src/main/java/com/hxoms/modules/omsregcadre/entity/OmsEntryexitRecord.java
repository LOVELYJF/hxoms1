package com.hxoms.modules.omsregcadre.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hxoms.common.hxannotation.ColumnAnnotation;
import com.hxoms.common.hxannotation.IdAnnotation;
import com.hxoms.common.hxannotation.TableAnnotation;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

@TableAnnotation(TableName = "oms_entryexit_record", TableDescription="出入境记录表")
public class OmsEntryexitRecord {
    @IdAnnotation
    @ColumnAnnotation(FieldName = "ID",   FieldDescription="主键")
    private String id;

    @ColumnAnnotation(FieldName = "B0100",   FieldDescription="")
    private String b0100;

    @ColumnAnnotation(FieldName = "PRIAPPLY_ID",   FieldDescription="因私出国申请记录id")
    private String priapplyId;

    @ColumnAnnotation(FieldName = "IMPORT_TIME",   FieldDescription="导入时间")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date importTime;

    @ColumnAnnotation(FieldName = "IMPORT_PERSON",   FieldDescription="导入人")
    private String importPerson;

    @ColumnAnnotation(FieldName = "OGA_MODE",   FieldDescription="出国（境）方式")
    private String ogaMode;

    @ColumnAnnotation(FieldName = "DATA_SOURCE",   FieldDescription="数据来源")
    private String dataSource;

    @ColumnAnnotation(FieldName = "OMS_ID",   FieldDescription="备案信息ID")
    private String omsId;

    @ColumnAnnotation(FieldName = "A0100",   FieldDescription="人员主键")
    private String a0100;

    @ColumnAnnotation(FieldName = "OGE_STATUS",   FieldDescription="出入境状态 出1，入2")
    private Integer ogeStatus;

    @ColumnAnnotation(FieldName = "NAME",   FieldDescription="姓名")
    private String name;

    @ColumnAnnotation(FieldName = "SEX",   FieldDescription="性别")
    private String sex;

    @ColumnAnnotation(FieldName = "BIRTH_DATE",   FieldDescription="出生年月")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date birthDate;

    @ColumnAnnotation(FieldName = "NATIONALITY",   FieldDescription="国籍")
    private String nationality;

    @ColumnAnnotation(FieldName = "ID_TYPE",   FieldDescription="证件种类")
    private Integer idType;

    @ColumnAnnotation(FieldName = "ID_NUMBER",   FieldDescription="证件号码")
    private String idNumber;

    @ColumnAnnotation(FieldName = "DESTINATION",   FieldDescription="前往地")
    private String destination;

    @ColumnAnnotation(FieldName = "ENTRACE_EXIT",   FieldDescription="出入口岸")
    private String entraceExit;

    @ColumnAnnotation(FieldName = "OGE_DATE",   FieldDescription="出入境日期")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date ogeDate;

    @ColumnAnnotation(FieldName = "OGE_TIME",   FieldDescription="出入境时间")
    private String ogeTime;

    @ColumnAnnotation(FieldName = "VALID_UNTIL",   FieldDescription="证件有效期至")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date validUntil;

    @ColumnAnnotation(FieldName = "VISITING_TASKS",   FieldDescription="出访任务出国（境）事项")
    private String visitingTasks;

    @ColumnAnnotation(FieldName = "COMPARISON_RESULT",   FieldDescription="比对结果")
    private String comparisonResult;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getB0100() {
        return b0100;
    }

    public void setB0100(String b0100) {
        this.b0100 = b0100 == null ? null : b0100.trim();
    }

    public String getPriapplyId() {
        return priapplyId;
    }

    public void setPriapplyId(String priapplyId) {
        this.priapplyId = priapplyId == null ? null : priapplyId.trim();
    }

    public Date getImportTime() {
        return importTime;
    }

    public void setImportTime(Date importTime) {
        this.importTime = importTime;
    }

    public String getImportPerson() {
        return importPerson;
    }

    public void setImportPerson(String importPerson) {
        this.importPerson = importPerson == null ? null : importPerson.trim();
    }

    public String getOgaMode() {
        return ogaMode;
    }

    public void setOgaMode(String ogaMode) {
        this.ogaMode = ogaMode == null ? null : ogaMode.trim();
    }

    public String getDataSource() {
        return dataSource;
    }

    public void setDataSource(String dataSource) {
        this.dataSource = dataSource == null ? null : dataSource.trim();
    }

    public String getOmsId() {
        return omsId;
    }

    public void setOmsId(String omsId) {
        this.omsId = omsId == null ? null : omsId.trim();
    }

    public String getA0100() {
        return a0100;
    }

    public void setA0100(String a0100) {
        this.a0100 = a0100 == null ? null : a0100.trim();
    }

    public Integer getOgeStatus() {
        return ogeStatus;
    }

    public void setOgeStatus(Integer ogeStatus) {
        this.ogeStatus = ogeStatus;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex == null ? null : sex.trim();
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality == null ? null : nationality.trim();
    }

    public Integer getIdType() {
        return idType;
    }

    public void setIdType(Integer idType) {
        this.idType = idType;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber == null ? null : idNumber.trim();
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination == null ? null : destination.trim();
    }

    public String getEntraceExit() {
        return entraceExit;
    }

    public void setEntraceExit(String entraceExit) {
        this.entraceExit = entraceExit == null ? null : entraceExit.trim();
    }

    public Date getOgeDate() {
        return ogeDate;
    }

    public void setOgeDate(Date ogeDate) {
        this.ogeDate = ogeDate;
    }

    public String getOgeTime() {
        return ogeTime;
    }

    public void setOgeTime(String ogeTime) {
        this.ogeTime = ogeTime == null ? null : ogeTime.trim();
    }

    public Date getValidUntil() {
        return validUntil;
    }

    public void setValidUntil(Date validUntil) {
        this.validUntil = validUntil;
    }

    public String getVisitingTasks() {
        return visitingTasks;
    }

    public void setVisitingTasks(String visitingTasks) {
        this.visitingTasks = visitingTasks == null ? null : visitingTasks.trim();
    }

    public String getComparisonResult() {
        return comparisonResult;
    }

    public void setComparisonResult(String comparisonResult) {
        this.comparisonResult = comparisonResult == null ? null : comparisonResult.trim();
    }
}