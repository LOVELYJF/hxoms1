package com.hxoms.modules.leaderSupervision.service.impl;

import com.hxoms.common.utils.Constants;
import com.hxoms.modules.leaderSupervision.mapper.LeaderCommonMapper;
import com.hxoms.modules.leaderSupervision.service.LeaderOtherStatisticalQueryService;
import com.hxoms.modules.leaderSupervision.vo.LeaderSupervisionVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @authore:wjf
 * @data 2020/9/3 16:25
 * @Description:
 ***/
@Service("LeaderOtherStatisticalQueryService")
public class LeaderOtherStatisticalQueryServiceImpl implements LeaderOtherStatisticalQueryService {

   @Autowired
   private LeaderCommonMapper leaderCommonMapper;

   // 代办业务管理模块 左边列表
   public List<Map> selectLeaderBussinessProcess(){

      List<Map> businessFlowWithASNum = leaderCommonMapper.selectBusinessFlowWithASNum();


       return businessFlowWithASNum;

   }

}
