package com.hxoms.support.customquery.service.impl;

import com.hxoms.common.CustomMessageException;
import com.hxoms.common.utils.UUIDGenerator;
import com.hxoms.support.customquery.entity.ShareQueryPlan;
import com.hxoms.support.customquery.entity.paramentity.SharePlanParam;
import com.hxoms.support.customquery.mapper.ShareQueryPlanMapper;
import com.hxoms.support.customquery.service.ShareQueryPlanService;
import com.hxoms.support.user.entity.User;
import com.hxoms.support.user.mapper.UserMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @desc: 分享方案
 * @author: lijing
 * @date: 2019/8/9
 */
@Service
public class ShareQueryPlanServiceImpl implements ShareQueryPlanService {
    @Autowired
    private ShareQueryPlanMapper shareQueryPlanMapper;
    @Autowired
    private UserMapper userMapper;

    @Transactional(rollbackFor=Exception.class)
    @Override
    public void insertShareQuery(SharePlanParam sharePlanParam) {
        //插入
        for (ShareQueryPlan shareQueryPlan : sharePlanParam.getShareQueryPlanList() ) {
            shareQueryPlan.setId(UUIDGenerator.getPrimaryKey());
            int insert = shareQueryPlanMapper.insertSelective(shareQueryPlan);
            if (insert == 0){
                throw new CustomMessageException("分享失败");
            }
        }
        //删除
        for (ShareQueryPlan shareQueryPlan : sharePlanParam.getShareQueryPlanDeleteList() ) {
            int delete = shareQueryPlanMapper.deleteShareUser(shareQueryPlan);
            if (delete == 0){
                throw new CustomMessageException("分享失败");
            }
        }
    }

    @Override
    public Map<String, Object> shareQueryList(String queryPlanId) {
        Map<String, Object> resultMap = new HashMap<>();
        if (StringUtils.isEmpty(queryPlanId)){
            throw new CustomMessageException("请选择需要分享的方案");
        }
        //用户列表
        List<User> users = userMapper.selectAllUser();
        //已分享用户列表
        List<String> shareUsers = shareQueryPlanMapper.selectShareUsers(queryPlanId);
        resultMap.put("users", users);
        resultMap.put("shareUsers", shareUsers);
        return resultMap;
    }
}
