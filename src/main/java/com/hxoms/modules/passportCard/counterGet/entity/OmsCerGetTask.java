package com.hxoms.modules.passportCard.counterGet.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hxoms.common.hxannotation.ColumnAnnotation;
import com.hxoms.common.hxannotation.IdAnnotation;
import com.hxoms.common.hxannotation.TableAnnotation;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

@TableAnnotation(TableName = "oms_cer_get_task", TableDescription="证照领取任务表")
public class OmsCerGetTask {
    @IdAnnotation
    @ColumnAnnotation(FieldName = "ID",   FieldDescription="主键")
    private String id;

    @ColumnAnnotation(FieldName = "OMS_ID",   FieldDescription="备案信息表ID")
    private String omsId;

    @ColumnAnnotation(FieldName = "CER_ID",   FieldDescription="证照管理表ID")
    private String cerId;

    @ColumnAnnotation(FieldName = "BUSI_ID",   FieldDescription="业务表ID")
    private String busiId;

    @ColumnAnnotation(FieldName = "NAME",   FieldDescription="姓名")
    private String name;

    @ColumnAnnotation(FieldName = "RF_B0000",   FieldDescription="机构")
    private String rfB0000;

    @ColumnAnnotation(FieldName = "ZJLX",   FieldDescription="证件类型(1:护照,2:港澳通行证,4:台湾通行证)")
    private Integer zjlx;

    @ColumnAnnotation(FieldName = "ZJHM",   FieldDescription="证件号码")
    private String zjhm;

    @ColumnAnnotation(FieldName = "GET_STATUS",   FieldDescription="领取状态(0:未领取,1:已领取)")
    private String getStatus;

    @ColumnAnnotation(FieldName = "DATA_SOURCE",   FieldDescription="数据来源(0:因私出国(境),1:撤销登记备案,2:证照借出,3:证照过期,4:管理员取证)")
    private String dataSource;

    @ColumnAnnotation(FieldName = "CREATOR",   FieldDescription="创建人")
    private String creator;

    @ColumnAnnotation(FieldName = "CREATETIME",   FieldDescription="创建时间")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date createtime;

    @ColumnAnnotation(FieldName = "UPDATOR",   FieldDescription="修改人")
    private String updator;

    @ColumnAnnotation(FieldName = "UPDATETIME",   FieldDescription="修改时间")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date updatetime;

    @ColumnAnnotation(FieldName = "CER_GET_ELE_SIGN",   FieldDescription="证照领取电子签名(BASE64数据)")
    private String cerGetEleSign;

    @ColumnAnnotation(FieldName = "SPB_GET_ELE_SIGN",   FieldDescription="审批表领取电子签名(BASE64数据)")
    private String spbGetEleSign;

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

    public String getDataSource() {
        return dataSource;
    }

    public void setDataSource(String dataSource) {
        this.dataSource = dataSource == null ? null : dataSource.trim();
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator == null ? null : creator.trim();
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getUpdator() {
        return updator;
    }

    public void setUpdator(String updator) {
        this.updator = updator == null ? null : updator.trim();
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
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
}