package com.hxoms.modules.passportCard.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hxoms.common.hxannotation.ColumnAnnotation;
import com.hxoms.common.hxannotation.IdAnnotation;
import com.hxoms.common.hxannotation.TableAnnotation;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@TableAnnotation(TableName = "cf_certificate", TableDescription="证照管理")
public class CfCertificate {

    /**
     * 主键
     */
    @IdAnnotation
    @ColumnAnnotation(FieldName = "ID",   FieldDescription="主键")
    private String id;
    /**
     * 证件拥有人ID
     */
    @ColumnAnnotation(FieldName = "A0100",   FieldDescription="证件拥有人ID")
    private String a0100;
    /**
     * 国籍
     */
    @ColumnAnnotation(FieldName = "GJ",   FieldDescription="国籍")
    private String gj;
    /**
     * 中文出生地
     */
    @ColumnAnnotation(FieldName = "ZWCSDD",   FieldDescription="中文出生地")
    private String zwcsdd;
    /**
     * 英文出生地
     */
    @ColumnAnnotation(FieldName = "YWCSDD",   FieldDescription="英文出生地")
    private String ywcsdd;
    /**
     * 证件号码
     */
    @ColumnAnnotation(FieldName = "ZJHM",   FieldDescription="证件号码")
    private String zjhm;
    /**
     * 证件类型(1、因公护照  2、因私护照  3、因公港澳证  4、因私港澳证  5、因公赴台证  6、因私赴台证)
     */
    @ColumnAnnotation(FieldName = "ZJLX",   FieldDescription="证件类型")
    private Integer zjlx;
    /**
     * 证件形式
     */
    @ColumnAnnotation(FieldName = "ZJXS",   FieldDescription="证件形式")
    private Integer zjxs;
    /**
     * 存放地址
     */
    @ColumnAnnotation(FieldName = "LOCATION",   FieldDescription="存放地址")
    private String location;
    /**
     * 中文签发地点
     */
    @ColumnAnnotation(FieldName = "ZWQFDD",   FieldDescription="中文签发地点")
    private String zwqfdd;
    /**
     * 英文签发地点
     */
    @ColumnAnnotation(FieldName = "YWQFDD",   FieldDescription="英文签发地点")
    private String ywqfdd;
    /**
     * 中文签发机关
     */
    @ColumnAnnotation(FieldName = "ZWQFJG",   FieldDescription="中文签发机关")
    private String zwqfjg;
    /**
     * 英文签发机关
     */
    @ColumnAnnotation(FieldName = "YWQFJG",   FieldDescription="英文签发机关")
    private String ywqfjg;
    /**
     * 签发日期
     */
    @ColumnAnnotation(FieldName = "QFRQ",   FieldDescription="签发日期")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date qfrq;
    /**
     * 有效期至
     */
    @ColumnAnnotation(FieldName = "YXQZ",   FieldDescription="有效期至")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date yxqz;
    /**
     * 证件上交日期
     */
    @ColumnAnnotation(FieldName = "ZJSJRQ",   FieldDescription="证件上交日期")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date zjsjrq;
    /**
     * 证件当前状态
     */
    @ColumnAnnotation(FieldName = "DQZZ",   FieldDescription="证件当前状态")
    private Integer dqzz;
    /**
     * 人员是否有效
     */
    @ColumnAnnotation(FieldName = "ISCABINET",   FieldDescription="人员是否有效")
    private Integer isCabinet;

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

    public String getGj() {
        return gj;
    }

    public void setGj(String gj) {
        this.gj = gj;
    }

    public String getZwcsdd() {
        return zwcsdd;
    }

    public void setZwcsdd(String zwcsdd) {
        this.zwcsdd = zwcsdd;
    }

    public String getYwcsdd() {
        return ywcsdd;
    }

    public void setYwcsdd(String ywcsdd) {
        this.ywcsdd = ywcsdd;
    }

    public String getZjhm() {
        return zjhm;
    }

    public void setZjhm(String zjhm) {
        this.zjhm = zjhm;
    }

    public Integer getZjlx() {
        return zjlx;
    }

    public void setZjlx(Integer zjlx) {
        this.zjlx = zjlx;
    }

    public Integer getZjxs() {
        return zjxs;
    }

    public void setZjxs(Integer zjxs) {
        this.zjxs = zjxs;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getZwqfdd() {
        return zwqfdd;
    }

    public void setZwqfdd(String zwqfdd) {
        this.zwqfdd = zwqfdd;
    }

    public String getYwqfdd() {
        return ywqfdd;
    }

    public void setYwqfdd(String ywqfdd) {
        this.ywqfdd = ywqfdd;
    }

    public String getZwqfjg() {
        return zwqfjg;
    }

    public void setZwqfjg(String zwqfjg) {
        this.zwqfjg = zwqfjg;
    }

    public String getYwqfjg() {
        return ywqfjg;
    }

    public void setYwqfjg(String ywqfjg) {
        this.ywqfjg = ywqfjg;
    }

    public Date getQfrq() {
        return qfrq;
    }

    public void setQfrq(Date qfrq) {
        this.qfrq = qfrq;
    }

    public Date getYxqz() {
        return yxqz;
    }

    public void setYxqz(Date yxqz) {
        this.yxqz = yxqz;
    }

    public Date getZjsjrq() {
        return zjsjrq;
    }

    public void setZjsjrq(Date zjsjrq) {
        this.zjsjrq = zjsjrq;
    }

    public Integer getDqzz() {
        return dqzz;
    }

    public void setDqzz(Integer dqzz) {
        this.dqzz = dqzz;
    }

    public Integer getIsCabinet() {
        return isCabinet;
    }

    public void setIsCabinet(Integer isCabinet) {
        this.isCabinet = isCabinet;
    }
}





































































