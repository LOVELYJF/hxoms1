package com.hxoms.modules.passportCard.initialise.entity.enums;

/**
 * @Desc：保管状态
 * @Author: wangyunquan
 * @Date: 2020/9/18
 */
public enum CardStatusEnum {
    ZC("0","正常"),
    GQ("1","过期"),
    ZX("2","注销"),
    YZSB("3","验证失败"),
    YYZ("4","已验证"),
    DYZ("5","待验证"),
    JC("6","借出"),
    DLQ("40","待领取"),
    YLQ("41","已领取");

    private String code;

    private String name;

    CardStatusEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
