package com.hxoms.modules.publicity.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class OmsPubApplyVO extends OmsPubApply {
    private String name;

    @DateTimeFormat(pattern = "yyyy.MM.dd")
    @JsonFormat(pattern = "yyyy.MM.dd", timezone = "GMT+8")
    private Date birthDate;

    private String sex;

    private String b0101;

    private String idnumber;
    //在职状态
    private String status;
    //校验结果
    private String checkResult;
    //人员状态
    private String applyStatus;
    //涉密等級
    private String SECRET_LEVEL;
    //核心涉密人员年审
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date SECRET_REVIEW_DATE;
    //健康情况
    private String HEALTH;
    //户口所在地
    private String REGISTE_RESIDENCE;

    public String getB0101() {
        return b0101;
    }

    public void setB0101(String b0101) {
        this.b0101 = b0101;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdnumber() {
        return idnumber;
    }

    public void setIdnumber(String idnumber) {
        this.idnumber = idnumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCheckResult() {
        return checkResult;
    }

    public void setCheckResult(String checkResult) {
        this.checkResult = checkResult;
    }

    public String getApplyStatus() {
        return applyStatus;
    }

    public void setApplyStatus(String applyStatus) {
        this.applyStatus = applyStatus;
    }

    public String getSECRET_LEVEL() {
        return SECRET_LEVEL;
    }

    public void setSECRET_LEVEL(String SECRET_LEVEL) {
        this.SECRET_LEVEL = SECRET_LEVEL;
    }

    public Date getSECRET_REVIEW_DATE() {
        return SECRET_REVIEW_DATE;
    }

    public void setSECRET_REVIEW_DATE(Date SECRET_REVIEW_DATE) {
        this.SECRET_REVIEW_DATE = SECRET_REVIEW_DATE;
    }

    public String getHEALTH() {
        return HEALTH;
    }

    public void setHEALTH(String HEALTH) {
        this.HEALTH = HEALTH;
    }

    public String getREGISTE_RESIDENCE() {
        return REGISTE_RESIDENCE;
    }

    public void setREGISTE_RESIDENCE(String REGISTE_RESIDENCE) {
        this.REGISTE_RESIDENCE = REGISTE_RESIDENCE;
    }
}
