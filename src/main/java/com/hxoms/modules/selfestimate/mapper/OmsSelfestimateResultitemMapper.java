package com.hxoms.modules.selfestimate.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hxoms.modules.selfestimate.entity.OmsSelfestimateResultitem;
import com.hxoms.modules.selfestimate.entity.OmsSelfestimateResultitemVO;

import java.util.List;
import java.util.Map;

public interface OmsSelfestimateResultitemMapper extends BaseMapper<OmsSelfestimateResultitem> {

    /**
     * 查询结果子集列表
     * @param paramsMap
     * @return
     */
    List<OmsSelfestimateResultitemVO> selectItemResultList(Map<String, String> paramsMap);
}