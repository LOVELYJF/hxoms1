package com.hxoms.modules.publicity.entity;

import java.util.List;

/**
 * 因公出国团体预审批及人员列表展示
 * @Author: gaozhenyuan
 * @Date: 2020/8/6 11:21
 */
public class OmsPubGroupAndApplyList{
    //
    private OmsPubGroupPreApproval omsPubGroupPreApproval;

    private List<OmsPubApplyVO> omsPubApplyVOList;

    public OmsPubGroupPreApproval getOmsPubGroupPreApproval() {
        return omsPubGroupPreApproval;
    }

    public void setOmsPubGroupPreApproval(OmsPubGroupPreApproval omsPubGroupPreApproval) {
        this.omsPubGroupPreApproval = omsPubGroupPreApproval;
    }

    public List<OmsPubApplyVO> getOmsPubApplyVOList() {
        return omsPubApplyVOList;
    }

    public void setOmsPubApplyVOList(List<OmsPubApplyVO> omsPubApplyVOList) {
        this.omsPubApplyVOList = omsPubApplyVOList;
    }
}
