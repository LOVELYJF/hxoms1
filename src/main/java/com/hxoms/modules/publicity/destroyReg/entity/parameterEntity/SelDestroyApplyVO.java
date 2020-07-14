package com.hxoms.modules.publicity.destroyReg.entity.parameterEntity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @Desc：返回撤销申请数据实体
 * @Author: wangyunquan
 * @Date: 2020/7/2
 */
public class SelDestroyApplyVO {
    //id
    private String id;
    //团组名称
    private String ztdw;
    //审批单位
    private String cgspdw;
    //工作单位
    private String workUnit;
    //姓名
    private String name;
    //出境日期
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date cgsj;
    //入境日期
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date hgsj;
    //目的地
    private String sdgj;
    //出访任务
    private String cfrw;
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
    //申请日期
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date createTime;
    //裸官
    private String sflg;
    //涉密等级
    private String smdj;
    //主要领导
    private String mainLeader;
    //负面信息
    private String fmxx;
    //材料审核结论
    private String clshsftg;
    //省纪委结论
    private String jwjl;
    //最终结论
    private String zzjl;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public Date getCgsj() {
        return cgsj;
    }

    public void setCgsj(Date cgsj) {
        this.cgsj = cgsj;
    }

    public Date getHgsj() {
        return hgsj;
    }

    public void setHgsj(Date hgsj) {
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getSflg() {
        return sflg;
    }

    public void setSflg(String sflg) {
        this.sflg = sflg;
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

    public String getFmxx() {
        return fmxx;
    }

    public void setFmxx(String fmxx) {
        this.fmxx = fmxx;
    }

    public String getClshsftg() {
        return clshsftg;
    }

    public void setClshsftg(String clshsftg) {
        this.clshsftg = clshsftg;
    }

    public String getJwjl() {
        return jwjl;
    }

    public void setJwjl(String jwjl) {
        this.jwjl = jwjl;
    }

    public String getZzjl() {
        return zzjl;
    }

    public void setZzjl(String zzjl) {
        this.zzjl = zzjl;
    }
}
