package com.hxoms.modules.privateabroad.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hxoms.modules.country.entity.Country;
import com.hxoms.modules.omsregcadre.entity.OmsEntryexitRecord;
import com.hxoms.modules.omssmrperson.entity.OmsSmrOldInfoVO;
import com.hxoms.modules.passportCard.initialise.entity.CfCertificate;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;
import java.util.Map;

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
    private List<OmsSmrOldInfoVO> omsSmrOldInfoVOS;
    //证照信息
    private List<CfCertificate> cfCertificates;
    //约束条件
    private List<Map<String, String>> condition;
    //出入境记录列表
    private List<OmsEntryexitRecord> entryexitRecordList;
    //出入境记录
    private OmsEntryexitRecord entryexitRecord;
    //国家列表
    private List<Country> countries;
    //实际去往国家
    private List<Country> realCountries;
    //实际途径国家
    private List<Country> realPassCountries;
    //所到国家
    private String sdgj;

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

    public List<CfCertificate> getCfCertificates() {
        return cfCertificates;
    }

    public void setCfCertificates(List<CfCertificate> cfCertificates) {
        this.cfCertificates = cfCertificates;
    }

    public List<OmsSmrOldInfoVO> getOmsSmrOldInfoVOS() {
        return omsSmrOldInfoVOS;
    }

    public void setOmsSmrOldInfoVOS(List<OmsSmrOldInfoVO> omsSmrOldInfoVOS) {
        this.omsSmrOldInfoVOS = omsSmrOldInfoVOS;
    }

    public List<Map<String, String>> getCondition() {
        return condition;
    }

    public void setCondition(List<Map<String, String>> condition) {
        this.condition = condition;
    }

    public List<OmsEntryexitRecord> getEntryexitRecordList() {
        return entryexitRecordList;
    }

    public void setEntryexitRecordList(List<OmsEntryexitRecord> entryexitRecordList) {
        this.entryexitRecordList = entryexitRecordList;
    }

    public OmsEntryexitRecord getEntryexitRecord() {
        return entryexitRecord;
    }

    public void setEntryexitRecord(OmsEntryexitRecord entryexitRecord) {
        this.entryexitRecord = entryexitRecord;
    }

    public List<Country> getCountries() {
        return countries;
    }

    public void setCountries(List<Country> countries) {
        this.countries = countries;
    }

    public List<Country> getRealCountries() {
        return realCountries;
    }

    public void setRealCountries(List<Country> realCountries) {
        this.realCountries = realCountries;
    }

    public List<Country> getRealPassCountries() {
        return realPassCountries;
    }

    public void setRealPassCountries(List<Country> realPassCountries) {
        this.realPassCountries = realPassCountries;
    }

    public String getSdgj() {
        return sdgj;
    }

    public void setSdgj(String sdgj) {
        this.sdgj = sdgj;
    }
}
