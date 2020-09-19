package com.hxoms.modules.passportCard.certificateCollect.entity.enums;

/**
 * @Desc：催缴数据来源
 * @Author: wangyunquan
 * @Date: 2020/9/18
 */
public enum CjDataSourceEnum {
    DJBA("0","登记备案"),
    YSCG("1","因私出国(境)"),
    ZZJC("2","证照借出"),
    CXCGSQ("3","撤销出国申请");

    private String code;

    private String name;

    CjDataSourceEnum(String code, String name) {
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
