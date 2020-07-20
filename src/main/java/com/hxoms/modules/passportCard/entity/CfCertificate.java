package com.hxoms.modules.passportCard.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.hxoms.common.hxannotation.ColumnAnnotation;
import com.hxoms.common.hxannotation.IdAnnotation;
import com.hxoms.common.hxannotation.TableAnnotation;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@TableAnnotation(TableName = "cf_certificate", TableDescription="证件信息表")
public class CfCertificate {
    @ApiModelProperty("ID")
    @IdAnnotation
    @ColumnAnnotation(FieldName = "ID",   FieldDescription="ID")
    private String id;

    @ApiModelProperty("备案信息表ID")
    @ColumnAnnotation(FieldName = "OMS_ID",   FieldDescription="备案信息表ID")
    private String omsId;

    @TableField(exist = false)
    private String b0100;

    @ApiModelProperty("姓名")
    @ColumnAnnotation(FieldName = "NAME",   FieldDescription="姓名")
    private String name;

    @ApiModelProperty("姓名首字母")
    @ColumnAnnotation(FieldName = "PY",   FieldDescription="姓名首字母")
    private String py;

    @ApiModelProperty("证件拥有者身份证号码")
    @ColumnAnnotation(FieldName = "A0184",   FieldDescription="证件拥有者身份证号码")
    private String a0184;

    @ApiModelProperty("出生年月")
    @ColumnAnnotation(FieldName = "CSRQ",   FieldDescription="出生年月")
    private Date csrq;

    @ApiModelProperty("性别")
    @ColumnAnnotation(FieldName = "SEX",   FieldDescription="性别")
    private  Integer sex;

    @ApiModelProperty("证件拥有者ID")
    @ColumnAnnotation(FieldName = "A0100",   FieldDescription="证件拥有者ID")
    private String a0100;

    @ApiModelProperty("国籍")
    @ColumnAnnotation(FieldName = "GJ",   FieldDescription="国籍")
    private String gj;

    @ApiModelProperty("中文出生地点")
    @ColumnAnnotation(FieldName = "ZWCSDD",   FieldDescription="中文出生地点")
    private String zwcsdd;

    @ApiModelProperty("英文出生地点")
    @ColumnAnnotation(FieldName = "YWCSDD",   FieldDescription="英文出生地点")
    private String ywcsdd;

    @ApiModelProperty("证件号码")
    @ColumnAnnotation(FieldName = "ZJHM",   FieldDescription="证件号码")
    private String zjhm;

    @ApiModelProperty("证件类型")
    @ColumnAnnotation(FieldName = "ZJLX",   FieldDescription="证件类型1护照、2港澳通行证、3台湾通行证")
    private Integer zjlx;

    @ApiModelProperty("证件形式")
    @ColumnAnnotation(FieldName = "ZJXS",   FieldDescription="证件形式")
    private String zjxs;

    @ApiModelProperty("证照存放地址")
    @ColumnAnnotation(FieldName = "LOCATION",   FieldDescription="证照存放地址")
    private String location;

    @ApiModelProperty("证件管理单位")
    @ColumnAnnotation(FieldName = "GJDW",   FieldDescription="证件管理单位")
    private  String gjdw;

    @ApiModelProperty("中文签发地点")
    @ColumnAnnotation(FieldName = "ZWQFDD",   FieldDescription="中文签发地点")
    private String zwqfdd;

    @ApiModelProperty("英文签发地点")
    @ColumnAnnotation(FieldName = "YWQFDD",   FieldDescription="英文签发地点")
    private String ywqfdd;

    @ApiModelProperty("中文签发机关")
    @ColumnAnnotation(FieldName = "ZWQFJG",   FieldDescription="中文签发机关")
    private String zwqfjg;

    @ApiModelProperty("英文签发机关")
    @ColumnAnnotation(FieldName = "YWQFJG",   FieldDescription="英文签发机关")
    private String ywqfjg;

    @ApiModelProperty("签发日期")
    @ColumnAnnotation(FieldName = "QFRQ",   FieldDescription="签发日期")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date qfrq;

    @ApiModelProperty("有效期至")
    @ColumnAnnotation(FieldName = "YXQZ",   FieldDescription="有效期至")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date yxqz;

    @ApiModelProperty("证件上缴日期")
    @ColumnAnnotation(FieldName = "ZJSJRQ",   FieldDescription="证件上缴日期")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date zjsjrq;

    @ApiModelProperty("管理单位")
    @ColumnAnnotation(FieldName = "MANAGE_UNIT",   FieldDescription="管理单位")
    private String manageUnit;

    @ApiModelProperty("柜子编号")
    @ColumnAnnotation(FieldName = "CABINET_NUM",   FieldDescription="柜子编号")
    private String cabinetNum;

    @ApiModelProperty("台办证件存放位置")
    @ColumnAnnotation(FieldName = "STORAGE_PLACE",   FieldDescription="台办证件存放位置")
    private  String storagePlace;

    @ApiModelProperty("证照的保存状态0:正常,1:超期未归还,2:过期证照,3:注销,4:转出,5:,6:验证失败,7:失效证照,8:其他")
    @ColumnAnnotation(FieldName = "SAVE_STATUS",   FieldDescription="证照的保存状态0:正常,1:超期未归还,2:过期证照,3:注销,4:转出,5:,6:验证失败,7:失效证照,8:其他")
    private String saveStatus;

    @ApiModelProperty("当前状态(1:正常保管,2:待领取，3:已取出，4:未上交)")
    @ColumnAnnotation(FieldName = "DQZT",   FieldDescription="当前状态(1:正常保管,2:待领取，3:已取出，4:未上交)")
    private Integer dqzt;

    @ApiModelProperty("是否锁定0:正常，1:锁定")
    @ColumnAnnotation(FieldName = "IS_LOCK",   FieldDescription="是否锁定0:正常，1:锁定")
    private Integer isLock;

    @ApiModelProperty("异常消息")
    @ColumnAnnotation(FieldName = "EXCEPTION_MESSAGE",   FieldDescription="异常消息")
    private String exceptionMessage;

    @ApiModelProperty("人员是否有效，0:有效，1:无效")
    @ColumnAnnotation(FieldName = "IS_CABINET",   FieldDescription="人员是否有效，0:有效，1:无效")
    private Integer isCabinet;

    @ApiModelProperty("证件修改时间")
    @ColumnAnnotation(FieldName = "UPDATE_TIME",   FieldDescription="证件修改时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:MM:SS")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:MM:SS")
    private Date updateTime;

    @ApiModelProperty("是否有效0:有效，1:无效")
    @ColumnAnnotation(FieldName = "IS_VALID",   FieldDescription="是否有效0:有效，1:无效")
    private Integer isValid;

    public String getB0100() {
        return b0100;
    }

    public void setB0100(String b0100) {
        this.b0100 = b0100;
    }

    public Integer getIsValid() {
        return isValid;
    }

    public void setIsValid(Integer isValid) {
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

    public String getPy() {
        return py;
    }

    public void setPy(String py) {
        this.py = py;
    }

    public String getOmsId() {
        return omsId;
    }

    public void setOmsId(String omsId) {
        this.omsId = omsId;
    }

    public Date getCsrq() {
        return csrq;
    }

    public void setCsrq(Date csrq) {
        this.csrq = csrq;
    }

    public String getSaveStatus() {
        return saveStatus;
    }

    public void setSaveStatus(String saveStatus) {
        this.saveStatus = saveStatus;
    }

    public String getExceptionMessage() {
        return exceptionMessage;
    }

    public void setExceptionMessage(String exceptionMessage) {
        this.exceptionMessage = exceptionMessage;
    }

    public Integer getIsCabinet() {
        return isCabinet;
    }

    public void setIsCabinet(Integer isCabinet) {
        this.isCabinet = isCabinet;
    }
}