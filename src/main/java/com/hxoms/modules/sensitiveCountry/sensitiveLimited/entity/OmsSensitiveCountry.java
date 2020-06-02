package com.hxoms.modules.sensitiveCountry.sensitiveLimited.entity;

import com.hxoms.common.hxannotation.ColumnAnnotation;
import com.hxoms.common.hxannotation.IdAnnotation;
import com.hxoms.common.hxannotation.TableAnnotation;

@TableAnnotation(TableName = "oms_sensitive_country", TableDescription="")
public class OmsSensitiveCountry {
    @IdAnnotation
    @ColumnAnnotation(FieldName = "ID",   FieldDescription="主键")
    private String id;

    @ColumnAnnotation(FieldName = "PARENT_ID",   FieldDescription="父级ID")
    private Integer parentId;

    @ColumnAnnotation(FieldName = "NAME",   FieldDescription="名称")
    private String name;

    @ColumnAnnotation(FieldName = "UPPER_LEVEL",   FieldDescription="上一级")
    private Integer upperLevel;

    @ColumnAnnotation(FieldName = "PUB_PRI",   FieldDescription="因公")
    private String pubPri;

    @ColumnAnnotation(FieldName = "AREA",   FieldDescription="地区")
    private String area;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getUpperLevel() {
        return upperLevel;
    }

    public void setUpperLevel(Integer upperLevel) {
        this.upperLevel = upperLevel;
    }

    public String getPubPri() {
        return pubPri;
    }

    public void setPubPri(String pubPri) {
        this.pubPri = pubPri == null ? null : pubPri.trim();
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area == null ? null : area.trim();
    }
}