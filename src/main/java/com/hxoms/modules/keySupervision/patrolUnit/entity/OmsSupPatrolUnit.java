package com.hxoms.modules.keySupervision.patrolUnit.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hxoms.common.hxannotation.ColumnAnnotation;
import com.hxoms.common.hxannotation.IdAnnotation;
import com.hxoms.common.hxannotation.TableAnnotation;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

@TableAnnotation(TableName = "oms_sup_patrol_unit", TableDescription="被巡视单位信息表")
public class OmsSupPatrolUnit {
    @IdAnnotation
    @ColumnAnnotation(FieldName = "id",   FieldDescription="主键")
    private String id;

    @ColumnAnnotation(FieldName = "modify_user",   FieldDescription="修改用户")
    private String modifyUser;

    @ColumnAnnotation(FieldName = "modify_time",   FieldDescription="修改时间")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date modifyTime;

    @ColumnAnnotation(FieldName = "B0100",   FieldDescription="单位主键")
    private String b0100;

    @ColumnAnnotation(FieldName = "UNIT",   FieldDescription="被巡视单位")
    private String unit;

    @ColumnAnnotation(FieldName = "PATROL_START_TIME",   FieldDescription="巡视开始时间")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date patrolStartTime;

    @ColumnAnnotation(FieldName = "PATROL_END_TIME",   FieldDescription="巡视结束时间")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date patrolEndTime;

    @ColumnAnnotation(FieldName = "PATROL_START_TIME_QUERY",   FieldDescription="巡视查询开始时间")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date patrolStartTimeQuery;

    @ColumnAnnotation(FieldName = "PATROL_END_TIME_QUERY",   FieldDescription="巡视查询结束时间")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date patrolEndTimeQuery;

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

    public String getB0100() {
        return b0100;
    }

    public void setB0100(String b0100) {
        this.b0100 = b0100 == null ? null : b0100.trim();
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit == null ? null : unit.trim();
    }

    public Date getPatrolStartTime() {
        return patrolStartTime;
    }

    public void setPatrolStartTime(Date patrolStartTime) {
        this.patrolStartTime = patrolStartTime;
    }

    public Date getPatrolEndTime() {
        return patrolEndTime;
    }

    public void setPatrolEndTime(Date patrolEndTime) {
        this.patrolEndTime = patrolEndTime;
    }

    public Date getPatrolStartTimeQuery() {
        return patrolStartTimeQuery;
    }

    public void setPatrolStartTimeQuery(Date patrolStartTimeQuery) {
        this.patrolStartTimeQuery = patrolStartTimeQuery;
    }

    public Date getPatrolEndTimeQuery() {
        return patrolEndTimeQuery;
    }

    public void setPatrolEndTimeQuery(Date patrolEndTimeQuery) {
        this.patrolEndTimeQuery = patrolEndTimeQuery;
    }
}