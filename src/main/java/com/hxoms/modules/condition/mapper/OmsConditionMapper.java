package com.hxoms.modules.condition.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hxoms.modules.condition.entity.OmsCondition;

public interface OmsConditionMapper extends BaseMapper<OmsCondition> {

    /**
     * sql执行
     * @param sql
     * @return
     */
    int excuteSelectSql(String sql);
}