package com.hxoms.modules.file.entity;

/**
 * @desc: 文件扩展类
 * @author: lijing
 * @date: 2020-06-02
 */
public class OmsFileVO extends OmsFile{
    //是否已办理
    private Integer isHandle;
    //是否有委托书
    private Integer isEntrust;

    public Integer getIsHandle() {
        return isHandle;
    }

    public void setIsHandle(Integer isHandle) {
        this.isHandle = isHandle;
    }

    public Integer getIsEntrust() {
        return isEntrust;
    }

    public void setIsEntrust(Integer isEntrust) {
        this.isEntrust = isEntrust;
    }
}
