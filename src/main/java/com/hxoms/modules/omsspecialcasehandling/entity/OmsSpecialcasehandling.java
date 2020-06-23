package com.hxoms.modules.omsspecialcasehandling.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hxoms.common.hxannotation.ColumnAnnotation;
import com.hxoms.common.hxannotation.IdAnnotation;
import com.hxoms.common.hxannotation.TableAnnotation;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@TableAnnotation(TableName = "oms_specialcasehandling", TableDescription="特殊情况处理")
public class OmsSpecialcasehandling {
    @IdAnnotation
    @ColumnAnnotation(FieldName = "ID",   FieldDescription="特殊情况ID")
    private String id;

    @ColumnAnnotation(FieldName = "Company",   FieldDescription="单位名称")
    private String company;

    @ColumnAnnotation(FieldName = "Name",   FieldDescription="姓名")
    private String name;

    @ColumnAnnotation(FieldName = "Sex",   FieldDescription="性别")
    private String sex;

    @ColumnAnnotation(FieldName = "Birth",   FieldDescription="出生日期")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date birth;

    @ColumnAnnotation(FieldName = "PoliticsStatus",   FieldDescription="政治面貌")
    private String politicsstatus;

    @ColumnAnnotation(FieldName = "Duty",   FieldDescription="职务（级）")
    private String duty;

    @ColumnAnnotation(FieldName = "Incumbency_status",   FieldDescription="在职状态")
    private String incumbencyStatus;

    @ColumnAnnotation(FieldName = "Start_time",   FieldDescription="申请开始时间")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date startTime;

    @ColumnAnnotation(FieldName = "End_time",   FieldDescription="申请结束时间")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date endTime;

    @ColumnAnnotation(FieldName = "A0100",   FieldDescription="人员主键")
    private String a0100;

    @ColumnAnnotation(FieldName = "B0100",   FieldDescription="单位主键")
    private String b0100;

    @ColumnAnnotation(FieldName = "Create_date",   FieldDescription="创建时间")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date createDate;

    @ColumnAnnotation(FieldName = "Create_user",   FieldDescription="创建人")
    private String createUser;

    @ColumnAnnotation(FieldName = "modify_user",   FieldDescription="修改人")
    private String modifyUser;

    @ColumnAnnotation(FieldName = "modify_time",   FieldDescription="修改时间")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date modifyTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company == null ? null : company.trim();
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

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    public String getPoliticsstatus() {
        return politicsstatus;
    }

    public void setPoliticsstatus(String politicsstatus) {
        this.politicsstatus = politicsstatus == null ? null : politicsstatus.trim();
    }

    public String getDuty() {
        return duty;
    }

    public void setDuty(String duty) {
        this.duty = duty == null ? null : duty.trim();
    }

    public String getIncumbencyStatus() {
        return incumbencyStatus;
    }

    public void setIncumbencyStatus(String incumbencyStatus) {
        this.incumbencyStatus = incumbencyStatus == null ? null : incumbencyStatus.trim();
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getA0100() {
        return a0100;
    }

    public void setA0100(String a0100) {
        this.a0100 = a0100 == null ? null : a0100.trim();
    }

    public String getB0100() {
        return b0100;
    }

    public void setB0100(String b0100) {
        this.b0100 = b0100 == null ? null : b0100.trim();
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser == null ? null : createUser.trim();
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
}