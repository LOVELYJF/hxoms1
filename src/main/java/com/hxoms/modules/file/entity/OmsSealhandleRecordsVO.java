package com.hxoms.modules.file.entity;

/**
 * @desc: 签字盖章扩展类
 * @author: lijing
 * @date: 2020-06-04
 */
public class OmsSealhandleRecordsVO extends OmsSealhandleRecords {
    //是否处理
    private String isHandle;

    public String getIsHandle() {
        return isHandle;
    }

    public void setIsHandle(String isHandle) {
        this.isHandle = isHandle;
    }
}
