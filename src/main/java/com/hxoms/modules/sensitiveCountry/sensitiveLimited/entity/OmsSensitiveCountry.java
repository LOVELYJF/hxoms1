package com.hxoms.modules.sensitiveCountry.sensitiveLimited.entity;

import com.hxoms.common.hxannotation.ColumnAnnotation;
import com.hxoms.common.hxannotation.IdAnnotation;
import com.hxoms.common.hxannotation.TableAnnotation;

import java.util.List;

@TableAnnotation(TableName = "oms_sensiti ve_country", TableDescription="")
public class OmsSensitiveCountry {
    @IdAnnotation
    @ColumnAnnotation(FieldName = "ID",   FieldDescription="主键")
    private String id;

    @ColumnAnnotation(FieldName = "PARENT_ID",   FieldDescription="父级ID")
    private String parentId;

    @ColumnAnnotation(FieldName = "NAME",   FieldDescription="名称")
    private String name;

    @ColumnAnnotation(FieldName = "UPPER_LEVEL",   FieldDescription="上一级")
    private String upperLevel;

    @ColumnAnnotation(FieldName = "PUB_PRI",   FieldDescription="因公")
    private String pubPri;

    private List<OmsSensitiveCountry> list;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getUpperLevel() {
        return upperLevel;
    }

    public void setUpperLevel(String upperLevel) {
        this.upperLevel = upperLevel;
    }

    public String getPubPri() {
        return pubPri;
    }

    public void setPubPri(String pubPri) {
        this.pubPri = pubPri == null ? null : pubPri.trim();
    }

    public List<OmsSensitiveCountry> getList() {
        return list;
    }

    public void setList(List<OmsSensitiveCountry> list) {
        this.list = list;
    }
}