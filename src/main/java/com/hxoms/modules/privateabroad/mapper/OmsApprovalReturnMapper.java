package com.hxoms.modules.privateabroad.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hxoms.modules.privateabroad.entity.OmsApprovalReturn;
import com.hxoms.modules.privateabroad.entity.OmsApprovalReturnVO;
import com.hxoms.modules.privateabroad.entity.paramentity.OmsPriApprovalReturnIPageParam;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OmsApprovalReturnMapper extends BaseMapper<OmsApprovalReturn> {
    /**
     * 因私出国审批表回收登记列表
     * @param params
     * @return
     */
    List<OmsApprovalReturnVO> selectPriApprovalReturnPagelist(@Param("params")OmsPriApprovalReturnIPageParam params);
}