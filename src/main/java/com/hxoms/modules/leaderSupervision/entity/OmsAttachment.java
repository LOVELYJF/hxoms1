package com.hxoms.modules.leaderSupervision.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hxoms.common.hxannotation.ColumnAnnotation;

import java.util.Date;



import com.hxoms.common.hxannotation.IdAnnotation;
import com.hxoms.common.hxannotation.TableAnnotation;

import org.springframework.format.annotation.DateTimeFormat;

@TableAnnotation(TableName = "oms_attachment", TableDescription="附件表")
public class OmsAttachment {
    @IdAnnotation
    @ColumnAnnotation(FieldName = "id",   FieldDescription="")
    private String id;

    @ColumnAnnotation(FieldName = "type",   FieldDescription="文件类型")
    private String type;

    @ColumnAnnotation(FieldName = "size",   FieldDescription="文件大小")
    private String size;

    @ColumnAnnotation(FieldName = "name",   FieldDescription="文件名")
    private String name;

    @ColumnAnnotation(FieldName = "url",   FieldDescription="存储路径")
    private String url;

    @ColumnAnnotation(FieldName = "bussiness_type",   FieldDescription="业务分类")
    private String bussinessType;

    @ColumnAnnotation(FieldName = "bussiness_occure_stpet",   FieldDescription="发生在业务的哪一步code")
    private String bussinessOccureStpet;

    @ColumnAnnotation(FieldName = "bussiness_occure_stpet_name",   FieldDescription="发生在业务的哪一步code的名称")
    private String bussinessOccureStpetName;

    @ColumnAnnotation(FieldName = "bussinessId",   FieldDescription="业务Id")
    private String bussinessid;

    @ColumnAnnotation(FieldName = "CREATE_TIME",   FieldDescription="")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date createTime;

    @ColumnAnnotation(FieldName = "CREATE_USER",   FieldDescription="创建人")
    private String createUser;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size == null ? null : size.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public String getBussinessType() {
        return bussinessType;
    }

    public void setBussinessType(String bussinessType) {
        this.bussinessType = bussinessType == null ? null : bussinessType.trim();
    }

    public String getBussinessOccureStpet() {
        return bussinessOccureStpet;
    }

    public void setBussinessOccureStpet(String bussinessOccureStpet) {
        this.bussinessOccureStpet = bussinessOccureStpet == null ? null : bussinessOccureStpet.trim();
    }

    public String getBussinessOccureStpetName() {
        return bussinessOccureStpetName;
    }

    public void setBussinessOccureStpetName(String bussinessOccureStpetName) {
        this.bussinessOccureStpetName = bussinessOccureStpetName == null ? null : bussinessOccureStpetName.trim();
    }

    public String getBussinessid() {
        return bussinessid;
    }

    public void setBussinessid(String bussinessid) {
        this.bussinessid = bussinessid == null ? null : bussinessid.trim();
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
}