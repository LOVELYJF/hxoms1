package com.hxoms.modules.condition.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hxoms.common.exception.CustomMessageException;
import com.hxoms.common.utils.Constants;
import com.hxoms.common.utils.UserInfo;
import com.hxoms.common.utils.UserInfoUtil;
import com.hxoms.modules.condition.entity.ConditionReplaceVO;
import com.hxoms.modules.condition.entity.OmsCondition;
import com.hxoms.modules.condition.mapper.OmsConditionMapper;
import com.hxoms.modules.condition.service.OmsConditionService;
import com.hxoms.modules.file.entity.OmsReplaceKeywords;
import com.hxoms.modules.file.mapper.OmsReplaceKeywordsMapper;
import com.hxoms.modules.privateabroad.entity.OmsPriApply;
import com.hxoms.modules.privateabroad.mapper.OmsPriApplyMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
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
    @Autowired
    private OmsReplaceKeywordsMapper omsReplaceKeywordsMapper;

    @Override
    public List<Map<String, String>> checkCondition(String applyId, String type) {
        //登录用户信息
        UserInfo userInfo = UserInfoUtil.getUserInfo();
        if (StringUtils.isBlank(applyId) || StringUtils.isBlank(type)){
            throw new CustomMessageException("参数错误");
        }
        String a0100 = "";
        if (Constants.oms_business[1].equals(type) || Constants.oms_business[2].equals(type)){
            //因私出国, 延期回国
            OmsPriApply omsPriApply = omsPriApplyMapper.selectById(applyId);
            if (omsPriApply == null){
                throw new CustomMessageException("申请不存在");
            }
            a0100 = omsPriApply.getA0100();
        }
        ConditionReplaceVO conditionReplaceVO = new ConditionReplaceVO();
        conditionReplaceVO.setA0100(a0100);
        conditionReplaceVO.setApplyId(applyId);
        conditionReplaceVO.setHandleId(userInfo.getId());
        return checkApply(conditionReplaceVO, Constants.OMS_CONDITION_CHECKTYPE[1], type);
    }

    @Override
    public List<Map<String, String>> checkConditionByA0100(String a0100, String type) {
        if (StringUtils.isBlank(a0100) || StringUtils.isBlank(type)){
            throw new CustomMessageException("参数错误");
        }
        //登录用户信息
        UserInfo userInfo = UserInfoUtil.getUserInfo();
        ConditionReplaceVO conditionReplaceVO = new ConditionReplaceVO();
        conditionReplaceVO.setA0100(a0100);
        conditionReplaceVO.setHandleId(userInfo.getId());
        return checkApply(conditionReplaceVO, Constants.OMS_CONDITION_CHECKTYPE[0], type);
    }

    /**
     * 申请信息校验
     * @param conditionReplaceVO 申请信息
     * @param checkType 保存前， 保存后(全部校验时传空)
     * @param type 因公 因私 延期回国
     * @return
     */
    public List<Map<String, String>> checkApply(ConditionReplaceVO conditionReplaceVO, String checkType, String type) {
        //结果
        List<Map<String, String>> result = new ArrayList<>();
        //登录用户信息
        UserInfo userInfo = UserInfoUtil.getUserInfo();
        //约束条件
        QueryWrapper<OmsCondition> queryWrapper = new QueryWrapper<>();
        if (!StringUtils.isBlank(checkType)){
            queryWrapper.eq("CHECK_TYPE", 1);
        }
        queryWrapper.eq("condition_type", type)
                .orderByAsc("check_type");
        List<OmsCondition> omsConditions = omsConditionMapper.selectList(queryWrapper);
        if (omsConditions != null && omsConditions.size() > 0){
            //查询关键词
            QueryWrapper<OmsReplaceKeywords> keywords = new QueryWrapper<>();
            keywords.eq("USE_TYPE", Constants.OMS_KEYWORDS_USERTYPE[1])
                    .eq("TYPE", type);
            List<OmsReplaceKeywords> omsReplaceKeywords = omsReplaceKeywordsMapper.selectList(keywords);
            //检验条件
            for (OmsCondition omsCondition : omsConditions) {
                String sql = omsCondition.getSqlContent();
                if (!StringUtils.isBlank(sql)) {
                    Map<String, String> map = new HashMap<>();
                    //替换关键词
                    Class clazz = conditionReplaceVO.getClass();
                    for (OmsReplaceKeywords item : omsReplaceKeywords) {
                        try {
                            String value = (String) clazz.getDeclaredMethod(item.getReplaceField()).invoke(conditionReplaceVO);
                            sql = sql.replaceAll(item.getKeyword(), value);
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                            throw new CustomMessageException("数据异常");
                        } catch (InvocationTargetException e) {
                            e.printStackTrace();
                            throw new CustomMessageException("数据异常");
                        } catch (NoSuchMethodException e) {
                            e.printStackTrace();
                            throw new CustomMessageException("数据异常");
                        }
                    }
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
