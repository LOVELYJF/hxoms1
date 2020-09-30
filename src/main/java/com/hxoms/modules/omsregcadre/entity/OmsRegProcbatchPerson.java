package com.hxoms.modules.omsregcadre.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hxoms.common.hxannotation.ColumnAnnotation;
import com.hxoms.common.hxannotation.IdAnnotation;
import com.hxoms.common.hxannotation.TableAnnotation;
import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

@TableAnnotation(TableName = "oms_reg_procbatch_person", TableDescription="省管干部登记备案批次人员")
public class OmsRegProcbatchPerson {
    @IdAnnotation
    @ColumnAnnotation(FieldName = "ID",   FieldDescription="主键")
    private String id;

    @ColumnAnnotation(FieldName = "BATCH_ID",   FieldDescription="批次号")
    private String batchId;

    @ColumnAnnotation(FieldName = "SURNAME",   FieldDescription="姓")
    private String surname;

    @ColumnAnnotation(FieldName = "RF_ID",   FieldDescription="备案信息表id")
    private String rfId;

    @ColumnAnnotation(FieldName = "A0100",   FieldDescription="申请人")
    private String a0100;

    @ColumnAnnotation(FieldName = "NAME",   FieldDescription="名")
    private String name;

    @ColumnAnnotation(FieldName = "SEX",   FieldDescription="性别")
    private String sex;

    @ColumnAnnotation(FieldName = "BIRTH_DATE",   FieldDescription="出生日期（身份证（公安））")
    @JsonFormat(pattern = "yyyy.MM.dd",timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date birthDate;

    @ColumnAnnotation(FieldName = "BIRTH_DATE_GB",   FieldDescription="出生日期（干部）")
    @JsonFormat(pattern = "yyyy.MM.dd",timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date birthDateGb;

    @ColumnAnnotation(FieldName = "IDNUMBER_GB",   FieldDescription="身份证号（干部）")
    private String idnumberGb;

    @ColumnAnnotation(FieldName = "IDNUMBER_GA",   FieldDescription="身份证号（公安）")
    private Integer idnumberGa;

    @ColumnAnnotation(FieldName = "REGISTE_RESIDENCE_CODE",   FieldDescription="户口所在地代码")
    private String registeResidenceCode;

    @ColumnAnnotation(FieldName = "REGISTE_RESIDENCE",   FieldDescription="户口所在地")
    private String registeResidence;

    @ColumnAnnotation(FieldName = "WORK_UNIT",   FieldDescription="工作单位")
    private String workUnit;

    @ColumnAnnotation(FieldName = "POST",   FieldDescription="职务（级）或职称")
    private String post;

    @ColumnAnnotation(FieldName = "POST_CODE",   FieldDescription="职务（级）或职称代码")
    private String postCode;

    @ColumnAnnotation(FieldName = "PERSON_MANAGER",   FieldDescription="人事主管单位")
    private String personManager;

    @ColumnAnnotation(FieldName = "IDENTITY_CODE",   FieldDescription="身份情况代码")
    private String identityCode;

    @ColumnAnnotation(FieldName = "IDENTITY",   FieldDescription="身份情况")
    private String identity;

    @ColumnAnnotation(FieldName = "SECRET_LEVEL",   FieldDescription="涉密等级0 非涉密  1 一般 2重要 3核心")
    private String secretLevel;

    @ColumnAnnotation(FieldName = "DECRYPT_STARTDATE",   FieldDescription="脱密期管理开始日期")
    @JsonFormat(pattern = "yyyy.MM.dd",timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date decryptStartdate;

    @ColumnAnnotation(FieldName = "DECRYPT_ENDDATE",   FieldDescription="脱密期管理终止日期")
    @JsonFormat(pattern = "yyyy.MM.dd",timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date decryptEnddate;

    @ColumnAnnotation(FieldName = "INBOUND_FLAG",   FieldDescription="入库标识新增U  修改I  撤消D")
    @ApiModelProperty(value="入库标识新增U  修改I  撤消D")
    private String inboundFlag;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getBatchId() {
        return batchId;
    }

    public void setBatchId(String batchId) {
        this.batchId = batchId == null ? null : batchId.trim();
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname == null ? null : surname.trim();
    }

    public String getRfId() {
        return rfId;
    }

    public void setRfId(String rfId) {
        this.rfId = rfId == null ? null : rfId.trim();
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

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public Date getBirthDateGb() {
        return birthDateGb;
    }

    public void setBirthDateGb(Date birthDateGb) {
        this.birthDateGb = birthDateGb;
    }

    public String getIdnumberGb() {
        return idnumberGb;
    }

    public void setIdnumberGb(String idnumberGb) {
        this.idnumberGb = idnumberGb == null ? null : idnumberGb.trim();
    }

    public Integer getIdnumberGa() {
        return idnumberGa;
    }

    public void setIdnumberGa(Integer idnumberGa) {
        this.idnumberGa = idnumberGa;
    }

    public String getRegisteResidenceCode() {
        return registeResidenceCode;
    }

    public void setRegisteResidenceCode(String registeResidenceCode) {
        this.registeResidenceCode = registeResidenceCode == null ? null : registeResidenceCode.trim();
    }

    public String getRegisteResidence() {
        return registeResidence;
    }

    public void setRegisteResidence(String registeResidence) {
        this.registeResidence = registeResidence == null ? null : registeResidence.trim();
    }

    public String getWorkUnit() {
        return workUnit;
    }

    public void setWorkUnit(String workUnit) {
        this.workUnit = workUnit == null ? null : workUnit.trim();
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post == null ? null : post.trim();
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode == null ? null : postCode.trim();
    }

    public String getPersonManager() {
        return personManager;
    }

    public void setPersonManager(String personManager) {
        this.personManager = personManager == null ? null : personManager.trim();
    }

    public String getIdentityCode() {
        return identityCode;
    }

    public void setIdentityCode(String identityCode) {
        this.identityCode = identityCode == null ? null : identityCode.trim();
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity == null ? null : identity.trim();
    }

    public String getSecretLevel() {
        return secretLevel;
    }

    public void setSecretLevel(String secretLevel) {
        this.secretLevel = secretLevel == null ? null : secretLevel.trim();
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

    public String getInboundFlag() {
        return inboundFlag;
    }

    public void setInboundFlag(String inboundFlag) {
        this.inboundFlag = inboundFlag;
    }
}