package com.hxoms.modules.omsregcadre.entity;

import com.hxoms.common.hxannotation.ColumnAnnotation;
import com.hxoms.common.hxannotation.IdAnnotation;
import com.hxoms.common.hxannotation.TableAnnotation;

import java.util.Date;
/**
 * oms_reg_procbatchperson
 */
@TableAnnotation(TableName = "oms_reg_procbatchperson", TableDescription="登记备案批次人员表")
public class OmsRegProcbatchPerson {
    /**
     * 主键
     */
    @IdAnnotation
    @ColumnAnnotation(FieldName = "ID",  FieldDescription="主键")
    private String id;

    /**
     * 批次表主键
     */
    @ColumnAnnotation(FieldName = "BATCH_ID",  FieldDescription="批次表主键")
    private String batchId;

    /**
     * 姓
     */
    @ColumnAnnotation(FieldName = "SURNAME",  FieldDescription="姓")
    private String surname;

    /**
     * 申请人
     */
    @ColumnAnnotation(FieldName = "A0100",  FieldDescription="申请人")
    private String a0100;

    /**
     * 名
     */
    @ColumnAnnotation(FieldName = "NAME",  FieldDescription="名")
    private String name;

    /**
     * 性别
     */
    @ColumnAnnotation(FieldName = "SEX",  FieldDescription="性别")
    private String sex;

    /**
     * 出生日期
     */
    @ColumnAnnotation(FieldName = "BIRTH_DATE",  FieldDescription="出生日期")
    private Date birthDate;

    /**
     * 身份证号
     */
    @ColumnAnnotation(FieldName = "IDNUMBER",  FieldDescription="身份证号")
    private String idnumber;

    /**
     * 户口所在地代码
     */
    @ColumnAnnotation(FieldName = "REGISTE_RESIDENCE_CODE",  FieldDescription="户口所在地代码")
    private String registeResidenceCode;

    /**
     * 户口所在地
     */
    @ColumnAnnotation(FieldName = "REGISTE_RESIDENCE",  FieldDescription="户口所在地")
    private String registeResidence;

    /**
     * 工作单位
     */
    @ColumnAnnotation(FieldName = "WORK_UNIT",  FieldDescription="工作单位")
    private String workUnit;

    /**
     * 脱密期限
     */
    @ColumnAnnotation(FieldName = "SECRET_TERM",  FieldDescription="脱密期限")
    private String secretTerm;

    /**
     * 职务（级）或职称
     */
    @ColumnAnnotation(FieldName = "POST",  FieldDescription="职务（级）或职称")
    private String post;

    /**
     * 职务（级）或职称代码
     */
    @ColumnAnnotation(FieldName = "POST_CODE",  FieldDescription="职务（级）或职称代码")
    private String postCode;

    /**
     * 人事主管单位
     */
    @ColumnAnnotation(FieldName = "PERSON_MANAGER",  FieldDescription="人事主管单位")
    private String personManager;

    /**
     * 身份情况代码
     */
    @ColumnAnnotation(FieldName = "IDENTITY_CODE",  FieldDescription="身份情况代码")
    private String identityCode;

    /**
     * 身份情况
     */
    @ColumnAnnotation(FieldName = "IDENTITY",  FieldDescription="身份情况")
    private String identity;

    /**
     * 涉密状态
     */
    @ColumnAnnotation(FieldName = "SECRET_STATUS",  FieldDescription="涉密状态")
    private String secretStatus;

    /**
     * 脱密脱密日期
     */
    @ColumnAnnotation(FieldName = "SECRET_DATE",  FieldDescription="脱密脱密日期")
    private Date secretDate;

    /**
     * 主键
     * @return ID 主键
     */
    public String getId() {
        return id;
    }

    /**
     * 主键
     * @param id 主键
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * 批次表主键
     * @return BATCH_ID 批次表主键
     */
    public String getBatchId() {
        return batchId;
    }

    /**
     * 批次表主键
     * @param batchId 批次表主键
     */
    public void setBatchId(String batchId) {
        this.batchId = batchId == null ? null : batchId.trim();
    }

    /**
     * 姓
     * @return SURNAME 姓
     */
    public String getSurname() {
        return surname;
    }

    /**
     * 姓
     * @param surname 姓
     */
    public void setSurname(String surname) {
        this.surname = surname == null ? null : surname.trim();
    }

    /**
     * 申请人
     * @return A0100 申请人
     */
    public String getA0100() {
        return a0100;
    }

    /**
     * 申请人
     * @param a0100 申请人
     */
    public void setA0100(String a0100) {
        this.a0100 = a0100 == null ? null : a0100.trim();
    }

    /**
     * 名
     * @return NAME 名
     */
    public String getName() {
        return name;
    }

    /**
     * 名
     * @param name 名
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * 性别
     * @return SEX 性别
     */
    public String getSex() {
        return sex;
    }

    /**
     * 性别
     * @param sex 性别
     */
    public void setSex(String sex) {
        this.sex = sex == null ? null : sex.trim();
    }

    /**
     * 出生日期
     * @return BIRTH_DATE 出生日期
     */
    public Date getBirthDate() {
        return birthDate;
    }

    /**
     * 出生日期
     * @param birthDate 出生日期
     */
    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    /**
     * 身份证号
     * @return IDNUMBER 身份证号
     */
    public String getIdnumber() {
        return idnumber;
    }

    /**
     * 身份证号
     * @param idnumber 身份证号
     */
    public void setIdnumber(String idnumber) {
        this.idnumber = idnumber == null ? null : idnumber.trim();
    }

    /**
     * 户口所在地代码
     * @return REGISTE_RESIDENCE_CODE 户口所在地代码
     */
    public String getRegisteResidenceCode() {
        return registeResidenceCode;
    }

    /**
     * 户口所在地代码
     * @param registeResidenceCode 户口所在地代码
     */
    public void setRegisteResidenceCode(String registeResidenceCode) {
        this.registeResidenceCode = registeResidenceCode == null ? null : registeResidenceCode.trim();
    }

    /**
     * 户口所在地
     * @return REGISTE_RESIDENCE 户口所在地
     */
    public String getRegisteResidence() {
        return registeResidence;
    }

    /**
     * 户口所在地
     * @param registeResidence 户口所在地
     */
    public void setRegisteResidence(String registeResidence) {
        this.registeResidence = registeResidence == null ? null : registeResidence.trim();
    }

    /**
     * 工作单位
     * @return WORK_UNIT 工作单位
     */
    public String getWorkUnit() {
        return workUnit;
    }

    /**
     * 工作单位
     * @param workUnit 工作单位
     */
    public void setWorkUnit(String workUnit) {
        this.workUnit = workUnit == null ? null : workUnit.trim();
    }

    /**
     * 脱密期限
     * @return SECRET_TERM 脱密期限
     */
    public String getSecretTerm() {
        return secretTerm;
    }

    /**
     * 脱密期限
     * @param secretTerm 脱密期限
     */
    public void setSecretTerm(String secretTerm) {
        this.secretTerm = secretTerm == null ? null : secretTerm.trim();
    }

    /**
     * 职务（级）或职称
     * @return POST 职务（级）或职称
     */
    public String getPost() {
        return post;
    }

    /**
     * 职务（级）或职称
     * @param post 职务（级）或职称
     */
    public void setPost(String post) {
        this.post = post == null ? null : post.trim();
    }

    /**
     * 职务（级）或职称代码
     * @return POST_CODE 职务（级）或职称代码
     */
    public String getPostCode() {
        return postCode;
    }

    /**
     * 职务（级）或职称代码
     * @param postCode 职务（级）或职称代码
     */
    public void setPostCode(String postCode) {
        this.postCode = postCode == null ? null : postCode.trim();
    }

    /**
     * 人事主管单位
     * @return PERSON_MANAGER 人事主管单位
     */
    public String getPersonManager() {
        return personManager;
    }

    /**
     * 人事主管单位
     * @param personManager 人事主管单位
     */
    public void setPersonManager(String personManager) {
        this.personManager = personManager == null ? null : personManager.trim();
    }

    /**
     * 身份情况代码
     * @return IDENTITY_CODE 身份情况代码
     */
    public String getIdentityCode() {
        return identityCode;
    }

    /**
     * 身份情况代码
     * @param identityCode 身份情况代码
     */
    public void setIdentityCode(String identityCode) {
        this.identityCode = identityCode == null ? null : identityCode.trim();
    }

    /**
     * 身份情况
     * @return IDENTITY 身份情况
     */
    public String getIdentity() {
        return identity;
    }

    /**
     * 身份情况
     * @param identity 身份情况
     */
    public void setIdentity(String identity) {
        this.identity = identity == null ? null : identity.trim();
    }

    /**
     * 涉密状态
     * @return SECRET_STATUS 涉密状态
     */
    public String getSecretStatus() {
        return secretStatus;
    }

    /**
     * 涉密状态
     * @param secretStatus 涉密状态
     */
    public void setSecretStatus(String secretStatus) {
        this.secretStatus = secretStatus == null ? null : secretStatus.trim();
    }

    /**
     * 脱密脱密日期
     * @return SECRET_DATE 脱密脱密日期
     */
    public Date getSecretDate() {
        return secretDate;
    }

    /**
     * 脱密脱密日期
     * @param secretDate 脱密脱密日期
     */
    public void setSecretDate(Date secretDate) {
        this.secretDate = secretDate;
    }
}