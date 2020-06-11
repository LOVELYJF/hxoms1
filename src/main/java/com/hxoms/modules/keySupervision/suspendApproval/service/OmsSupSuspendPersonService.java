package com.hxoms.modules.keySupervision.suspendApproval.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hxoms.modules.keySupervision.suspendApproval.entity.OmsSupSuspendPerson;

import java.util.List;
/**
 * <b>暂停出入境管理审批模块业务层接口</b>
 * @author luoshuai
 * @date 2020/5/20 17:04
 */
public interface OmsSupSuspendPersonService {


	/**
	 * <b>查询暂停审批人员信息</b>
	 * @param page
	 * @param idList
	 * @return
	 */
	Page<OmsSupSuspendPerson> getSuspendPersonInfo(Page<OmsSupSuspendPerson> page, List<String> idList,
	                                               OmsSupSuspendPerson omsSupSuspendPerson);


	/**
	 * <b>修改暂停审批人员信息(允许审批)</b>
	 * @param idList
	 * @return
	 */
	void updateSuspendPersonInfo(List<String> idList);
}
