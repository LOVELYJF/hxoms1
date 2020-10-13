package com.hxoms.modules.omsregcadre.entity.enums;

/**
 * @Desc：证照持有情况状态
 * @Author: wangyunquan
 * @Date: 2020/10/12
 */
public enum  LicenceIdentityEnum {
    CXZ(32,"查询中"),
    HZ(1,"护照"),
    GATXZ(2,"港澳通行证"),
    TWTXZ(4,"台湾通行证"),
    WZZ(8,"无证照"),
    FSWGLZZ(16,"非省委管理证照");

    private Integer code;

    private String name;

    LicenceIdentityEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // 普通方法
    public static String getName(Integer code) {
        for (LicenceIdentityEnum c : LicenceIdentityEnum.values()) {
            if (c.getCode()==code) {
                return c.name;
            }
        }
        return null;
    }
}
