package com.hxoms.modules.privateabroad.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hxoms.common.hxannotation.ColumnAnnotation;
import com.hxoms.common.hxannotation.IdAnnotation;
import com.hxoms.common.hxannotation.TableAnnotation;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class OmsPriPubApply {
    @IdAnnotation
    @ColumnAnnotation(FieldName = "ID",   FieldDescription="主键")
    private String id;

    @ColumnAnnotation(FieldName = "A0100",   FieldDescription="干部id")
    private String a0100;

    @ColumnAnnotation(FieldName = "CGSJ",   FieldDescription="出国时间")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date cgsj;

    @ColumnAnnotation(FieldName = "HGSJ",   FieldDescription="回国时间")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date hgsj;

    @ColumnAnnotation(FieldName = "SDGJ",   FieldDescription="出访国家")
    private String sdgj;

    @ColumnAnnotation(FieldName = "APPLY_TIME",   FieldDescription="申请时间")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date applyTime;

    @ColumnAnnotation(FieldName = "PAPER",   FieldDescription="证件类型（护照1、港澳通行证2、台湾通行证4 ）")
    private Integer paper;

    @ColumnAnnotation(FieldName = "REAL_GO_COUNTRY",   FieldDescription="实际出访国家")
    private String realGoCountry;

    @ColumnAnnotation(FieldName = "SJCGSJ",   FieldDescription="实际出国时间")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date sjcgsj;

    @ColumnAnnotation(FieldName = "SJHGSJ",   FieldDescription="实际回国时间")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date sjhgsj;

    @ColumnAnnotation(FieldName = "IS_ABROAD",   FieldDescription="是否出入境")
    private String isAbroad;

    @ColumnAnnotation(FieldName = "PASSPORT",   FieldDescription="护照(1申领新证、2换发、3失效申领、4签注)")
    private Integer passport;

    @ColumnAnnotation(FieldName = "PASSPORT_NUM",   FieldDescription="护照号")
    private String passportNum;

    @ColumnAnnotation(FieldName = "HONGKONGANDMACAO_PASSPORT",   FieldDescription="港澳通行证(1申领新证、2换发、3失效申领、4签注)")
    private Integer hongkongandmacaoPassport;

    @ColumnAnnotation(FieldName = "HONGKONGANDMACAO_PASSPORT",   FieldDescription="港澳通行证号")
    private String hongkongandmacaoPassportNum;

    @ColumnAnnotation(FieldName = "TAIWAN_PASSPORT",   FieldDescription="台湾通行证(1申领新证、2换发、3失效申领、4签注)")
    private Integer taiwanPassport;

    @ColumnAnnotation(FieldName = "TAIWAN_PASSPORT_NUM",   FieldDescription="台湾通行证号")
    private String taiwanPassportNum;

    @ColumnAnnotation(FieldName = "IS_COMPARISON",   FieldDescription="是否已比对(1是、0否    是否与出入境管理局提供的出入境记录进行过比对)")
    private String isComparison;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getA0100() {
        return a0100;
    }

    public void setA0100(String a0100) {
        this.a0100 = a0100;
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

    public Date getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(Date applyTime) {
        this.applyTime = applyTime;
    }

    public Integer getPaper() {
        return paper;
    }

    public void setPaper(Integer paper) {
        this.paper = paper;
    }

    public String getRealGoCountry() {
        return realGoCountry;
    }

    public void setRealGoCountry(String realGoCountry) {
        this.realGoCountry = realGoCountry;
    }

    public Date getSjcgsj() {
        return sjcgsj;
    }

    public void setSjcgsj(Date sjcgsj) {
        this.sjcgsj = sjcgsj;
    }

    public Date getSjhgsj() {
        return sjhgsj;
    }

    public void setSjhgsj(Date sjhgsj) {
        this.sjhgsj = sjhgsj;
    }

    public String getIsAbroad() {
        return isAbroad;
    }

    public void setIsAbroad(String isAbroad) {
        this.isAbroad = isAbroad;
    }

    public Integer getPassport() {
        return passport;
    }

    public void setPassport(Integer passport) {
        this.passport = passport;
    }

    public String getPassportNum() {
        return passportNum;
    }

    public void setPassportNum(String passportNum) {
        this.passportNum = passportNum;
    }

    public Integer getHongkongandmacaoPassport() {
        return hongkongandmacaoPassport;
    }

    public void setHongkongandmacaoPassport(Integer hongkongandmacaoPassport) {
        this.hongkongandmacaoPassport = hongkongandmacaoPassport;
    }

    public String getHongkongandmacaoPassportNum() {
        return hongkongandmacaoPassportNum;
    }

    public void setHongkongandmacaoPassportNum(String hongkongandmacaoPassportNum) {
        this.hongkongandmacaoPassportNum = hongkongandmacaoPassportNum;
    }

    public Integer getTaiwanPassport() {
        return taiwanPassport;
    }

    public void setTaiwanPassport(Integer taiwanPassport) {
        this.taiwanPassport = taiwanPassport;
    }

    public String getTaiwanPassportNum() {
        return taiwanPassportNum;
    }

    public void setTaiwanPassportNum(String taiwanPassportNum) {
        this.taiwanPassportNum = taiwanPassportNum;
    }

    public String getIsComparison() {
        return isComparison;
    }

    public void setIsComparison(String isComparison) {
        this.isComparison = isComparison;
    }
}