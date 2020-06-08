package com.hxoms.modules.selfestimate.entity;

import com.hxoms.modules.publicity.entity.PersonInfoVO;

import java.util.List;

/**
 * @desc: 自评结果集
 * @author: lijing
 * @date: 2020-05-26
 */
public class OmsSelfestimateResultitemResult{
    //结果项名称
    private String checkItem;
    //机构id
    private String b0100;
    //机构名称
    private String b0101;
    //机构主要领导
    private List<PersonInfoVO> personInfoVOS;
    //结果集
    private List<OmsSelfestimateResultitemVO> omsSelfestimateResultitems;

    public String getCheckItem() {
        return checkItem;
    }

    public void setCheckItem(String checkItem) {
        this.checkItem = checkItem;
    }

    public String getB0100() {
        return b0100;
    }

    public void setB0100(String b0100) {
        this.b0100 = b0100;
    }

    public String getB0101() {
        return b0101;
    }

    public void setB0101(String b0101) {
        this.b0101 = b0101;
    }

    public List<PersonInfoVO> getPersonInfoVOS() {
        return personInfoVOS;
    }

    public void setPersonInfoVOS(List<PersonInfoVO> personInfoVOS) {
        this.personInfoVOS = personInfoVOS;
    }

    public List<OmsSelfestimateResultitemVO> getOmsSelfestimateResultitems() {
        return omsSelfestimateResultitems;
    }

    public void setOmsSelfestimateResultitems(List<OmsSelfestimateResultitemVO> omsSelfestimateResultitems) {
        this.omsSelfestimateResultitems = omsSelfestimateResultitems;
    }
}