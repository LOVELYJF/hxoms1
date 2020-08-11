package com.hxoms.modules.keySupervision.suspendApproval.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hxoms.common.hxannotation.ColumnAnnotation;
import com.hxoms.common.hxannotation.IdAnnotation;
import com.hxoms.common.hxannotation.TableAnnotation;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

@TableAnnotation(TableName = "oms_sup_suspend_unit", TableDescription="暂停出国境单位")
public class OmsSupSuspendUnit {
    @IdAnnotation
    @ColumnAnnotation(FieldName = "ID",   FieldDescription="主键")
    private String id;

    @ColumnAnnotation(FieldName = "MODIFY_USER",   FieldDescription="修改用户")
    private String modifyUser;

    @ColumnAnnotation(FieldName = "MODIFY_TIME",   FieldDescription="修改时间")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date modifyTime;

    @ColumnAnnotation(FieldName = "UNIT",   FieldDescription="单位名称")
    private String unit;

    @ColumnAnnotation(FieldName = "B0100",   FieldDescription="单位主键")
    private String b0100;

    @ColumnAnnotation(FieldName = "SUSPEND_TIME",   FieldDescription="暂停开始时间")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date suspendTime;

    @ColumnAnnotation(FieldName = "PAUSE_TIME",   FieldDescription="暂停时长（月）")
    private String pauseTime;

    @ColumnAnnotation(FieldName = "SUSPEND_REASON",   FieldDescription="暂停原因")
    private String suspendReason;

    @ColumnAnnotation(FieldName = "STATUS",   FieldDescription="状态")
    private String status;

    @ColumnAnnotation(FieldName = "SUSPEND_STRAT_TIME_QUERY",   FieldDescription="暂停状态查询开始时间")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date suspendStratTimeQuery;

    @ColumnAnnotation(FieldName = "SUSPEND_END_TIME_QUERY",   FieldDescription="暂停状态查询结束时间")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date suspendEndTimeQuery;

    @ColumnAnnotation(FieldName = "CREATE_USER",   FieldDescription="暂停操作人")
    private String createUser;

    @ColumnAnnotation(FieldName = "CREATE_TIME",   FieldDescription="创建时间")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date createTime;

    @ColumnAnnotation(FieldName = "RECOVERY_USER",   FieldDescription="暂停操作人")
    private String recoverUser;

    @ColumnAnnotation(FieldName = "RECOVERY_TIME",   FieldDescription="恢复时间")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date recoverTime;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
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

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit == null ? null : unit.trim();
    }

    public String getB0100() {
        return b0100;
    }

    public void setB0100(String b0100) {
        this.b0100 = b0100 == null ? null : b0100.trim();
    }

    public Date getSuspendTime() {
        return suspendTime;
    }

    public void setSuspendTime(Date suspendTime) {
        this.suspendTime = suspendTime;
    }

    public String getSuspendReason() {
        return suspendReason;
    }

    public void setSuspendReason(String suspendReason) {
        this.suspendReason = suspendReason == null ? null : suspendReason.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public Date getSuspendStratTimeQuery() {
        return suspendStratTimeQuery;
    }

    public void setSuspendStratTimeQuery(Date suspendStratTimeQuery) {
        this.suspendStratTimeQuery = suspendStratTimeQuery;
    }

    public Date getSuspendEndTimeQuery() {
        return suspendEndTimeQuery;
    }

    public void setSuspendEndTimeQuery(Date suspendEndTimeQuery) {
        this.suspendEndTimeQuery = suspendEndTimeQuery;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser == null ? null : createUser.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getPauseTime() {
        return pauseTime;
    }

    public void setPauseTime(String pauseTime) {
        this.pauseTime = pauseTime == null ? null : pauseTime.trim();
    }

    public String getRevoverUser() {
        return recoverUser;
    }

    public void setRecoverUser(String recoverUser) {
        this.recoverUser = recoverUser == null ? null : recoverUser.trim();
    }

    public Date getRecoverTime() {
        return recoverTime;
    }

    public void setRecoverTime(Date recoverTime) {
        this.recoverTime = recoverTime;
    }
}