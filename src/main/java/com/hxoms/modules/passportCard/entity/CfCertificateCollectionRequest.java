package com.hxoms.modules.passportCard.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hxoms.common.hxannotation.ColumnAnnotation;
import com.hxoms.common.hxannotation.TableAnnotation;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@TableAnnotation(TableName = "cf_certificate_collection_request", TableDescription="")
public class CfCertificateCollectionRequest {

    @ColumnAnnotation(FieldName = "ID",   FieldDescription="")
    private String id;

    @ColumnAnnotation(FieldName = "WORKSHEET_ID",   FieldDescription="工作单ID")
    private String worksheetId;

    @ColumnAnnotation(FieldName = "CF_ID",   FieldDescription="护照ID")
    private String cfId;

    @ColumnAnnotation(FieldName = "REGISTERED_LOCATION",   FieldDescription="户口所在地")
    private String registeredLocation;

    @ColumnAnnotation(FieldName = "CF_NUM",   FieldDescription="护照号码")
    private String cfNum;

    @ColumnAnnotation(FieldName = "CJ_STATUS",   FieldDescription="催缴状态0:解除，1：已上缴，2:未上缴")
    private Integer cjStatus;

    @ColumnAnnotation(FieldName = "UNIT_PERSON",   FieldDescription="单位联系人")
    private String unitPerson;

    @ColumnAnnotation(FieldName = "PHONE",   FieldDescription="联系电话")
    private String phone;

    @ColumnAnnotation(FieldName = "DATA_SOURCE",   FieldDescription="数据来源")
    private String dataSource;

    @ColumnAnnotation(FieldName = "HAPPEN_TIME",   FieldDescription="业务发生时间")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date happenTime;

    @ColumnAnnotation(FieldName = "RETURN_TIME",   FieldDescription="归还时间")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date returnTime;

    @ColumnAnnotation(FieldName = "CJ_CONDITION",   FieldDescription="催缴情况0:电话催缴，1:短信催缴")
    private Integer cjCondition;

    @ColumnAnnotation(FieldName = "CJ_RESULT",   FieldDescription="催缴结果")
    private String cjResult;

    @ColumnAnnotation(FieldName = "IS_VALID",   FieldDescription="是否有效0:有效，1:无效")
    private Integer isValid;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getWorksheetId() {
        return worksheetId;
    }

    public void setWorksheetId(String worksheetId) {
        this.worksheetId = worksheetId == null ? null : worksheetId.trim();
    }

    public String getCfId() {
        return cfId;
    }

    public void setCfId(String cfId) {
        this.cfId = cfId == null ? null : cfId.trim();
    }

    public String getRegisteredLocation() {
        return registeredLocation;
    }

    public void setRegisteredLocation(String registeredLocation) {
        this.registeredLocation = registeredLocation == null ? null : registeredLocation.trim();
    }

    public String getCfNum() {
        return cfNum;
    }

    public void setCfNum(String cfNum) {
        this.cfNum = cfNum == null ? null : cfNum.trim();
    }

    public Integer getCjStatus() {
        return cjStatus;
    }

    public void setCjStatus(Integer cjStatus) {
        this.cjStatus = cjStatus;
    }

    public String getUnitPerson() {
        return unitPerson;
    }

    public void setUnitPerson(String unitPerson) {
        this.unitPerson = unitPerson == null ? null : unitPerson.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getDataSource() {
        return dataSource;
    }

    public void setDataSource(String dataSource) {
        this.dataSource = dataSource == null ? null : dataSource.trim();
    }

    public Date getHappenTime() {
        return happenTime;
    }

    public void setHappenTime(Date happenTime) {
        this.happenTime = happenTime;
    }

    public Date getReturnTime() {
        return returnTime;
    }

    public void setReturnTime(Date returnTime) {
        this.returnTime = returnTime;
    }

    public Integer getCjCondition() {
        return cjCondition;
    }

    public void setCjCondition(Integer cjCondition) {
        this.cjCondition = cjCondition;
    }

    public String getCjResult() {
        return cjResult;
    }

    public void setCjResult(String cjResult) {
        this.cjResult = cjResult == null ? null : cjResult.trim();
    }

    public Integer getIsValid() {
        return isValid;
    }

    public void setIsValid(Integer isValid) {
        this.isValid = isValid;
    }
}