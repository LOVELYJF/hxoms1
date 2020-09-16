package com.hxoms.modules.omsregcadre.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.hxoms.common.hxannotation.ColumnAnnotation;
import com.hxoms.common.hxannotation.IdAnnotation;
import com.hxoms.common.hxannotation.TableAnnotation;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class OmsEntryexitRecordModel{

    @ExcelProperty(value = "出入境状态 出1，入2", index = 0)
    private String ogeStatus;
    @ExcelProperty(value = "姓名", index = 1)
    private String name;
    @ExcelProperty(value = "性别", index = 2)
    private String sex;
    @ExcelProperty(value = "出生年月", index = 3)
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date birthDate;
    @ExcelProperty(value = "国籍", index = 4)
    private String nationality;
    @ExcelProperty(value = "证件种类", index = 5)
    private String idType;
    @ExcelProperty(value = "证件号码", index = 6)
    private String idNumber;
    @ExcelProperty(value = "前往地", index = 7)
    private String destination;
    @ExcelProperty(value = "出入口岸", index = 8)
    private String entraceExit;
    @ExcelProperty(value = "出入境日期", index = 9)
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date ogeDate;
    @ExcelProperty(value = "证件有效期至", index = 10)
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date validUntil;
    @ExcelProperty(value = "出访任务出国（境）事项", index = 11)
    private String visitingTasks;
    @ExcelProperty(value = "数据来源 1.手录  2导入", index = 12)
    private String dataSource;

    public String getOgeStatus() {
        return ogeStatus.equals("1")?"出境":"入境";
    }

    public void setOgeStatus(String ogeStatus) {
        this.ogeStatus = ogeStatus;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex.equals("1")?"男":"女";
    }

    public void setSex(String sex) {
        this.sex = sex;
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
        this.nationality = nationality;
    }

    public String getIdType() {
        if (idType.equals("1")){
            idType = "护照";
        }else if (idType.equals("2")){
            idType = "港澳通行证";
        }else if (idType.equals("4")){
            idType = "台湾通行证";
        }
        return idType;
    }

    public void setIdType(String idType) {
        this.idType = idType;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getEntraceExit() {
        return entraceExit;
    }

    public void setEntraceExit(String entraceExit) {
        this.entraceExit = entraceExit;
    }

    public Date getOgeDate() {
        return ogeDate;
    }

    public void setOgeDate(Date ogeDate) {
        this.ogeDate = ogeDate;
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
        this.visitingTasks = visitingTasks;
    }

    public String getDataSource() {
        return dataSource.equals("1")?"出入境导入":"手工录入";
    }

    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
    }
}