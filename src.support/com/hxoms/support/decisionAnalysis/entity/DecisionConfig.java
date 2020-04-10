package com.hxoms.support.decisionAnalysis.entity;

import com.hxoms.common.hxannotation.ColumnAnnotation;
import com.hxoms.common.hxannotation.IdAnnotation;
import com.hxoms.common.hxannotation.TableAnnotation;

@TableAnnotation(TableName = "decision_show", TableDescription="")
public class DecisionConfig {
    /**
     * id
     */
    @IdAnnotation
    @ColumnAnnotation(FieldName = "id", FieldDescription = "id")
    private String id;

    /**
     * 父id
     */
    @IdAnnotation
    @ColumnAnnotation(FieldName = "pid", FieldDescription = "父id")
    private String pid;

    /**
     * 展示方式
     */
    @IdAnnotation
    @ColumnAnnotation(FieldName = "show_way", FieldDescription = "展示方式")
    private String showWay;

    /**
     * json格式字符串
     */
    @IdAnnotation
    @ColumnAnnotation(FieldName = "json", FieldDescription = "json格式字符串")
    private String json;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getShowWay() {
        return showWay;
    }

    public void setShowWay(String showWay) {
        this.showWay = showWay;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }
}
