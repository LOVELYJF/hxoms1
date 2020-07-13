package com.hxoms.modules.publicity.docCallback.entity.parameterEntity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @Desc：返回可回收登记申请数据实体
 * @Author: wangyunquan
 * @Date: 2020/7/7
 */
public class SelCanCallbApplyVO {
    //团组名称
    private String ztdw;
    //审批单位
    private String cgspdw;
    //工作单位
    private String work_Unit;
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
    private Date birth_Date;
    //政治面貌
    private String political_AffiName;
    //职务
    private String post;
    //申请日期
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date create_Time;
    //裸官
    private String sflg;
    //涉密等级
    private String smdj;
    //主要领导
    private String main_Leader;
    //负面信息
    private String fmxx;
    //材料审核结论
    private String clshsftg;
    //省纪委结论
    private String jwjl;
    //最终结论
    private String zzjl;

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

    public String getWork_Unit() {
        return work_Unit;
    }

    public void setWork_Unit(String work_Unit) {
        this.work_Unit = work_Unit;
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

    public Date getBirth_Date() {
        return birth_Date;
    }

    public void setBirth_Date(Date birth_Date) {
        this.birth_Date = birth_Date;
    }

    public String getPolitical_AffiName() {
        return political_AffiName;
    }

    public void setPolitical_AffiName(String political_AffiName) {
        this.political_AffiName = political_AffiName;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public Date getCreate_Time() {
        return create_Time;
    }

    public void setCreate_Time(Date create_Time) {
        this.create_Time = create_Time;
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

    public String getMain_Leader() {
        return main_Leader;
    }

    public void setMain_Leader(String main_Leader) {
        this.main_Leader = main_Leader;
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
