package com.hxoms.modules.leaderSupervision.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.hxoms.common.utils.Constants;
import com.hxoms.common.utils.PageUtil;
import com.hxoms.common.utils.Result;
import com.hxoms.general.select.entity.SqlVo;
import com.hxoms.general.select.mapper.SelectMapper;
import com.hxoms.modules.leaderSupervision.Enum.BussinessApplyStatus;
import com.hxoms.modules.leaderSupervision.entity.OmsLeaderBatch;
import com.hxoms.modules.leaderSupervision.mapper.LeaderCommonMapper;
import com.hxoms.modules.leaderSupervision.mapper.OmsLeaderBatchMapper;
import com.hxoms.modules.leaderSupervision.service.LeaderOtherStatisticalQueryService;
import com.hxoms.modules.leaderSupervision.until.LeaderSupervisionUntil;
import com.hxoms.modules.leaderSupervision.vo.BussinessTypeAndIdVo;
import com.hxoms.modules.leaderSupervision.vo.JiweiStatisticsVo;
import com.hxoms.modules.leaderSupervision.vo.LeaderSupervisionVo;
import com.hxoms.modules.privateabroad.entity.OmsPriApplyVO;
import com.hxoms.modules.privateabroad.entity.paramentity.OmsPriApplyIPageParam;
import com.hxoms.modules.publicity.entity.OmsPubApplyQueryParam;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @authore:wjf
 * @data 2020/9/3 16:25
 * @Description:
 ***/
@Service("LeaderOtherStatisticalQueryService")
public class LeaderOtherStatisticalQueryServiceImpl implements LeaderOtherStatisticalQueryService {

   @Autowired
   private LeaderCommonMapper leaderCommonMapper;
   @Autowired
   private OmsLeaderBatchMapper omsLeaderBatchMapper;

   @Autowired
   private SelectMapper selectMapper;  // 通用 自定义sql

   private final Logger log = LoggerFactory.getLogger(getClass());

   // 代办业务管理模块 左边列表
   public List<Map> selectLeaderBussinessProcess() {

      List<Map> businessFlowWithASNum = leaderCommonMapper.selectBusinessFlowWithASNum();


      return businessFlowWithASNum;

   }

   @Override
   public Map selectBatchlistShapePortrait() {

      Map<String, List> map = new LinkedHashMap();

      // 查询批次
      QueryWrapper<OmsLeaderBatch> leaderBatch_wrapper = new QueryWrapper<OmsLeaderBatch>();
      leaderBatch_wrapper.select("id , name,master_status as status ");
      List<Map<String, Object>> lists = omsLeaderBatchMapper.selectMaps(leaderBatch_wrapper);

//      int a = Constants.leader_business[Constants.leader_business.length-1];
      // 待处理 列表
      map.put("pendingList", lists.stream().filter((Map m) -> Integer.valueOf(m.get("status").toString()) < Constants.leader_business[Constants.leader_business.length - 1]).collect(Collectors.toList()));
      // 已处理列表
      map.put("pendedList", lists.stream().filter((Map m) -> Integer.valueOf(m.get("status").toString()) == Constants.leader_business[Constants.leader_business.length - 1]).collect(Collectors.toList()));


      return map;
   }

   @Override
   public PageInfo selectAllOmsPriApplyManange(OmsPriApplyIPageParam omsPriApplyIPageParam) {


      if (!StringUtils.isBlank(omsPriApplyIPageParam.getApplyStatusString())) {
         String[] applyStatus = omsPriApplyIPageParam.getApplyStatusString().split(",");
         omsPriApplyIPageParam.setApplyStatus((Integer[]) ConvertUtils.convert(applyStatus, Integer.class));
      }
      //分页
      PageUtil.pageHelp(omsPriApplyIPageParam.getPageNum() == null ? 1 : omsPriApplyIPageParam.getPageNum(),
              omsPriApplyIPageParam.getPageSize() == null ? 10 : omsPriApplyIPageParam.getPageSize());
      List<Map> ures = leaderCommonMapper.selectPrivateApplyManager(omsPriApplyIPageParam);
      //返回数据
      PageInfo pageInfo = new PageInfo(ures);
      return pageInfo;

   }

   public PageInfo selectAllOmsPubApplyManange(OmsPubApplyQueryParam omsPubApplyQueryParam) {

      //分页
      PageUtil.pageHelp(omsPubApplyQueryParam.getPageNum() == null ? 1 : omsPubApplyQueryParam.getPageNum(),
              omsPubApplyQueryParam.getPageSize() == null ? 10 : omsPubApplyQueryParam.getPageSize());
      List<Map> ures = leaderCommonMapper.selectPulicApplyManager(omsPubApplyQueryParam);
      //返回数据
      PageInfo pageInfo = new PageInfo(ures);
      return pageInfo;
   }

   /**
    * 终止登记备案
    **/

   @Override
   public void terminationPutOnRecords(LeaderSupervisionVo leaderSupervisionVo) {

      LeaderSupervisionUntil.throwableByParam(leaderSupervisionVo);

      //第一步 修改流程状态 为已撤销 并且 解除与批次之间的关系

      updateBussinessStatusRevocationed(leaderSupervisionVo.getBussinessTypeAndIdVos());




   }



   private void updateBussinessStatusRevocationed(List<BussinessTypeAndIdVo> bussinessTypeAndIdVos) {

      for (int i = 0; i < bussinessTypeAndIdVos.size(); i++) {

         String bussinessType = LeaderSupervisionUntil.selectorBussinessTypeByName(bussinessTypeAndIdVos.get(i).getBussinessName());


         String updateApplyStatusSql = getUpdateStatusSql(bussinessTypeAndIdVos.get(i).getBussinessId(), bussinessType);

         log.info("修改业务 流程的 sql =" + updateApplyStatusSql);


         if (updateApplyStatusSql.length() > 0) {

            SqlVo instance = SqlVo.getInstance(updateApplyStatusSql);
            selectMapper.update(instance);


         }


      }

   }



   // TODO  统一 修改 业务申请表状态
   public String getUpdateStatusSql(String busessId,String bussinesType){

      String updateSql = "update "+bussinesType;

      String setSql = " set  " ;

      String whereCondition = " where id = '" + busessId+"'";


      for(BussinessApplyStatus applyStatus  : BussinessApplyStatus.values()){

         if(bussinesType.indexOf(applyStatus.getTableName())!=-1){

            String status =  applyStatus.getApplySatus();
            // 干部监督处的状态
            setSql+= status + "=" + Constants.private_business[Constants.private_business.length-1];

            setSql+= ", leader_batch_id = '' ";
            break;


         }

      }

      return  updateSql+setSql+whereCondition;


   }


   @Override
   public PageInfo selectAllOmsPriDelayApplyManage(OmsPriApplyIPageParam omsPriApplyIPageParam) {

       //分页
      PageUtil.pageHelp(omsPriApplyIPageParam.getPageNum(), omsPriApplyIPageParam.getPageSize());

       List<Map> ures = leaderCommonMapper.selectPridelayApplyManager(omsPriApplyIPageParam);
       //返回数据
       PageInfo pageInfo = new PageInfo(ures);
       return pageInfo;
   }


   @Override
   public PageInfo selectjieweiOpinionDetail(JiweiStatisticsVo jiweiStatisticsVo) {

      PageUtil.pageHelp(jiweiStatisticsVo.getPageNum(), jiweiStatisticsVo.getPageSize());

      List<Map> ures  = leaderCommonMapper.selectjieweiOpinionDetail(jiweiStatisticsVo);
      //返回数据
      PageInfo pageInfo = new PageInfo(ures);
      return pageInfo;
   }
}
