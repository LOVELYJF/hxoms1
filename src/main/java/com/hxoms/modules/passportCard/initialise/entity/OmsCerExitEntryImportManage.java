package com.hxoms.modules.passportCard.initialise.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hxoms.common.hxannotation.ColumnAnnotation;
import com.hxoms.common.hxannotation.IdAnnotation;
import com.hxoms.common.hxannotation.TableAnnotation;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

@TableAnnotation(TableName = "oms_cer_exit_entry_import_manage", TableDescription="证照出入境导入管理表")
@ApiModel(value = "证照出入境导入管理表")
public class OmsCerExitEntryImportManage {
    @IdAnnotation
    @ColumnAnnotation(FieldName = "ID",   FieldDescription="主键")
    @ApiModelProperty(value="主键")
    private String id;

    @ColumnAnnotation(FieldName = "BATCH_NO",   FieldDescription="批次号")
    @ApiModelProperty(value="批次号")
    private String batchNo;

    @ColumnAnnotation(FieldName = "OGE_STATUS",   FieldDescription="出入境状态 (1:出,2:入)")
    @ApiModelProperty(value="出入境状态 (1:出,2:入)")
    private Integer ogeStatus;

    @ColumnAnnotation(FieldName = "NAME",   FieldDescription="姓名")
    @ApiModelProperty(value="姓名")
    private String name;

    @ColumnAnnotation(FieldName = "SEX",   FieldDescription="性别")
    @ApiModelProperty(value="性别")
    private String sex;

    @ColumnAnnotation(FieldName = "BIRTH_DATE",   FieldDescription="出生日期")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    @ApiModelProperty(value="出生日期")
    private Date birthDate;

    @ColumnAnnotation(FieldName = "NATIONALITY",   FieldDescription="国籍")
    @ApiModelProperty(value="国籍")
    private String nationality;

    @ColumnAnnotation(FieldName = "ID_TYPE",   FieldDescription="证件类型(1:护照,2:港澳通行证,4:台湾通行证)")
    @ApiModelProperty(value="证件类型(1:护照,2:港澳通行证,4:台湾通行证)")
    private Integer idType;

    @ColumnAnnotation(FieldName = "ID_NUMBER",   FieldDescription="证件号码")
    @ApiModelProperty(value="证件号码")
    private String idNumber;

    @ColumnAnnotation(FieldName = "DESTINATION",   FieldDescription="前往地")
    @ApiModelProperty(value="前往地")
    private String destination;

    @ColumnAnnotation(FieldName = "ENTRACE_EXIT",   FieldDescription="出入口岸")
    @ApiModelProperty(value="出入口岸")
    private String entraceExit;

    @ColumnAnnotation(FieldName = "OGE_DATE",   FieldDescription="出入境日期")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    @ApiModelProperty(value="出入境日期")
    private Date ogeDate;

    @ColumnAnnotation(FieldName = "OGE_TIME",   FieldDescription="出入境时间")
    @ApiModelProperty(value="出入境时间")
    private String ogeTime;

    @ColumnAnnotation(FieldName = "STATUS",   FieldDescription="状态(0:成功导入,1:重复导入,2:匹配失败)")
    @ApiModelProperty(value="状态(0:成功导入,1:重复导入,2:匹配失败)")
    private String status;

    @ColumnAnnotation(FieldName = "IMPORT_PERSON",   FieldDescription="导入人")
    @ApiModelProperty(value="导入人")
    private String importPerson;

    @ColumnAnnotation(FieldName = "IMPORT_TIME",   FieldDescription="导入时间")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    @ApiModelProperty(value="导入时间")
    private Date importTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo == null ? null : batchNo.trim();
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
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