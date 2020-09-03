package com.hxoms.modules.file.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @desc: 约束条件替换实体类
 * @author: lijing
 * @date: 2020-06-12
 */
public class FileReplaceVO {
    //姓名
    private String name;
    //性别
    private String sex;
    //政治面貌
    private String politicalAffi;
    //出生日期
    private Date birthDate;
    //健康状况
    private String health;
    //身份证号码
    private String idnumber;
    //户籍地
    private String registeResidence;
    //涉密等级
    private String secretLevel;
    //出境时间
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date abroadTime;
    //入境时间
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date returnTime;
    //国家地区
    private String goCountry;
    //停留时间
    private Integer outsideTime;
    //出国（境）事由
    private String abroadReasons;
    //当前时间
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date nowTime;
    //职务职级
    private String postrank;
    //费用来源
    private String fundsSource;
    //联系人
    private String contactPerson;
    //联系电话
    private String contactPhone;
    //工作单位
    private String b0101;
    //批件文号
    private String approvalNo;
    //预计证照归还时间
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date revertLicenceTime;
    //当前用户
    private String nowUsername;
    //延期时间
    private Date delayTime;

    // 出访任务
    private String cfrw;

    // 批文号
    private String pwh;

    //审批表备注
    private String remarks;

    //发生地
    private String appendPlace;

    //申请证照信息
    private String applyCerInfo;

    //注销原因
    private String zxyy;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPoliticalAffi() {
        return politicalAffi;
    }

    public void setPoliticalAffi(String politicalAffi) {
        this.politicalAffi = politicalAffi;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getHealth() {
        return health;
    }

    public void setHealth(String health) {
        this.health = health;
    }

    public String getIdnumber() {
        return idnumber;
    }

    public void setIdnumber(String idnumber) {
        this.idnumber = idnumber;
    }

    public String getRegisteResidence() {
        return registeResidence;
    }

    public void setRegisteResidence(String registeResidence) {
        this.registeResidence = registeResidence;
    }

    public String getSecretLevel() {
        return secretLevel;
    }

    public void setSecretLevel(String secretLevel) {
        this.secretLevel = secretLevel;
    }

    public Date getAbroadTime() {
        return abroadTime;
    }

    public void setAbroadTime(Date abroadTime) {
        this.abroadTime = abroadTime;
    }

    public Date getReturnTime() {
        return returnTime;
    }

    public void setReturnTime(Date returnTime) {
        this.returnTime = returnTime;
    }

    public String getGoCountry() {
        return goCountry;
    }

    public void setGoCountry(String goCountry) {
        this.goCountry = goCountry;
    }

    public Integer getOutsideTime() {
        return outsideTime;
    }

    public void setOutsideTime(Integer outsideTime) {
        this.outsideTime = outsideTime;
    }

    public String getAbroadReasons() {
        return abroadReasons;
    }

    public void setAbroadReasons(String abroadReasons) {
        this.abroadReasons = abroadReasons;
    }

    public Date getNowTime() {
        return nowTime;
    }

    public void setNowTime(Date nowTime) {
        this.nowTime = nowTime;
    }

    public String getPostrank() {
        return postrank;
    }

    public void setPostrank(String postrank) {
        this.postrank = postrank;
    }

    public String getFundsSource() {
        return fundsSource;
    }

    public void setFundsSource(String fundsSource) {
        this.fundsSource = fundsSource;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getB0101() {
        return b0101;
    }

    public void setB0101(String b0101) {
        this.b0101 = b0101;
    }

    public String getApprovalNo() {
        return approvalNo;
    }

    public void setApprovalNo(String approvalNo) {
        this.approvalNo = approvalNo;
    }

    public Date getRevertLicenceTime() {
        return revertLicenceTime;
    }

    public void setRevertLicenceTime(Date revertLicenceTime) {
        this.revertLicenceTime = revertLicenceTime;
    }

    public String getNowUsername() {
        return nowUsername;
    }

    public void setNowUsername(String nowUsername) {
        this.nowUsername = nowUsername;
    }

    public Date getDelayTime() {
        return delayTime;
    }

    public void setDelayTime(Date delayTime) {
        this.delayTime = delayTime;
    }

    public String getCfrw() {
        return cfrw;
    }

    public void setCfrw(String cfrw) {
        this.cfrw = cfrw;
    }

    public String getPwh() {
        return pwh;
    }

    public void setPwh(String pwh) {
        this.pwh = pwh;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getAppendPlace() {
        return appendPlace;
    }

    public void setAppendPlace(String appendPlace) {
        this.appendPlace = appendPlace;
    }

    public String getApplyCerInfo() {
        return applyCerInfo;
    }

    public void setApplyCerInfo(String applyCerInfo) {
        this.applyCerInfo = applyCerInfo;
    }

    public String getZxyy() {
        return zxyy;
    }

    public void setZxyy(String zxyy) {
        this.zxyy = zxyy;
    }
}
