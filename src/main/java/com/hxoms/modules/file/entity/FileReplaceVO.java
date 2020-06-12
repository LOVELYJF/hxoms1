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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}