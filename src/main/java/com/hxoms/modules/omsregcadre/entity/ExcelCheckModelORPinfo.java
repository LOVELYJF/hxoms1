package com.hxoms.modules.omsregcadre.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.hxoms.common.hxannotation.ColumnAnnotation;
import com.hxoms.common.hxannotation.IdAnnotation;
import com.hxoms.common.hxannotation.TableAnnotation;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class ExcelCheckModelORPinfo extends BaseRowModel {

    @ExcelProperty(value = "数据来源", index = 0)
    private String dataType;
    @ExcelProperty(value = "备案机构", index = 1)
    private String rfB0000;
    @ExcelProperty(value = "在职状态", index = 2)
    private String incumbencyStatus;
    @ExcelProperty(value = "备案状态", index = 3)
    private String rfStatus;
    @ExcelProperty(value = "验收状态", index = 4)
    private String checkStatus;
    @ExcelProperty(value = "姓", index = 5)
    private String surname;
    @ExcelProperty(value = "名", index = 6)
    private String name;
    @ExcelProperty(value = "工作单位", index = 7)
    private String workUnit;
    @ExcelProperty(value = "性别", index = 8)
    private String sex;
    @ExcelProperty(value = "出生日期", index = 9)
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date birthDate;
    @ExcelProperty(value = "政治面貌", index = 10)
    private String politicalAffi;
    @ExcelProperty(value = "户口所在地", index = 11)
    private String registeResidence;
    @ExcelProperty(value = "职务（级）", index = 12)
    private String post;
    @ExcelProperty(value = "人事主管单位", index = 13)
    private String personManager;
    @ExcelProperty(value = "身份情况", index = 14)
    private String identityCode;

    @ExcelProperty(value = "涉密等级", index = 15)
    private String secretLevel;
    @ExcelProperty(value = "脱密期开始时间", index = 16)
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date decryptStartdate;
    @ExcelProperty(value = "脱密期结束时间", index = 17)
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date decryptEnddate;
    @ExcelProperty(value = "主要领导", index = 18)
    private String mainLeader;
    @ExcelProperty(value = "证照持有情况", index = 19)
    private Integer licenceIdentity;
    @ExcelProperty(value = "裸官", index = 20)
    private String nf;
    @ExcelProperty(value = "家属受监管裸官", index = 21)
    private String fjgnf;
    @ExcelProperty(value = "裸官在限入性岗位", index = 22)
    private String xrxgw;
    @ExcelProperty(value = "离琼挂职", index = 23)
    private String lqgz;
    @ExcelProperty(value = "到琼", index = 24)
    private String dqgz;

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getRfB0000() {
        return rfB0000;
    }

    public void setRfB0000(String rfB0000) {
        this.rfB0000 = rfB0000;
    }

    public String getIncumbencyStatus() {
        return incumbencyStatus;
    }

    public void setIncumbencyStatus(String incumbencyStatus) {
        this.incumbencyStatus = incumbencyStatus;
    }

    public String getRfStatus() {
        return rfStatus;
    }

    public void setRfStatus(String rfStatus) {
        this.rfStatus = rfStatus;
    }

    public String getCheckStatus() {
        return checkStatus;
    }

    public void setCheckStatus(String checkStatus) {
        this.checkStatus = checkStatus;
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

    public String getWorkUnit() {
        return workUnit;
    }

    public void setWorkUnit(String workUnit) {
        this.workUnit = workUnit;
    }

    public String getSex() {
        return sex;
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

    public String getPoliticalAffi() {
        return politicalAffi;
    }

    public void setPoliticalAffi(String politicalAffi) {
        this.politicalAffi = politicalAffi;
    }

    public String getRegisteResidence() {
        return registeResidence;
    }

    public void setRegisteResidence(String registeResidence) {
        this.registeResidence = registeResidence;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getPersonManager() {
        return personManager;
    }

    public void setPersonManager(String personManager) {
        this.personManager = personManager;
    }

    public String getIdentityCode() {
        return identityCode;
    }

    public void setIdentityCode(String identityCode) {
        this.identityCode = identityCode;
    }

    public String getSecretLevel() {
        return secretLevel;
    }

    public void setSecretLevel(String secretLevel) {
        this.secretLevel = secretLevel;
    }

    public Date getDecryptStartdate() {
        return decryptStartdate;
    }

    public void setDecryptStartdate(Date decryptStartdate) {
        this.decryptStartdate = decryptStartdate;
    }

    public Date getDecryptEnddate() {
        return decryptEnddate;
    }

    public void setDecryptEnddate(Date decryptEnddate) {
        this.decryptEnddate = decryptEnddate;
    }

    public String getMainLeader() {
        return mainLeader;
    }

    public void setMainLeader(String mainLeader) {
        this.mainLeader = mainLeader;
    }

    public Integer getLicenceIdentity() {
        return licenceIdentity;
    }

    public void setLicenceIdentity(Integer licenceIdentity) {
        this.licenceIdentity = licenceIdentity;
    }

    public String getNf() {
        return nf;
    }

    public void setNf(String nf) {
        this.nf = nf;
    }

    public String getFjgnf() {
        return fjgnf;
    }

    public void setFjgnf(String fjgnf) {
        this.fjgnf = fjgnf;
    }

    public String getXrxgw() {
        return xrxgw;
    }

    public void setXrxgw(String xrxgw) {
        this.xrxgw = xrxgw;
    }

    public String getLqgz() {
        return lqgz;
    }

    public void setLqgz(String lqgz) {
        this.lqgz = lqgz;
    }

    public String getDqgz() {
        return dqgz;
    }

    public void setDqgz(String dqgz) {
        this.dqgz = dqgz;
    }
}