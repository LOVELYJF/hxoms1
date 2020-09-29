package com.hxoms.modules.omsoperator.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hxoms.common.hxannotation.ColumnAnnotation;
import com.hxoms.common.hxannotation.IdAnnotation;
import com.hxoms.common.hxannotation.TableAnnotation;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@TableAnnotation(TableName = "a17", TableDescription="")
@ApiModel(value = "")
public class A17 {
    @IdAnnotation
    @ColumnAnnotation(FieldName = "a1700",   FieldDescription="")
    @ApiModelProperty(value="")
    private String a1700;

    @ColumnAnnotation(FieldName = "a1704",   FieldDescription="")
    @ApiModelProperty(value="")
    private String a1704;

    @ColumnAnnotation(FieldName = "a1705",   FieldDescription="")
    @ApiModelProperty(value="")
    private String a1705;

    @ColumnAnnotation(FieldName = "is_deleted",   FieldDescription="")
    @ApiModelProperty(value="")
    private String isDeleted;

    @ColumnAnnotation(FieldName = "a1708",   FieldDescription="")
    @ApiModelProperty(value="")
    private String a1708;

    @ColumnAnnotation(FieldName = "a0100",   FieldDescription="")
    @ApiModelProperty(value="")
    private String a0100;

    @ColumnAnnotation(FieldName = "id",   FieldDescription="")
    @ApiModelProperty(value="")
    private String id;

    @ColumnAnnotation(FieldName = "modify_user",   FieldDescription="")
    @ApiModelProperty(value="")
    private String modifyUser;

    @ColumnAnnotation(FieldName = "modify_time",   FieldDescription="")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    @ApiModelProperty(value="")
    private Date modifyTime;

    @ColumnAnnotation(FieldName = "node_id",   FieldDescription="")
    @ApiModelProperty(value="")
    private String nodeId;

    @ColumnAnnotation(FieldName = "A1701",   FieldDescription="起始时间")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    @ApiModelProperty(value="起始时间")
    private Date a1701;

    @ColumnAnnotation(FieldName = "A1702",   FieldDescription="截止时间")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    @ApiModelProperty(value="截止时间")
    private Date a1702;

    @ColumnAnnotation(FieldName = "a1706",   FieldDescription="")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    @ApiModelProperty(value="")
    private Date a1706;

    @ColumnAnnotation(FieldName = "a1707",   FieldDescription="")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    @ApiModelProperty(value="")
    private Date a1707;

    @ColumnAnnotation(FieldName = "is_normal",   FieldDescription="")
    @ApiModelProperty(value="")
    private String isNormal;

    @ColumnAnnotation(FieldName = "operate_batch",   FieldDescription="")
    @ApiModelProperty(value="")
    private String operateBatch;

    @ColumnAnnotation(FieldName = "a1703",   FieldDescription="")
    @ApiModelProperty(value="")
    private String a1703;

    @ColumnAnnotation(FieldName = "time_stamp",   FieldDescription="")
    @ApiModelProperty(value="")
    private byte[] timeStamp;

    public String getA1700() {
        return a1700;
    }

    public void setA1700(String a1700) {
        this.a1700 = a1700 == null ? null : a1700.trim();
    }

    public String getA1704() {
        return a1704;
    }

    public void setA1704(String a1704) {
        this.a1704 = a1704 == null ? null : a1704.trim();
    }

    public String getA1705() {
        return a1705;
    }

    public void setA1705(String a1705) {
        this.a1705 = a1705 == null ? null : a1705.trim();
    }

    public String getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(String isDeleted) {
        this.isDeleted = isDeleted == null ? null : isDeleted.trim();
    }

    public String getA1708() {
        return a1708;
    }

    public void setA1708(String a1708) {
        this.a1708 = a1708 == null ? null : a1708.trim();
    }

    public String getA0100() {
        return a0100;
    }

    public void setA0100(String a0100) {
        this.a0100 = a0100 == null ? null : a0100.trim();
    }

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

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId == null ? null : nodeId.trim();
    }

    public Date getA1701() {
        return a1701;
    }

    public void setA1701(Date a1701) {
        this.a1701 = a1701;
    }

    public Date getA1702() {
        return a1702;
    }

    public void setA1702(Date a1702) {
        this.a1702 = a1702;
    }

    public Date getA1706() {
        return a1706;
    }

    public void setA1706(Date a1706) {
        this.a1706 = a1706;
    }

    public Date getA1707() {
        return a1707;
    }

    public void setA1707(Date a1707) {
        this.a1707 = a1707;
    }

    public String getIsNormal() {
        return isNormal;
    }

    public void setIsNormal(String isNormal) {
        this.isNormal = isNormal == null ? null : isNormal.trim();
    }

    public String getOperateBatch() {
        return operateBatch;
    }

    public void setOperateBatch(String operateBatch) {
        this.operateBatch = operateBatch == null ? null : operateBatch.trim();
    }

    public String getA1703() {
        return a1703;
    }

    public void setA1703(String a1703) {
        this.a1703 = a1703 == null ? null : a1703.trim();
    }

    public byte[] getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(byte[] timeStamp) {
        this.timeStamp = timeStamp;
    }
}