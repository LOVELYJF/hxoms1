package com.hxoms.modules.passportCard.certificateCollect.entity.enums;

/**
 * @Desc：催缴状态
 * @Author: wangyunquan
 * @Date: 2020/9/18
 */
public enum CjStatusEnum {
    SDJC("0","手动解除"),
    YSJ("1","已上缴"),
    WSJ("2","未上缴"),
    ZDJC("3","自动解除");
    private String code;

    private String name;

    CjStatusEnum(String code, String name) {
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
