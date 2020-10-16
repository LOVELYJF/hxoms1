package com.hxoms.modules.passportCard.initialise.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hxoms.common.hxannotation.ColumnAnnotation;
import com.hxoms.common.hxannotation.IdAnnotation;
import com.hxoms.common.hxannotation.TableAnnotation;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@TableAnnotation(TableName = "cf_certificate_history_record", TableDescription="证照历史记录表")
@ApiModel(value = "证照历史记录表")
public class CfCertificateHistoryRecord {
    @IdAnnotation
    @ColumnAnnotation(FieldName = "ID",   FieldDescription="主键")
    @ApiModelProperty(value="主键")
    private String id;

    @ColumnAnnotation(FieldName = "OMS_ID",   FieldDescription="备案信息表ID")
    @ApiModelProperty(value="备案信息表ID")
    private String omsId;

    @ColumnAnnotation(FieldName = "A0100",   FieldDescription="人员主键")
    @ApiModelProperty(value="人员主键")
    private String a0100;

    @ColumnAnnotation(FieldName = "A0184",   FieldDescription="身份证号码")
    @ApiModelProperty(value="身份证号码")
    private String a0184;

    @ColumnAnnotation(FieldName = "NAME",   FieldDescription="姓名")
    @ApiModelProperty(value="姓名")
    private String name;

    @ColumnAnnotation(FieldName = "PY",   FieldDescription="拼音简称")
    @ApiModelProperty(value="拼音简称")
    private String py;

    @ColumnAnnotation(FieldName = "SEX",   FieldDescription="性别")
    @ApiModelProperty(value="性别")
    private String sex;

    @ColumnAnnotation(FieldName = "CSRQ",   FieldDescription="出生日期")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    @ApiModelProperty(value="出生日期")
    private Date csrq;

    @ColumnAnnotation(FieldName = "GJ",   FieldDescription="国籍")
    @ApiModelProperty(value="国籍")
    private String gj;

    @ColumnAnnotation(FieldName = "CSDD",   FieldDescription="出生地点")
    @ApiModelProperty(value="出生地点")
    private String csdd;

    @ColumnAnnotation(FieldName = "ZJLX",   FieldDescription="证件类型(1:护照,2:港澳通行证,4:台湾通行证)")
    @ApiModelProperty(value="证件类型(1:护照,2:港澳通行证,4:台湾通行证)")
    private Integer zjlx;

    @ColumnAnnotation(FieldName = "ZJXS",   FieldDescription="证件形式(0:本式,1:卡式)")
    @ApiModelProperty(value="证件形式(0:本式,1:卡式)")
    private String zjxs;

    @ColumnAnnotation(FieldName = "XPLX",   FieldDescription="芯片类型(0:自带,1:粘贴)")
    @ApiModelProperty(value="芯片类型(0:自带,1:粘贴)")
    private String xplx;

    @ColumnAnnotation(FieldName = "ZJHM",   FieldDescription="证件号码")
    @ApiModelProperty(value="证件号码")
    private String zjhm;

    @ColumnAnnotation(FieldName = "QFJG",   FieldDescription="签发机关")
    @ApiModelProperty(value="签发机关")
    private String qfjg;

    @ColumnAnnotation(FieldName = "QFDD",   FieldDescription="签发地点")
    @ApiModelProperty(value="签发地点")
    private String qfdd;

    @ColumnAnnotation(FieldName = "QFRQ",   FieldDescription="签发日期")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    @ApiModelProperty(value="签发日期")
    private Date qfrq;

    @ColumnAnnotation(FieldName = "YXQZ",   FieldDescription="有效期至")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    @ApiModelProperty(value="有效期至")
    private Date yxqz;

    @ColumnAnnotation(FieldName = "SAVE_STATUS",   FieldDescription="保管状态(0:正常保管,1:已取出,2:未上缴)")
    @ApiModelProperty(value="保管状态(0:正常保管,1:已取出,2:未上缴)")
    private String saveStatus;

    @ColumnAnnotation(FieldName = "CARD_STATUS",   FieldDescription="证照状态(0:正常,1:过期,2:注销,3:验证失败,4:已验证,5:待验证,6:借出,40:待领取,41:已领取)")
    @ApiModelProperty(value="证照状态(0:正常,1:过期,2:注销,3:验证失败,4:已验证,5:待验证,6:借出,40:待领取,41:已领取)")
    private String cardStatus;

    @ColumnAnnotation(FieldName = "SURELY_UNIT",   FieldDescription="保管单位(0:干部监督处,1:省委统战部(台办))")
    @ApiModelProperty(value="保管单位(0:干部监督处,1:省委统战部(台办))")
    private String surelyUnit;

    @ColumnAnnotation(FieldName = "SURELY_WAY",   FieldDescription="保管方式(0:证照机,1:柜台)")
    @ApiModelProperty(value="保管方式(0:证照机,1:柜台)")
    private String surelyWay;

    @ColumnAnnotation(FieldName = "CABINET_NUM",   FieldDescription="机柜编号")
    @ApiModelProperty(value="机柜编号")
    private String cabinetNum;

    @ColumnAnnotation(FieldName = "PLACE",   FieldDescription="机柜位置")
    @ApiModelProperty(value="机柜位置")
    private String place;

    @ColumnAnnotation(FieldName = "COUNTER_NUM",   FieldDescription="柜台编号")
    @ApiModelProperty(value="柜台编号")
    private Integer counterNum;

    @ColumnAnnotation(FieldName = "EXCEPTION_MESSAGE",   FieldDescription="异常消息")
    @ApiModelProperty(value="异常消息")
    private String exceptionMessage;

    @ColumnAnnotation(FieldName = "EXCEPTION_CONCLUSION",   FieldDescription="异常处理结论")
    @ApiModelProperty(value="异常处理结论")
    private String exceptionConclusion;

    @ColumnAnnotation(FieldName = "EXCEPTION_HANDLER",   FieldDescription="异常处理人")
    @ApiModelProperty(value="异常处理人")
    private String exceptionHandler;

    @ColumnAnnotation(FieldName = "EXCEPTION_SOLVEDATE",   FieldDescription="异常处理时间")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    @ApiModelProperty(value="异常处理时间")
    private Date exceptionSolvedate;

    @ColumnAnnotation(FieldName = "COMPARISON_RESULT",   FieldDescription="比对结果")
    @ApiModelProperty(value="比对结果")
    private String comparisonResult;

    @ColumnAnnotation(FieldName = "REMARK",   FieldDescription="备注")
    @ApiModelProperty(value="备注")
    private String remark;

    @ColumnAnnotation(FieldName = "YEAR",   FieldDescription="年度")
    @ApiModelProperty(value="年度")
    private String year;

    @ColumnAnnotation(FieldName = "IMPORT_PERSON",   FieldDescription="导入人")
    @ApiModelProperty(value="导入人")
    private String importPerson;

    @ColumnAnnotation(FieldName = "IMPORT_TIME",   FieldDescription="导入时间")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    @ApiModelProperty(value="导入时间")
    private Date importTime;

    @ColumnAnnotation(FieldName = "UPDATER",   FieldDescription="修改人")
    @ApiModelProperty(value="修改人")
    private String updater;

    @ColumnAnnotation(FieldName = "UPDATE_TIME",   FieldDescription="修改时间")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    @ApiModelProperty(value="修改时间")
    private Date updateTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
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

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex == null ? null : sex.trim();
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

    public String getCsdd() {
        return csdd;
    }

    public void setCsdd(String csdd) {
        this.csdd = csdd == null ? null : csdd.trim();
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

    public String getXplx() {
        return xplx;
    }

    public void setXplx(String xplx) {
        this.xplx = xplx == null ? null : xplx.trim();
    }

    public String getZjhm() {
        return zjhm;
    }

    public void setZjhm(String zjhm) {
        this.zjhm = zjhm == null ? null : zjhm.trim();
    }

    public String getQfjg() {
        return qfjg;
    }

    public void setQfjg(String qfjg) {
        this.qfjg = qfjg == null ? null : qfjg.trim();
    }

    public String getQfdd() {
        return qfdd;
    }

    public void setQfdd(String qfdd) {
        this.qfdd = qfdd == null ? null : qfdd.trim();
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

    public String getSaveStatus() {
        return saveStatus;
    }

    public void setSaveStatus(String saveStatus) {
        this.saveStatus = saveStatus == null ? null : saveStatus.trim();
    }

    public String getCardStatus() {
        return cardStatus;
    }

    public void setCardStatus(String cardStatus) {
        this.cardStatus = cardStatus == null ? null : cardStatus.trim();
    }

    public String getSurelyUnit() {
        return surelyUnit;
    }

    public void setSurelyUnit(String surelyUnit) {
        this.surelyUnit = surelyUnit == null ? null : surelyUnit.trim();
    }

    public String getSurelyWay() {
        return surelyWay;
    }

    public void setSurelyWay(String surelyWay) {
        this.surelyWay = surelyWay == null ? null : surelyWay.trim();
    }

    public String getCabinetNum() {
        return cabinetNum;
    }

    public void setCabinetNum(String cabinetNum) {
        this.cabinetNum = cabinetNum == null ? null : cabinetNum.trim();
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place == null ? null : place.trim();
    }

    public Integer getCounterNum() {
        return counterNum;
    }

    public void setCounterNum(Integer counterNum) {
        this.counterNum = counterNum;
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

    public String getComparisonResult() {
        return comparisonResult;
    }

    public void setComparisonResult(String comparisonResult) {
        this.comparisonResult = comparisonResult == null ? null : comparisonResult.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year == null ? null : year.trim();
    }

    public String getImportPerson() {
        return importPerson;
    }

    public void setImportPerson(String importPerson) {
        this.importPerson = importPerson == null ? null : importPerson.trim();
    }

    public Date getImportTime() {
        return importTime;
    }

    public void setImportTime(Date importTime) {
        this.importTime = importTime;
    }

    public String getUpdater() {
        return updater;
    }

    public void setUpdater(String updater) {
        this.updater = updater == null ? null : updater.trim();
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}