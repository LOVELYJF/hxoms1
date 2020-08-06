package com.hxoms.modules.passportCard.initialise.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hxoms.common.hxannotation.ColumnAnnotation;
import com.hxoms.common.hxannotation.IdAnnotation;
import com.hxoms.common.hxannotation.TableAnnotation;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

@TableAnnotation(TableName = "cf_certificate", TableDescription="证照管理信息表")
public class CfCertificate {
    @IdAnnotation
    @ColumnAnnotation(FieldName = "ID",   FieldDescription="ID")
    private String id;

    @ColumnAnnotation(FieldName = "IMPORT_TIME",   FieldDescription="导入时间")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date importTime;

    @ColumnAnnotation(FieldName = "IMPORT_PERSON",   FieldDescription="导入人")
    private String importPerson;

    @ColumnAnnotation(FieldName = "OMS_ID",   FieldDescription="备案信息表ID")
    private String omsId;

    @ColumnAnnotation(FieldName = "A0100",   FieldDescription="证件拥有者ID")
    private String a0100;

    @ColumnAnnotation(FieldName = "A0184",   FieldDescription="身份证号码")
    private String a0184;

    @ColumnAnnotation(FieldName = "NAME",   FieldDescription="")
    private String name;

    @ColumnAnnotation(FieldName = "PY",   FieldDescription="拼音首字母")
    private String py;

    @ColumnAnnotation(FieldName = "SEX",   FieldDescription="")
    private Integer sex;

    @ColumnAnnotation(FieldName = "CSRQ",   FieldDescription="出生日期")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date csrq;

    @ColumnAnnotation(FieldName = "GJ",   FieldDescription="国籍")
    private String gj;

    @ColumnAnnotation(FieldName = "ZWCSDD",   FieldDescription="中文出生地点")
    private String zwcsdd;

    @ColumnAnnotation(FieldName = "YWCSDD",   FieldDescription="英文出生地点")
    private String ywcsdd;

    @ColumnAnnotation(FieldName = "ZJHM",   FieldDescription="证件号码")
    private String zjhm;

    @ColumnAnnotation(FieldName = "ZJLX",   FieldDescription="证件类型(1:护照,2:港澳通行证,4:台湾通行证)")
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

    @ColumnAnnotation(FieldName = "GJDW",   FieldDescription="管理单位")
    private String gjdw;

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

    @ColumnAnnotation(FieldName = "MANAGE_UNIT",   FieldDescription="管理单位")
    private String manageUnit;

    @ColumnAnnotation(FieldName = "CABINET_NUM",   FieldDescription="柜子编号")
    private String cabinetNum;

    @ColumnAnnotation(FieldName = "STORAGE_PLACE",   FieldDescription="证件存放地点")
    private String storagePlace;

    @ColumnAnnotation(FieldName = "SAVE_STATUS",   FieldDescription="保管状态(0:正常保管,1:已取出,2:未上缴)")
    private String saveStatus;

    @ColumnAnnotation(FieldName = "DQZT",   FieldDescription="证照状态(0:正常,1:过期,2:注销,3:验证失败,4:已验证,5:待验证,6:借出,7:待领取,8:其它)")
    private Integer dqzt;

    @ColumnAnnotation(FieldName = "IS_LOCK",   FieldDescription="是否锁定0:正常，1:锁定")
    private Integer isLock;

    @ColumnAnnotation(FieldName = "IS_CABINET",   FieldDescription="人员是否有效，0:有效，1:无效")
    private Integer isCabinet;

    @ColumnAnnotation(FieldName = "UPDATE_TIME",   FieldDescription="修改证件信息")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date updateTime;

    @ColumnAnnotation(FieldName = "IS_VALID",   FieldDescription="是否有效0:有效，1:无效")
    private Integer isValid;

    @ColumnAnnotation(FieldName = "EXCEPTION_MESSAGE",   FieldDescription="异常消息")
    private String exceptionMessage;

    @ColumnAnnotation(FieldName = "EXCEPTION_CONCLUSION",   FieldDescription="异常处理结论")
    private String exceptionConclusion;

    @ColumnAnnotation(FieldName = "EXCEPTION_HANDLER",   FieldDescription="异常处理人")
    private String exceptionHandler;

    @ColumnAnnotation(FieldName = "EXCEPTION_SOLVEDATE",   FieldDescription="异常处理时间")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date exceptionSolvedate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public Date getImportTime() {
        return importTime;
    }

    public void setImportTime(Date importTime) {
        this.importTime = importTime;
    }

    public String getImportPerson() {
        return importPerson;
    }

    public void setImportPerson(String importPerson) {
        this.importPerson = importPerson == null ? null : importPerson.trim();
    }

    public String getOmsId() {
        return omsId;
    }

    public void setOmsId(String omsId) {
        this.omsId = omsId == null ? null : omsId.trim();
    }

    public String getA0100() {
        return a0100;
    }

    public void setA0100(String a0100) {
        this.a0100 = a0100 == null ? null : a0100.trim();
    }

    public String getA0184() {
        return a0184;
    }

    public void setA0184(String a0184) {
        this.a0184 = a0184 == null ? null : a0184.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getPy() {
        return py;
    }

    public void setPy(String py) {
        this.py = py == null ? null : py.trim();
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Date getCsrq() {
        return csrq;
    }

    public void setCsrq(Date csrq) {
        this.csrq = csrq;
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

    public String getGjdw() {
        return gjdw;
    }

    public void setGjdw(String gjdw) {
        this.gjdw = gjdw == null ? null : gjdw.trim();
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

    public String getManageUnit() {
        return manageUnit;
    }

    public void setManageUnit(String manageUnit) {
        this.manageUnit = manageUnit == null ? null : manageUnit.trim();
    }

    public String getCabinetNum() {
        return cabinetNum;
    }

    public void setCabinetNum(String cabinetNum) {
        this.cabinetNum = cabinetNum == null ? null : cabinetNum.trim();
    }

    public String getStoragePlace() {
        return storagePlace;
    }

    public void setStoragePlace(String storagePlace) {
        this.storagePlace = storagePlace == null ? null : storagePlace.trim();
    }

    public String getSaveStatus() {
        return saveStatus;
    }

    public void setSaveStatus(String saveStatus) {
        this.saveStatus = saveStatus == null ? null : saveStatus.trim();
    }

    public Integer getDqzt() {
        return dqzt;
    }

    public void setDqzt(Integer dqzt) {
        this.dqzt = dqzt;
    }

    public Integer getIsLock() {
        return isLock;
    }

    public void setIsLock(Integer isLock) {
        this.isLock = isLock;
    }

    public Integer getIsCabinet() {
        return isCabinet;
    }

    public void setIsCabinet(Integer isCabinet) {
        this.isCabinet = isCabinet;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getIsValid() {
        return isValid;
    }

    public void setIsValid(Integer isValid) {
        this.isValid = isValid;
    }

    public String getExceptionMessage() {
        return exceptionMessage;
    }

    public void setExceptionMessage(String exceptionMessage) {
        this.exceptionMessage = exceptionMessage == null ? null : exceptionMessage.trim();
    }

    public String getExceptionConclusion() {
        return exceptionConclusion;
    }

    public void setExceptionConclusion(String exceptionConclusion) {
        this.exceptionConclusion = exceptionConclusion == null ? null : exceptionConclusion.trim();
    }

    public String getExceptionHandler() {
        return exceptionHandler;
    }

    public void setExceptionHandler(String exceptionHandler) {
        this.exceptionHandler = exceptionHandler == null ? null : exceptionHandler.trim();
    }

    public Date getExceptionSolvedate() {
        return exceptionSolvedate;
    }

    public void setExceptionSolvedate(Date exceptionSolvedate) {
        this.exceptionSolvedate = exceptionSolvedate;
    }
}