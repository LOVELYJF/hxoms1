package com.hxoms.support.customquery.mapper;

import com.hxoms.support.customquery.entity.QueryPlan;
import com.hxoms.support.customquery.entity.QueryPlanWithBLOBs;
import com.hxoms.support.customquery.entity.custom.QueryPlanCustom;

import java.util.List;
import java.util.Map;

public interface QueryPlanMapper {
    int deleteByPrimaryKey(String id);

    int insert(QueryPlanWithBLOBs record);

    int insertSelective(QueryPlanWithBLOBs record);

    QueryPlanWithBLOBs selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(QueryPlanWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(QueryPlanWithBLOBs record);

    int updateByPrimaryKey(QueryPlan record);

    /**
     * @desc: 方案列表
     * @author: lijing
     * @date: 2019/8/10
     */
    List<QueryPlanCustom> selectQueryList(Map<String, String> params);
}