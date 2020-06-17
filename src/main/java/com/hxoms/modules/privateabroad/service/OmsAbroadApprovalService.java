package com.hxoms.modules.privateabroad.service;

import com.hxoms.modules.privateabroad.entity.OmsAbroadApproval;

import java.util.List;

public interface OmsAbroadApprovalService {
    /**
     * 审批列表
     * @param applyId  申请id
     * @param type 类型（因公 因私 延期回国）
     * @return
     */
    List<OmsAbroadApproval> selectOmsAbroadApprovalList(String applyId, String type);

    /**
     * 新增审批记录
     * @param omsAbroadApproval
     * @return
     */
    String insertOmsAbroadApproval(OmsAbroadApproval omsAbroadApproval);
}
