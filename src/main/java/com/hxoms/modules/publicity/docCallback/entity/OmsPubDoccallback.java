package com.hxoms.modules.publicity.docCallback.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hxoms.common.hxannotation.ColumnAnnotation;
import com.hxoms.common.hxannotation.IdAnnotation;
import com.hxoms.common.hxannotation.TableAnnotation;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

@TableAnnotation(TableName = "oms_pub_doccallback", TableDescription="因公备案批件回收登记表")
public class OmsPubDoccallback {
    @IdAnnotation
    @ColumnAnnotation(FieldName = "ID",   FieldDescription="主键")
    private String id;

    @ColumnAnnotation(FieldName = "USER_ID",   FieldDescription="用户表ID")
    private String userId;

    @ColumnAnnotation(FieldName = "RECEIVER",   FieldDescription="接收人")
    private String receiver;

    @ColumnAnnotation(FieldName = "ORG_ID",   FieldDescription="机构ID")
    private String orgId;

    @ColumnAnnotation(FieldName = "MOBILE",   FieldDescription="电话")
    private String mobile;

    @ColumnAnnotation(FieldName = "RECEIVE_TIME",   FieldDescription="接收时间")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date receiveTime;

    @ColumnAnnotation(FieldName = "RECEIVE_DESC",   FieldDescription="接收说明")
    private String receiveDesc;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver == null ? null : receiver.trim();
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId == null ? null : orgId.trim();
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public Date getReceiveTime() {
        return receiveTime;
    }

    public void setReceiveTime(Date receiveTime) {
        this.receiveTime = receiveTime;
    }

    public String getReceiveDesc() {
        return receiveDesc;
    }

    public void setReceiveDesc(String receiveDesc) {
        this.receiveDesc = receiveDesc == null ? null : receiveDesc.trim();
    }
}