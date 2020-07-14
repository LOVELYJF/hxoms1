package com.hxoms.modules.publicity.taskSupervise.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @Desc：组团单位人员任务监管
 * @Author: wangyunquan
 * @Date: 2020/6/23
 */
public class ZtDwPersionVO {
    //因公出国备案申请id
    private String id;
    //审批状态
    private String sqzt;
    //团组名称
    private String ztdw;
    //审批单位
    private String cgspdw;
    //工作单位
    private String workUnit;
    //姓名
    private String name;
    //性别
    private String sex;
    //出生日期
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date birthDate;
    //政治面貌
    private String politicalAffiName;
    //职务
    private String post;
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    //出境日期
    private String cgsj;
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    //入境日期
    private String hgsj;
    //目的地
    private String sdgj;
    //出访任务
    private String cfrw;
    //备案号
    private String bah;
    //涉密登记
    private String smdj;
    //主要领导
    private String mainLeader;
    //是否变更
    private String sfbg;
    //最终结论
    private String zzjl;
    //批文号
    private String pwh;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSqzt() {
        return sqzt;
    }

    public void setSqzt(String sqzt) {
        this.sqzt = sqzt;
    }

    public String getZtdw() {
        return ztdw;
    }

    public void setZtdw(String ztdw) {
        this.ztdw = ztdw;
    }

    public String getCgspdw() {
        return cgspdw;
    }

    public void setCgspdw(String cgspdw) {
        this.cgspdw = cgspdw;
    }

    public String getWorkUnit() {
        return workUnit;
    }

    public void setWorkUnit(String workUnit) {
        this.workUnit = workUnit;
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

    public String getPoliticalAffiName() {
        return politicalAffiName;
    }

    public void setPoliticalAffiName(String politicalAffiName) {
        this.politicalAffiName = politicalAffiName;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getCgsj() {
        return cgsj;
    }

    public void setCgsj(String cgsj) {
        this.cgsj = cgsj;
    }

    public String getHgsj() {
        return hgsj;
    }

    public void setHgsj(String hgsj) {
        this.hgsj = hgsj;
    }

    public String getSdgj() {
        return sdgj;
    }

    public void setSdgj(String sdgj) {
        this.sdgj = sdgj;
    }

    public String getCfrw() {
        return cfrw;
    }

    public void setCfrw(String cfrw) {
        this.cfrw = cfrw;
    }

    public String getBah() {
        return bah;
    }

    public void setBah(String bah) {
        this.bah = bah;
    }

    public String getSmdj() {
        return smdj;
    }

    public void setSmdj(String smdj) {
        this.smdj = smdj;
    }

    public String getMainLeader() {
        return mainLeader;
    }

    public void setMainLeader(String mainLeader) {
        this.mainLeader = mainLeader;
    }

    public String getSfbg() {
        return sfbg;
    }

    public void setSfbg(String sfbg) {
        this.sfbg = sfbg;
    }

    public String getZzjl() {
        return zzjl;
    }

    public void setZzjl(String zzjl) {
        this.zzjl = zzjl;
    }

    public String getPwh() {
        return pwh;
    }

    public void setPwh(String pwh) {
        this.pwh = pwh;
    }
}
