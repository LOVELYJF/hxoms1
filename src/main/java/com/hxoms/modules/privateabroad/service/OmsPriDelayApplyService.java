package com.hxoms.modules.privateabroad.service;

import com.github.pagehelper.PageInfo;
import com.hxoms.modules.privateabroad.entity.OmsPriDelayApply;
import com.hxoms.modules.privateabroad.entity.OmsPriDelayApplyVO;
import com.hxoms.modules.privateabroad.entity.paramentity.OmsPriApplyIPageParam;

import java.util.List;
import java.util.Map;

public interface OmsPriDelayApplyService {
    /**
     * 延期回国申请列表
     * @param omsPriApplyIPageParam  查询条件
     * @return
     */
    PageInfo<OmsPriDelayApplyVO> selectOmsDelayApplyIPage(OmsPriApplyIPageParam omsPriApplyIPageParam);
    /**
     * 新增或修改延期回国申请
     * @param omsPriDelayApply  申请接收参数类
     * @return
     */
    List<Map<String, String>> insertOrUpdateApply(OmsPriDelayApply omsPriDelayApply);
    /**
     * 修改延期回国申请状态
     * @param omsPriDelayApply 申请id
     * @return
     */
    String updateDelayApplyStatus(OmsPriDelayApply omsPriDelayApply);
    /**
     * 删除延期回国申请
     * @param id 申请id
     * @return
     */
    String deleteDelayApply(String id);
    /**
     * 延期回国申请详情
     * @param id 申请id
     * @return
     */
    OmsPriDelayApplyVO selectDelayApplyById(String id);
}
