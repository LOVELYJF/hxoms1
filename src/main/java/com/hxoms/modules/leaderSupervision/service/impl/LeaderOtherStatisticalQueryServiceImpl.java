package com.hxoms.modules.leaderSupervision.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.hxoms.common.utils.Constants;
import com.hxoms.common.utils.PageUtil;
import com.hxoms.common.utils.Result;
import com.hxoms.modules.leaderSupervision.entity.OmsLeaderBatch;
import com.hxoms.modules.leaderSupervision.mapper.LeaderCommonMapper;
import com.hxoms.modules.leaderSupervision.mapper.OmsLeaderBatchMapper;
import com.hxoms.modules.leaderSupervision.service.LeaderOtherStatisticalQueryService;
import com.hxoms.modules.leaderSupervision.until.LeaderSupervisionUntil;
import com.hxoms.modules.leaderSupervision.vo.LeaderSupervisionVo;
import com.hxoms.modules.privateabroad.entity.OmsPriApplyVO;
import com.hxoms.modules.privateabroad.entity.paramentity.OmsPriApplyIPageParam;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.lang3.StringUtils;
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

   // 代办业务管理模块 左边列表
   public List<Map> selectLeaderBussinessProcess(){

      List<Map> businessFlowWithASNum = leaderCommonMapper.selectBusinessFlowWithASNum();


       return businessFlowWithASNum;

   }

   @Override
   public Map selectBatchlistShapePortrait() {

      Map<String,List>  map = new LinkedHashMap();

      // 查询批次
      QueryWrapper<OmsLeaderBatch> leaderBatch_wrapper = new QueryWrapper<OmsLeaderBatch>();
      leaderBatch_wrapper.select("id , name,master_status as status ");
      List<Map<String, Object>> lists = omsLeaderBatchMapper.selectMaps(leaderBatch_wrapper);

//      int a = Constants.leader_business[Constants.leader_business.length-1];

      map.put("待处理列表",lists.stream().filter((Map m)-> Integer.valueOf(m.get("status").toString()) < Constants.leader_business[Constants.leader_business.length-1]).collect(Collectors.toList()));
      map.put("已处理列表",lists.stream().filter((Map m)-> Integer.valueOf(m.get("status").toString()) == Constants.leader_business[Constants.leader_business.length-1]).collect(Collectors.toList()));


      return map;
   }

   @Override
   public PageInfo selectAllOmsPriApplyManange(OmsPriApplyIPageParam omsPriApplyIPageParam) {


      if (!StringUtils.isBlank(omsPriApplyIPageParam.getApplyStatusString())){
         String[] applyStatus = omsPriApplyIPageParam.getApplyStatusString().split(",");
         omsPriApplyIPageParam.setApplyStatus((Integer[]) ConvertUtils.convert(applyStatus, Integer.class));
      }
      //分页
      PageUtil.pageHelp(omsPriApplyIPageParam.getPageNum() == null ? 1 : omsPriApplyIPageParam.getPageNum(),
              omsPriApplyIPageParam.getPageSize() == null ? 10 : omsPriApplyIPageParam.getPageSize());
      List<Map> ures = leaderCommonMapper.selectPrivateApplyManager(omsPriApplyIPageParam);
      //返回数据
      PageInfo<OmsPriApplyVO> pageInfo = new PageInfo(ures);
      return pageInfo;

   }


}
