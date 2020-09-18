package com.hxoms.modules.passportCard.certificateCollect.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hxoms.common.hxannotation.ColumnAnnotation;
import com.hxoms.common.hxannotation.IdAnnotation;
import com.hxoms.common.hxannotation.TableAnnotation;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@TableAnnotation(TableName = "cf_certificate_collection", TableDescription="证照催缴表")
@ApiModel(value = "证照催缴表")
public class CfCertificateCollection {
    @IdAnnotation
    @ColumnAnnotation(FieldName = "ID",   FieldDescription="主键")
    @ApiModelProperty(value="主键")
    private String id;

    @ColumnAnnotation(FieldName = "OMS_ID",   FieldDescription="备案信息表ID")
    @ApiModelProperty(value="备案信息表ID")
    private String omsId;

    @ColumnAnnotation(FieldName = "CER_ID",   FieldDescription="证照管理表ID")
    @ApiModelProperty(value="证照管理表ID")
    private String cerId;

    @ColumnAnnotation(FieldName = "BUSI_ID",   FieldDescription="业务表ID")
    @ApiModelProperty(value="业务表ID")
    private String busiId;

    @ColumnAnnotation(FieldName = "NAME",   FieldDescription="姓名")
    @ApiModelProperty(value="姓名")
    private String name;

    @ColumnAnnotation(FieldName = "RF_B0000",   FieldDescription="机构")
    @ApiModelProperty(value="机构")
    private String rfB0000;

    @ColumnAnnotation(FieldName = "WORK_UNIT",   FieldDescription="工作单位")
    @ApiModelProperty(value="工作单位")
    private String workUnit;

    @ColumnAnnotation(FieldName = "ZJLX",   FieldDescription="证件类型(1:护照,2:港澳通行证,4:台湾通行证)")
    @ApiModelProperty(value="证件类型(1:护照,2:港澳通行证,4:台湾通行证)")
    private Integer zjlx;

    @ColumnAnnotation(FieldName = "ZJHM",   FieldDescription="证件号码")
    @ApiModelProperty(value="证件号码")
    private String zjhm;

    @ColumnAnnotation(FieldName = "CJ_WAY",   FieldDescription="催缴方式(0:电话催缴,1:短信催缴)")
    @ApiModelProperty(value="催缴方式(0:电话催缴,1:短信催缴)")
    private String cjWay;

    @ColumnAnnotation(FieldName = "HAPPEN_DATE",   FieldDescription="业务发生日期")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    @ApiModelProperty(value="业务发生日期")
    private Date happenDate;

    @ColumnAnnotation(FieldName = "RETURN_DATE",   FieldDescription="归还日期")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    @ApiModelProperty(value="归还日期")
    private Date returnDate;

    @ColumnAnnotation(FieldName = "REAL_RETURN_DATE",   FieldDescription="实际归还日期")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    @ApiModelProperty(value="实际归还日期")
    private Date realReturnDate;

    @ColumnAnnotation(FieldName = "DATA_SOURCE",   FieldDescription="数据来源(0:登记备案,1:因私出国(境),2:证照借出,3:撤销出国申请)")
    @ApiModelProperty(value="数据来源(0:登记备案,1:因私出国(境),2:证照借出,3:撤销出国申请)")
    private String dataSource;

    @ColumnAnnotation(FieldName = "CJ_STATUS",   FieldDescription="催缴状态(0:手动解除,1:已上缴,2:未上缴,3:自动解除)")
    @ApiModelProperty(value="催缴状态(0:手动解除,1:已上缴,2:未上缴,3:自动解除)")
    private String cjStatus;

    @ColumnAnnotation(FieldName = "CJ_RESULT",   FieldDescription="催缴结果")
    @ApiModelProperty(value="催缴结果")
    private String cjResult;

    @ColumnAnnotation(FieldName = "REMOVE_CJ_DESC",   FieldDescription="解除催缴说明")
    @ApiModelProperty(value="解除催缴说明")
    private String removeCjDesc;

    @ColumnAnnotation(FieldName = "CREATOR",   FieldDescription="创建人")
    @ApiModelProperty(value="创建人")
    private String creator;

    @ColumnAnnotation(FieldName = "CREATETIME",   FieldDescription="创建时间")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    @ApiModelProperty(value="创建时间")
    private Date createtime;

    @ColumnAnnotation(FieldName = "UPDATETIME",   FieldDescription="修改时间")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    @ApiModelProperty(value="修改时间")
    private Date updatetime;

    @ColumnAnnotation(FieldName = "UPDATOR",   FieldDescription="修改人")
    @ApiModelProperty(value="修改人")
    private String updator;

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

    public String getWorkUnit() {
        return workUnit;
    }

    public void setWorkUnit(String workUnit) {
        this.workUnit = workUnit == null ? null : workUnit.trim();
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

    public String getCjWay() {
        return cjWay;
    }

    public void setCjWay(String cjWay) {
        this.cjWay = cjWay == null ? null : cjWay.trim();
    }

    public Date getHappenDate() {
        return happenDate;
    }

    public void setHappenDate(Date happenDate) {
        this.happenDate = happenDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public Date getRealReturnDate() {
        return realReturnDate;
    }

    public void setRealReturnDate(Date realReturnDate) {
        this.realReturnDate = realReturnDate;
    }

    public String getDataSource() {
        return dataSource;
    }

    public void setDataSource(String dataSource) {
        this.dataSource = dataSource == null ? null : dataSource.trim();
    }

    public String getCjStatus() {
        return cjStatus;
    }

    public void setCjStatus(String cjStatus) {
        this.cjStatus = cjStatus == null ? null : cjStatus.trim();
    }

    public String getCjResult() {
        return cjResult;
    }

    public void setCjResult(String cjResult) {
        this.cjResult = cjResult == null ? null : cjResult.trim();
    }

    public String getRemoveCjDesc() {
        return removeCjDesc;
    }

    public void setRemoveCjDesc(String removeCjDesc) {
        this.removeCjDesc = removeCjDesc == null ? null : removeCjDesc.trim();
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

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public String getUpdator() {
        return updator;
    }

    public void setUpdator(String updator) {
        this.updator = updator == null ? null : updator.trim();
    }
}