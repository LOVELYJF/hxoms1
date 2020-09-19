package com.hxoms.modules.passportCard.initialise.entity.enums;

/**
 * @Desc：保管状态
 * @Author: wangyunquan
 * @Date: 2020/9/18
 */
public enum SaveStatusEnum {
    ZCBG("0","正常保管"),
    YQC("1","已取出"),
    WSQ("2","未上缴");

    private String code;

    private String name;

    SaveStatusEnum(String code, String name) {
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
