package com.hxoms.modules.privateabroad.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
 * @desc: 因私出国境申请扩展类
 * @author: lijing
 * @date: 2020-05-14
 */
public class OmsPriApplyVO extends OmsPriApply{
    //姓名
    private String name;
    //性别
    private String sex;
    //出生日期
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    @JsonFormat(pattern = "yyyy.MM.dd", timezone = "GMT+8")
    private Date birthDate;
    //身份证号
    private String idnumber;
    //户籍所在地
    private String registeResidence;
    //工作单位
    private String b0101;
    //身份类别
    private String identifyType;
    //随行人员
    private List<OmsPriTogetherperson> omsPriTogetherpeoples;
    //原涉密信息

    //证照信息

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

    public String getB0101() {
        return b0101;
    }

    public void setB0101(String b0101) {
        this.b0101 = b0101;
    }

    public String getIdentifyType() {
        return identifyType;
    }

    public void setIdentifyType(String identifyType) {
        this.identifyType = identifyType;
    }

    public List<OmsPriTogetherperson> getOmsPriTogetherpeoples() {
        return omsPriTogetherpeoples;
    }

    public void setOmsPriTogetherpeoples(List<OmsPriTogetherperson> omsPriTogetherpeoples) {
        this.omsPriTogetherpeoples = omsPriTogetherpeoples;
    }
}