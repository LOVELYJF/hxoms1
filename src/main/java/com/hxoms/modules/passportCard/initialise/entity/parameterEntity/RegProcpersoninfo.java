package com.hxoms.modules.passportCard.initialise.entity.parameterEntity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * @Desc：TODO
 * @Author: wangyunquan
 * @Date: 2020/8/29
 */
@ApiModel(value = "登记备案人员信息")
public class RegProcpersoninfo {
    @ApiModelProperty(value="主键")
    private String id;

    @ApiModelProperty(value="人员主键")
    private String a0100;

    @ApiModelProperty(value="姓")
    private String surname;

    @ApiModelProperty(value="名")
    private String name;

    @ApiModelProperty(value="性别")
    private String sex;

    @ApiModelProperty(value="民族")
    private String nationName;

    @JsonFormat(pattern = "yyyy.MM.dd")
    @ApiModelProperty(value="出生日期（身份证）")
    private Date birthDate;

    @ApiModelProperty(value="身份证号（干部）")
    private String idnumberGb;

    @ApiModelProperty(value="户口所在地")
    private String registeResidence;

    @ApiModelProperty(value="政治面貌")
    private String politicalAffiname;

    @ApiModelProperty(value="在职状态1在职 2辞职 3退休 4去世 5开除 6调出 7.省管变中管 8 未匹配 9其它")
    private String incumbencyStatus;

    @ApiModelProperty(value="工作单位")
    private String workUnit;

    @ApiModelProperty(value="职务")
    private String post;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getA0100() {
        return a0100;
    }

    public void setA0100(String a0100) {
        this.a0100 = a0100;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getNationName() {
        return nationName;
    }

    public void setNationName(String nationName) {
        this.nationName = nationName;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getIdnumberGb() {
        return idnumberGb;
    }

    public void setIdnumberGb(String idnumberGb) {
        this.idnumberGb = idnumberGb;
    }

    public String getRegisteResidence() {
        return registeResidence;
    }

    public void setRegisteResidence(String registeResidence) {
        this.registeResidence = registeResidence;
    }

    public String getPoliticalAffiname() {
        return politicalAffiname;
    }

    public void setPoliticalAffiname(String politicalAffiname) {
        this.politicalAffiname = politicalAffiname;
    }

    public String getIncumbencyStatus() {
        return incumbencyStatus;
    }

    public void setIncumbencyStatus(String incumbencyStatus) {
        this.incumbencyStatus = incumbencyStatus;
    }

    public String getWorkUnit() {
        return workUnit;
    }

    public void setWorkUnit(String workUnit) {
        this.workUnit = workUnit;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }
}
