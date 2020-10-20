package com.hxoms.modules.passportCard.admintorGet.entity.parameterEntiry;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @Desc：TODO
 * @Author: wangyunquan
 * @Date: 2020/8/13
 */
@ApiModel(value = "管理员取证页面查询信息")
public class AdmintorGetCerInfo {
    @ApiModelProperty(value = "主键")
    private String id;
    //证照表主键
    @ApiModelProperty(value = "证照表id")
    private String cerId;
    //登记备案表主键
    @ApiModelProperty(value = "登记备案表主键")
    private String omsId;
    //单位
    @ApiModelProperty(value = "单位")
    private String workUnit;
    //机构
    @ApiModelProperty(value = "机构")
    private String rfB0000;
    //姓名
    @ApiModelProperty(value = "姓名")
    private String name;
    //性别
    @ApiModelProperty(value = "性别")
    private String sex;
    //任职状态
    @ApiModelProperty(value = "任职状态(1在职 2辞职 3退休 4去世 5开除 6调出 7.省管变中管 8 未匹配 9其它)")
    private String incumbencyStatus;
    @ApiModelProperty(value = "任职状态名称")
    private String incumbencyStatusName;
    //职务
    @ApiModelProperty(value = "职务")
    private String post;
    //证照类型
    @ApiModelProperty(value = "证照类型")
    private Integer zjlx;
    //证照类型
    @ApiModelProperty(value = "证照类型名称")
    private String zjlxName;
    //芯片类型
    @ApiModelProperty(value = "芯片类型")
    private String xplx;
    @ApiModelProperty(value = "芯片类型名称")
    private String xplxName;
    //证照形式
    @ApiModelProperty(value = "证照形式")
    private String zjxs;
    @ApiModelProperty(value = "证照形式名称")
    private String zjxsName;
    //证件号码
    @ApiModelProperty(value = "证件号码")
    private String zjhm;
    //有效期至
    @ApiModelProperty(value = "有效期至")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    @JsonFormat(pattern = "yyyy.MM.dd",timezone="GMT+8")
    private Date yxqz;

    @ApiModelProperty(value = "取证人")
    private String operator;

    @ApiModelProperty(value = "取证原因")
    private String getCause;

    @DateTimeFormat(pattern = "yyyy.MM.dd")
    @JsonFormat(pattern = "yyyy.MM.dd",timezone="GMT+8")
    @ApiModelProperty(value = "申请时间")
    private String operateTime;

    @DateTimeFormat(pattern = "yyyy.MM.dd")
    @JsonFormat(pattern = "yyyy.MM.dd",timezone="GMT+8")
    @ApiModelProperty(value = "取出时间")
    private String getTime;
    //管理单位
    @ApiModelProperty(value = "管理单位")
    private String surelyUnit;

    //管理单位
    @ApiModelProperty(value = "管理单位名称")
    private String surelyUnitName;
    //证照状态
    @ApiModelProperty(value = "证照状态")
    private String cardStatus;

    //证照状态
    @ApiModelProperty(value = "证照状态名称")
    private String cardStatusName;
    //保管状态
    @ApiModelProperty(value = "保管状态")
    private String saveStatus;

    //保管状态
    @ApiModelProperty(value = "保管状态名称")
    private String saveStatusName;
    //保管方式
    @ApiModelProperty(value = "保管方式")
    private String surelyWay;

    @ApiModelProperty(value = "保管方式名称")
    private String surelyWayName;
    //机柜
    @ApiModelProperty(value = "机柜")
    private String cabinetNum;
    //位置
    @ApiModelProperty(value = "位置")
    private String place;
    //柜台编号
    @ApiModelProperty(value = "柜台编号")
    private Integer counterNum;
    //出生日期
    @ApiModelProperty(value = "出生日期")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    @JsonFormat(pattern = "yyyy.MM.dd",timezone="GMT+8")
    private Date csrq;
    //签发单位
    @ApiModelProperty(value = "签发单位")
    private String qfjg;
    //签发日期
    @ApiModelProperty(value = "签发日期")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    @JsonFormat(pattern = "yyyy.MM.dd",timezone="GMT+8")
    private Date qfrq;
    //出生地点
    @ApiModelProperty(value = "出生地点")
    private String csdd;
    //存疑信息
    @ApiModelProperty(value = "存疑信息")
    private String exceprionMessqge;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCerId() {
        return cerId;
    }

    public void setCerId(String cerId) {
        this.cerId = cerId;
    }

    public String getOmsId() {
        return omsId;
    }

    public void setOmsId(String omsId) {
        this.omsId = omsId;
    }

    public String getWorkUnit() {
        return workUnit;
    }

    public void setWorkUnit(String workUnit) {
        this.workUnit = workUnit;
    }

    public String getRfB0000() {
        return rfB0000;
    }

    public void setRfB0000(String rfB0000) {
        this.rfB0000 = rfB0000;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getIncumbencyStatus() {
        return incumbencyStatus;
    }

    public void setIncumbencyStatus(String incumbencyStatus) {
        this.incumbencyStatus = incumbencyStatus;
    }

    public String getIncumbencyStatusName() {
        return incumbencyStatusName;
    }

    public void setIncumbencyStatusName(String incumbencyStatusName) {
        this.incumbencyStatusName = incumbencyStatusName;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public Integer getZjlx() {
        return zjlx;
    }

    public void setZjlx(Integer zjlx) {
        this.zjlx = zjlx;
    }

    public String getZjlxName() {
        return zjlxName;
    }

    public void setZjlxName(String zjlxName) {
        this.zjlxName = zjlxName;
    }

    public String getXplx() {
        return xplx;
    }

    public void setXplx(String xplx) {
        this.xplx = xplx;
    }

    public String getXplxName() {
        return xplxName;
    }

    public void setXplxName(String xplxName) {
        this.xplxName = xplxName;
    }

    public String getZjxs() {
        return zjxs;
    }

    public void setZjxs(String zjxs) {
        this.zjxs = zjxs;
    }

    public String getZjxsName() {
        return zjxsName;
    }

    public void setZjxsName(String zjxsName) {
        this.zjxsName = zjxsName;
    }

    public String getZjhm() {
        return zjhm;
    }

    public void setZjhm(String zjhm) {
        this.zjhm = zjhm;
    }

    public Date getYxqz() {
        return yxqz;
    }

    public void setYxqz(Date yxqz) {
        this.yxqz = yxqz;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getGetCause() {
        return getCause;
    }

    public void setGetCause(String getCause) {
        this.getCause = getCause;
    }

    public String getOperateTime() {
        return operateTime;
    }

    public void setOperateTime(String operateTime) {
        this.operateTime = operateTime;
    }

    public String getGetTime() {
        return getTime;
    }

    public void setGetTime(String getTime) {
        this.getTime = getTime;
    }

    public String getSurelyUnit() {
        return surelyUnit;
    }

    public void setSurelyUnit(String surelyUnit) {
        this.surelyUnit = surelyUnit;
    }

    public String getSurelyUnitName() {
        return surelyUnitName;
    }

    public void setSurelyUnitName(String surelyUnitName) {
        this.surelyUnitName = surelyUnitName;
    }

    public String getCardStatus() {
        return cardStatus;
    }

    public void setCardStatus(String cardStatus) {
        this.cardStatus = cardStatus;
    }

    public String getCardStatusName() {
        return cardStatusName;
    }

    public void setCardStatusName(String cardStatusName) {
        this.cardStatusName = cardStatusName;
    }

    public String getSaveStatus() {
        return saveStatus;
    }

    public void setSaveStatus(String saveStatus) {
        this.saveStatus = saveStatus;
    }

    public String getSaveStatusName() {
        return saveStatusName;
    }

    public void setSaveStatusName(String saveStatusName) {
        this.saveStatusName = saveStatusName;
    }

    public String getSurelyWay() {
        return surelyWay;
    }

    public void setSurelyWay(String surelyWay) {
        this.surelyWay = surelyWay;
    }

    public String getSurelyWayName() {
        return surelyWayName;
    }

    public void setSurelyWayName(String surelyWayName) {
        this.surelyWayName = surelyWayName;
    }

    public String getCabinetNum() {
        return cabinetNum;
    }

    public void setCabinetNum(String cabinetNum) {
        this.cabinetNum = cabinetNum;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public Integer getCounterNum() {
        return counterNum;
    }

    public void setCounterNum(Integer counterNum) {
        this.counterNum = counterNum;
    }

    public Date getCsrq() {
        return csrq;
    }

    public void setCsrq(Date csrq) {
        this.csrq = csrq;
    }

    public String getQfjg() {
        return qfjg;
    }

    public void setQfjg(String qfjg) {
        this.qfjg = qfjg;
    }

    public Date getQfrq() {
        return qfrq;
    }

    public void setQfrq(Date qfrq) {
        this.qfrq = qfrq;
    }

    public String getCsdd() {
        return csdd;
    }

    public void setCsdd(String csdd) {
        this.csdd = csdd;
    }

    public String getExceprionMessqge() {
        return exceprionMessqge;
    }

    public void setExceprionMessqge(String exceprionMessqge) {
        this.exceprionMessqge = exceprionMessqge;
    }
}
