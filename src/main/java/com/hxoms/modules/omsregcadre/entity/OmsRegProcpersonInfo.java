package com.hxoms.modules.omsregcadre.entity;

import com.hxoms.common.hxannotation.ColumnAnnotation;
import com.hxoms.common.hxannotation.IdAnnotation;
import com.hxoms.common.hxannotation.TableAnnotation;


import java.util.Date;
/**
 * oms_reg_procpersoninfo
 */
@TableAnnotation(TableName = "oms_reg_procpersoninfo", TableDescription="登记备案人员信息表")
public class OmsRegProcpersonInfo {
    /**
     * 主键
     */
    @IdAnnotation
    @ColumnAnnotation(FieldName = "ID", FieldDescription = "主键")
    private String id;

    /**
     * 人员主键
     */
    @ColumnAnnotation(FieldName = "A0100", FieldDescription = "人员主键")
    private String a0100;

    /**
     * 备案机构
     */
    @ColumnAnnotation(FieldName = "RF_B0000", FieldDescription = "备案机构")
    private String rfB0000;

    /**
     * 入库标识
     */
    @ColumnAnnotation(FieldName = "INBOUND_FLAG", FieldDescription = "入库标识")
    private String inboundFlag;

    /**
     * 备案状态
     */
    @ColumnAnnotation(FieldName = "RF_STATUS", FieldDescription = "备案状态")
    private String rfStatus;

    /**
     * 验收状态
     */
    @ColumnAnnotation(FieldName = "CHECK_STATUS", FieldDescription = "验收状态")
    private String checkStatus;

    /**
     * 在职状态
     */
    @ColumnAnnotation(FieldName = "INCUMBENCY_STATUS", FieldDescription = "在职状态")
    private String incumbencyStatus;

    /**
     * 数据类型
     */
    @ColumnAnnotation(FieldName = "DATA_TYPE", FieldDescription = "数据类型")
    private String dataType;

    /**
     * 姓
     */
    @ColumnAnnotation(FieldName = "SURNAME", FieldDescription = "姓")
    private String surname;

    /**
     * 名
     */
    @ColumnAnnotation(FieldName = "NAME", FieldDescription = "名")
    private String name;

    /**
     * 性别
     */
    @ColumnAnnotation(FieldName = "SEX", FieldDescription = "性别")
    private String sex;

    /**
     * 出生日期
     */
    @ColumnAnnotation(FieldName = "BIRTH_DATE", FieldDescription = "出生日期")
    private Date birthDate;

    /**
     * 民族
     */
    @ColumnAnnotation(FieldName = "NATION", FieldDescription = "民族")
    private String nation;

    /**
     * 身份证号
     */
    @ColumnAnnotation(FieldName = "IDNUMBER", FieldDescription = "身份证号")
    private String idnumber;

    /**
     * 政治面貌
     */
    @ColumnAnnotation(FieldName = "POLITICAL_AFFI", FieldDescription = "政治面貌")
    private String politicalAffi;

    /**
     * 健康状况
     */
    @ColumnAnnotation(FieldName = "HEALTH", FieldDescription = "健康状况")
    private String health;

    /**
     * 户口所在地代码
     */
    @ColumnAnnotation(FieldName = "REGISTE_RESIDENCE_CODE", FieldDescription = "户口所在地代码")
    private String registeResidenceCode;

    /**
     * 户口所在地
     */
    @ColumnAnnotation(FieldName = "REGISTE_RESIDENCE", FieldDescription = "户口所在地")
    private String registeResidence;

    /**
     * 工作单位
     */
    @ColumnAnnotation(FieldName = "WORK_UNIT", FieldDescription = "工作单位")
    private String workUnit;

    /**
     * 职务代码
     */
    @ColumnAnnotation(FieldName = "POST_CODE", FieldDescription = "职务代码")
    private String postCode;

    /**
     * 职务
     */
    @ColumnAnnotation(FieldName = "POST", FieldDescription = "职务")
    private String post;

    /**
     * 人事主管单位
     */
    @ColumnAnnotation(FieldName = "PERSON_MANAGER", FieldDescription = "人事主管单位")
    private String personManager;

    /**
     * 身份情况代码
     */
    @ColumnAnnotation(FieldName = "IDENTITY_CODE", FieldDescription = "身份情况代码")
    private String identityCode;

    /**
     * 身份情况
     */
    @ColumnAnnotation(FieldName = "IDENTITY", FieldDescription = "身份情况")
    private String identity;

    /**
     * 涉密等级
     */
    @ColumnAnnotation(FieldName = "SECRET_LEVEL", FieldDescription = "涉密等级")
    private String secretLevel;

    /**
     * 脱密期管理开始日期
     */
    @ColumnAnnotation(FieldName = "DECRYPT_STARTDATE", FieldDescription = "脱密期管理开始日期")
    private Date decryptStartdate;

    /**
     * 脱密期管理终止日期
     */
    @ColumnAnnotation(FieldName = "DECRYPT_ENDDATE", FieldDescription = "脱密期管理终止日期")
    private Date decryptEnddate;

    /**
     * 是否主要领导
     */
    @ColumnAnnotation(FieldName = "MAIN_LEADER", FieldDescription = "是否主要领导")
    private String mainLeader;

    /**
     * 证照持有情况
     */
    @ColumnAnnotation(FieldName = "LICENCE_IDENTITY", FieldDescription = "证照持有情况")
    private Integer licenceIdentity;

    /**
     * 是否为裸官
     */
    @ColumnAnnotation(FieldName = "NF", FieldDescription = "是否为裸官")
    private String nf;

    /**
     * 家属受监管裸官
     */
    @ColumnAnnotation(FieldName = "FJGNF", FieldDescription = "家属受监管裸官")
    private String fjgnf;

    /**
     * 裸官在限入性岗位
     */
    @ColumnAnnotation(FieldName = "XRXGW", FieldDescription = "裸官在限入性岗位")
    private String xrxgw;

    /**
     * 离琼挂职
     */
    @ColumnAnnotation(FieldName = "LQGZ", FieldDescription = "离琼挂职")
    private String lqgz;

    /**
     * 到琼挂职
     */
    @ColumnAnnotation(FieldName = "DQGZ", FieldDescription = "到琼挂职")
    private String dqgz;

    /**
     * 纪委不回复意见人员
     */
    @ColumnAnnotation(FieldName = "REPLYOPINION", FieldDescription = "纪委不回复意见人员")
    private String replyopinion;

    /**
     * 锁定出国时间到
     */
    @ColumnAnnotation(FieldName = "ABROADTIME", FieldDescription = "锁定出国时间到")
    private Date abroadtime;

    /**
     * 锁定原因
     */
    @ColumnAnnotation(FieldName = "REASON", FieldDescription = "锁定原因")
    private String reason;

    /**
     *
     */
    @ColumnAnnotation(FieldName = "CREATE_TIME", FieldDescription = "")
    private Date createTime;

    /**
     *
     */
    @ColumnAnnotation(FieldName = "CREATE_USER", FieldDescription = "")
    private String createUser;

    /**
     *
     */
    @ColumnAnnotation(FieldName = "MODIFY_TIME", FieldDescription = "")
    private Date modifyTime;

    /**
     *
     */
    @ColumnAnnotation(FieldName = "MODIFY_USER", FieldDescription = "")
    private String modifyUser;

    /**
     *
     */
    @ColumnAnnotation(FieldName = "SORT_ID", FieldDescription = "")
    private Integer sortId;

    /**
     * 拼音简称
     */
    @ColumnAnnotation(FieldName = "PY", FieldDescription = "拼音简称")
    private String py;

    /**
     * 主键
     *
     * @return ID 主键
     */
    public String getId() {
        return id;
    }

    /**
     * 主键
     *
     * @param id 主键
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * 人员主键
     *
     * @return A0100 人员主键
     */
    public String getA0100() {
        return a0100;
    }

    /**
     * 人员主键
     *
     * @param a0100 人员主键
     */
    public void setA0100(String a0100) {
        this.a0100 = a0100 == null ? null : a0100.trim();
    }

    /**
     * 备案机构
     *
     * @return RF_B0000 备案机构
     */
    public String getRfB0000() {
        return rfB0000;
    }

    /**
     * 备案机构
     *
     * @param rfB0000 备案机构
     */
    public void setRfB0000(String rfB0000) {
        this.rfB0000 = rfB0000 == null ? null : rfB0000.trim();
    }

    /**
     * 入库标识
     *
     * @return INBOUND_FLAG 入库标识
     */
    public String getInboundFlag() {
        return inboundFlag;
    }

    /**
     * 入库标识
     *
     * @param inboundFlag 入库标识
     */
    public void setInboundFlag(String inboundFlag) {
        this.inboundFlag = inboundFlag == null ? null : inboundFlag.trim();
    }

    /**
     * 备案状态
     *
     * @return RF_STATUS 备案状态
     */
    public String getRfStatus() {
        return rfStatus;
    }

    /**
     * 备案状态
     *
     * @param rfStatus 备案状态
     */
    public void setRfStatus(String rfStatus) {
        this.rfStatus = rfStatus == null ? null : rfStatus.trim();
    }

    /**
     * 验收状态
     *
     * @return CHECK_STATUS 验收状态
     */
    public String getCheckStatus() {
        return checkStatus;
    }

    /**
     * 验收状态
     *
     * @param checkStatus 验收状态
     */
    public void setCheckStatus(String checkStatus) {
        this.checkStatus = checkStatus == null ? null : checkStatus.trim();
    }

    /**
     * 在职状态
     *
     * @return INCUMBENCY_STATUS 在职状态
     */
    public String getIncumbencyStatus() {
        return incumbencyStatus;
    }

    /**
     * 在职状态
     *
     * @param incumbencyStatus 在职状态
     */
    public void setIncumbencyStatus(String incumbencyStatus) {
        this.incumbencyStatus = incumbencyStatus == null ? null : incumbencyStatus.trim();
    }

    /**
     * 数据类型
     *
     * @return DATA_TYPE 数据类型
     */
    public String getDataType() {
        return dataType;
    }

    /**
     * 数据类型
     *
     * @param dataType 数据类型
     */
    public void setDataType(String dataType) {
        this.dataType = dataType == null ? null : dataType.trim();
    }

    /**
     * 姓
     *
     * @return SURNAME 姓
     */
    public String getSurname() {
        return surname;
    }

    /**
     * 姓
     *
     * @param surname 姓
     */
    public void setSurname(String surname) {
        this.surname = surname == null ? null : surname.trim();
    }

    /**
     * 名
     *
     * @return NAME 名
     */
    public String getName() {
        return name;
    }

    /**
     * 名
     *
     * @param name 名
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * 性别
     *
     * @return SEX 性别
     */
    public String getSex() {
        return sex;
    }

    /**
     * 性别
     *
     * @param sex 性别
     */
    public void setSex(String sex) {
        this.sex = sex == null ? null : sex.trim();
    }

    /**
     * 出生日期
     *
     * @return BIRTH_DATE 出生日期
     */
    public Date getBirthDate() {
        return birthDate;
    }

    /**
     * 出生日期
     *
     * @param birthDate 出生日期
     */
    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    /**
     * 民族
     *
     * @return NATION 民族
     */
    public String getNation() {
        return nation;
    }

    /**
     * 民族
     *
     * @param nation 民族
     */
    public void setNation(String nation) {
        this.nation = nation == null ? null : nation.trim();
    }

    /**
     * 身份证号
     *
     * @return IDNUMBER 身份证号
     */
    public String getIdnumber() {
        return idnumber;
    }

    /**
     * 身份证号
     *
     * @param idnumber 身份证号
     */
    public void setIdnumber(String idnumber) {
        this.idnumber = idnumber == null ? null : idnumber.trim();
    }

    /**
     * 政治面貌
     *
     * @return POLITICAL_AFFI 政治面貌
     */
    public String getPoliticalAffi() {
        return politicalAffi;
    }

    /**
     * 政治面貌
     *
     * @param politicalAffi 政治面貌
     */
    public void setPoliticalAffi(String politicalAffi) {
        this.politicalAffi = politicalAffi == null ? null : politicalAffi.trim();
    }

    /**
     * 健康状况
     *
     * @return HEALTH 健康状况
     */
    public String getHealth() {
        return health;
    }

    /**
     * 健康状况
     *
     * @param health 健康状况
     */
    public void setHealth(String health) {
        this.health = health == null ? null : health.trim();
    }

    /**
     * 户口所在地代码
     *
     * @return REGISTE_RESIDENCE_CODE 户口所在地代码
     */
    public String getRegisteResidenceCode() {
        return registeResidenceCode;
    }

    /**
     * 户口所在地代码
     *
     * @param registeResidenceCode 户口所在地代码
     */
    public void setRegisteResidenceCode(String registeResidenceCode) {
        this.registeResidenceCode = registeResidenceCode == null ? null : registeResidenceCode.trim();
    }

    /**
     * 户口所在地
     *
     * @return REGISTE_RESIDENCE 户口所在地
     */
    public String getRegisteResidence() {
        return registeResidence;
    }

    /**
     * 户口所在地
     *
     * @param registeResidence 户口所在地
     */
    public void setRegisteResidence(String registeResidence) {
        this.registeResidence = registeResidence == null ? null : registeResidence.trim();
    }

    /**
     * 工作单位
     *
     * @return WORK_UNIT 工作单位
     */
    public String getWorkUnit() {
        return workUnit;
    }

    /**
     * 工作单位
     *
     * @param workUnit 工作单位
     */
    public void setWorkUnit(String workUnit) {
        this.workUnit = workUnit == null ? null : workUnit.trim();
    }

    /**
     * 职务代码
     *
     * @return POST_CODE 职务代码
     */
    public String getPostCode() {
        return postCode;
    }

    /**
     * 职务代码
     *
     * @param postCode 职务代码
     */
    public void setPostCode(String postCode) {
        this.postCode = postCode == null ? null : postCode.trim();
    }

    /**
     * 职务
     *
     * @return POST 职务
     */
    public String getPost() {
        return post;
    }

    /**
     * 职务
     *
     * @param post 职务
     */
    public void setPost(String post) {
        this.post = post == null ? null : post.trim();
    }

    /**
     * 人事主管单位
     *
     * @return PERSON_MANAGER 人事主管单位
     */
    public String getPersonManager() {
        return personManager;
    }

    /**
     * 人事主管单位
     *
     * @param personManager 人事主管单位
     */
    public void setPersonManager(String personManager) {
        this.personManager = personManager == null ? null : personManager.trim();
    }

    /**
     * 身份情况代码
     *
     * @return IDENTITY_CODE 身份情况代码
     */
    public String getIdentityCode() {
        return identityCode;
    }

    /**
     * 身份情况代码
     *
     * @param identityCode 身份情况代码
     */
    public void setIdentityCode(String identityCode) {
        this.identityCode = identityCode == null ? null : identityCode.trim();
    }

    /**
     * 身份情况
     *
     * @return IDENTITY 身份情况
     */
    public String getIdentity() {
        return identity;
    }

    /**
     * 身份情况
     *
     * @param identity 身份情况
     */
    public void setIdentity(String identity) {
        this.identity = identity == null ? null : identity.trim();
    }

    /**
     * 涉密等级
     *
     * @return SECRET_LEVEL 涉密等级
     */
    public String getSecretLevel() {
        return secretLevel;
    }

    /**
     * 涉密等级
     *
     * @param secretLevel 涉密等级
     */
    public void setSecretLevel(String secretLevel) {
        this.secretLevel = secretLevel == null ? null : secretLevel.trim();
    }

    /**
     * 脱密期管理开始日期
     *
     * @return DECRYPT_STARTDATE 脱密期管理开始日期
     */
    public Date getDecryptStartdate() {
        return decryptStartdate;
    }

    /**
     * 脱密期管理开始日期
     *
     * @param decryptStartdate 脱密期管理开始日期
     */
    public void setDecryptStartdate(Date decryptStartdate) {
        this.decryptStartdate = decryptStartdate;
    }

    /**
     * 脱密期管理终止日期
     *
     * @return DECRYPT_ENDDATE 脱密期管理终止日期
     */
    public Date getDecryptEnddate() {
        return decryptEnddate;
    }

    /**
     * 脱密期管理终止日期
     *
     * @param decryptEnddate 脱密期管理终止日期
     */
    public void setDecryptEnddate(Date decryptEnddate) {
        this.decryptEnddate = decryptEnddate;
    }

    /**
     * 是否主要领导
     *
     * @return MAIN_LEADER 是否主要领导
     */
    public String getMainLeader() {
        return mainLeader;
    }

    /**
     * 是否主要领导
     *
     * @param mainLeader 是否主要领导
     */
    public void setMainLeader(String mainLeader) {
        this.mainLeader = mainLeader == null ? null : mainLeader.trim();
    }

    /**
     * 证照持有情况
     *
     * @return LICENCE_IDENTITY 证照持有情况
     */
    public Integer getLicenceIdentity() {
        return licenceIdentity;
    }

    /**
     * 证照持有情况
     *
     * @param licenceIdentity 证照持有情况
     */
    public void setLicenceIdentity(Integer licenceIdentity) {
        this.licenceIdentity = licenceIdentity;
    }

    /**
     * 是否为裸官
     *
     * @return NF 是否为裸官
     */
    public String getNf() {
        return nf;
    }

    /**
     * 是否为裸官
     *
     * @param nf 是否为裸官
     */
    public void setNf(String nf) {
        this.nf = nf == null ? null : nf.trim();
    }

    /**
     * 家属受监管裸官
     *
     * @return FJGNF 家属受监管裸官
     */
    public String getFjgnf() {
        return fjgnf;
    }

    /**
     * 家属受监管裸官
     *
     * @param fjgnf 家属受监管裸官
     */
    public void setFjgnf(String fjgnf) {
        this.fjgnf = fjgnf == null ? null : fjgnf.trim();
    }

    /**
     * 裸官在限入性岗位
     *
     * @return XRXGW 裸官在限入性岗位
     */
    public String getXrxgw() {
        return xrxgw;
    }

    /**
     * 裸官在限入性岗位
     *
     * @param xrxgw 裸官在限入性岗位
     */
    public void setXrxgw(String xrxgw) {
        this.xrxgw = xrxgw == null ? null : xrxgw.trim();
    }

    /**
     * 离琼挂职
     *
     * @return LQGZ 离琼挂职
     */
    public String getLqgz() {
        return lqgz;
    }

    /**
     * 离琼挂职
     *
     * @param lqgz 离琼挂职
     */
    public void setLqgz(String lqgz) {
        this.lqgz = lqgz == null ? null : lqgz.trim();
    }

    /**
     * 到琼挂职
     *
     * @return DQGZ 到琼挂职
     */
    public String getDqgz() {
        return dqgz;
    }

    /**
     * 到琼挂职
     *
     * @param dqgz 到琼挂职
     */
    public void setDqgz(String dqgz) {
        this.dqgz = dqgz == null ? null : dqgz.trim();
    }

    /**
     * 纪委不回复意见人员
     *
     * @return REPLYOPINION 纪委不回复意见人员
     */
    public String getReplyopinion() {
        return replyopinion;
    }

    /**
     * 纪委不回复意见人员
     *
     * @param replyopinion 纪委不回复意见人员
     */
    public void setReplyopinion(String replyopinion) {
        this.replyopinion = replyopinion == null ? null : replyopinion.trim();
    }

    /**
     * 锁定出国时间到
     *
     * @return ABROADTIME 锁定出国时间到
     */
    public Date getAbroadtime() {
        return abroadtime;
    }

    /**
     * 锁定出国时间到
     *
     * @param abroadtime 锁定出国时间到
     */
    public void setAbroadtime(Date abroadtime) {
        this.abroadtime = abroadtime;
    }

    /**
     * 锁定原因
     *
     * @return REASON 锁定原因
     */
    public String getReason() {
        return reason;
    }

    /**
     * 锁定原因
     *
     * @param reason 锁定原因
     */
    public void setReason(String reason) {
        this.reason = reason == null ? null : reason.trim();
    }

    /**
     * @return CREATE_TIME
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * @return CREATE_USER
     */
    public String getCreateUser() {
        return createUser;
    }

    /**
     * @param createUser
     */
    public void setCreateUser(String createUser) {
        this.createUser = createUser == null ? null : createUser.trim();
    }

    /**
     * @return MODIFY_TIME
     */
    public Date getModifyTime() {
        return modifyTime;
    }

    /**
     * @param modifyTime
     */
    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    /**
     * @return MODIFY_USER
     */
    public String getModifyUser() {
        return modifyUser;
    }

    /**
     * @param modifyUser
     */
    public void setModifyUser(String modifyUser) {
        this.modifyUser = modifyUser == null ? null : modifyUser.trim();
    }

    /**
     * @return SORT_ID
     */
    public Integer getSortId() {
        return sortId;
    }

    /**
     * @param sortId
     */
    public void setSortId(Integer sortId) {
        this.sortId = sortId;
    }

    /**
     * 拼音简称
     *
     * @return PY 拼音简称
     */
    public String getPy() {
        return py;
    }

    /**
     * 拼音简称
     *
     * @param py 拼音简称
     */
    public void setPy(String py) {
        this.py = py == null ? null : py.trim();
    }

}