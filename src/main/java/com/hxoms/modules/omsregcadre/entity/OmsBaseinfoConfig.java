package com.hxoms.modules.omsregcadre.entity;

import com.hxoms.common.hxannotation.ColumnAnnotation;
import com.hxoms.common.hxannotation.IdAnnotation;
import com.hxoms.common.hxannotation.TableAnnotation;

@TableAnnotation(TableName = "oms_baseinfo_config", TableDescription="职务配置表（出国境职务与干综职务）")
public class OmsBaseinfoConfig {
    @IdAnnotation
    @ColumnAnnotation(FieldName = "ID",   FieldDescription="")
    private String id;

    @ColumnAnnotation(FieldName = "INFO_ID",   FieldDescription="信息id")
    private String infoId;

    @ColumnAnnotation(FieldName = "PARENT_ID",   FieldDescription="父级id")
    private String parentId;

    @ColumnAnnotation(FieldName = "INFO_NAME",   FieldDescription="信息名称")
    private String infoName;

    @ColumnAnnotation(FieldName = "DICT_CODE",   FieldDescription="字典code")
    private String dictCode;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getInfoId() {
        return infoId;
    }

    public void setInfoId(String infoId) {
        this.infoId = infoId == null ? null : infoId.trim();
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId == null ? null : parentId.trim();
    }

    public String getInfoName() {
        return infoName;
    }

    public void setInfoName(String infoName) {
        this.infoName = infoName == null ? null : infoName.trim();
    }

    public String getDictCode() {
        return dictCode;
    }

    public void setDictCode(String dictCode) {
        this.dictCode = dictCode == null ? null : dictCode.trim();
    }
}