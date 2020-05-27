package com.hxoms.modules.keySupervision.majorLeader.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hxoms.common.hxannotation.ColumnAnnotation;
import com.hxoms.common.hxannotation.IdAnnotation;
import com.hxoms.common.hxannotation.TableAnnotation;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * <b>主要领导信息表</b>
 * @author luoshuai
 * @date 2020/5/10 18:26
 */
@TableAnnotation(TableName = "oms_sup_major_leader", TableDescription="主要领导信息表")
public class OmsSupMajorLeader {
    @IdAnnotation
    @ColumnAnnotation(FieldName = "ID",   FieldDescription="主键")
    private String id;

    @ColumnAnnotation(FieldName = "A0100",   FieldDescription="")
    private String a0100;

    @ColumnAnnotation(FieldName = "WORK_UNIT",   FieldDescription="工作单位")
    private String workUnit;

    @ColumnAnnotation(FieldName = "NAME",   FieldDescription="姓名")
    private String name;

    @ColumnAnnotation(FieldName = "PINYIN",   FieldDescription="拼音")
    private String pinyin;

    @ColumnAnnotation(FieldName = "SEX",   FieldDescription="性别")
    private String sex;

    @ColumnAnnotation(FieldName = "A3600",   FieldDescription="主要领导")
    private String a3600;

    @ColumnAnnotation(FieldName = "BIRTH_DATE",   FieldDescription="出生日期")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date birthDate;

    @ColumnAnnotation(FieldName = "POLITICAL_AFFI",   FieldDescription="政治面貌")
    private String politicalAffi;

    @ColumnAnnotation(FieldName = "POST",   FieldDescription="职务")
    private String post;

    @ColumnAnnotation(FieldName = "RANK",   FieldDescription="职级")
    private String rank;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
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

    public String getA3600() {
        return a3600;
    }

    public void setA3600(String a3600) {
        this.a3600 = a3600 == null ? null : a3600.trim();
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

}