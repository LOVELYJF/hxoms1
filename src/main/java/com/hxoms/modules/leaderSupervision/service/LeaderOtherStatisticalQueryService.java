package com.hxoms.modules.leaderSupervision.service;

import com.github.pagehelper.PageInfo;
import com.hxoms.modules.leaderSupervision.vo.JiweiStatisticsVo;
import com.hxoms.modules.leaderSupervision.vo.LeaderSupervisionVo;
import com.hxoms.modules.privateabroad.entity.paramentity.OmsPriApplyIPageParam;
import com.hxoms.modules.publicity.entity.OmsPubApplyQueryParam;

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

   /** 因公出国境申请 管理 **/
   PageInfo selectAllOmsPubApplyManange(OmsPubApplyQueryParam omsPubApplyQueryParam);

   /** 终止备案 **/

   void terminationPutOnRecords(LeaderSupervisionVo leaderSupervisionVo);

   /**因私出国境延期申请 **/

   PageInfo selectAllOmsPriDelayApplyManage(OmsPriApplyIPageParam omsPriApplyIPageParam);


   /**征求纪委意见 回复 情况 明细查询 **/

   PageInfo selectjieweiOpinionDetail(JiweiStatisticsVo JiweiStatisticsVo);


}
