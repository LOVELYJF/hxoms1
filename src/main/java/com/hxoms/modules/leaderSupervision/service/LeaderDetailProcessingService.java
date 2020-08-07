package com.hxoms.modules.leaderSupervision.service;

import com.github.pagehelper.PageInfo;
import com.hxoms.modules.leaderSupervision.entity.OmsLeaderBatch;
import com.hxoms.modules.leaderSupervision.vo.LeaderSupervisionVo;

import java.util.List;
import java.util.Map;

/**
 * @authore:wjf
 * @data 2020/7/29 10:28
 * @Description:
 ***/
public interface LeaderDetailProcessingService {

    /**
     *  通知经办人重新递交备案函
     * **/
    void sendMessageToAgent(String applyId,String tableCode);

    /**
     *
     * **/

    List<Map> getApplicationType();

    /**
     * 查询 批次 列表
     * **/

    PageInfo selectOmsLeaderBatch(LeaderSupervisionVo leaderSupervisionVo);

    /**
     *  纳入选择人员 修改 批次
     * **/

    void updateLeaderBatch(OmsLeaderBatch omsLeaderBatch);

    /**
     *  获取 批次 状态
     * **/

    List<Map> selectLeaderBatchStatus();


}
