package com.hxoms.modules.leaderSupervision.service;

import com.github.pagehelper.PageInfo;
import com.hxoms.modules.leaderSupervision.vo.AuditOpinionVo;
import com.hxoms.modules.leaderSupervision.vo.LeaderSupervisionVo;

import java.util.Map;

public interface LeaderCommonService {

    /** 业务办理 选择 人员 页面 **/

    PageInfo selectBusinessUser(LeaderSupervisionVo leaderSupervisionVo, int applyStatus);

    /** 进入生成 批次 页面 **/
    Map createBacthByUsers(LeaderSupervisionVo leaderSupervisionVo);

    /** 保存 批次页面 **/
    void saveBatch(LeaderSupervisionVo leaderSupervisionVo);

    /**材料 审核 人员名单 **/
    PageInfo selectMaterialReviewBusinessUser(LeaderSupervisionVo leaderSupervisionVo) ;

    /** 选择人员 纳入批次 **/

    void leaderBatchAddApplyUser(LeaderSupervisionVo leaderSupervisionVo);

    PageInfo selectAuditOpinionBusinessUser(AuditOpinionVo auditOpinionVo);

    PageInfo selectChuZhangBusinessUser(LeaderSupervisionVo leaderSupervisionVo);

    PageInfo selectBuZhangBusinessUser(LeaderSupervisionVo leaderSupervisionVo);

    PageInfo selectInstructionsVerify(LeaderSupervisionVo leaderSupervisionVo);

    PageInfo selectjiweiBusinessUser(LeaderSupervisionVo leaderSupervisionVo);

    PageInfo selectjiweiWriteBusinessUser(LeaderSupervisionVo leaderSupervisionVo);


    void updateBussinessFiledsByJiweiExport(AuditOpinionVo auditOpinionVo);


}
