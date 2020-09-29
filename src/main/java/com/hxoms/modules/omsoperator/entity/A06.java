package com.hxoms.modules.omsoperator.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hxoms.common.hxannotation.ColumnAnnotation;
import com.hxoms.common.hxannotation.IdAnnotation;
import com.hxoms.common.hxannotation.TableAnnotation;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@TableAnnotation(TableName = "a06", TableDescription="")
@ApiModel(value = "")
public class A06 {
    @IdAnnotation
    @ColumnAnnotation(FieldName = "a0600",   FieldDescription="")
    @ApiModelProperty(value="")
    private String a0600;

    @ColumnAnnotation(FieldName = "a0100",   FieldDescription="")
    @ApiModelProperty(value="")
    private String a0100;

    @ColumnAnnotation(FieldName = "a0601",   FieldDescription="")
    @ApiModelProperty(value="")
    private String a0601;

    @ColumnAnnotation(FieldName = "a0602",   FieldDescription="")
    @ApiModelProperty(value="")
    private String a0602;

    @ColumnAnnotation(FieldName = "a0604",   FieldDescription="")
    @ApiModelProperty(value="")
    private String a0604;

    @ColumnAnnotation(FieldName = "a0607",   FieldDescription="")
    @ApiModelProperty(value="")
    private String a0607;

    @ColumnAnnotation(FieldName = "a0611",   FieldDescription="")
    @ApiModelProperty(value="")
    private String a0611;

    @ColumnAnnotation(FieldName = "modify_user",   FieldDescription="")
    @ApiModelProperty(value="")
    private String modifyUser;

    @ColumnAnnotation(FieldName = "modify_time",   FieldDescription="")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    @ApiModelProperty(value="")
    private Date modifyTime;

    @ColumnAnnotation(FieldName = "is_deleted",   FieldDescription="")
    @ApiModelProperty(value="")
    private String isDeleted;

    @ColumnAnnotation(FieldName = "id",   FieldDescription="")
    @ApiModelProperty(value="")
    private String id;

    @ColumnAnnotation(FieldName = "node_id",   FieldDescription="")
    @ApiModelProperty(value="")
    private String nodeId;

    @ColumnAnnotation(FieldName = "prolevel",   FieldDescription="")
    @ApiModelProperty(value="")
    private String prolevel;

    @ColumnAnnotation(FieldName = "a0699",   FieldDescription="")
    @ApiModelProperty(value="")
    private String a0699;

    @ColumnAnnotation(FieldName = "operate_batch",   FieldDescription="")
    @ApiModelProperty(value="")
    private String operateBatch;

    @ColumnAnnotation(FieldName = "is_normal",   FieldDescription="")
    @ApiModelProperty(value="")
    private String isNormal;

    @ColumnAnnotation(FieldName = "time_stamp",   FieldDescription="")
    @ApiModelProperty(value="")
    private byte[] timeStamp;

    public String getA0600() {
        return a0600;
    }

    public void setA0600(String a0600) {
        this.a0600 = a0600 == null ? null : a0600.trim();
    }

    public String getA0100() {
        return a0100;
    }

    public void setA0100(String a0100) {
        this.a0100 = a0100 == null ? null : a0100.trim();
    }

    public String getA0601() {
        return a0601;
    }

    public void setA0601(String a0601) {
        this.a0601 = a0601 == null ? null : a0601.trim();
    }

    public String getA0602() {
        return a0602;
    }

    public void setA0602(String a0602) {
        this.a0602 = a0602 == null ? null : a0602.trim();
    }

    public String getA0604() {
        return a0604;
    }

    public void setA0604(String a0604) {
        this.a0604 = a0604 == null ? null : a0604.trim();
    }

    public String getA0607() {
        return a0607;
    }

    public void setA0607(String a0607) {
        this.a0607 = a0607 == null ? null : a0607.trim();
    }

    public String getA0611() {
        return a0611;
    }

    public void setA0611(String a0611) {
        this.a0611 = a0611 == null ? null : a0611.trim();
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

    public String getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(String isDeleted) {
        this.isDeleted = isDeleted == null ? null : isDeleted.trim();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId == null ? null : nodeId.trim();
    }

    public String getProlevel() {
        return prolevel;
    }

    public void setProlevel(String prolevel) {
        this.prolevel = prolevel == null ? null : prolevel.trim();
    }

    public String getA0699() {
        return a0699;
    }

    public void setA0699(String a0699) {
        this.a0699 = a0699 == null ? null : a0699.trim();
    }

    public String getOperateBatch() {
        return operateBatch;
    }

    public void setOperateBatch(String operateBatch) {
        this.operateBatch = operateBatch == null ? null : operateBatch.trim();
    }

    public String getIsNormal() {
        return isNormal;
    }

    public void setIsNormal(String isNormal) {
        this.isNormal = isNormal == null ? null : isNormal.trim();
    }

    public byte[] getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(byte[] timeStamp) {
        this.timeStamp = timeStamp;
    }
}