package com.hxoms.modules.passportCard.certificateCollect.entity.parameterEntity;


import java.util.Date;

public class CfCertificateCollectionRequestParam {
    private Integer pageNum;

    private Integer pageSize;

    private String id;

    private String worksheetId;

    private String cfId;

    private String name;

    private Integer sex;

    private String registeredLocation;

    private String cfNum;

    private Integer cjStatus;

    private String unitPerson;

    private String phone;

    private String dataSource;

    private Date happenTime;

    private Date returnTime;

    private Integer cjCondition;

    private String cjResult;

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

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }
}
