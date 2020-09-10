package com.hxoms.modules.omsregcadre.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hxoms.common.hxannotation.ColumnAnnotation;
import com.hxoms.common.hxannotation.IdAnnotation;
import com.hxoms.common.hxannotation.TableAnnotation;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

@TableAnnotation(TableName = "oms_reg_revokeapply", TableDescription="撤销登记备案申请表")
public class OmsRegRevokeapply {
    @IdAnnotation
    @ColumnAnnotation(FieldName = "ID",   FieldDescription="主键")
    private String id;

    @ColumnAnnotation(FieldName = "RF_ID",   FieldDescription="备案id")
    private String rfId;

    @ColumnAnnotation(FieldName = "A0100",   FieldDescription="申请人")
    private String a0100;

    @ColumnAnnotation(FieldName = "CREATE_USER",   FieldDescription="创建人")
    private String createUser;

    @ColumnAnnotation(FieldName = "CREATE_DATE",   FieldDescription="创建时间")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date createDate;

    @ColumnAnnotation(FieldName = "SURNAME",   FieldDescription="姓")
    private String surname;

    @ColumnAnnotation(FieldName = "NAME",   FieldDescription="名")
    private String name;

    @ColumnAnnotation(FieldName = "SEX",   FieldDescription="性别")
    private String sex;

    @ColumnAnnotation(FieldName = "BIRTH_DATE",   FieldDescription="出生日期")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date birthDate;

    @ColumnAnnotation(FieldName = "BIRTH_DATE_GB",   FieldDescription="出生日期（干部）")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date birthDateGb;

    @ColumnAnnotation(FieldName = "IDNUMBER_GB",   FieldDescription="身份证号(干部)")
    private String idnumberGb;

    @ColumnAnnotation(FieldName = "IDNUMBER_GA",   FieldDescription="身份证号（公安）")
    private String idnumberGa;

    @ColumnAnnotation(FieldName = "REGISTE_RESIDENCE_CODE",   FieldDescription="户口所在地代码")
    private String registeResidenceCode;

    @ColumnAnnotation(FieldName = "REGISTE_RESIDENCE",   FieldDescription="户口所在地")
    private String registeResidence;

    @ColumnAnnotation(FieldName = "WORK_UNIT",   FieldDescription="工作单位")
    private String workUnit;

    @ColumnAnnotation(FieldName = "POST_CODE",   FieldDescription="职务（级）或职称代码")
    private String postCode;

    @ColumnAnnotation(FieldName = "POST",   FieldDescription="职务（级）或职称")
    private String post;

    @ColumnAnnotation(FieldName = "PERSON_MANAGER",   FieldDescription="人事主管单位")
    private String personManager;

    @ColumnAnnotation(FieldName = "IDENTITY_CODE",   FieldDescription="身份情况代码")
    private String identityCode;

    @ColumnAnnotation(FieldName = "IDENTITY",   FieldDescription="身份情况")
    private String identity;

    @ColumnAnnotation(FieldName = "SECRET_STATUS",   FieldDescription="涉密状态(核心/重要/一般/非涉密）)")
    private String secretStatus;

    @ColumnAnnotation(FieldName = "SECRET_START_DATE",   FieldDescription="脱密日期")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date secretStartDate;

    @ColumnAnnotation(FieldName = "SECRET_END_DATE",   FieldDescription="脱密期限")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date secretEndDate;

    @ColumnAnnotation(FieldName = "APPLY_REASON",   FieldDescription="申请理由")
    private String applyReason;

    @ColumnAnnotation(FieldName = "STATUS",   FieldDescription="状态(填写0，已上报1（经办人上报干部监督处），已审批2（干部监督处已审批），已抽取3（登记备案工作已经抽取数据）、已备案4（登记备案结果已确认）、退回5（资料不全，干部监督处退回）、拒批9（干部监督处不允许撤销）)")
    private String status;

    @ColumnAnnotation(FieldName = "RF_B0000",   FieldDescription="备案机构")
    private String rfB0000;

    @ColumnAnnotation(FieldName = "EXIT_TYPE",   FieldDescription="退出方式")
    private String exitType;

    @ColumnAnnotation(FieldName = "EXIT_DATE",   FieldDescription="退出时间")
    private String exitDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
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

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser == null ? null : createUser.trim();
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname == null ? null : surname.trim();
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

    public String getIdnumberGa() {
        return idnumberGa;
    }

    public void setIdnumberGa(String idnumberGa) {
        this.idnumberGa = idnumberGa == null ? null : idnumberGa.trim();
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

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode == null ? null : postCode.trim();
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post == null ? null : post.trim();
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

    public String getSecretStatus() {
        return secretStatus;
    }

    public void setSecretStatus(String secretStatus) {
        this.secretStatus = secretStatus == null ? null : secretStatus.trim();
    }

    public Date getSecretStartDate() {
        return secretStartDate;
    }

    public void setSecretStartDate(Date secretStartDate) {
        this.secretStartDate = secretStartDate;
    }

    public Date getSecretEndDate() {
        return secretEndDate;
    }

    public void setSecretEndDate(Date secretEndDate) {
        this.secretEndDate = secretEndDate;
    }

    public String getApplyReason() {
        return applyReason;
    }

    public void setApplyReason(String applyReason) {
        this.applyReason = applyReason == null ? null : applyReason.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getRfB0000() {
        return rfB0000;
    }

    public void setRfB0000(String rfB0000) {
        this.rfB0000 = rfB0000 == null ? null : rfB0000.trim();
    }

    public String getExitType() {
        return exitType;
    }

    public void setExitType(String exitType) {
        this.exitType = exitType == null ? null : exitType.trim();
    }

    public String getExitDate() {
        return exitDate;
    }

    public void setExitDate(String exitDate) {
        this.exitDate = exitDate == null ? null : exitDate.trim();
    }
}