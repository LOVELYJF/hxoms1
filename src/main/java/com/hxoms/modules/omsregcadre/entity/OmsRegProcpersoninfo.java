package com.hxoms.modules.omsregcadre.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hxoms.common.hxannotation.ColumnAnnotation;
import com.hxoms.common.hxannotation.IdAnnotation;
import com.hxoms.common.hxannotation.TableAnnotation;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

@TableAnnotation(TableName = "oms_reg_procpersoninfo", TableDescription="省管干部登记备案人员信息")
public class OmsRegProcpersoninfo {
    @IdAnnotation
    @ColumnAnnotation(FieldName = "ID",   FieldDescription="主键")
    private String id;

    @ColumnAnnotation(FieldName = "A0100",   FieldDescription="人员主键")
    private String a0100;

    @ColumnAnnotation(FieldName = "RF_B0000",   FieldDescription="备案机构")
    private String rfB0000;

    @ColumnAnnotation(FieldName = "INBOUND_FLAG",   FieldDescription="入库标识  新增U  修改I  撤消D")
    private String inboundFlag;

    @ColumnAnnotation(FieldName = "RF_STATUS",   FieldDescription="备案状态  0未备案，1已备案，2已确认")
    private String rfStatus;

    @ColumnAnnotation(FieldName = "CHECK_STATUS",   FieldDescription="验收状态  1已验收，0待验收")
    private String checkStatus;

    @ColumnAnnotation(FieldName = "INCUMBENCY_STATUS",   FieldDescription="在职状态 1在职 2辞职 3退休 4去世 5开除 6调出 7.省管变中管 8 未匹配 9其它")
    private String incumbencyStatus;

    @ColumnAnnotation(FieldName = "DATA_TYPE",   FieldDescription="数据类型  1.干部    2 公安")
    private String dataType;

    @ColumnAnnotation(FieldName = "SURNAME",   FieldDescription="姓")
    private String surname;

    @ColumnAnnotation(FieldName = "NAME",   FieldDescription="名")
    private String name;

    @ColumnAnnotation(FieldName = "SEX",   FieldDescription="性别")
    private String sex;

    @ColumnAnnotation(FieldName = "BIRTH_DATE",   FieldDescription="出生日期（身份证（公安））")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date birthDate;

    @ColumnAnnotation(FieldName = "BIRTH_DATE_GB",   FieldDescription="出生日期（干部）")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date birthDateGb;

    @ColumnAnnotation(FieldName = "NATION_CODE",   FieldDescription="民族代码")
    private String nationCode;

    @ColumnAnnotation(FieldName = "NATION_NAME",   FieldDescription="民族")
    private String nationName;

    @ColumnAnnotation(FieldName = "IDNUMBER_GB",   FieldDescription="身份证号（干部）")
    private String idnumberGb;

    @ColumnAnnotation(FieldName = "IDNUMBER_GA",   FieldDescription="身份证号（公安）")
    private String idnumberGa;

    @ColumnAnnotation(FieldName = "POLITICAL_AFFICODE",   FieldDescription="政治面貌代码")
    private String politicalAfficode;

    @ColumnAnnotation(FieldName = "POLITICAL_AFFINAME",   FieldDescription="政治面貌")
    private String politicalAffiname;

    @ColumnAnnotation(FieldName = "HEALTH_CODE",   FieldDescription="健康状况代码")
    private String healthCode;

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

    @ColumnAnnotation(FieldName = "IDENTITY_CODE",   FieldDescription="身份情况代码 省管干部-1\n" +
            "科级以上公务员（企事业单位中层以上管理人员）-2\n" +
            "涉密人员-3\n" +
            "重要岗位人-4\n" +
            "重点监管人员-5\n" +
            "其他人员-9")
    private String identityCode;

    @ColumnAnnotation(FieldName = "IDENTITY",   FieldDescription="身份情况")
    private String identity;

    @ColumnAnnotation(FieldName = "SECRET_LEVEL",   FieldDescription="涉密等级0 非涉密  1 一般 2重要 3核心")
    private String secretLevel;

    @ColumnAnnotation(FieldName = "DECRYPT_STARTDATE",   FieldDescription="脱密期管理开始日期")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date decryptStartdate;

    @ColumnAnnotation(FieldName = "DECRYPT_ENDDATE",   FieldDescription="脱密期管理终止日期")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date decryptEnddate;

    @ColumnAnnotation(FieldName = "MAIN_LEADER",   FieldDescription="是否主要领导 1主要领导，0非主要领导")
    private String mainLeader;

    @ColumnAnnotation(FieldName = "LICENCE_IDENTITY",   FieldDescription="证照持有情况0查询中、 1护照、2港澳通行证、4台湾通行证，多个通行证用或运算表达，如果有前两个，值为3，如果全有，值为7，证照每次变动都自动更新此列")
    private Integer licenceIdentity;

    @ColumnAnnotation(FieldName = "NF",   FieldDescription="是否为裸官0-不是 1-是")
    private String nf;

    @ColumnAnnotation(FieldName = "FJGNF",   FieldDescription="家属受监管裸官1是，0否")
    private String fjgnf;

    @ColumnAnnotation(FieldName = "XRXGW",   FieldDescription="裸官在限入性岗位")
    private String xrxgw;

    @ColumnAnnotation(FieldName = "LQGZ",   FieldDescription="离琼挂职1是，0否")
    private String lqgz;

    @ColumnAnnotation(FieldName = "DQGZ",   FieldDescription="到琼挂职1是，0否")
    private String dqgz;

    @ColumnAnnotation(FieldName = "REPLYOPINION",   FieldDescription="纪委不回复意见人员1是，0否")
    private String replyopinion;

    @ColumnAnnotation(FieldName = "ABROADTIME",   FieldDescription="锁定出国时间到")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date abroadtime;

    @ColumnAnnotation(FieldName = "REASON",   FieldDescription="锁定原因")
    private String reason;

    @ColumnAnnotation(FieldName = "CREATE_TIME",   FieldDescription="")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date createTime;

    @ColumnAnnotation(FieldName = "CREATE_USER",   FieldDescription="")
    private String createUser;

    @ColumnAnnotation(FieldName = "MODIFY_TIME",   FieldDescription="")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date modifyTime;

    @ColumnAnnotation(FieldName = "MODIFY_USER",   FieldDescription="")
    private String modifyUser;

    @ColumnAnnotation(FieldName = "SORT_ID",   FieldDescription="排序")
    private Integer sortId;

    @ColumnAnnotation(FieldName = "PY",   FieldDescription="拼音简称")
    private String py;

    @ColumnAnnotation(FieldName = "SECRET_POST",   FieldDescription="涉密岗位")
    private String secretPost;

    @ColumnAnnotation(FieldName = "EXIT_DATE",   FieldDescription="辞职、退休等日期 ")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date exitDate;

    @ColumnAnnotation(FieldName = "FINISH_TIME",   FieldDescription="备案完成时间")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date finishTime;

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
        this.idnumberGb = idnumberGb;
    }

    public String getIdnumberGa() {
        return idnumberGa;
    }

    public void setIdnumberGa(String idnumberGa) {
        this.idnumberGa = idnumberGa;
    }

    public String getNationCode() {
        return nationCode;
    }

    public void setNationCode(String nationCode) {
        this.nationCode = nationCode == null ? null : nationCode.trim();
    }

    public String getNationName() {
        return nationName;
    }

    public void setNationName(String nationName) {
        this.nationName = nationName == null ? null : nationName.trim();
    }

    public String getPoliticalAfficode() {
        return politicalAfficode;
    }

    public void setPoliticalAfficode(String politicalAfficode) {
        this.politicalAfficode = politicalAfficode == null ? null : politicalAfficode.trim();
    }

    public String getPoliticalAffiname() {
        return politicalAffiname;
    }

    public void setPoliticalAffiname(String politicalAffiname) {
        this.politicalAffiname = politicalAffiname == null ? null : politicalAffiname.trim();
    }

    public String getHealthCode() {
        return healthCode;
    }

    public void setHealthCode(String healthCode) {
        this.healthCode = healthCode == null ? null : healthCode.trim();
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser == null ? null : createUser.trim();
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getModifyUser() {
        return modifyUser;
    }

    public void setModifyUser(String modifyUser) {
        this.modifyUser = modifyUser == null ? null : modifyUser.trim();
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

    public String getSecretPost() {
        return secretPost;
    }

    public void setSecretPost(String secretPost) {
        this.secretPost = secretPost == null ? null : secretPost.trim();
    }

    public Date getExitDate() {
        return exitDate;
    }

    public void setExitDate(Date exitDate) {
        this.exitDate = exitDate;
    }

    public Date getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }


}