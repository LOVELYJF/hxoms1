package com.hxoms.modules.selfestimate.service;

import com.hxoms.modules.selfestimate.entity.OmsSelfFile;
import com.hxoms.modules.selfestimate.entity.OmsSelfestimateResultitem;
import com.hxoms.modules.selfestimate.entity.OmsSelfestimateResultitemResult;
import com.hxoms.modules.selfestimate.entity.paramentity.ResultListParam;

import java.util.List;

public interface OmsSelfestimateResultService {
    /**
     * 插入自评结果
     * @param omsSelfestimateResultitems
     * @return
     */
    List<OmsSelfestimateResultitem> insertOrUpdateResult(List<OmsSelfestimateResultitem> omsSelfestimateResultitems);
}
