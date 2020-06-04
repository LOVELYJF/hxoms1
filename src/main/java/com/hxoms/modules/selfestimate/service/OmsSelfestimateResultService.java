package com.hxoms.modules.selfestimate.service;

import com.hxoms.modules.selfestimate.entity.OmsSelfestimateResultitem;
import com.hxoms.modules.selfestimate.entity.OmsSelfestimateResultitemResult;

import java.util.List;

public interface OmsSelfestimateResultService {
    /**
     * 插入自评结果
     * @param omsSelfestimateResultitems
     * @return
     */
    String insertOrUpdateResult(List<OmsSelfestimateResultitem> omsSelfestimateResultitems);
    /**
     * 查询结果集
     * @param applyId 申请Id
     * @param selffileId 文件Id
     * @param type 类型（因公，因私）
     * @return
     */
    OmsSelfestimateResultitemResult selectResultList(String applyId, String selffileId, String type);
}
