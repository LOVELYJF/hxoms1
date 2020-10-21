package com.hxoms.modules.leaderSupervision.service;

import com.github.pagehelper.PageInfo;
import com.hxoms.modules.leaderSupervision.entity.OmsLeaderBatch;
import com.hxoms.modules.leaderSupervision.vo.AuditOpinionVo;
import com.hxoms.modules.leaderSupervision.vo.LeaderSupervisionVo;
import com.hxoms.modules.leaderSupervision.vo.OmsJiweiOpinionVo;

import java.util.List;
import java.util.Map;

public interface LeaderCommonService {

    /** 业务办理 选择 人员 页面 **/

    PageInfo selectBusinessUser(LeaderSupervisionVo leaderSupervisionVo, int applyStatus);

    /** 进入生成 批次 页面 **/
    Map createBacthByUsers();

    /** 保存 批次页面 **/
    void saveBatch(LeaderSupervisionVo leaderSupervisionVo);

    /**材料 审核 人员名单 **/
    PageInfo selectMaterialReviewBusinessUser(LeaderSupervisionVo leaderSupervisionVo) ;

    /** 选择人员 纳入批次 **/

    void leaderBatchAddApplyUser(LeaderSupervisionVo leaderSupervisionVo);
    /** 做出审核意见查询 页面**/
    PageInfo selectAuditOpinionBusinessUser(AuditOpinionVo auditOpinionVo);

    /** 处领导审批页面 **/
    PageInfo selectChuZhangBusinessUser(LeaderSupervisionVo leaderSupervisionVo);

    /** 部长审批页面 **/
    PageInfo selectBuZhangBusinessUser(LeaderSupervisionVo leaderSupervisionVo);

    /** 批件核实页面 **/

    PageInfo selectInstructionsVerify(LeaderSupervisionVo leaderSupervisionVo);

    /**
     * 征求纪委意见 查询
     *
     * **/

    PageInfo selectjiweiBusinessUser(LeaderSupervisionVo leaderSupervisionVo);

    /**
     * 再次征求纪委意见
     * **/
    void  updateBussinessByagainAskFor(String bussinessType,String applyId);

    /**
     *
     * 记录 纪委意见 查询页面
     * **/

    PageInfo selectjiweiWriteBusinessUser(LeaderSupervisionVo leaderSupervisionVo);


    void updateBussinessFiledsByJiweiExport(LeaderSupervisionVo leaderSupervisionVo);

    /**
     *  删除批次
     * **/
    void deleteLeaderBatch(OmsLeaderBatch omsLeaderBatch);

//    void clickJieweiOpinion(OmsJiweiOpinionVo omsJiweiOpinionVo);


    /**
     *  保存纪委意见
     * **/
    void saveJieweiOpinion(OmsJiweiOpinionVo omsJiweiOpinionVo);

    /**
     * 查询 书面纪委意见 需要关联的批次
     * **/
    List<Map> selectOffictJiiweiOpinionRelevanceLeaderBatch();

    /**
     * 进入 处领导审批 逐条 审批 页面
     * **/
    Map goInChuZhangApplyPage(AuditOpinionVo auditOpinionVo);


    /**
     * @authore:wjf
     * @data 2020/9/28 16:25
     * @Description:
     ***/

}
