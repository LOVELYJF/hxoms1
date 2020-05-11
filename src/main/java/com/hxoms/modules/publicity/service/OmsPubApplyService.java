package com.hxoms.modules.publicity.service;

import com.hxoms.modules.publicity.entity.OmsPubApply;
import com.hxoms.modules.publicity.entity.OmsPubApplyVO;
import com.hxoms.modules.publicity.entity.PersonInfoVO;

import java.util.List;

public interface OmsPubApplyService {
    /**
     * 根据机构b0111查询当前的人员
     *
     * @author sunqian
     * @date 2020/4/22 16:43
     */
    List<PersonInfoVO> selectPersonListByOrg(String orgId);

    /**
     * 根据人员id查询申请需要的基本信息
     * 
     * @author sunqian
     * @date 2020/4/23 15:21
     */
    OmsPubApplyVO selectPubApplyPersonInfo(String b0100, String a0100);

    /**
     * 新增备案申请
     * 
     * @author sunqian
     * @date 2020/4/26 17:22
     */
    String insertOrUpdatePubApply(OmsPubApply omsPubApply);

    /**
     *
     * 查询当前登录经办人管理单位的申请人员列表
     * @author sunqian
     * @date 2020/5/4 17:42
     */
    List<OmsPubApply> selectPubApplyList();

    OmsPubApply selectPubApplyById(String id);
}
