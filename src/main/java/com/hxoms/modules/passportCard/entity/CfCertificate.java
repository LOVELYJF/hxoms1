package com.hxoms.modules.passportCard.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.hxoms.common.hxannotation.ColumnAnnotation;
import com.hxoms.common.hxannotation.IdAnnotation;
import com.hxoms.common.hxannotation.TableAnnotation;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

@TableAnnotation(TableName = "cf_certificate", TableDescription="证件信息表")
public class CfCertificate {
    @IdAnnotation
    @ColumnAnnotation(FieldName = "ID",   FieldDescription="ID")
    private String id;

    @ColumnAnnotation(FieldName = "NAME",   FieldDescription="姓名")
    private String name;

    @ColumnAnnotation(FieldName = "A0184",   FieldDescription="证件拥有者身份证号码")
    private String a0184;


    private  Integer sex;

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

    @ColumnAnnotation(FieldName = "ZJLX",   FieldDescription="证件类型1护照、2港澳通行证、3台湾通行证")
    private Integer zjlx;

    @ColumnAnnotation(FieldName = "ZJXS",   FieldDescription="证件形式")
    private String zjxs;

    @ColumnAnnotation(FieldName = "LOCATION",   FieldDescription="证照存放地址")
    private String location;

    @ColumnAnnotation(FieldName = "GJDW",   FieldDescription="证件管理单位")
    private  String gjdw;

    @ColumnAnnotation(FieldName = "ZWQFDD",   FieldDescription="中文签发地点")
    private String zwqfdd;

    @ColumnAnnotation(FieldName = "YWQFDD",   FieldDescription="英文签发地点")
    private String ywqfdd;

    @ColumnAnnotation(FieldName = "ZWQFJG",   FieldDescription="中文签发机关")
    private String zwqfjg;

    @ColumnAnnotation(FieldName = "YWQFJG",   FieldDescription="英文签发机关")
    private String ywqfjg;

    @ColumnAnnotation(FieldName = "QFRQ",   FieldDescription="签发日期")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date qfrq;
    @ColumnAnnotation(FieldName = "YXQZ",   FieldDescription="有效期至")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date yxqz;

    @ColumnAnnotation(FieldName = "ZJSJRQ",   FieldDescription="证件上缴日期")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date zjsjrq;

    @ColumnAnnotation(FieldName = "MANAGEUNIT",   FieldDescription="管理单位")
    @TableField("MANAGEUNIT")
    private String manageUnit;

    @ColumnAnnotation(FieldName = "ISLOCK",   FieldDescription="是否锁定0:未锁定,1:以锁定")
    private Integer islock;

    @ColumnAnnotation(FieldName = "STORAGEPLACE",   FieldDescription="台办证件存放位置")
    @TableField("STORAGEPLACE")
    private  String storagePlace;

    @ColumnAnnotation(FieldName = "STORAGEPLACE",   FieldDescription="是否锁定0:正常，1:锁定")
    @TableField("ISLOCK")
    private Integer isLock;

    @ColumnAnnotation(FieldName = "EXCEPTIONMESSAGE",   FieldDescription="异常消息")
    private String exceptionmessage;

    @ColumnAnnotation(FieldName = "SAVESTATUS",   FieldDescription="证照的保存状态0:正常,1:超期未归还,2:过期证照,3:注销,4:转出,5:,6:验证失败,7:失效证照,8:其他")
    private String savestatus;

    @ColumnAnnotation(FieldName = "CABINETNUM",   FieldDescription="柜子编号")
    private String cabinetNum;

    @ColumnAnnotation(FieldName = "DQZT",   FieldDescription="当前状态(1:正常保管,2:待领取，3:已取出，4:未上交)")
    private Integer dqzt;

    @ColumnAnnotation(FieldName = "ISCABINET",   FieldDescription="人员是否有效，1，有效，0，无效")
    private Integer iscabinet;


    @ColumnAnnotation(FieldName = "UPDATE_TIME",   FieldDescription="证件修改时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:MM:SS")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:MM:SS")
    private Date updateTime;

    @ColumnAnnotation(FieldName = "IS_VALID",   FieldDescription="是否有效0:有效，1:无效")
    private int isValid;
    /**
     * 单位id
     */
    private String[] b0100s;

    private String b0100;
    /**
     * 单位
     */
    private String b0101;

    /**
     * 职位
     */
    private String a0215a;

    /**
     * 在职状态
     */
    private String incumbencyStatus;
    /**
     * 单位父ID
     */
    private String b0100Pid;

    /**
     * 职务
     */
    private String duty;

    public String[] getB0100s() {
        return b0100s;
    }

    public void setB0100s(String[] b0100s) {
        this.b0100s = b0100s;
    }

    public String getB0100() {
        return b0100;
    }

    public void setB0100(String b0100) {
        this.b0100 = b0100;
    }

    public String getB0101() {
        return b0101;
    }

    public void setB0101(String b0101) {
        this.b0101 = b0101;
    }

    public String getA0215a() {
        return a0215a;
    }

    public void setA0215a(String a0215a) {
        this.a0215a = a0215a;
    }

    public String getIncumbencyStatus() {
        return incumbencyStatus;
    }

    public void setIncumbencyStatus(String incumbencyStatus) {
        this.incumbencyStatus = incumbencyStatus;
    }

    public String getB0100Pid() {
        return b0100Pid;
    }

    public void setB0100Pid(String b0100Pid) {
        this.b0100Pid = b0100Pid;
    }

    public String getDuty() {
        return duty;
    }

    public void setDuty(String duty) {
        this.duty = duty;
    }

    public int getIsValid() {
        return isValid;
    }

    public void setIsValid(int isValid) {
        this.isValid = isValid;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getGjdw() {
        return gjdw;
    }

    public void setGjdw(String gjdw) {
        this.gjdw = gjdw;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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

    public Integer getIsLock() {
        return isLock;
    }

    public void setIsLock(Integer isLock) {
        this.isLock = isLock;
    }

    public String getA0184() {
        return a0184;
    }

    public void setA0184(String a0184) {
        this.a0184 = a0184;
    }

    public String getManageUnit() {
        return manageUnit;
    }

    public void setManageUnit(String manageUnit) {
        this.manageUnit = manageUnit;
    }

    public String getStoragePlace() {
        return storagePlace;
    }

    public void setStoragePlace(String storagePlace) {
        this.storagePlace = storagePlace;
    }

    public String getExceptionmessage() {
        return exceptionmessage;
    }

    public void setExceptionmessage(String exceptionmessage) {
        this.exceptionmessage = exceptionmessage;
    }

    public String getSavestatus() {
        return savestatus;
    }

    public void setSavestatus(String savestatus) {
        this.savestatus = savestatus;
    }

    public String getCabinetNum() {
        return cabinetNum;
    }

    public void setCabinetNum(String cabinetNum) {
        this.cabinetNum = cabinetNum;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }


}