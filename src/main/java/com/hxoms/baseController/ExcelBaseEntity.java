package com.hxoms.baseController;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;
import java.util.Map;

public class ExcelBaseEntity{

    @ApiModelProperty(value = "查询接口名称",name = "methodName",required = true)
    private String methodName;
    @ApiModelProperty(value = "对象实体",name = "data",required = false)
    private Object data;
    @ApiModelProperty(value = "Excel表头标题",name = "columnList",required = true)
    private List columnList;
    @ApiModelProperty(value = "普通参数",name = "parameters",required = false)
    private Map parameters;

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public List getColumnList() {
        return columnList;
    }

    public void setColumnList(List columnList) {
        this.columnList = columnList;
    }

    public Map getParameters() {
        return parameters;
    }

    public void setParameters(Map parameters) {
        this.parameters = parameters;
    }
}