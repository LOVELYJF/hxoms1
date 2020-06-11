package com.hxoms.modules.keySupervision.suspendApproval.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hxoms.modules.keySupervision.suspendApproval.entity.OmsSupSuspendUnit;

import java.util.List;

/**
 * <b>暂停出入境管理审批模块业务层接口</b>
 * @author luoshuai
 * @date 2020/5/19 14:17
 */
public interface OmsSupSuspendUnitService {

	/**
	 * <b>查询暂停单位信息</b>
	 * @param omsSupSuspendUnit
	 * @param page
	 * @return
	 */
	Page<OmsSupSuspendUnit> getSuspendUnitInfo(Page<OmsSupSuspendUnit> page, OmsSupSuspendUnit omsSupSuspendUnit);


	/**
	 * <b>修改暂停审批单位信息(允许审批)</b>
	 * @param idList
	 * @return
	 */
	void updateSuspendUnitInfo(List<String> idList);


}
