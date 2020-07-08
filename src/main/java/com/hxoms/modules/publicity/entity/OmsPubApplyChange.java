package com.hxoms.modules.publicity.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hxoms.common.hxannotation.ColumnAnnotation;
import com.hxoms.common.hxannotation.IdAnnotation;
import com.hxoms.common.hxannotation.TableAnnotation;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

@TableAnnotation(TableName = "oms_pub_apply_change", TableDescription="因公出国境备案变更表")
public class OmsPubApplyChange {
    @IdAnnotation
    @ColumnAnnotation(FieldName = "ID",   FieldDescription="变更表主键")
    private String id;

    @ColumnAnnotation(FieldName = "BA_ID",   FieldDescription="备案表主键")
    private String baId;

    @ColumnAnnotation(FieldName = "TT_ID",   FieldDescription="存团体预审批申请表主键，不会与备案主键同时存在。")
    private String ttId;

    @ColumnAnnotation(FieldName = "YCGSJ",   FieldDescription="原出访时间")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date ycgsj;

    @ColumnAnnotation(FieldName = "YHGSJ",   FieldDescription="原入境时间")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date yhgsj;

    @ColumnAnnotation(FieldName = "YSDGJ",   FieldDescription="原出访国")
    private String ysdgj;

    @ColumnAnnotation(FieldName = "YTJGJ",   FieldDescription="原途经国")
    private String ytjgj;

    @ColumnAnnotation(FieldName = "YCFSY",   FieldDescription="原事由")
    private String ycfsy;

    @ColumnAnnotation(FieldName = "XCGSJ",   FieldDescription="现出访时间")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date xcgsj;

    @ColumnAnnotation(FieldName = "XHGSJ",   FieldDescription="现入境时间")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date xhgsj;

    @ColumnAnnotation(FieldName = "XSDGJ",   FieldDescription="现出访国")
    private String xsdgj;

    @ColumnAnnotation(FieldName = "XTJGJ",   FieldDescription="现途径国")
    private String xtjgj;

    @ColumnAnnotation(FieldName = "XCFSY",   FieldDescription="现事由")
    private String xcfsy;

    @ColumnAnnotation(FieldName = "MODIFY_USER",   FieldDescription="变更人")
    private String modifyUser;

    @ColumnAnnotation(FieldName = "MODIFY_TIME",   FieldDescription="变更时间")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date modifyTime;

    @ColumnAnnotation(FieldName = "CHECK_PERSON",   FieldDescription="审批人")
    private String checkPerson;

    @ColumnAnnotation(FieldName = "CHECK_TIME",   FieldDescription="审批时间")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date checkTime;

    @ColumnAnnotation(FieldName = "SQZT",   FieldDescription="申请状态")
    private Integer sqzt;

    @ColumnAnnotation(FieldName = "YCFRW",   FieldDescription="原出访任务")
    private String ycfrw;

    @ColumnAnnotation(FieldName = "XCFRW",   FieldDescription="现出访任务")
    private String xcfrw;

    @ColumnAnnotation(FieldName = "BGYY",   FieldDescription="变更原因")
    private String bgyy;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getBaId() {
        return baId;
    }

    public void setBaId(String baId) {
        this.baId = baId == null ? null : baId.trim();
    }

    public String getTtId() {
        return ttId;
    }

    public void setTtId(String ttId) {
        this.ttId = ttId == null ? null : ttId.trim();
    }

    public Date getYcgsj() {
        return ycgsj;
    }

    public void setYcgsj(Date ycgsj) {
        this.ycgsj = ycgsj;
    }

    public Date getYhgsj() {
        return yhgsj;
    }

    public void setYhgsj(Date yhgsj) {
        this.yhgsj = yhgsj;
    }

    public String getYsdgj() {
        return ysdgj;
    }

    public void setYsdgj(String ysdgj) {
        this.ysdgj = ysdgj == null ? null : ysdgj.trim();
    }

    public String getYtjgj() {
        return ytjgj;
    }

    public void setYtjgj(String ytjgj) {
        this.ytjgj = ytjgj == null ? null : ytjgj.trim();
    }

    public String getYcfsy() {
        return ycfsy;
    }

    public void setYcfsy(String ycfsy) {
        this.ycfsy = ycfsy == null ? null : ycfsy.trim();
    }

    public Date getXcgsj() {
        return xcgsj;
    }

    public void setXcgsj(Date xcgsj) {
        this.xcgsj = xcgsj;
    }

    public Date getXhgsj() {
        return xhgsj;
    }

    public void setXhgsj(Date xhgsj) {
        this.xhgsj = xhgsj;
    }

    public String getXsdgj() {
        return xsdgj;
    }

    public void setXsdgj(String xsdgj) {
        this.xsdgj = xsdgj == null ? null : xsdgj.trim();
    }

    public String getXtjgj() {
        return xtjgj;
    }

    public void setXtjgj(String xtjgj) {
        this.xtjgj = xtjgj == null ? null : xtjgj.trim();
    }

    public String getXcfsy() {
        return xcfsy;
    }

    public void setXcfsy(String xcfsy) {
        this.xcfsy = xcfsy == null ? null : xcfsy.trim();
    }

    public String getModifyUser() {
        return modifyUser;
    }

    public void setModifyUser(String modifyUser) {
        this.modifyUser = modifyUser == null ? null : modifyUser.trim();
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getCheckPerson() {
        return checkPerson;
    }

    public void setCheckPerson(String checkPerson) {
        this.checkPerson = checkPerson == null ? null : checkPerson.trim();
    }

    public Date getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(Date checkTime) {
        this.checkTime = checkTime;
    }

    public Integer getSqzt() {
        return sqzt;
    }

    public void setSqzt(Integer sqzt) {
        this.sqzt = sqzt;
    }

    public String getYcfrw() {
        return ycfrw;
    }

    public void setYcfrw(String ycfrw) {
        this.ycfrw = ycfrw == null ? null : ycfrw.trim();
    }

    public String getXcfrw() {
        return xcfrw;
    }

    public void setXcfrw(String xcfrw) {
        this.xcfrw = xcfrw == null ? null : xcfrw.trim();
    }

    public String getBgyy() {
        return bgyy;
    }

    public void setBgyy(String bgyy) {
        this.bgyy = bgyy == null ? null : bgyy.trim();
    }
}