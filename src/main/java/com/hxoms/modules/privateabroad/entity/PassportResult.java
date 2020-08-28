package com.hxoms.modules.privateabroad.entity;

/**
 * @desc: 因私出国申请结果
 * @author: lijing
 * @date: 2020-08-27
 */
public class PassportResult {
    //证照类型
    private Integer zjlx;
    //证照描述
    private String desc;
    //1申领新证、2换发、3失效申领、4申请签注
    private Integer type;
    //证件号
    private String num;

    public Integer getZjlx() {
        return zjlx;
    }

    public void setZjlx(Integer zjlx) {
        this.zjlx = zjlx;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }
}
