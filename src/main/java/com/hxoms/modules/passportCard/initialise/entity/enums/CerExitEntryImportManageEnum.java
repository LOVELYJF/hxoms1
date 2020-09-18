package com.hxoms.modules.passportCard.initialise.entity.enums;

/**
 * @Desc：出入境记录导入管理状态
 * @Author: wangyunquan
 * @Date: 2020/9/18
 */
public enum CerExitEntryImportManageEnum {
    CGDR("0","成功导入"),
    ZFDR("1","重复导入"),
    PPSB("2","匹配失败");

    private String code;

    private String name;

    CerExitEntryImportManageEnum(String code, String name) {
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
