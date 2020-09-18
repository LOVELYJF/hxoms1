package com.hxoms.modules.leaderSupervision.service;

import com.github.pagehelper.PageInfo;
import com.hxoms.modules.leaderSupervision.vo.LeaderSupervisionVo;
import com.hxoms.modules.privateabroad.entity.paramentity.OmsPriApplyIPageParam;

import java.util.List;
import java.util.Map;

/**
 * @authore:wjf
 * @data 2020/9/3 16:25
 * @Description:
 ***/
public interface LeaderOtherStatisticalQueryService {

   List<Map> selectLeaderBussinessProcess();

   Map selectBatchlistShapePortrait();
   /*** 因私出国境申请 管理**/
   PageInfo selectAllOmsPriApplyManange(OmsPriApplyIPageParam omsPriApplyIPageParam);


}
