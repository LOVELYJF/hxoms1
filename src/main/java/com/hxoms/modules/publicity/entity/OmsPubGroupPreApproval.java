package com.hxoms.modules.publicity.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hxoms.common.hxannotation.ColumnAnnotation;
import com.hxoms.common.hxannotation.IdAnnotation;
import com.hxoms.common.hxannotation.TableAnnotation;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

@TableAnnotation(TableName = "oms_pub_group_pre_approval", TableDescription="因公出国团体预审批申请表")
public class OmsPubGroupPreApproval {
    @IdAnnotation
    @ColumnAnnotation(FieldName = "ID",   FieldDescription="主键")
    private String id;

    @ColumnAnnotation(FieldName = "B0100",   FieldDescription="申请单位主键")
    private String b0100;

    @ColumnAnnotation(FieldName = "BAZT",   FieldDescription="备案主体1.因公出国（境）教育培训预备案、2.省外办下达任务方式、3.因特殊原因因公赴港澳预审批、4.台办发起的赴台备案流程")
    private Integer bazt;

    @ColumnAnnotation(FieldName = "TZMC",   FieldDescription="团组名称")
    private String tzmc;

    @ColumnAnnotation(FieldName = "TZCY",   FieldDescription="团组成员")
    private String tzcy;

    @ColumnAnnotation(FieldName = "TZFZR",   FieldDescription="团组负责人")
    private String tzfzr;

    @ColumnAnnotation(FieldName = "TZRS",   FieldDescription="团组人数")
    private Integer tzrs;

    @ColumnAnnotation(FieldName = "ZTDW",   FieldDescription="组团单位")
    private String ztdw;

    @ColumnAnnotation(FieldName = "CGSJ",   FieldDescription="出国时间")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date cgsj;

    @ColumnAnnotation(FieldName = "HGSJ",   FieldDescription="回国时间")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date hgsj;

    @ColumnAnnotation(FieldName = "ZWTLSJ",   FieldDescription="在外停留时间")
    private Integer zwtlsj;

    @ColumnAnnotation(FieldName = "SDGJ",   FieldDescription="出访国家或地区")
    private String sdgj;

    @ColumnAnnotation(FieldName = "SDGJTLSJ",   FieldDescription="出访国家或地区停留时间")
    private Integer sdgjtlsj;

    @ColumnAnnotation(FieldName = "TJGJ",   FieldDescription="途径国家或地区")
    private String tjgj;

    @ColumnAnnotation(FieldName = "TJGJTLSJ",   FieldDescription="途径国家或地区停留时间")
    private Integer tjgjtlsj;

    @ColumnAnnotation(FieldName = "CFRW",   FieldDescription="出访任务")
    private String cfrw;

    @ColumnAnnotation(FieldName = "PWH",   FieldDescription="批文号（琼府出[年号][编码]号、琼台出[年号][编码]号、琼港澳出[年号][编码]号）")
    private String pwh;

    @ColumnAnnotation(FieldName = "ZYPWH",   FieldDescription="中央批准文号")
    private String zypwh;

    @ColumnAnnotation(FieldName = "YQDW",   FieldDescription="邀请单位")
    private String yqdw;

    @ColumnAnnotation(FieldName = "FYLYKZXM",   FieldDescription="费用来源开支项目")
    private String fylykzxm;

    @ColumnAnnotation(FieldName = "SOURCE",   FieldDescription="数据来源(0:填写，1：上传)")
    private String source;


    @ColumnAnnotation(FieldName = "CREATE_USER",   FieldDescription="创建人")
    private String createUser;

    @ColumnAnnotation(FieldName = "CREATE_TIME",   FieldDescription="创建时间")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date createTime;

    @ColumnAnnotation(FieldName = "SQZT",   FieldDescription="申请状态(0:撤销,1:上传/填写申请,2:审核备案,3:上传批文)")
    private Integer sqzt;

    @ColumnAnnotation(FieldName = "CFSY",   FieldDescription="出访事由")
    private String cfsy;

    @ColumnAnnotation(FieldName = "CXYY",   FieldDescription="撤销原因")
    private String cxyy;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getB0100() {
        return b0100;
    }

    public void setB0100(String b0100) {
        this.b0100 = b0100 == null ? null : b0100.trim();
    }

    public Integer getBazt() {
        return bazt;
    }

    public void setBazt(Integer bazt) {
        this.bazt = bazt;
    }

    public String getTzmc() {
        return tzmc;
    }

    public void setTzmc(String tzmc) {
        this.tzmc = tzmc == null ? null : tzmc.trim();
    }

    public String getTzcy() {
        return tzcy;
    }

    public void setTzcy(String tzcy) {
        this.tzcy = tzcy;
    }

    public String getTzfzr() {
        return tzfzr;
    }

    public void setTzfzr(String tzfzr) {
        this.tzfzr = tzfzr == null ? null : tzfzr.trim();
    }

    public Integer getTzrs() {
        return tzrs;
    }

    public void setTzrs(Integer tzrs) {
        this.tzrs = tzrs;
    }

    public String getZtdw() {
        return ztdw;
    }

    public void setZtdw(String ztdw) {
        this.ztdw = ztdw == null ? null : ztdw.trim();
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

    public Integer getZwtlsj() {
        return zwtlsj;
    }

    public void setZwtlsj(Integer zwtlsj) {
        this.zwtlsj = zwtlsj;
    }

    public String getSdgj() {
        return sdgj;
    }

    public void setSdgj(String sdgj) {
        this.sdgj = sdgj == null ? null : sdgj.trim();
    }

    public Integer getSdgjtlsj() {
        return sdgjtlsj;
    }

    public void setSdgjtlsj(Integer sdgjtlsj) {
        this.sdgjtlsj = sdgjtlsj;
    }

    public String getTjgj() {
        return tjgj;
    }

    public void setTjgj(String tjgj) {
        this.tjgj = tjgj == null ? null : tjgj.trim();
    }

    public Integer getTjgjtlsj() {
        return tjgjtlsj;
    }

    public void setTjgjtlsj(Integer tjgjtlsj) {
        this.tjgjtlsj = tjgjtlsj;
    }

    public String getCfrw() {
        return cfrw;
    }

    public void setCfrw(String cfrw) {
        this.cfrw = cfrw == null ? null : cfrw.trim();
    }

    public String getPwh() {
        return pwh;
    }

    public void setPwh(String pwh) {
        this.pwh = pwh == null ? null : pwh.trim();
    }

    public String getZypwh() {
        return zypwh;
    }

    public void setZypwh(String zypwh) {
        this.zypwh = zypwh == null ? null : zypwh.trim();
    }

    public String getYqdw() {
        return yqdw;
    }

    public void setYqdw(String yqdw) {
        this.yqdw = yqdw == null ? null : yqdw.trim();
    }

    public String getFylykzxm() {
        return fylykzxm;
    }

    public void setFylykzxm(String fylykzxm) {
        this.fylykzxm = fylykzxm == null ? null : fylykzxm.trim();
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser == null ? null : createUser.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getSqzt() {
        return sqzt;
    }

    public void setSqzt(Integer sqzt) {
        this.sqzt = sqzt;
    }

    public String getCfsy() {
        return cfsy;
    }

    public void setCfsy(String cfsy) {
        this.cfsy = cfsy == null ? null : cfsy.trim();
    }

    public String getCxyy() {
        return cxyy;
    }

    public void setCxyy(String cxyy) {
        this.cxyy = cxyy;
    }
}