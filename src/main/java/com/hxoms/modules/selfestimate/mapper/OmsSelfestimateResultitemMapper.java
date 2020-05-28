package com.hxoms.modules.selfestimate.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hxoms.modules.selfestimate.entity.OmsSelfestimateResultitem;
import com.hxoms.modules.selfestimate.entity.OmsSelfestimateResultitemVO;

import java.util.List;

public interface OmsSelfestimateResultitemMapper extends BaseMapper<OmsSelfestimateResultitem> {

    /**
     * 查询结果子集列表
     * @param id
     * @return
     */
    List<OmsSelfestimateResultitemVO> selectItemResultList(String id);
}