package com.hxoms.modules.condition.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hxoms.common.exception.CustomMessageException;
import com.hxoms.common.utils.UserInfo;
import com.hxoms.common.utils.UserInfoUtil;
import com.hxoms.modules.condition.entity.OmsCondition;
import com.hxoms.modules.condition.mapper.OmsConditionMapper;
import com.hxoms.modules.condition.service.OmsConditionService;
import com.hxoms.modules.privateabroad.entity.OmsPriApply;
import com.hxoms.modules.privateabroad.mapper.OmsPriApplyMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @desc: 约束条件管理
 * @author: lijing
 * @date: 2020-05-28
 */
@Service
public class OmsConditionServiceImpl implements OmsConditionService {

    @Autowired
    private OmsConditionMapper omsConditionMapper;
    @Autowired
    private OmsPriApplyMapper omsPriApplyMapper;

    @Override
    public List<Map<String, String>> checkCondition(String applyId, String type) {
        if (StringUtils.isBlank(applyId) || StringUtils.isBlank(type)){
            throw new CustomMessageException("参数错误");
        }
        String a0100 = "";
        if ("oms_pri_apply".equals(type) || "oms_pri_delay_apply".equals(type)){
            //因私出国, 因公出国
            OmsPriApply omsPriApply = omsPriApplyMapper.selectById(applyId);
            if (omsPriApply == null){
                throw new CustomMessageException("申请不存在");
            }
            a0100 = omsPriApply.getA0100();
        }
        return checkPriApply(a0100, applyId, type);
    }

    /**
     * 申请信息校验
     * @param a0100 申请信息
     * @param applyId 申请id
     * @param type 因公 因私 延期回国
     * @return
     */
    public List<Map<String, String>> checkPriApply(String a0100, String applyId, String type) {
        //结果
        List<Map<String, String>> result = new ArrayList<>();
        //登录用户信息
        UserInfo userInfo = UserInfoUtil.getUserInfo();
        //约束条件
        QueryWrapper<OmsCondition> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("condition_type", type)
                .orderByAsc("check_type");
        List<OmsCondition> omsConditions = omsConditionMapper.selectList(queryWrapper);

        if (omsConditions != null && omsConditions.size() > 0){
            //检验条件
            for (OmsCondition omsCondition : omsConditions) {
                String sql = omsCondition.getSqlContent();
                if (!StringUtils.isBlank(sql)) {
                    Map<String, String> map = new HashMap<>();
                    sql = sql
                            .replace("@a0100", a0100)
                            .replace("@applyId", applyId)
                            .replace("@loginA0100", userInfo.getId());

                    int count = omsConditionMapper.excuteSelectSql(sql);
                    //条件标题
                    map.put("title", omsCondition.getName());
                    map.put("desc", omsCondition.getDescription());
                    if (count > 0) {
                        //不符合条件
                        map.put("isFit" , "0");
                    }else{
                        //符合条件
                        map.put("isFit" , "1");
                    }
                    result.add(map);
                }
            }
        }
        return result;
    }
}
