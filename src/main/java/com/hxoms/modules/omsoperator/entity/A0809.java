package com.hxoms.modules.omsoperator.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hxoms.common.hxannotation.ColumnAnnotation;
import com.hxoms.common.hxannotation.IdAnnotation;
import com.hxoms.common.hxannotation.TableAnnotation;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@TableAnnotation(TableName = "a0809", TableDescription="")
@ApiModel(value = "")
public class A0809 {
    @IdAnnotation
    @ColumnAnnotation(FieldName = "a080900",   FieldDescription="")
    @ApiModelProperty(value="")
    private String a080900;

    @ColumnAnnotation(FieldName = "a0801b",   FieldDescription="")
    @ApiModelProperty(value="")
    private String a0801b;

    @ColumnAnnotation(FieldName = "a0901a",   FieldDescription="")
    @ApiModelProperty(value="")
    private String a0901a;

    @ColumnAnnotation(FieldName = "a0901b",   FieldDescription="")
    @ApiModelProperty(value="")
    private String a0901b;

    @ColumnAnnotation(FieldName = "a0804",   FieldDescription="")
    @ApiModelProperty(value="")
    private String a0804;

    @ColumnAnnotation(FieldName = "a0807",   FieldDescription="")
    @ApiModelProperty(value="")
    private String a0807;

    @ColumnAnnotation(FieldName = "a0811",   FieldDescription="")
    @ApiModelProperty(value="")
    private String a0811;

    @ColumnAnnotation(FieldName = "a0904",   FieldDescription="")
    @ApiModelProperty(value="")
    private String a0904;

    @ColumnAnnotation(FieldName = "a0814",   FieldDescription="")
    @ApiModelProperty(value="")
    private String a0814;

    @ColumnAnnotation(FieldName = "a0824",   FieldDescription="")
    @ApiModelProperty(value="")
    private String a0824;

    @ColumnAnnotation(FieldName = "a0827",   FieldDescription="")
    @ApiModelProperty(value="")
    private String a0827;

    @ColumnAnnotation(FieldName = "a0837",   FieldDescription="")
    @ApiModelProperty(value="")
    private String a0837;

    @ColumnAnnotation(FieldName = "a0100",   FieldDescription="")
    @ApiModelProperty(value="")
    private String a0100;

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

    @ColumnAnnotation(FieldName = "a0801a",   FieldDescription="")
    @ApiModelProperty(value="")
    private String a0801a;

    @ColumnAnnotation(FieldName = "id",   FieldDescription="主键")
    @ApiModelProperty(value="主键")
    private String id;

    @ColumnAnnotation(FieldName = "node_id",   FieldDescription="节点编码")
    @ApiModelProperty(value="节点编码")
    private String nodeId;

    @ColumnAnnotation(FieldName = "a0898",   FieldDescription="")
    @ApiModelProperty(value="")
    private String a0898;

    @ColumnAnnotation(FieldName = "is_normal",   FieldDescription="")
    @ApiModelProperty(value="")
    private String isNormal;

    @ColumnAnnotation(FieldName = "operate_batch",   FieldDescription="")
    @ApiModelProperty(value="")
    private String operateBatch;

    @ColumnAnnotation(FieldName = "a0899",   FieldDescription="")
    @ApiModelProperty(value="")
    private String a0899;

    @ColumnAnnotation(FieldName = "a0834",   FieldDescription="")
    @ApiModelProperty(value="")
    private String a0834;

    @ColumnAnnotation(FieldName = "a0835",   FieldDescription="")
    @ApiModelProperty(value="")
    private String a0835;

    @ColumnAnnotation(FieldName = "time_stamp",   FieldDescription="")
    @ApiModelProperty(value="")
    private byte[] timeStamp;

    public String getA080900() {
        return a080900;
    }

    public void setA080900(String a080900) {
        this.a080900 = a080900 == null ? null : a080900.trim();
    }

    public String getA0801b() {
        return a0801b;
    }

    public void setA0801b(String a0801b) {
        this.a0801b = a0801b == null ? null : a0801b.trim();
    }

    public String getA0901a() {
        return a0901a;
    }

    public void setA0901a(String a0901a) {
        this.a0901a = a0901a == null ? null : a0901a.trim();
    }

    public String getA0901b() {
        return a0901b;
    }

    public void setA0901b(String a0901b) {
        this.a0901b = a0901b == null ? null : a0901b.trim();
    }

    public String getA0804() {
        return a0804;
    }

    public void setA0804(String a0804) {
        this.a0804 = a0804 == null ? null : a0804.trim();
    }

    public String getA0807() {
        return a0807;
    }

    public void setA0807(String a0807) {
        this.a0807 = a0807 == null ? null : a0807.trim();
    }

    public String getA0811() {
        return a0811;
    }

    public void setA0811(String a0811) {
        this.a0811 = a0811 == null ? null : a0811.trim();
    }

    public String getA0904() {
        return a0904;
    }

    public void setA0904(String a0904) {
        this.a0904 = a0904 == null ? null : a0904.trim();
    }

    public String getA0814() {
        return a0814;
    }

    public void setA0814(String a0814) {
        this.a0814 = a0814 == null ? null : a0814.trim();
    }

    public String getA0824() {
        return a0824;
    }

    public void setA0824(String a0824) {
        this.a0824 = a0824 == null ? null : a0824.trim();
    }

    public String getA0827() {
        return a0827;
    }

    public void setA0827(String a0827) {
        this.a0827 = a0827 == null ? null : a0827.trim();
    }

    public String getA0837() {
        return a0837;
    }

    public void setA0837(String a0837) {
        this.a0837 = a0837 == null ? null : a0837.trim();
    }

    public String getA0100() {
        return a0100;
    }

    public void setA0100(String a0100) {
        this.a0100 = a0100 == null ? null : a0100.trim();
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

    public String getA0801a() {
        return a0801a;
    }

    public void setA0801a(String a0801a) {
        this.a0801a = a0801a == null ? null : a0801a.trim();
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

    public String getA0898() {
        return a0898;
    }

    public void setA0898(String a0898) {
        this.a0898 = a0898 == null ? null : a0898.trim();
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

    public String getA0899() {
        return a0899;
    }

    public void setA0899(String a0899) {
        this.a0899 = a0899 == null ? null : a0899.trim();
    }

    public String getA0834() {
        return a0834;
    }

    public void setA0834(String a0834) {
        this.a0834 = a0834 == null ? null : a0834.trim();
    }

    public String getA0835() {
        return a0835;
    }

    public void setA0835(String a0835) {
        this.a0835 = a0835 == null ? null : a0835.trim();
    }

    public byte[] getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(byte[] timeStamp) {
        this.timeStamp = timeStamp;
    }
}