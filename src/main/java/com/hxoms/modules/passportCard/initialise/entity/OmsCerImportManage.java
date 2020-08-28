package com.hxoms.modules.passportCard.initialise.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hxoms.common.hxannotation.ColumnAnnotation;
import com.hxoms.common.hxannotation.IdAnnotation;
import com.hxoms.common.hxannotation.TableAnnotation;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@TableAnnotation(TableName = "oms_cer_import_manage", TableDescription="证照信息导入管理表")
@ApiModel(value = "证照信息导入管理表")
public class OmsCerImportManage {
    @IdAnnotation
    @ColumnAnnotation(FieldName = "ID",   FieldDescription="主键")
    @ApiModelProperty(value="主键")
    private String id;

    @ColumnAnnotation(FieldName = "BATCH_NO",   FieldDescription="批次号")
    @ApiModelProperty(value="批次号")
    private String batchNo;

    @ColumnAnnotation(FieldName = "A0184",   FieldDescription="身份证号码")
    @ApiModelProperty(value="身份证号码")
    private String a0184;

    @ColumnAnnotation(FieldName = "NAME",   FieldDescription="姓名")
    @ApiModelProperty(value="姓名")
    private String name;

    @ColumnAnnotation(FieldName = "SEX",   FieldDescription="性别")
    @ApiModelProperty(value="性别")
    private String sex;

    @ColumnAnnotation(FieldName = "CSRQ",   FieldDescription="出生日期")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    @ApiModelProperty(value="出生日期")
    private Date csrq;

    @ColumnAnnotation(FieldName = "GJ",   FieldDescription="国籍")
    @ApiModelProperty(value="国籍")
    private String gj;

    @ColumnAnnotation(FieldName = "ZJLX",   FieldDescription="证件类型(1:护照,2:港澳通行证,4:台湾通行证)")
    @ApiModelProperty(value="证件类型(1:护照,2:港澳通行证,4:台湾通行证)")
    private Integer zjlx;

    @ColumnAnnotation(FieldName = "ZJHM",   FieldDescription="证件号码")
    @ApiModelProperty(value="证件号码")
    private String zjhm;

    @ColumnAnnotation(FieldName = "QFJG",   FieldDescription="签发机关")
    @ApiModelProperty(value="签发机关")
    private String qfjg;

    @ColumnAnnotation(FieldName = "CSDD",   FieldDescription="出生地点")
    @ApiModelProperty(value="出生地点")
    private String csdd;

    @ColumnAnnotation(FieldName = "QFRQ",   FieldDescription="签发日期")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    @ApiModelProperty(value="签发日期")
    private Date qfrq;

    @ColumnAnnotation(FieldName = "YXQZ",   FieldDescription="有效期至")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    @ApiModelProperty(value="有效期至")
    private Date yxqz;

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

    public String getA0184() {
        return a0184;
    }

    public void setA0184(String a0184) {
        this.a0184 = a0184 == null ? null : a0184.trim();
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

    public Date getCsrq() {
        return csrq;
    }

    public void setCsrq(Date csrq) {
        this.csrq = csrq;
    }

    public String getGj() {
        return gj;
    }

    public void setGj(String gj) {
        this.gj = gj == null ? null : gj.trim();
    }

    public Integer getZjlx() {
        return zjlx;
    }

    public void setZjlx(Integer zjlx) {
        this.zjlx = zjlx;
    }

    public String getZjhm() {
        return zjhm;
    }

    public void setZjhm(String zjhm) {
        this.zjhm = zjhm == null ? null : zjhm.trim();
    }

    public String getQfjg() {
        return qfjg;
    }

    public void setQfjg(String qfjg) {
        this.qfjg = qfjg == null ? null : qfjg.trim();
    }

    public String getCsdd() {
        return csdd;
    }

    public void setCsdd(String csdd) {
        this.csdd = csdd == null ? null : csdd.trim();
    }

    public Date getQfrq() {
        return qfrq;
    }

    public void setQfrq(Date qfrq) {
        this.qfrq = qfrq;
    }

    public Date getYxqz() {
        return yxqz;
    }

    public void setYxqz(Date yxqz) {
        this.yxqz = yxqz;
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