package com.hxoms.modules.sysUser.entity;

import com.hxoms.common.hxannotation.ColumnAnnotation;
import com.hxoms.common.hxannotation.IdAnnotation;
import com.hxoms.common.hxannotation.TableAnnotation;

import java.util.Date;

/**
 * cf_user
 */
@TableAnnotation(TableName = "cf_user", TableDescription="")
public class CfUser {
    /**
     * 用户id
     */
    @IdAnnotation
    @ColumnAnnotation(FieldName = "user_id",  FieldDescription="用户id")
    private String userId;

    /**
     * 经办人真实姓名
     */
    @ColumnAnnotation(FieldName = "user_name",  FieldDescription="经办人真实姓名")
    private String userName;

    /**
     * 登录名，(建议机构简称+姓名)
     */
    @ColumnAnnotation(FieldName = "user_code",  FieldDescription="登录名，(建议机构简称+姓名)")
    private String userCode;

    /**
     * 用户类型(1超级管理员、2系统管理员、3安全审计管理员、4安全保密管理员、5监督处工作人员、6经办人、7相关处室、8其他)
     */
    @ColumnAnnotation(FieldName = "user_type",  FieldDescription="用户类型(1超级管理员、2系统管理员、3安全审计管理员、4安全保密管理员、5监督处工作人员、6经办人、7相关处室、8其他)")
    private String userType;

    /**
     * 用户状态(注册0、正常1、撤销2、征求意见3、待审批4、拒绝5、待撤消6、暂停7)
     */
    @ColumnAnnotation(FieldName = "user_state",  FieldDescription="用户状态(注册0、正常1、撤销2、征求意见3、待审批4、拒绝5、待撤消6、暂停7)")
    private String userState;

    /**
     * 用户照片
     */
    @ColumnAnnotation(FieldName = "user_photo",  FieldDescription="用户照片")
    private String userPhoto;

    /**
     * 用户邮箱
     */
    @ColumnAnnotation(FieldName = "user_email",  FieldDescription="用户邮箱")
    private String userEmail;

    /**
     * 用户电话
     */
    @ColumnAnnotation(FieldName = "user_mobile",  FieldDescription="用户电话")
    private String userMobile;

    /**
     * 用户密码
     */
    @ColumnAnnotation(FieldName = "user_password",  FieldDescription="用户密码")
    private String userPassword;

    /**
     * 排序
     */
    @ColumnAnnotation(FieldName = "order_index",  FieldDescription="排序")
    private Integer orderIndex;

    /**
     * 任职机构ID
     */
    @ColumnAnnotation(FieldName = "org_id",  FieldDescription="任职机构ID")
    private String orgId;

    /**
     * 修改用户
     */
    @ColumnAnnotation(FieldName = "modify_user",  FieldDescription="修改用户")
    private String modifyUser;

    /**
     * 修改时间
     */
    @ColumnAnnotation(FieldName = "modify_time",  FieldDescription="修改时间")
    private Date modifyTime;

    /**
     * 
     */
    @ColumnAnnotation(FieldName = "org_name",  FieldDescription="")
    private String orgName;

    /**
     * 有效期(过了此日期不允许登录)
     */
    @ColumnAnnotation(FieldName = "USER_EXPIRE",  FieldDescription="有效期(过了此日期不允许登录)")
    private Date userExpire;

    /**
     * 登录失败次数
     */
    @ColumnAnnotation(FieldName = "USER_LOGINFAILNUM",  FieldDescription="登录失败次数")
    private Integer userLoginfailnum;

    /**
     * 创建人
     */
    @ColumnAnnotation(FieldName = "CREATOR",  FieldDescription="创建人")
    private String creator;

    /**
     * 创建时间
     */
    @ColumnAnnotation(FieldName = "CREATETIME",  FieldDescription="创建时间")
    private Date createtime;

    /**
     * IP地址
     */
    @ColumnAnnotation(FieldName = "IP",  FieldDescription="IP地址")
    private String ip;

    /**
     * 干部信息库A01表主键
     */
    @ColumnAnnotation(FieldName = "A0100",  FieldDescription="干部信息库A01表主键")
    private String a0100;

    /**
     * 户口所在地
     */
    @ColumnAnnotation(FieldName = "Registe_residence",  FieldDescription="户口所在地")
    private String registeResidence;

    /**
     * 身份证签发机关
     */
    @ColumnAnnotation(FieldName = "CARD_ISSUING_AUTHORITY",  FieldDescription="身份证签发机关")
    private String cardIssuingAuthority;

    /**
     * 身份证有效期开始时间
     */
    @ColumnAnnotation(FieldName = "CARE_START_DATE",  FieldDescription="身份证有效期开始时间")
    private Date careStartDate;

    /**
     * 身份证有效期结束时间
     */
    @ColumnAnnotation(FieldName = "CARD_EXPIRY_DATE",  FieldDescription="身份证有效期结束时间")
    private Date cardExpiryDate;

    /**
     * 身份证物理ID
     */
    @ColumnAnnotation(FieldName = "CARD_PHYSICAL_ID",  FieldDescription="身份证物理ID")
    private String cardPhysicalId;

    /**
     * 指纹1(存储指纹BASE64数据)
     */

    @ColumnAnnotation(FieldName = "FINGERPRINT_1",  FieldDescription="指纹1(存储指纹BASE64数据)")
    private String fingerprint1;

    /**
     * 指纹2(存储指纹BASE64数据)
     */

    @ColumnAnnotation(FieldName = "FINGERPRINT_2",  FieldDescription="指纹2(存储指纹BASE64数据)")
    private String fingerprint2;
    /**
     * 身份证号
     */
    @ColumnAnnotation(FieldName = "IDNUMBER",  FieldDescription="身份证号")
    private String idnumber;
    /**
     * 政治面貌
     */
    @ColumnAnnotation(FieldName = "POLITICAL_AFFI",  FieldDescription="政治面貌")
    private String politicalAffi;
    /**
     * 指纹1(存储指纹BASE64数据)
     * @return FINGERPRINT_1 指纹1(存储指纹BASE64数据)
     */
    public String getFingerprint1() {
        return fingerprint1;
    }

    /**
     * 指纹1(存储指纹BASE64数据)
     * @param fingerprint1 指纹1(存储指纹BASE64数据)
     */
    public void setFingerprint1(String fingerprint1) {
        this.fingerprint1 = fingerprint1 == null ? null : fingerprint1.trim();
    }

    /**
     * 指纹2(存储指纹BASE64数据)
     * @return FINGERPRINT_2 指纹2(存储指纹BASE64数据)
     */
    public String getFingerprint2() {
        return fingerprint2;
    }

    /**
     * 指纹2(存储指纹BASE64数据)
     * @param fingerprint2 指纹2(存储指纹BASE64数据)
     */
    public void setFingerprint2(String fingerprint2) {
        this.fingerprint2 = fingerprint2 == null ? null : fingerprint2.trim();
    }

    /**
     * 用户id
     * @return user_id 用户id
     */
    public String getUserId() {
        return userId;
    }

    /**
     * 用户id
     * @param userId 用户id
     */
    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    /**
     * 经办人真实姓名
     * @return user_name 经办人真实姓名
     */
    public String getUserName() {
        return userName;
    }

    /**
     * 经办人真实姓名
     * @param userName 经办人真实姓名
     */
    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    /**
     * 登录名，(建议机构简称+姓名)
     * @return user_code 登录名，(建议机构简称+姓名)
     */
    public String getUserCode() {
        return userCode;
    }

    /**
     * 登录名，(建议机构简称+姓名)
     * @param userCode 登录名，(建议机构简称+姓名)
     */
    public void setUserCode(String userCode) {
        this.userCode = userCode == null ? null : userCode.trim();
    }

    /**
     * 用户类型(1超级管理员、2系统管理员、3安全审计管理员、4安全保密管理员、5监督处工作人员、6经办人、7相关处室、8其他)
     * @return user_type 用户类型(1超级管理员、2系统管理员、3安全审计管理员、4安全保密管理员、5监督处工作人员、6经办人、7相关处室、8其他)
     */
    public String getUserType() {
        return userType;
    }

    /**
     * 用户类型(1超级管理员、2系统管理员、3安全审计管理员、4安全保密管理员、5监督处工作人员、6经办人、7相关处室、8其他)
     * @param userType 用户类型(1超级管理员、2系统管理员、3安全审计管理员、4安全保密管理员、5监督处工作人员、6经办人、7相关处室、8其他)
     */
    public void setUserType(String userType) {
        this.userType = userType == null ? null : userType.trim();
    }

    /**
     * 用户状态(注册0、正常1、撤销2、征求意见3、待审批4、拒绝5、待撤消6、暂停7)
     * @return user_state 用户状态(注册0、正常1、撤销2、征求意见3、待审批4、拒绝5、待撤消6、暂停7)
     */
    public String getUserState() {
        return userState;
    }

    /**
     * 用户状态(注册0、正常1、撤销2、征求意见3、待审批4、拒绝5、待撤消6、暂停7)
     * @param userState 用户状态(注册0、正常1、撤销2、征求意见3、待审批4、拒绝5、待撤消6、暂停7)
     */
    public void setUserState(String userState) {
        this.userState = userState == null ? null : userState.trim();
    }

    /**
     * 用户照片
     * @return user_photo 用户照片
     */
    public String getUserPhoto() {
        return userPhoto;
    }

    /**
     * 用户照片
     * @param userPhoto 用户照片
     */
    public void setUserPhoto(String userPhoto) {
        this.userPhoto = userPhoto == null ? null : userPhoto.trim();
    }

    /**
     * 用户邮箱
     * @return user_email 用户邮箱
     */
    public String getUserEmail() {
        return userEmail;
    }

    /**
     * 用户邮箱
     * @param userEmail 用户邮箱
     */
    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail == null ? null : userEmail.trim();
    }

    /**
     * 用户电话
     * @return user_mobile 用户电话
     */
    public String getUserMobile() {
        return userMobile;
    }

    /**
     * 用户电话
     * @param userMobile 用户电话
     */
    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile == null ? null : userMobile.trim();
    }

    /**
     * 用户密码
     * @return user_password 用户密码
     */
    public String getUserPassword() {
        return userPassword;
    }

    /**
     * 用户密码
     * @param userPassword 用户密码
     */
    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword == null ? null : userPassword.trim();
    }

    /**
     * 排序
     * @return order_index 排序
     */
    public Integer getOrderIndex() {
        return orderIndex;
    }

    /**
     * 排序
     * @param orderIndex 排序
     */
    public void setOrderIndex(Integer orderIndex) {
        this.orderIndex = orderIndex;
    }

    /**
     * 任职机构ID
     * @return org_id 任职机构ID
     */
    public String getOrgId() {
        return orgId;
    }

    /**
     * 任职机构ID
     * @param orgId 任职机构ID
     */
    public void setOrgId(String orgId) {
        this.orgId = orgId == null ? null : orgId.trim();
    }

    /**
     * 修改用户
     * @return modify_user 修改用户
     */
    public String getModifyUser() {
        return modifyUser;
    }

    /**
     * 修改用户
     * @param modifyUser 修改用户
     */
    public void setModifyUser(String modifyUser) {
        this.modifyUser = modifyUser == null ? null : modifyUser.trim();
    }

    /**
     * 修改时间
     * @return modify_time 修改时间
     */
    public Date getModifyTime() {
        return modifyTime;
    }

    /**
     * 修改时间
     * @param modifyTime 修改时间
     */
    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    /**
     * 
     * @return org_name 
     */
    public String getOrgName() {
        return orgName;
    }

    /**
     * 
     * @param orgName 
     */
    public void setOrgName(String orgName) {
        this.orgName = orgName == null ? null : orgName.trim();
    }

    /**
     * 有效期(过了此日期不允许登录)
     * @return USER_EXPIRE 有效期(过了此日期不允许登录)
     */
    public Date getUserExpire() {
        return userExpire;
    }

    /**
     * 有效期(过了此日期不允许登录)
     * @param userExpire 有效期(过了此日期不允许登录)
     */
    public void setUserExpire(Date userExpire) {
        this.userExpire = userExpire;
    }

    /**
     * 登录失败次数
     * @return USER_LOGINFAILNUM 登录失败次数
     */
    public Integer getUserLoginfailnum() {
        return userLoginfailnum;
    }

    /**
     * 登录失败次数
     * @param userLoginfailnum 登录失败次数
     */
    public void setUserLoginfailnum(Integer userLoginfailnum) {
        this.userLoginfailnum = userLoginfailnum;
    }

    /**
     * 创建人
     * @return CREATOR 创建人
     */
    public String getCreator() {
        return creator;
    }

    /**
     * 创建人
     * @param creator 创建人
     */
    public void setCreator(String creator) {
        this.creator = creator == null ? null : creator.trim();
    }

    /**
     * 创建时间
     * @return CREATETIME 创建时间
     */
    public Date getCreatetime() {
        return createtime;
    }

    /**
     * 创建时间
     * @param createtime 创建时间
     */
    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    /**
     * IP地址
     * @return IP IP地址
     */
    public String getIp() {
        return ip;
    }

    /**
     * IP地址
     * @param ip IP地址
     */
    public void setIp(String ip) {
        this.ip = ip == null ? null : ip.trim();
    }

    /**
     * 干部信息库A01表主键
     * @return A0100 干部信息库A01表主键
     */
    public String getA0100() {
        return a0100;
    }

    /**
     * 干部信息库A01表主键
     * @param a0100 干部信息库A01表主键
     */
    public void setA0100(String a0100) {
        this.a0100 = a0100 == null ? null : a0100.trim();
    }

    /**
     * 户口所在地
     * @return Registe_residence 户口所在地
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
     * 身份证签发机关
     * @return CARD_ISSUING_AUTHORITY 身份证签发机关
     */
    public String getCardIssuingAuthority() {
        return cardIssuingAuthority;
    }

    /**
     * 身份证签发机关
     * @param cardIssuingAuthority 身份证签发机关
     */
    public void setCardIssuingAuthority(String cardIssuingAuthority) {
        this.cardIssuingAuthority = cardIssuingAuthority == null ? null : cardIssuingAuthority.trim();
    }

    /**
     * 身份证有效期开始时间
     * @return CARE_START_DATE 身份证有效期开始时间
     */
    public Date getCareStartDate() {
        return careStartDate;
    }

    /**
     * 身份证有效期开始时间
     * @param careStartDate 身份证有效期开始时间
     */
    public void setCareStartDate(Date careStartDate) {
        this.careStartDate = careStartDate;
    }

    /**
     * 身份证有效期结束时间
     * @return CARD_EXPIRY_DATE 身份证有效期结束时间
     */
    public Date getCardExpiryDate() {
        return cardExpiryDate;
    }

    /**
     * 身份证有效期结束时间
     * @param cardExpiryDate 身份证有效期结束时间
     */
    public void setCardExpiryDate(Date cardExpiryDate) {
        this.cardExpiryDate = cardExpiryDate;
    }

    /**
     * 身份证物理ID
     * @return CARD_PHYSICAL_ID 身份证物理ID
     */
    public String getCardPhysicalId() {
        return cardPhysicalId;
    }

    /**
     * 身份证物理ID
     * @param cardPhysicalId 身份证物理ID
     */
    public void setCardPhysicalId(String cardPhysicalId) {
        this.cardPhysicalId = cardPhysicalId == null ? null : cardPhysicalId.trim();
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
     * 政治面貌
     * @return POLITICAL_AFFI 政治面貌
     */
    public String getPoliticalAffi() {
        return politicalAffi;
    }

    /**
     * 政治面貌
     * @param politicalAffi 政治面貌
     */
    public void setPoliticalAffi(String politicalAffi) {
        this.politicalAffi = politicalAffi == null ? null : politicalAffi.trim();
    }
}