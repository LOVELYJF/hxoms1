package com.hxoms.modules.condition.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.hxoms.common.hxannotation.ColumnAnnotation;
import com.hxoms.common.hxannotation.IdAnnotation;
import com.hxoms.common.hxannotation.TableAnnotation;

/**
 * oms_condition
 */
@TableAnnotation(TableName = "oms_condition", TableDescription="")
public class OmsCondition {
    /**
     * 主键
     */
    @IdAnnotation
    @ColumnAnnotation(FieldName = "id",  FieldDescription="主键")
    private String id;

    /**
     * 约束条件名称
     */
    @ColumnAnnotation(FieldName = "name",  FieldDescription="约束条件名称")
    private String name;

    /**
     * 条件类型1--因公条件,2--因私条件，后续可以增加。。。
     */
    @ColumnAnnotation(FieldName = "condition_type",  FieldDescription="条件类型1--因公条件,2--因私条件，后续可以增加。。。")
    private String conditionType;

    /**
     * 1--保存前校验;2--保存后校验
     */
    @ColumnAnnotation(FieldName = "check_type",  FieldDescription="1--保存前校验;2--保存后校验")
    private String checkType;

    /**
     * 1--干部备案信息校验，2--因公、因私出国记录校验
     */
    @ColumnAnnotation(FieldName = "info_type",  FieldDescription="1--干部备案信息校验，2--因公、因私出国记录校验")
    private String infoType;

    /**
     * 生成的sql条件
     */
    @ColumnAnnotation(FieldName = "sql",  FieldDescription="生成的sql条件")
    @TableField("`sql`")
    private String sql;

    /**
     * json格式的条件
     */
    @ColumnAnnotation(FieldName = "json",  FieldDescription="json格式的条件")
    private String json;

    /**
     * 条件的中文描述
     */
    @ColumnAnnotation(FieldName = "desc",  FieldDescription="条件的中文描述")
    @TableField("`desc`")
    private String desc;

    /**
     * 主键
     * @return id 主键
     */
    public String getId() {
        return id;
    }

    /**
     * 主键
     * @param id 主键
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * 约束条件名称
     * @return name 约束条件名称
     */
    public String getName() {
        return name;
    }

    /**
     * 约束条件名称
     * @param name 约束条件名称
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * 条件类型1--因公条件,2--因私条件，后续可以增加。。。
     * @return condition_type 条件类型1--因公条件,2--因私条件，后续可以增加。。。
     */
    public String getConditionType() {
        return conditionType;
    }

    /**
     * 条件类型1--因公条件,2--因私条件，后续可以增加。。。
     * @param conditionType 条件类型1--因公条件,2--因私条件，后续可以增加。。。
     */
    public void setConditionType(String conditionType) {
        this.conditionType = conditionType == null ? null : conditionType.trim();
    }

    /**
     * 1--保存前校验;2--保存后校验
     * @return check_type 1--保存前校验;2--保存后校验
     */
    public String getCheckType() {
        return checkType;
    }

    /**
     * 1--保存前校验;2--保存后校验
     * @param checkType 1--保存前校验;2--保存后校验
     */
    public void setCheckType(String checkType) {
        this.checkType = checkType == null ? null : checkType.trim();
    }

    /**
     * 1--干部备案信息校验，2--因公、因私出国记录校验
     * @return info_type 1--干部备案信息校验，2--因公、因私出国记录校验
     */
    public String getInfoType() {
        return infoType;
    }

    /**
     * 1--干部备案信息校验，2--因公、因私出国记录校验
     * @param infoType 1--干部备案信息校验，2--因公、因私出国记录校验
     */
    public void setInfoType(String infoType) {
        this.infoType = infoType == null ? null : infoType.trim();
    }

    /**
     * 生成的sql条件
     * @return sql 生成的sql条件
     */
    public String getSql() {
        return sql;
    }

    /**
     * 生成的sql条件
     * @param sql 生成的sql条件
     */
    public void setSql(String sql) {
        this.sql = sql == null ? null : sql.trim();
    }

    /**
     * json格式的条件
     * @return json json格式的条件
     */
    public String getJson() {
        return json;
    }

    /**
     * json格式的条件
     * @param json json格式的条件
     */
    public void setJson(String json) {
        this.json = json == null ? null : json.trim();
    }

    /**
     * 条件的中文描述
     * @return desc 条件的中文描述
     */
    public String getDesc() {
        return desc;
    }

    /**
     * 条件的中文描述
     * @param desc 条件的中文描述
     */
    public void setDesc(String desc) {
        this.desc = desc == null ? null : desc.trim();
    }
}