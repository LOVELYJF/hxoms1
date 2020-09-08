package com.hxoms.modules.passportCard.counterGet.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hxoms.common.hxannotation.ColumnAnnotation;
import com.hxoms.common.hxannotation.IdAnnotation;
import com.hxoms.common.hxannotation.TableAnnotation;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@TableAnnotation(TableName = "oms_cer_get_task", TableDescription="证照领取任务表")
public class OmsCerGetTask {

    @ApiModelProperty(value="主键")
    @IdAnnotation
    @ColumnAnnotation(FieldName = "ID",   FieldDescription="主键")
    private String id;

    @ApiModelProperty(value="备案信息表ID")
    @ColumnAnnotation(FieldName = "OMS_ID",   FieldDescription="备案信息表ID")
    private String omsId;

    @ApiModelProperty(value="证照管理表ID")
    @ColumnAnnotation(FieldName = "CER_ID",   FieldDescription="证照管理表ID")
    private String cerId;

    @ApiModelProperty(value="业务表ID")
    @ColumnAnnotation(FieldName = "BUSI_ID",   FieldDescription="业务表ID")
    private String busiId;

    @ApiModelProperty(value="姓名")
    @ColumnAnnotation(FieldName = "NAME",   FieldDescription="姓名")
    private String name;

    @ApiModelProperty(value="机构")
    @ColumnAnnotation(FieldName = "RF_B0000",   FieldDescription="机构")
    private String rfB0000;

    @ApiModelProperty(value="证件类型(1:护照,2:港澳通行证,4:台湾通行证)")
    @ColumnAnnotation(FieldName = "ZJLX",   FieldDescription="证件类型(1:护照,2:港澳通行证,4:台湾通行证)")
    private Integer zjlx;

    @ApiModelProperty(value="证件号码")
    @ColumnAnnotation(FieldName = "ZJHM",   FieldDescription="证件号码")
    private String zjhm;

    @ApiModelProperty(value="领取状态(0:未领取,1:已领取)")
    @ColumnAnnotation(FieldName = "GET_STATUS",   FieldDescription="领取状态(0:未领取,1:已领取)")
    private String getStatus;

    @ApiModelProperty(value="业务发生日期")
    @ColumnAnnotation(FieldName = "HAPPEN_DATE",   FieldDescription="业务发生日期")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date happenDate;

    @ApiModelProperty(value="数据来源(0:因私出国(境),1:撤销登记备案,2:证照借出,3:证照过期,4:管理员取证,5:证照注销)")
    @ColumnAnnotation(FieldName = "DATA_SOURCE",   FieldDescription="数据来源(0:因私出国(境),1:撤销登记备案,2:证照借出,3:证照过期,4:管理员取证,5:证照注销)")
    private String dataSource;

    @ApiModelProperty(value="领取人")
    @ColumnAnnotation(FieldName = "GET_PEOPLE",   FieldDescription="领取人")
    private String getPeople;

    @ApiModelProperty(value="领取时间")
    @ColumnAnnotation(FieldName = "GET_TIME",   FieldDescription="领取时间")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date getTime;

    @ApiModelProperty(value="创建人")
    @ColumnAnnotation(FieldName = "CREATOR",   FieldDescription="创建人")
    private String creator;

    @ApiModelProperty(value="创建时间")
    @ColumnAnnotation(FieldName = "CREATE_TIME",   FieldDescription="创建时间")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date createTime;

    @ApiModelProperty(value="修改人")
    @ColumnAnnotation(FieldName = "UPDATOR",   FieldDescription="修改人")
    private String updator;

    @ApiModelProperty(value="修改时间")
    @ColumnAnnotation(FieldName = "UPDATE_TIME",   FieldDescription="修改时间")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date updateTime;

    @ApiModelProperty(value="证照领取电子签名(BASE64数据)")
    @ColumnAnnotation(FieldName = "CER_GET_ELE_SIGN",   FieldDescription="证照领取电子签名(BASE64数据)")
    private String cerGetEleSign;

    @ApiModelProperty(value="审批表领取电子签名(BASE64数据)")
    @ColumnAnnotation(FieldName = "SPB_GET_ELE_SIGN",   FieldDescription="审批表领取电子签名(BASE64数据)")
    private String spbGetEleSign;

    @ApiModelProperty(value="备注")
    @ColumnAnnotation(FieldName = "REMARKS",   FieldDescription="备注")
    private String remarks;

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

    public String getCerId() {
        return cerId;
    }

    public void setCerId(String cerId) {
        this.cerId = cerId == null ? null : cerId.trim();
    }

    public String getBusiId() {
        return busiId;
    }

    public void setBusiId(String busiId) {
        this.busiId = busiId == null ? null : busiId.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getRfB0000() {
        return rfB0000;
    }

    public void setRfB0000(String rfB0000) {
        this.rfB0000 = rfB0000 == null ? null : rfB0000.trim();
    }

    public Integer getZjlx() {
        return zjlx;
    }

    public void setZjlx(Integer zjlx) {
        this.zjlx = zjlx;
    }

    public String getZjhm() {
        return zjhm;
    }

    public void setZjhm(String zjhm) {
        this.zjhm = zjhm == null ? null : zjhm.trim();
    }

    public String getGetStatus() {
        return getStatus;
    }

    public void setGetStatus(String getStatus) {
        this.getStatus = getStatus == null ? null : getStatus.trim();
    }

    public Date getHappenDate() {
        return happenDate;
    }

    public void setHappenDate(Date happenDate) {
        this.happenDate = happenDate;
    }

    public String getDataSource() {
        return dataSource;
    }

    public void setDataSource(String dataSource) {
        this.dataSource = dataSource == null ? null : dataSource.trim();
    }

    public String getGetPeople() {
        return getPeople;
    }

    public void setGetPeople(String getPeople) {
        this.getPeople = getPeople == null ? null : getPeople.trim();
    }

    public Date getGetTime() {
        return getTime;
    }

    public void setGetTime(Date getTime) {
        this.getTime = getTime;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator == null ? null : creator.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUpdator() {
        return updator;
    }

    public void setUpdator(String updator) {
        this.updator = updator == null ? null : updator.trim();
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getCerGetEleSign() {
        return cerGetEleSign;
    }

    public void setCerGetEleSign(String cerGetEleSign) {
        this.cerGetEleSign = cerGetEleSign == null ? null : cerGetEleSign.trim();
    }

    public String getSpbGetEleSign() {
        return spbGetEleSign;
    }

    public void setSpbGetEleSign(String spbGetEleSign) {
        this.spbGetEleSign = spbGetEleSign == null ? null : spbGetEleSign.trim();
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks == null ? null : remarks.trim();
    }
}