package com.hxoms.modules.sysUser.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hxoms.common.hxannotation.ColumnAnnotation;
import com.hxoms.common.hxannotation.IdAnnotation;
import com.hxoms.common.hxannotation.TableAnnotation;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@TableAnnotation(TableName = "cf_user", TableDescription="用户表")
public class CfUser {
    @IdAnnotation
    @ColumnAnnotation(FieldName = "user_id",   FieldDescription="用户id")
    private String userId;

    @ColumnAnnotation(FieldName = "user_name",   FieldDescription="经办人真实姓名")
    private String userName;

    @ColumnAnnotation(FieldName = "user_code",   FieldDescription="登录名，(建议机构简称+姓名)")
    private String userCode;

    @ColumnAnnotation(FieldName = "user_type",   FieldDescription="用户类型(1超级管理员、2安全保密管理员、3安全审计管理员、4各单位管理员、5监督处工作人员、6经办人、7组织部相关处室、8省外办、9统战部（港澳办）、10统战部（台办）、11省保密局、12省纪委、13部领导、14其他)")
    private String userType;

    @ColumnAnnotation(FieldName = "user_state",   FieldDescription="用户状态(注册0、正常1、撤销2、征求意见3、待审批4、拒绝5、待撤消6、暂停7、登记指纹8)")
    private String userState;

    @ColumnAnnotation(FieldName = "user_photo",   FieldDescription="用户照片")
    private String userPhoto;

    @ColumnAnnotation(FieldName = "user_email",   FieldDescription="用户邮箱")
    private String userEmail;

    @ColumnAnnotation(FieldName = "user_mobile",   FieldDescription="用户电话")
    private String userMobile;

    @ColumnAnnotation(FieldName = "user_password",   FieldDescription="用户密码")
    private String userPassword;

    @ColumnAnnotation(FieldName = "order_index",   FieldDescription="排序")
    private Integer orderIndex;

    @ColumnAnnotation(FieldName = "org_id",   FieldDescription="任职机构ID")
    private String orgId;

    @ColumnAnnotation(FieldName = "modify_user",   FieldDescription="修改用户")
    private String modifyUser;

    @ColumnAnnotation(FieldName = "modify_time",   FieldDescription="修改时间")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date modifyTime;

    @ColumnAnnotation(FieldName = "org_name",   FieldDescription="机构名称")
    private String orgName;

    @ColumnAnnotation(FieldName = "USER_EXPIRE",   FieldDescription="有效期(过了此日期不允许登录)")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date userExpire;

    @ColumnAnnotation(FieldName = "USER_LOGINFAILNUM",   FieldDescription="登录失败次数")
    private Integer userLoginfailnum;

    @ColumnAnnotation(FieldName = "CREATOR",   FieldDescription="创建人")
    private String creator;

    @ColumnAnnotation(FieldName = "CREATETIME",   FieldDescription="创建时间")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date createtime;

    @ColumnAnnotation(FieldName = "IP",   FieldDescription="IP地址")
    private String ip;

    @ColumnAnnotation(FieldName = "A0100",   FieldDescription="干部信息库A01表主键")
    private String a0100;

    @ColumnAnnotation(FieldName = "Registe_residence",   FieldDescription="户口所在地")
    private String registeResidence;

    @ColumnAnnotation(FieldName = "CARD_ISSUING_AUTHORITY",   FieldDescription="身份证签发机关")
    private String cardIssuingAuthority;

    @ColumnAnnotation(FieldName = "CARE_START_DATE",   FieldDescription="身份证有效期开始时间")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date careStartDate;

    @ColumnAnnotation(FieldName = "CARD_EXPIRY_DATE",   FieldDescription="身份证有效期结束时间")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date cardExpiryDate;

    @ColumnAnnotation(FieldName = "CARD_PHYSICAL_ID",   FieldDescription="身份证物理ID")
    private String cardPhysicalId;

    @ColumnAnnotation(FieldName = "IDNUMBER",   FieldDescription="身份证号")
    private String idnumber;

    @ColumnAnnotation(FieldName = "POLITICAL_AFFI",   FieldDescription="政治面貌")
    private String politicalAffi;

    @ColumnAnnotation(FieldName = "SEX",   FieldDescription="性别")
    private String sex;

    @ColumnAnnotation(FieldName = "Duty",   FieldDescription="职务（级）")
    private String duty;

    @ColumnAnnotation(FieldName = "MAC",   FieldDescription="Mac地址")
    private String mac;

    @ColumnAnnotation(FieldName = "Telephone",   FieldDescription="座机号")
    private String telephone;

    @ColumnAnnotation(FieldName = "NATION",   FieldDescription="民族")
    private String nation;

    @ColumnAnnotation(FieldName = "BIRTH",   FieldDescription="出生日期")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date birth;

    @ColumnAnnotation(FieldName = "FINGERPRINT_1",   FieldDescription="指纹1(存储指纹BASE64数据)")
    private String fingerprint1;

    @ColumnAnnotation(FieldName = "FINGERPRINT_2",   FieldDescription="指纹2(存储指纹BASE64数据)")
    private String fingerprint2;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode == null ? null : userCode.trim();
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType == null ? null : userType.trim();
    }

    public String getUserState() {
        return userState;
    }

    public void setUserState(String userState) {
        this.userState = userState == null ? null : userState.trim();
    }

    public String getUserPhoto() {
        return userPhoto;
    }

    public void setUserPhoto(String userPhoto) {
        this.userPhoto = userPhoto == null ? null : userPhoto.trim();
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail == null ? null : userEmail.trim();
    }

    public String getUserMobile() {
        return userMobile;
    }

    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile == null ? null : userMobile.trim();
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword == null ? null : userPassword.trim();
    }

    public Integer getOrderIndex() {
        return orderIndex;
    }

    public void setOrderIndex(Integer orderIndex) {
        this.orderIndex = orderIndex;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId == null ? null : orgId.trim();
    }

    public String getModifyUser() {
        return modifyUser;
    }

    public void setModifyUser(String modifyUser) {
        this.modifyUser = modifyUser == null ? null : modifyUser.trim();
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName == null ? null : orgName.trim();
    }

    public Date getUserExpire() {
        return userExpire;
    }

    public void setUserExpire(Date userExpire) {
        this.userExpire = userExpire;
    }

    public Integer getUserLoginfailnum() {
        return userLoginfailnum;
    }

    public void setUserLoginfailnum(Integer userLoginfailnum) {
        this.userLoginfailnum = userLoginfailnum;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator == null ? null : creator.trim();
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip == null ? null : ip.trim();
    }

    public String getA0100() {
        return a0100;
    }

    public void setA0100(String a0100) {
        this.a0100 = a0100 == null ? null : a0100.trim();
    }

    public String getRegisteResidence() {
        return registeResidence;
    }

    public void setRegisteResidence(String registeResidence) {
        this.registeResidence = registeResidence == null ? null : registeResidence.trim();
    }

    public String getCardIssuingAuthority() {
        return cardIssuingAuthority;
    }

    public void setCardIssuingAuthority(String cardIssuingAuthority) {
        this.cardIssuingAuthority = cardIssuingAuthority == null ? null : cardIssuingAuthority.trim();
    }

    public Date getCareStartDate() {
        return careStartDate;
    }

    public void setCareStartDate(Date careStartDate) {
        this.careStartDate = careStartDate;
    }

    public Date getCardExpiryDate() {
        return cardExpiryDate;
    }

    public void setCardExpiryDate(Date cardExpiryDate) {
        this.cardExpiryDate = cardExpiryDate;
    }

    public String getCardPhysicalId() {
        return cardPhysicalId;
    }

    public void setCardPhysicalId(String cardPhysicalId) {
        this.cardPhysicalId = cardPhysicalId == null ? null : cardPhysicalId.trim();
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

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex == null ? null : sex.trim();
    }

    public String getDuty() {
        return duty;
    }

    public void setDuty(String duty) {
        this.duty = duty == null ? null : duty.trim();
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac == null ? null : mac.trim();
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone == null ? null : telephone.trim();
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation == null ? null : nation.trim();
    }

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    public String getFingerprint1() {
        return fingerprint1;
    }

    public void setFingerprint1(String fingerprint1) {
        this.fingerprint1 = fingerprint1 == null ? null : fingerprint1.trim();
    }

    public String getFingerprint2() {
        return fingerprint2;
    }

    public void setFingerprint2(String fingerprint2) {
        this.fingerprint2 = fingerprint2 == null ? null : fingerprint2.trim();
    }
}