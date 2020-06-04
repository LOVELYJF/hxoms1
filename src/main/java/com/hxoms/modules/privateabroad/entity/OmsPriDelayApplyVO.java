package com.hxoms.modules.privateabroad.entity;

/**
 * @desc: 延期回国扩展
 * @author: lijing
 * @date: 2020-06-03
 */
public class OmsPriDelayApplyVO extends OmsPriDelayApply{
    //申请记录
    private OmsPriApplyVO omsPriApplyVO;

    public OmsPriApplyVO getOmsPriApplyVO() {
        return omsPriApplyVO;
    }

    public void setOmsPriApplyVO(OmsPriApplyVO omsPriApplyVO) {
        this.omsPriApplyVO = omsPriApplyVO;
    }
}
