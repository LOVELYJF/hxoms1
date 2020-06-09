package com.hxoms.modules.passportCard.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hxoms.common.hxannotation.ColumnAnnotation;
import com.hxoms.common.hxannotation.TableAnnotation;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

@TableAnnotation(TableName = "cf_certificate_collection", TableDescription="")
public class CfCertificateCollection {
    @ColumnAnnotation(FieldName = "ID",   FieldDescription="保存时间")
    private String id;

    @ColumnAnnotation(FieldName = "COLLECTION_ID",   FieldDescription="催缴人")
    private String collectionId;


    @ColumnAnnotation(FieldName = "CF_ID",   FieldDescription="证件号码ID，关联证件表主键ID")
    private String cfId;

    @ColumnAnnotation(FieldName = "COLLECTION_TIME",   FieldDescription="催缴时间")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date collectionTime;

    @ColumnAnnotation(FieldName = "RESPONSIBLE_USER",   FieldDescription="经办人")
    private String responsibleUser;

    @ColumnAnnotation(FieldName = "RESPONSIBLE_PHONE",   FieldDescription="经办人联系方式")
    private String responsiblePhone;

    @ColumnAnnotation(FieldName = "SAVE_TIME",   FieldDescription="保存时间")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date saveTime;

    @ColumnAnnotation(FieldName = "COLLECTION_CONTEXT",   FieldDescription="催缴内容")
    private String collectionContext;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getCollectionId() {
        return collectionId;
    }

    public void setCollectionId(String collectionId) {
        this.collectionId = collectionId == null ? null : collectionId.trim();
    }

    public Date getCollectionTime() {
        return collectionTime;
    }

    public void setCollectionTime(Date collectionTime) {
        this.collectionTime = collectionTime;
    }

    public String getResponsibleUser() {
        return responsibleUser;
    }

    public void setResponsibleUser(String responsibleUser) {
        this.responsibleUser = responsibleUser == null ? null : responsibleUser.trim();
    }

    public String getResponsiblePhone() {
        return responsiblePhone;
    }

    public void setResponsiblePhone(String responsiblePhone) {
        this.responsiblePhone = responsiblePhone == null ? null : responsiblePhone.trim();
    }

    public Date getSaveTime() {
        return saveTime;
    }

    public void setSaveTime(Date saveTime) {
        this.saveTime = saveTime;
    }

    public String getCollectionContext() {
        return collectionContext;
    }

    public void setCollectionContext(String collectionContext) {
        this.collectionContext = collectionContext == null ? null : collectionContext.trim();
    }

    public String getCfId() {
        return cfId;
    }

    public void setCfId(String cfId) {
        this.cfId = cfId;
    }
}