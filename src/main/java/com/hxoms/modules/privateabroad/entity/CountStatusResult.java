package com.hxoms.modules.privateabroad.entity;

/**
 * @desc: 统计结果类
 * @author: lijing
 * @date: 2020-06-11
 */
public class CountStatusResult {
    //标签
    private String label;
    //数量
    private int num;
    //代码
    private int statusCode;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }
}
