package com.hxoms.modules.omsregcadre.entity;

import com.hxoms.common.hxannotation.ColumnAnnotation;
import com.hxoms.common.hxannotation.IdAnnotation;
import com.hxoms.common.hxannotation.TableAnnotation;

import java.util.Date;
/**
 * oms_reg_revokeapply
 */
@TableAnnotation(TableName = "oms_reg_revokeapply", TableDescription="撤销登记备案申请表")
public class OmsRegRevokeapply {
    /**
     * 主键
     */
    @IdAnnotation
    @ColumnAnnotation(FieldName = "ID",  FieldDescription="主键")
    private String id;

    /**
     * 申请人
     */
    @ColumnAnnotation(FieldName = "A0100",  FieldDescription="申请人")
    private String a0100;

    /**
     * 创建人
     */
    @ColumnAnnotation(FieldName = "CREATE_USER",  FieldDescription="创建人")
    private String createUser;

    /**
     * 创建时间
     */
    @ColumnAnnotation(FieldName = "CREATE_DATE",  FieldDescription="创建时间")
    private Date createDate;

    /**
     * 姓
     */
    @ColumnAnnotation(FieldName = "SURNAME",  FieldDescription="姓")
    private String surname;

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
     * 职务（级）或职称代码
     */
    @ColumnAnnotation(FieldName = "POST_CODE",  FieldDescription="职务（级）或职称代码")
    private String postCode;

    /**
     * 职务（级）或职称
     */
    @ColumnAnnotation(FieldName = "POST",  FieldDescription="职务（级）或职称")
    private String post;

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
     * 涉密状态(核心/重要/一般/非涉密）)
     */
    @ColumnAnnotation(FieldName = "SECRET_STATUS",  FieldDescription="涉密状态(核心/重要/一般/非涉密）)")
    private String secretStatus;

    /**
     * 脱密日期
     */
    @ColumnAnnotation(FieldName = "SECRET_START_DATE",  FieldDescription="脱密日期")
    private Date secretStartDate;

    /**
     * 脱密期限
     */
    @ColumnAnnotation(FieldName = "SECRET_END_DATE",  FieldDescription="脱密期限")
    private Date secretEndDate;

    /**
     * 申请理由
     */
    @ColumnAnnotation(FieldName = "APPLY_REASON",  FieldDescription="申请理由")
    private String applyReason;

    /**
     * 状态(填写0，已上报1（经办人上报干部监督处），已审批2（干部监督处已审批），已抽取3（登记备案工作已经抽取数据）、已备案4（登记备案结果已确认）、退回5（资料不全，干部监督处退回）、拒批9（干部监督处不允许撤销）)
     */
    @ColumnAnnotation(FieldName = "STATUS",  FieldDescription="状态(填写0，已上报1（经办人上报干部监督处），已审批2（干部监督处已审批），已抽取3（登记备案工作已经抽取数据）、已备案4（登记备案结果已确认）、退回5（资料不全，干部监督处退回）、拒批9（干部监督处不允许撤销）)")
    private String status;

    /**
     * 备案机构
     */
    @ColumnAnnotation(FieldName = "RF_B0000",  FieldDescription="备案机构")
    private String rfB0000;

    /**
     * 脱密日期
     */
    @ColumnAnnotation(FieldName = "EXIT_DATE",  FieldDescription="退出日期")
    private String exitDate;


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
     * 创建人
     * @return CREATE_USER 创建人
     */
    public String getCreateUser() {
        return createUser;
    }

    /**
     * 创建人
     * @param createUser 创建人
     */
    public void setCreateUser(String createUser) {
        this.createUser = createUser == null ? null : createUser.trim();
    }

    /**
     * 创建时间
     * @return CREATE_DATE 创建时间
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * 创建时间
     * @param createDate 创建时间
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
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
     * 涉密状态(核心/重要/一般/非涉密）)
     * @return SECRET_STATUS 涉密状态(核心/重要/一般/非涉密）)
     */
    public String getSecretStatus() {
        return secretStatus;
    }

    /**
     * 涉密状态(核心/重要/一般/非涉密）)
     * @param secretStatus 涉密状态(核心/重要/一般/非涉密）)
     */
    public void setSecretStatus(String secretStatus) {
        this.secretStatus = secretStatus == null ? null : secretStatus.trim();
    }

    /**
     * 脱密日期
     * @return SECRET_START_DATE 脱密日期
     */
    public Date getSecretStartDate() {
        return secretStartDate;
    }

    /**
     * 脱密日期
     * @param secretStartDate 脱密日期
     */
    public void setSecretStartDate(Date secretStartDate) {
        this.secretStartDate = secretStartDate;
    }

    /**
     * 脱密期限
     * @return SECRET_END_DATE 脱密期限
     */
    public Date getSecretEndDate() {
        return secretEndDate;
    }

    /**
     * 脱密期限
     * @param secretEndDate 脱密期限
     */
    public void setSecretEndDate(Date secretEndDate) {
        this.secretEndDate = secretEndDate;
    }

    /**
     * 申请理由
     * @return APPLY_REASON 申请理由
     */
    public String getApplyReason() {
        return applyReason;
    }

    /**
     * 申请理由
     * @param applyReason 申请理由
     */
    public void setApplyReason(String applyReason) {
        this.applyReason = applyReason == null ? null : applyReason.trim();
    }

    /**
     * 状态(填写0，已上报1（经办人上报干部监督处），已审批2（干部监督处已审批），已抽取3（登记备案工作已经抽取数据）、已备案4（登记备案结果已确认）、退回5（资料不全，干部监督处退回）、拒批9（干部监督处不允许撤销）)
     * @return STATUS 状态(填写0，已上报1（经办人上报干部监督处），已审批2（干部监督处已审批），已抽取3（登记备案工作已经抽取数据）、已备案4（登记备案结果已确认）、退回5（资料不全，干部监督处退回）、拒批9（干部监督处不允许撤销）)
     */
    public String getStatus() {
        return status;
    }

    /**
     * 状态(填写0，已上报1（经办人上报干部监督处），已审批2（干部监督处已审批），已抽取3（登记备案工作已经抽取数据）、已备案4（登记备案结果已确认）、退回5（资料不全，干部监督处退回）、拒批9（干部监督处不允许撤销）)
     * @param status 状态(填写0，已上报1（经办人上报干部监督处），已审批2（干部监督处已审批），已抽取3（登记备案工作已经抽取数据）、已备案4（登记备案结果已确认）、退回5（资料不全，干部监督处退回）、拒批9（干部监督处不允许撤销）)
     */
    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    /**
     * 备案机构
     * @return RF_B0000 备案机构
     */
    public String getRfB0000() {
        return rfB0000;
    }

    /**
     * 备案机构
     * @param rfB0000 备案机构
     */
    public void setRfB0000(String rfB0000) {
        this.rfB0000 = rfB0000 == null ? null : rfB0000.trim();
    }

    /**
     * 退出时间
     */
    public String getExitDate() {
        return exitDate;
    }

    public void setExitDate(String exitDate) {
        this.exitDate = exitDate;
    }
}