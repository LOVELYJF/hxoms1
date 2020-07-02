package com.hxoms.modules.condition.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hxoms.modules.condition.entity.OmsCondition;

import java.util.List;
import java.util.Map;

public interface OmsConditionMapper extends BaseMapper<OmsCondition> {

    /**
     * sql执行
     * @param sql
     * @return
     */
    int excuteSelectSql(String sql);

    /**
     * 处分信息
     * @param params
     * @return
     */
    List<String> selectNegativeInfo(Map<String, Object> params);

    /**
     * 约束条件列表
     * @param paramsMap
     * @return
     */
    List<OmsCondition> selectConditionList(Map<String, Object> paramsMap);
}