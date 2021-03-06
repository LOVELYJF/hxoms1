package com.hxoms.modules.omsregcadre.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hxoms.common.hxannotation.ColumnAnnotation;
import com.hxoms.common.hxannotation.IdAnnotation;
import com.hxoms.common.hxannotation.TableAnnotation;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@TableAnnotation(TableName = "oms_reg_yearcheckinfo", TableDescription="年度检查比对结果集")
public class OmsRegYearcheckinfo {
    @IdAnnotation
    @ColumnAnnotation(FieldName = "ID",   FieldDescription="主键")
    private String id;

    @ColumnAnnotation(FieldName = "A0100",   FieldDescription="人员主键")
    private String a0100;

    @ColumnAnnotation(FieldName = "RF_ID",   FieldDescription="备案id")
    private String rfId;

    @ColumnAnnotation(FieldName = "RF_B0000",   FieldDescription="备案机构")
    private String rfB0000;

    @ColumnAnnotation(FieldName = "INBOUND_FLAG",   FieldDescription="入库标识")
    private String inboundFlag;

    @ColumnAnnotation(FieldName = "RF_STATUS",   FieldDescription="备案状态")
    private String rfStatus;

    @ColumnAnnotation(FieldName = "CHECK_STATUS",   FieldDescription="验收状态")
    private String checkStatus;

    @ColumnAnnotation(FieldName = "INCUMBENCY_STATUS",   FieldDescription="在职状态")
    private String incumbencyStatus;

    @ColumnAnnotation(FieldName = "DATA_TYPE",   FieldDescription="数据类型")
    private String dataType;

    @ColumnAnnotation(FieldName = "SURNAME",   FieldDescription="姓")
    private String surname;

    @ColumnAnnotation(FieldName = "NAME",   FieldDescription="名")
    private String name;

    @ColumnAnnotation(FieldName = "SEX",   FieldDescription="性别")
    private String sex;

    @ColumnAnnotation(FieldName = "BIRTH_DATE",   FieldDescription="出生日期")
    @JsonFormat(pattern = "yyyy.MM.dd",timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date birthDate;

    @ColumnAnnotation(FieldName = "NATION",   FieldDescription="民族")
    private String nation;

    @ColumnAnnotation(FieldName = "IDNUMBER",   FieldDescription="身份证号")
    private String idnumber;

    @ColumnAnnotation(FieldName = "POLITICAL_AFFI",   FieldDescription="政治面貌")
    private String politicalAffi;

    @ColumnAnnotation(FieldName = "HEALTH",   FieldDescription="健康状况")
    private String health;

    @ColumnAnnotation(FieldName = "REGISTE_RESIDENCE_CODE",   FieldDescription="户口所在地代码")
    private String registeResidenceCode;

    @ColumnAnnotation(FieldName = "REGISTE_RESIDENCE",   FieldDescription="户口所在地")
    private String registeResidence;

    @ColumnAnnotation(FieldName = "WORK_UNIT",   FieldDescription="工作单位")
    private String workUnit;

    @ColumnAnnotation(FieldName = "POST_CODE",   FieldDescription="职务代码")
    private String postCode;

    @ColumnAnnotation(FieldName = "POST",   FieldDescription="职务")
    private String post;

    @ColumnAnnotation(FieldName = "PERSON_MANAGER",   FieldDescription="人事主管单位")
    private String personManager;

    @ColumnAnnotation(FieldName = "IDENTITY_CODE",   FieldDescription="身份情况代码")
    private String identityCode;

    @ColumnAnnotation(FieldName = "IDENTITY",   FieldDescription="身份情况")
    private String identity;

    @ColumnAnnotation(FieldName = "SECRET_LEVEL",   FieldDescription="涉密等级")
    private String secretLevel;

    @ColumnAnnotation(FieldName = "DECRYPT_STARTDATE",   FieldDescription="脱密期管理开始日期")
    @JsonFormat(pattern = "yyyy.MM.dd",timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date decryptStartdate;

    @ColumnAnnotation(FieldName = "DECRYPT_ENDDATE",   FieldDescription="脱密期管理终止日期")
    @JsonFormat(pattern = "yyyy.MM.dd",timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date decryptEnddate;

    @ColumnAnnotation(FieldName = "MAIN_LEADER",   FieldDescription="是否主要领导")
    private String mainLeader;

    @ColumnAnnotation(FieldName = "LICENCE_IDENTITY",   FieldDescription="证照持有情况")
    private Integer licenceIdentity;

    @ColumnAnnotation(FieldName = "NF",   FieldDescription="是否为裸官")
    private String nf;

    @ColumnAnnotation(FieldName = "FJGNF",   FieldDescription="家属受监管裸官")
    private String fjgnf;

    @ColumnAnnotation(FieldName = "XRXGW",   FieldDescription="裸官在限入性岗位")
    private String xrxgw;

    @ColumnAnnotation(FieldName = "LQGZ",   FieldDescription="离琼挂职")
    private String lqgz;

    @ColumnAnnotation(FieldName = "DQGZ",   FieldDescription="到琼挂职")
    private String dqgz;

    @ColumnAnnotation(FieldName = "REPLYOPINION",   FieldDescription="纪委不回复意见人员")
    private String replyopinion;

    @ColumnAnnotation(FieldName = "ABROADTIME",   FieldDescription="锁定出国时间到")
    @JsonFormat(pattern = "yyyy.MM.dd",timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date abroadtime;

    @ColumnAnnotation(FieldName = "REASON",   FieldDescription="锁定原因")
    private String reason;

    @ColumnAnnotation(FieldName = "CREATE_DATE",   FieldDescription="")
    private String createDate;

    @ColumnAnnotation(FieldName = "CREATE_USER",   FieldDescription="")
    private String createUser;

    @ColumnAnnotation(FieldName = "SORT_ID",   FieldDescription="")
    private Integer sortId;

    @ColumnAnnotation(FieldName = "PY",   FieldDescription="拼音简称")
    private String py;

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

    public String getRfId() {
        return rfId;
    }

    public void setRfId(String rfId) {
        this.rfId = rfId;
    }

    public String getRfB0000() {
        return rfB0000;
    }

    public void setRfB0000(String rfB0000) {
        this.rfB0000 = rfB0000 == null ? null : rfB0000.trim();
    }

    public String getInboundFlag() {
        return inboundFlag;
    }

    public void setInboundFlag(String inboundFlag) {
        this.inboundFlag = inboundFlag == null ? null : inboundFlag.trim();
    }

    public String getRfStatus() {
        return rfStatus;
    }

    public void setRfStatus(String rfStatus) {
        this.rfStatus = rfStatus == null ? null : rfStatus.trim();
    }

    public String getCheckStatus() {
        return checkStatus;
    }

    public void setCheckStatus(String checkStatus) {
        this.checkStatus = checkStatus == null ? null : checkStatus.trim();
    }

    public String getIncumbencyStatus() {
        return incumbencyStatus;
    }

    public void setIncumbencyStatus(String incumbencyStatus) {
        this.incumbencyStatus = incumbencyStatus == null ? null : incumbencyStatus.trim();
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType == null ? null : dataType.trim();
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

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation == null ? null : nation.trim();
    }

    public String getIdnumber() {
        return idnumber;
    }

    public void setIdnumber(String idnumber) {
        this.idnumber = idnumber == null ? null : idnumber.trim();
    }

    public String getPoliticalAffi() {
        return politicalAffi;
    }

    public void setPoliticalAffi(String politicalAffi) {
        this.politicalAffi = politicalAffi == null ? null : politicalAffi.trim();
    }

    public String getHealth() {
        return health;
    }

    public void setHealth(String health) {
        this.health = health == null ? null : health.trim();
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

    public String getMainLeader() {
        return mainLeader;
    }

    public void setMainLeader(String mainLeader) {
        this.mainLeader = mainLeader == null ? null : mainLeader.trim();
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
        this.nf = nf == null ? null : nf.trim();
    }

    public String getFjgnf() {
        return fjgnf;
    }

    public void setFjgnf(String fjgnf) {
        this.fjgnf = fjgnf == null ? null : fjgnf.trim();
    }

    public String getXrxgw() {
        return xrxgw;
    }

    public void setXrxgw(String xrxgw) {
        this.xrxgw = xrxgw == null ? null : xrxgw.trim();
    }

    public String getLqgz() {
        return lqgz;
    }

    public void setLqgz(String lqgz) {
        this.lqgz = lqgz == null ? null : lqgz.trim();
    }

    public String getDqgz() {
        return dqgz;
    }

    public void setDqgz(String dqgz) {
        this.dqgz = dqgz == null ? null : dqgz.trim();
    }

    public String getReplyopinion() {
        return replyopinion;
    }

    public void setReplyopinion(String replyopinion) {
        this.replyopinion = replyopinion == null ? null : replyopinion.trim();
    }

    public Date getAbroadtime() {
        return abroadtime;
    }

    public void setAbroadtime(Date abroadtime) {
        this.abroadtime = abroadtime;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason == null ? null : reason.trim();
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser == null ? null : createUser.trim();
    }

    public Integer getSortId() {
        return sortId;
    }

    public void setSortId(Integer sortId) {
        this.sortId = sortId;
    }

    public String getPy() {
        return py;
    }

    public void setPy(String py) {
        this.py = py == null ? null : py.trim();
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }
}