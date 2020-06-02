package com.hxoms.modules.selfestimate.entity;

import com.hxoms.modules.publicity.entity.PersonInfoVO;

import java.util.List;

/**
 * @desc: 自评核对结果结果集
 * @author: lijing
 * @date: 2020-05-25
 */
public class OmsSelfestimateResultVO extends OmsSelfestimateResult{
    //机构id
    private String b0100;
    //机构名称
    private String b0101;
    //机构主要领导
    private List<PersonInfoVO> personInfoVOS;
    //检查项目结果集
    private List<OmsSelfestimateResultitemVO> omsSelfestimateResultitemsVO;

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

    public List<OmsSelfestimateResultitemVO> getOmsSelfestimateResultitemsVO() {
        return omsSelfestimateResultitemsVO;
    }

    public void setOmsSelfestimateResultitemsVO(List<OmsSelfestimateResultitemVO> omsSelfestimateResultitemsVO) {
        this.omsSelfestimateResultitemsVO = omsSelfestimateResultitemsVO;
    }
}
