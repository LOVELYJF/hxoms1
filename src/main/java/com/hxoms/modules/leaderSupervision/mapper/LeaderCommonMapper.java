package com.hxoms.modules.leaderSupervision.mapper;

import com.hxoms.modules.leaderSupervision.vo.AuditOpinionVo;
import com.hxoms.modules.leaderSupervision.vo.JiweiStatisticsVo;
import com.hxoms.modules.leaderSupervision.vo.LeaderSupervisionVo;
import com.hxoms.modules.privateabroad.entity.paramentity.OmsPriApplyIPageParam;
import com.hxoms.modules.publicity.entity.OmsPubApplyQueryParam;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface LeaderCommonMapper {

    /** 业务处理 选择 人员 **/
   List<Map> selectBusinessUser(@Param("applyStatus") int applyStatus, @Param("leaderBtachId") String leaderBtachId,@Param("userName") String userName,@Param("bussinessType") String bussinessType);

   /**  业务受理 材料审核 人员 **/

   List<Map>  selectMaterialReviewBusinessUser();

   int  selectMaxStatusByBatchId(String batchId);

   List<Map>  selectStatusByBatchId(String batchId);

   /** 根据所选的 业务流程Id 去查询 包含多少个批次 **/

   List<Map>  selectLeaderBatchIdByBussinessIds(List bussinessIds);

   /**  查询业务流程及其数量 **/
   List<Map> selectBusinessFlowWithASNum();

   /** 查询批次 **/

   List<Map> selectLeaderBatch();
   /** 征求纪委 意见 查询页面 **/
   List<Map> selectJiweiApply(@Param("bussinessIds")Object[] bussinessIds,@Param("clshsftg") String clshsftg );

   /** 记录纪委 意见 查询 页面 **/

   List<Map> selectJiweiWriteApply(Object[] bussinessIds);
   /**查询纪委意见人员 就保存 **/
   List<Map> selectJiweiWriteSave();

   /** 查询 因私出国境（申请）管理 ***/

   List<Map> selectPrivateApplyManager(@Param("params") OmsPriApplyIPageParam omsPriApplyIPageParam);

   /** 查询 因公出国境（申请）管理 ***/
   List<Map> selectPulicApplyManager(@Param("omsPubApplyQueryParam") OmsPubApplyQueryParam omsPubApplyQueryParam);

   /** 查询 因私延期出国境（申请）管理 ***/
   List<Map> selectPridelayApplyManager(@Param("omsPriApplyIPageParam") OmsPriApplyIPageParam omsPriApplyIPageParam);

   /** 征求纪委意见(统计查询) 查询情况 **/

   List<Map>  selectjieweiOpinionCase(@Param("jiweiStatisticsVo") JiweiStatisticsVo jiweiStatisticsVo);

   /** 征求纪委意见 明细查询 **/

   List<Map>  selectjieweiOpinionDetail(@Param("jiweiStatisticsVo") JiweiStatisticsVo jiweiStatisticsVo);


   /** 做出审核意见 查询业务流程（因公） 页面 **/

   List<Map>  selectAuditOpinionOmsPua(AuditOpinionVo auditOpinionVo);

    /** 做出审核意见 查询业务流程(因私) 页面 **/
    List<Map>  selectAuditOpinionOmsPri(AuditOpinionVo auditOpinionVo);
    /**做出审核意见 查询业务流程(延期) 页面**/

    List<Map>  selectAuditOpinionOmsPriDelay(AuditOpinionVo auditOpinionVo);


    /** 处领导审批 查询业务流程 页面   **/

   List<Map> selectChuZhangApprover(LeaderSupervisionVo leaderSupervisionVo);

   /*** 部领导 审批 查询业务流程 页面 ***/

   List<Map> selectBuZhangApprover(LeaderSupervisionVo leaderSupervisionVo);

   /** 批件核实 查询 业务流程 页面 **/

   List<Map> selectInstructionsVerify(LeaderSupervisionVo leaderSupervisionVo);

   /**查询 经办人 */
   List<Map> selectAgent(@Param("applyId") String applyId, @Param("tableCode") String tableCode);

   /** 通知经办人重新递交备案函 **/
   List<Map> selectSendAgentMessage(String applyId);

   /** 查询材料 检查项 是否通过 **/
   List<Map> selectMaterialReview(String applyId);

   /**申请类型 查询 条件 **/

   List<Map> selectgetApplicationType();

   /**
    * 查看附件列表
    * ***/
   List<Map> selectAttachmentList(String[] leaderBatchIds);

   /**查询团组 **/

   List<Map> selectGroupConditions();

   Map  selectDefaultId();

   /**生成备案表 查询列表 **/

   List<Map> createPutOnRecordList(LeaderSupervisionVo leaderSupervisionVo,@Param("bussinessIds") Object[] bussinessIds);
}
