package com.hxoms.modules.publicity.docCallback.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hxoms.common.hxannotation.ColumnAnnotation;
import com.hxoms.common.hxannotation.IdAnnotation;
import com.hxoms.common.hxannotation.TableAnnotation;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

@TableAnnotation(TableName = "oms_pub_doccallbackdetail", TableDescription="因公备案批件回收登记明细表")
public class OmsPubDoccallbackdetail {
    @IdAnnotation
    @ColumnAnnotation(FieldName = "ID",   FieldDescription="备案申请表主键")
    private String id;

    @ColumnAnnotation(FieldName = "DOCCALLBACK_ID",   FieldDescription="批件回收登记表主键")
    private String doccallbackId;

    @ColumnAnnotation(FieldName = "NAME",   FieldDescription="姓名")
    private String name;

    @ColumnAnnotation(FieldName = "POST",   FieldDescription="职务")
    private String post;

    @ColumnAnnotation(FieldName = "CGSPDW",   FieldDescription="出国审批单位")
    private String cgspdw;

    @ColumnAnnotation(FieldName = "ZTDW",   FieldDescription="组团单位")
    private String ztdw;

    @ColumnAnnotation(FieldName = "DESCRIPTION",   FieldDescription="说明")
    private String description;

    @ColumnAnnotation(FieldName = "CREAT_DATE",   FieldDescription="创建时间")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date creatDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getDoccallbackId() {
        return doccallbackId;
    }

    public void setDoccallbackId(String doccallbackId) {
        this.doccallbackId = doccallbackId == null ? null : doccallbackId.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post == null ? null : post.trim();
    }

    public String getCgspdw() {
        return cgspdw;
    }

    public void setCgspdw(String cgspdw) {
        this.cgspdw = cgspdw == null ? null : cgspdw.trim();
    }

    public String getZtdw() {
        return ztdw;
    }

    public void setZtdw(String ztdw) {
        this.ztdw = ztdw == null ? null : ztdw.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public Date getCreatDate() {
        return creatDate;
    }

    public void setCreatDate(Date creatDate) {
        this.creatDate = creatDate;
    }
}