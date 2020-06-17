package com.hxoms.modules.privateabroad.entity;

/**
 * @desc: 因私出国审批表回收扩展类
 * @author: lijing
 * @date: 2020-06-15
 */
public class OmsApprovalReturnVO extends OmsApprovalReturn{
    //因私出国信息
    private OmsPriApplyVO omsPriApplyVO;

    public OmsPriApplyVO getOmsPriApplyVO() {
        return omsPriApplyVO;
    }

    public void setOmsPriApplyVO(OmsPriApplyVO omsPriApplyVO) {
        this.omsPriApplyVO = omsPriApplyVO;
    }
}
