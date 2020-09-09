package com.hxoms.modules.publicity.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 返回前端的人员实体类
 *
 * @author sunqian
 * @date 2020/4/21 17:42
 */
public class PersonInfoVO {
    //人员主键
    private String a0100;
    //姓名
    private String name;
    //性别
    private String sex;
    //出生日期
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date birthDate;
    //职务
    private String job;
    //单位id
    private String b0100;
    //机构id
    private String b0111;
    //单位名称
    private String b0101;
    //状态
    private String status;
    //政治面貌
    private String POLITICAL_AFFI;
    //民族
    private String NATION;
    //身份证号
    private String idnumber;
    //登记备案id
    private String procpersonId;
    //核心涉密人员年审
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date SECRET_REVIEW_DATE;

    public String getA0100() {
        return a0100;
    }

    public void setA0100(String a0100) {
        this.a0100 = a0100;
    }

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

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getB0100() {
        return b0100;
    }

    public void setB0100(String b0100) {
        this.b0100 = b0100;
    }

    public String getB0101() {
        return b0101;
    }

    public void setB0101(String b0101) {
        this.b0101 = b0101;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPOLITICAL_AFFI() {
        return POLITICAL_AFFI;
    }

    public void setPOLITICAL_AFFI(String POLITICAL_AFFI) {
        this.POLITICAL_AFFI = POLITICAL_AFFI;
    }

    public String getNATION() {
        return NATION;
    }

    public void setNATION(String NATION) {
        this.NATION = NATION;
    }

    public String getIdnumber() {
        return idnumber;
    }

    public void setIdnumber(String idnumber) {
        this.idnumber = idnumber;
    }

    public String getB0111() {
        return b0111;
    }

    public void setB0111(String b0111) {
        this.b0111 = b0111;
    }

    public String getProcpersonId() {
        return procpersonId;
    }

    public void setProcpersonId(String procpersonId) {
        this.procpersonId = procpersonId;
    }

    public Date getSECRET_REVIEW_DATE() {
        return SECRET_REVIEW_DATE;
    }

    public void setSECRET_REVIEW_DATE(Date SECRET_REVIEW_DATE) {
        this.SECRET_REVIEW_DATE = SECRET_REVIEW_DATE;
    }
}
