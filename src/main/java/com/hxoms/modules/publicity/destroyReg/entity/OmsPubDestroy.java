package com.hxoms.modules.publicity.destroyReg.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hxoms.common.hxannotation.ColumnAnnotation;
import com.hxoms.common.hxannotation.IdAnnotation;
import com.hxoms.common.hxannotation.TableAnnotation;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

@TableAnnotation(TableName = "oms_pub_destroy", TableDescription="因公备案销毁登记表")
public class OmsPubDestroy {
    @IdAnnotation
    @ColumnAnnotation(FieldName = "ID",   FieldDescription="主键")
    private String id;

    @ColumnAnnotation(FieldName = "USER_ID",   FieldDescription="用户表ID")
    private String userId;

    @ColumnAnnotation(FieldName = "DESTROYER",   FieldDescription="销毁人")
    private String destroyer;

    @ColumnAnnotation(FieldName = "ORG_ID",   FieldDescription="机构ID")
    private String orgId;

    @ColumnAnnotation(FieldName = "MOBILE",   FieldDescription="电话")
    private String mobile;

    @ColumnAnnotation(FieldName = "DESTROY_TIME",   FieldDescription="销毁时间")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date destroyTime;

    @ColumnAnnotation(FieldName = "DESTROY_DESC",   FieldDescription="销毁说明")
    private String destroyDesc;

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

    public String getDestroyer() {
        return destroyer;
    }

    public void setDestroyer(String destroyer) {
        this.destroyer = destroyer == null ? null : destroyer.trim();
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

    public Date getDestroyTime() {
        return destroyTime;
    }

    public void setDestroyTime(Date destroyTime) {
        this.destroyTime = destroyTime;
    }

    public String getDestroyDesc() {
        return destroyDesc;
    }

    public void setDestroyDesc(String destroyDesc) {
        this.destroyDesc = destroyDesc == null ? null : destroyDesc.trim();
    }
}