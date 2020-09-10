package com.hxoms.modules.privateabroad.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hxoms.modules.privateabroad.entity.OmsAbroadApproval;

public interface OmsAbroadApprovalMapper extends BaseMapper<OmsAbroadApproval> {
	
	
	/**
     * 根据申请id和步骤编码查询
     * @param stepCode
     * @param applyId
     * @return
     */
    List<OmsAbroadApproval> selcetByApplyIdAndStepCode(@Param("stepCode")Integer stepCode,@Param("applyId")String applyId);
}