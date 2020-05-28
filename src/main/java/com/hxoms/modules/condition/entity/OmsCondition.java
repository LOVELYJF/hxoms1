package com.hxoms.modules.condition.entity;

import com.hxoms.common.hxannotation.ColumnAnnotation;
import com.hxoms.common.hxannotation.IdAnnotation;
import com.hxoms.common.hxannotation.TableAnnotation;

@TableAnnotation(TableName = "oms_condition", TableDescription="约束条件")
public class OmsCondition {
    @IdAnnotation
    @ColumnAnnotation(FieldName = "ID",   FieldDescription="主键")
    private String id;

    @ColumnAnnotation(FieldName = "NAME",   FieldDescription="约束条件名称")
    private String name;

    @ColumnAnnotation(FieldName = "CONDITION_TYPE",   FieldDescription="条件类型1--因公条件,2--因私条件，3延期回国 后续可以增加。。。")
    private String conditionType;

    @ColumnAnnotation(FieldName = "CHECK_TYPE",   FieldDescription="1--保存前校验;2--保存后校验")
    private Integer checkType;

    @ColumnAnnotation(FieldName = "INFO_TYPE",   FieldDescription="1--干部备案信息校验，2--因公、因私出国记录校验")
    private Integer infoType;

    @ColumnAnnotation(FieldName = "SQL_CONTENT",   FieldDescription="生成的sql条件")
    private String sqlContent;

    @ColumnAnnotation(FieldName = "JSON_CONTENT",   FieldDescription="json格式的条件")
    private String jsonContent;

    @ColumnAnnotation(FieldName = "DESCRIPTION",   FieldDescription="条件的中文描述")
    private String description;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getConditionType() {
        return conditionType;
    }

    public void setConditionType(String conditionType) {
        this.conditionType = conditionType == null ? null : conditionType.trim();
    }

    public Integer getCheckType() {
        return checkType;
    }

    public void setCheckType(Integer checkType) {
        this.checkType = checkType;
    }

    public Integer getInfoType() {
        return infoType;
    }

    public void setInfoType(Integer infoType) {
        this.infoType = infoType;
    }

    public String getSqlContent() {
        return sqlContent;
    }

    public void setSqlContent(String sqlContent) {
        this.sqlContent = sqlContent == null ? null : sqlContent.trim();
    }

    public String getJsonContent() {
        return jsonContent;
    }

    public void setJsonContent(String jsonContent) {
        this.jsonContent = jsonContent == null ? null : jsonContent.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }
}