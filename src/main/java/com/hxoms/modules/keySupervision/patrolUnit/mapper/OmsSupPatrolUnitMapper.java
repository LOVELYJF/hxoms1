package com.hxoms.modules.keySupervision.patrolUnit.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hxoms.modules.keySupervision.patrolUnit.entity.OmsSupPatrolUnit;

import java.util.Map;

/**
 * <b>被巡视单位信息数据持久层</b>
 * @author luoshuai
 * @date 2020/5/19 9:15
 */
public interface OmsSupPatrolUnitMapper extends BaseMapper<OmsSupPatrolUnit> {


	/**
	 * <b>查询单位是否被巡视</b>
	 * @param map
	 * @return
	 */
	OmsSupPatrolUnit getPatrolUnit(Map<String, Object> map);
}