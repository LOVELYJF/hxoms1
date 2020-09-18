package com.hxoms.modules.passportCard.certificateCollect.entity.enums;

/**
 * @Desc：催缴方式
 * @Author: wangyunquan
 * @Date: 2020/9/18
 */
public enum CjWayEnum {
    DHCJ("0","电话催缴"),
    DXCJ("1","短信催缴");

    private String code;

    private String name;

    CjWayEnum(String code, String name) {
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
