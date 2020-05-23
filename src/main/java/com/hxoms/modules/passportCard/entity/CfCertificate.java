package com.hxoms.modules.passportCard.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hxoms.common.hxannotation.ColumnAnnotation;
import com.hxoms.common.hxannotation.IdAnnotation;
import com.hxoms.common.hxannotation.TableAnnotation;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

@TableAnnotation(TableName = "cf_certificate", TableDescription="证件信息表")
public class CfCertificate {
    @IdAnnotation
    @ColumnAnnotation(FieldName = "ID",   FieldDescription="ID")
    private String id;

    @ColumnAnnotation(FieldName = "A0184",   FieldDescription="证件拥有者身份证号码")
    private String a0184;

    @ColumnAnnotation(FieldName = "A0100",   FieldDescription="证件拥有者ID")
    private String a0100;

    @ColumnAnnotation(FieldName = "GJ",   FieldDescription="国籍")
    private String gj;

    @ColumnAnnotation(FieldName = "ZWCSDD",   FieldDescription="中文出生地点")
    private String zwcsdd;

    @ColumnAnnotation(FieldName = "YWCSDD",   FieldDescription="英文出生地点")
    private String ywcsdd;

    @ColumnAnnotation(FieldName = "ZJHM",   FieldDescription="证件号码")
    private String zjhm;

    @ColumnAnnotation(FieldName = "ZJLX",   FieldDescription="证件类型(1、因公护照  2、因私护照  3、因公港澳证  4、因私港澳证  5、因公赴台证  6、因私赴台证)")
    private Integer zjlx;

    @ColumnAnnotation(FieldName = "ZJXS",   FieldDescription="证件形式")
    private String zjxs;

    @ColumnAnnotation(FieldName = "LOCATION",   FieldDescription="证照存放地址")
    private String location;

    @ColumnAnnotation(FieldName = "ZWQFDD",   FieldDescription="中文签发地点")
    private String zwqfdd;

    @ColumnAnnotation(FieldName = "YWQFDD",   FieldDescription="英文签发地点")
    private String ywqfdd;

    @ColumnAnnotation(FieldName = "ZWQFJG",   FieldDescription="中文签发机关")
    private String zwqfjg;

    @ColumnAnnotation(FieldName = "YWQFJG",   FieldDescription="英文签发机关")
    private String ywqfjg;

    @ColumnAnnotation(FieldName = "QFRQ",   FieldDescription="签发日期")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date qfrq;

    @ColumnAnnotation(FieldName = "YXQZ",   FieldDescription="有效期至")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date yxqz;

    @ColumnAnnotation(FieldName = "ZJSJRQ",   FieldDescription="证件上缴日期")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date zjsjrq;

    @ColumnAnnotation(FieldName = "DQZT",   FieldDescription="当前状态(1、初次录入 2、入柜未确认 3、在库  4、借出未出柜 5、借出出柜未确认  6、借出  7、归还未入柜  8、归还入柜未确认   9、盘点未取出 ，10、盘点取出未确认 11 、盘出   12、盘点未入柜  13 盘点入柜未确认，14永久取出)")
    private Integer dqzt;

    @ColumnAnnotation(FieldName = "ISCABINET",   FieldDescription="人员是否有效，1，有效，0，无效")
    private Integer iscabinet;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getA0100() {
        return a0100;
    }

    public void setA0100(String a0100) {
        this.a0100 = a0100 == null ? null : a0100.trim();
    }

    public String getGj() {
        return gj;
    }

    public void setGj(String gj) {
        this.gj = gj == null ? null : gj.trim();
    }

    public String getZwcsdd() {
        return zwcsdd;
    }

    public void setZwcsdd(String zwcsdd) {
        this.zwcsdd = zwcsdd == null ? null : zwcsdd.trim();
    }

    public String getYwcsdd() {
        return ywcsdd;
    }

    public void setYwcsdd(String ywcsdd) {
        this.ywcsdd = ywcsdd == null ? null : ywcsdd.trim();
    }

    public String getZjhm() {
        return zjhm;
    }

    public void setZjhm(String zjhm) {
        this.zjhm = zjhm == null ? null : zjhm.trim();
    }

    public Integer getZjlx() {
        return zjlx;
    }

    public void setZjlx(Integer zjlx) {
        this.zjlx = zjlx;
    }

    public String getZjxs() {
        return zjxs;
    }

    public void setZjxs(String zjxs) {
        this.zjxs = zjxs == null ? null : zjxs.trim();
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location == null ? null : location.trim();
    }

    public String getZwqfdd() {
        return zwqfdd;
    }

    public void setZwqfdd(String zwqfdd) {
        this.zwqfdd = zwqfdd == null ? null : zwqfdd.trim();
    }

    public String getYwqfdd() {
        return ywqfdd;
    }

    public void setYwqfdd(String ywqfdd) {
        this.ywqfdd = ywqfdd == null ? null : ywqfdd.trim();
    }

    public String getZwqfjg() {
        return zwqfjg;
    }

    public void setZwqfjg(String zwqfjg) {
        this.zwqfjg = zwqfjg == null ? null : zwqfjg.trim();
    }

    public String getYwqfjg() {
        return ywqfjg;
    }

    public void setYwqfjg(String ywqfjg) {
        this.ywqfjg = ywqfjg == null ? null : ywqfjg.trim();
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

    public Integer getDqzt() {
        return dqzt;
    }

    public void setDqzt(Integer dqzt) {
        this.dqzt = dqzt;
    }

    public Integer getIscabinet() {
        return iscabinet;
    }

    public void setIscabinet(Integer iscabinet) {
        this.iscabinet = iscabinet;
    }


    public String getA0184() {
        return a0184;
    }

    public void setA0184(String a0184) {
        this.a0184 = a0184;
    }
}