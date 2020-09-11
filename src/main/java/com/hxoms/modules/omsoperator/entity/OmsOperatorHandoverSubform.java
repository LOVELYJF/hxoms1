package com.hxoms.modules.omsoperator.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hxoms.common.hxannotation.ColumnAnnotation;
import com.hxoms.common.hxannotation.IdAnnotation;
import com.hxoms.common.hxannotation.TableAnnotation;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@TableAnnotation(TableName = "oms_operator_handover_subform", TableDescription="经办人交接子表")
@ApiModel(value = "经办人交接子表")
public class OmsOperatorHandoverSubform {
    @IdAnnotation
    @ColumnAnnotation(FieldName = "ID",   FieldDescription="交接子表主键")
    @ApiModelProperty(value="交接子表主键")
    private String id;

    @ColumnAnnotation(FieldName = "HandoverFormID",   FieldDescription="交接表主表主键")
    @ApiModelProperty(value="交接表主表主键")
    private String handoverformid;

    @ColumnAnnotation(FieldName = "BusinessId",   FieldDescription="业务主键")
    @ApiModelProperty(value="业务主键")
    private String businessid;

    @ColumnAnnotation(FieldName = "BusinessType",   FieldDescription="业务类别（0.证照领取,1.因公出国（境）,2.因私出国（境），3.延期回国，4.撤销登记备案，5.注销证照）")
    @ApiModelProperty(value="业务类别（0.证照领取,1.因公出国（境）,2.因私出国（境），3.延期回国，4.撤销登记备案，5.注销证照）")
    private String businesstype;

    @ColumnAnnotation(FieldName = "A0100",   FieldDescription="人员主键")
    @ApiModelProperty(value="人员主键")
    private String a0100;

    @ColumnAnnotation(FieldName = "Name",   FieldDescription="姓名")
    @ApiModelProperty(value="姓名")
    private String name;

    @ColumnAnnotation(FieldName = "Sex",   FieldDescription="性别")
    @ApiModelProperty(value="性别")
    private String sex;

    @ColumnAnnotation(FieldName = "Birthday",   FieldDescription="出生日期")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    @ApiModelProperty(value="出生日期")
    private Date birthday;

    @ColumnAnnotation(FieldName = "IDCard",   FieldDescription="身份证号")
    @ApiModelProperty(value="身份证号")
    private String idcard;

    @ColumnAnnotation(FieldName = "PoliticsStatus",   FieldDescription="政治面貌")
    @ApiModelProperty(value="政治面貌")
    private String politicsstatus;

    @ColumnAnnotation(FieldName = "Company",   FieldDescription="工作单位")
    @ApiModelProperty(value="工作单位")
    private String company;

    @ColumnAnnotation(FieldName = "Duty",   FieldDescription="职务（级）")
    @ApiModelProperty(value="职务（级）")
    private String duty;

    @ColumnAnnotation(FieldName = "HandoverTime",   FieldDescription="交接时间")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    @ApiModelProperty(value="交接时间")
    private Date handovertime;

    @ColumnAnnotation(FieldName = "HandoverId",   FieldDescription="交接人ID")
    @ApiModelProperty(value="交接人ID")
    private String handoverid;

    @ColumnAnnotation(FieldName = "ExitDate",   FieldDescription="出国（境）时间")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    @ApiModelProperty(value="出国（境）时间")
    private Date exitdate;

    @ColumnAnnotation(FieldName = "EntryDate",   FieldDescription="入境时间")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    @ApiModelProperty(value="入境时间")
    private Date entrydate;

    @ColumnAnnotation(FieldName = "SM",   FieldDescription="说明")
    @ApiModelProperty(value="说明")
    private String sm;

    @ColumnAnnotation(FieldName = "BZ",   FieldDescription="备注")
    @ApiModelProperty(value="备注")
    private String bz;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getHandoverformid() {
        return handoverformid;
    }

    public void setHandoverformid(String handoverformid) {
        this.handoverformid = handoverformid == null ? null : handoverformid.trim();
    }

    public String getBusinessid() {
        return businessid;
    }

    public void setBusinessid(String businessid) {
        this.businessid = businessid == null ? null : businessid.trim();
    }

    public String getBusinesstype() {
        return businesstype;
    }

    public void setBusinesstype(String businesstype) {
        this.businesstype = businesstype == null ? null : businesstype.trim();
    }

    public String getA0100() {
        return a0100;
    }

    public void setA0100(String a0100) {
        this.a0100 = a0100 == null ? null : a0100.trim();
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

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard == null ? null : idcard.trim();
    }

    public String getPoliticsstatus() {
        return politicsstatus;
    }

    public void setPoliticsstatus(String politicsstatus) {
        this.politicsstatus = politicsstatus == null ? null : politicsstatus.trim();
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company == null ? null : company.trim();
    }

    public String getDuty() {
        return duty;
    }

    public void setDuty(String duty) {
        this.duty = duty == null ? null : duty.trim();
    }

    public Date getHandovertime() {
        return handovertime;
    }

    public void setHandovertime(Date handovertime) {
        this.handovertime = handovertime;
    }

    public String getHandoverid() {
        return handoverid;
    }

    public void setHandoverid(String handoverid) {
        this.handoverid = handoverid == null ? null : handoverid.trim();
    }

    public Date getExitdate() {
        return exitdate;
    }

    public void setExitdate(Date exitdate) {
        this.exitdate = exitdate;
    }

    public Date getEntrydate() {
        return entrydate;
    }

    public void setEntrydate(Date entrydate) {
        this.entrydate = entrydate;
    }

    public String getSm() {
        return sm;
    }

    public void setSm(String sm) {
        this.sm = sm == null ? null : sm.trim();
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz == null ? null : bz.trim();
    }
}