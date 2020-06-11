package com.hxoms.modules.privateabroad.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hxoms.modules.privateabroad.entity.CountStatusResult;
import com.hxoms.modules.privateabroad.entity.OmsPriDelayApply;
import com.hxoms.modules.privateabroad.entity.OmsPriDelayApplyVO;
import com.hxoms.modules.privateabroad.entity.paramentity.OmsPriApplyIPageParam;

import java.util.List;

public interface OmsPriDelayApplyMapper extends BaseMapper<OmsPriDelayApply> {

    /**
     * 延期回国申请列表
     * @param omsPriApplyIPageParam
     * @return
     */
    List<OmsPriDelayApplyVO> selectOmsDelayApplyIPage(OmsPriApplyIPageParam omsPriApplyIPageParam);
    /**
     * 基本流程数据统计
     * @return
     */
    List<CountStatusResult> selectCountStatus();
}