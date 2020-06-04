package com.hxoms.modules.selfestimate.entity;

import com.hxoms.modules.publicity.entity.PersonInfoVO;

import java.util.List;

/**
 * @desc: 自评结果项扩展类
 * @author: lijing
 * @date: 2020-05-26
 */
public class OmsSelfestimateResultitemVO extends OmsSelfestimateResultitem{
    //结果项名称
    private String checkItem;

    public String getCheckItem() {
        return checkItem;
    }

    public void setCheckItem(String checkItem) {
        this.checkItem = checkItem;
    }
}
