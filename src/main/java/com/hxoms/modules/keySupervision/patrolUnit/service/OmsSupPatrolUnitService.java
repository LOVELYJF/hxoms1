package com.hxoms.modules.keySupervision.patrolUnit.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hxoms.modules.keySupervision.patrolUnit.entity.OmsSupPatrolUnit;


/**
 * <b>被巡视单位信息业务层接口</b>
 * @author luoshuai
 * @date 2020/5/19 9:15
 */
public interface OmsSupPatrolUnitService {

	/**
	 * <b>查询被巡视单位信息</b>
	 * @param omsSupPatrolUnit
	 * @param page
	 * @return
	 */
	Page<OmsSupPatrolUnit> getPatrolUnitInfo(Page<OmsSupPatrolUnit> page, OmsSupPatrolUnit omsSupPatrolUnit);


	/**
	 * <b>增加被巡视单位</b>
	 * @param omsSupPatrolUnit
	 * @return
	 */
	void addPatrolUnitInfo(OmsSupPatrolUnit omsSupPatrolUnit);


	/**
	 * <b>修改被巡视单位信息</b>
	 * @param omsSupPatrolUnit
	 * @return
	 */
	void updatePatrolUnitInfo(OmsSupPatrolUnit omsSupPatrolUnit);


	/**
	 * <b>删除被巡视单位</b>
	 * @param omsSupPatrolUnit
	 * @return
	 */
	void removePatrolUnitInfo(OmsSupPatrolUnit omsSupPatrolUnit);
}
