package com.hxoms.common.utils;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class ResultSwg<T> implements Serializable {

    @ApiModelProperty("响应编码")
    private int code;
    @ApiModelProperty("响应信息")
    private String msg;
    @ApiModelProperty("反回数据")
    private T data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public ResultSwg setData(T data) {
        this.data = data;
        return this;
    }

    /**
     * @ author：sunqian
     * @ desc：不带数据的返回的对象
     * @ date：2019/5/27 11:15
     */
    public static ResultSwg success() {
        ResultSwg result = new ResultSwg();
        result.setCode(Constants.SUCCESS);
        result.setMsg("操作成功");
        return result;
    }
    /**
     * @ author：sunqian
     * @ desc：带数据的返回对象
     * @ date：2019/5/27 11:16
     */
    public static ResultSwg success(Object data) {
        ResultSwg result = new ResultSwg();
        result.setCode(Constants.SUCCESS);
        result.setMsg("操作成功");
        result.setData(data);
        return result;
    }

    /**
     * @ author：sunqian
     * @ desc：处理错误的返回对象
     * @ date：2019/5/27 11:18
     */
    public static ResultSwg error(String msg) {
        ResultSwg result = new ResultSwg();
        result.setCode(Constants.ERROR);
        result.setMsg(msg);
        return result;
    }
}
