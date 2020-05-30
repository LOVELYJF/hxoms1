package com.hxoms.modules.selfestimate.entity.paramentity;

import com.hxoms.modules.selfestimate.entity.OmsSelfestimateResult;
import com.hxoms.modules.selfestimate.entity.OmsSelfestimateResultitem;

import java.util.List;

/**
 * @desc: 插入自评结果参数
 * @author: lijing
 * @date: 2020-05-26
 */
public class InsertResultParam {
    //自评结果
    private OmsSelfestimateResult omsSelfestimateResult;
    //自评项结果
    private List<OmsSelfestimateResultitem> omsSelfestimateResultitems;

    public OmsSelfestimateResult getOmsSelfestimateResult() {
        return omsSelfestimateResult;
    }

    public void setOmsSelfestimateResult(OmsSelfestimateResult omsSelfestimateResult) {
        this.omsSelfestimateResult = omsSelfestimateResult;
    }

    public List<OmsSelfestimateResultitem> getOmsSelfestimateResultitems() {
        return omsSelfestimateResultitems;
    }

    public void setOmsSelfestimateResultitems(List<OmsSelfestimateResultitem> omsSelfestimateResultitems) {
        this.omsSelfestimateResultitems = omsSelfestimateResultitems;
    }
}
