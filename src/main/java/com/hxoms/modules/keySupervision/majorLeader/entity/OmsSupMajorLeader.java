package com.hxoms.modules.keySupervision.majorLeader.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hxoms.common.hxannotation.ColumnAnnotation;
import com.hxoms.common.hxannotation.IdAnnotation;
import com.hxoms.common.hxannotation.TableAnnotation;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@TableAnnotation(TableName = "oms_sup_major_leader", TableDescription="主要领导信息表")
public class OmsSupMajorLeader {
    @ApiModelProperty("主键")
    @IdAnnotation
    @ColumnAnnotation(FieldName = "ID",   FieldDescription="主键")
    private String id;

    @ApiModelProperty("修改用户")
    @ColumnAnnotation(FieldName = "MODIFY_USER",   FieldDescription="修改用户")
    private String modifyUser;

    @ApiModelProperty("修改时间")
    @ColumnAnnotation(FieldName = "MODIFY_TIME",   FieldDescription="修改时间")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date modifyTime;

    @ApiModelProperty("干部主键")
    @ColumnAnnotation(FieldName = "A0100",   FieldDescription="干部主键")
    private String a0100;

    @ApiModelProperty("工作单位")
    @ColumnAnnotation(FieldName = "WORK_UNIT",   FieldDescription="工作单位")
    private String workUnit;

    @ApiModelProperty("姓名")
    @ColumnAnnotation(FieldName = "NAME",   FieldDescription="姓名")
    private String name;

    @ApiModelProperty("拼音")
    @ColumnAnnotation(FieldName = "PINYIN",   FieldDescription="拼音")
    private String pinyin;

    @ApiModelProperty("性别")
    @ColumnAnnotation(FieldName = "SEX",   FieldDescription="性别")
    private String sex;

    @ApiModelProperty("出生日期")
    @ColumnAnnotation(FieldName = "BIRTH_DATE",   FieldDescription="出生日期")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date birthDate;

    @ApiModelProperty("政治面貌")
    @ColumnAnnotation(FieldName = "POLITICAL_AFFI",   FieldDescription="政治面貌")
    private String politicalAffi;

    @ApiModelProperty("职务")
    @ColumnAnnotation(FieldName = "POST",   FieldDescription="职务")
    private String post;

    @ApiModelProperty("职级")
    @ColumnAnnotation(FieldName = "RANK",   FieldDescription="职级")
    private String rank;

    @ApiModelProperty("创建人")
    @ColumnAnnotation(FieldName = "CREATE_USER",   FieldDescription="创建人")
    private String createUser;

    @ApiModelProperty("创建时间")
    @ColumnAnnotation(FieldName = "CREATE_TIME",   FieldDescription="创建时间")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date createTime;

    @ApiModelProperty("单位主键")
    @ColumnAnnotation(FieldName = "B0100",   FieldDescription="单位主键")
    private String b0100;

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

    public String getPoliticalAffi() {
        return politicalAffi;
    }

    public void setPoliticalAffi(String politicalAffi) {
        this.politicalAffi = politicalAffi == null ? null : politicalAffi.trim();
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post == null ? null : post.trim();
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank == null ? null : rank.trim();
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser == null ? null : createUser.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getB0100() {
        return b0100;
    }

    public void setB0100(String b0100) {
        this.b0100 = b0100 == null ? null : b0100.trim();
    }
}