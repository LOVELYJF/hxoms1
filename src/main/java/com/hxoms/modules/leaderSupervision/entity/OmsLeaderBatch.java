package com.hxoms.modules.leaderSupervision.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hxoms.common.hxannotation.ColumnAnnotation;
import com.hxoms.common.hxannotation.IdAnnotation;
import com.hxoms.common.hxannotation.TableAnnotation;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@TableAnnotation(TableName = "oms_leader_batch", TableDescription="干部监督处管理 批次表 ")
public class OmsLeaderBatch {
    @IdAnnotation
    @ColumnAnnotation(FieldName = "id",   FieldDescription="批次ID")
    private String id;

    @ColumnAnnotation(FieldName = "name",   FieldDescription="批次号")
    private String name;

    @ColumnAnnotation(FieldName = "accept_date",   FieldDescription="受理时间")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date acceptDate;

    @ColumnAnnotation(FieldName = "create_time",   FieldDescription="创建时间")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date createTime;

    @ColumnAnnotation(FieldName = "create_user",   FieldDescription="创建人")
    private String createUser;

    @ColumnAnnotation(FieldName = "master_status",   FieldDescription="主状态")
    private String masterStatus;

    @ColumnAnnotation(FieldName = "slave_status",   FieldDescription="副状态")
    private String slaveStatus;

    @ColumnAnnotation(FieldName = "modify_user",   FieldDescription="修改人")
    private String modifyUser;

    @ColumnAnnotation(FieldName = "modify_time",   FieldDescription="修改时间")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date modifyTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Date getAcceptDate() {
        return acceptDate;
    }

    public void setAcceptDate(Date acceptDate) {
        this.acceptDate = acceptDate;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser == null ? null : createUser.trim();
    }

    public String getMasterStatus() {
        return masterStatus;
    }

    public void setMasterStatus(String masterStatus) {
        this.masterStatus = masterStatus == null ? null : masterStatus.trim();
    }

    public String getSlaveStatus() {
        return slaveStatus;
    }

    public void setSlaveStatus(String slaveStatus) {
        this.slaveStatus = slaveStatus == null ? null : slaveStatus.trim();
    }

    public String getModifyUser() {
        return modifyUser;
    }

    public void setModifyUser(String modifyUser) {
        this.modifyUser = modifyUser == null ? null : modifyUser.trim();
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }
}