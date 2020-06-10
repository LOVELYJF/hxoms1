package com.hxoms.modules.keySupervision.suspendApproval.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hxoms.common.exception.CustomMessageException;
import com.hxoms.modules.keySupervision.suspendApproval.entity.OmsSupSuspendUnit;
import com.hxoms.modules.keySupervision.suspendApproval.mapper.OmsSupSuspendUnitMapper;
import com.hxoms.modules.keySupervision.suspendApproval.service.OmsSupSuspendUnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <b>暂停出入境管理审批模块业务层接口实现类</b>
 * @author luoshuai
 * @date 2020/5/19 14:17
 */
@Service
public class OmsSupSuspendUnitServiceImpl extends ServiceImpl<OmsSupSuspendUnitMapper,OmsSupSuspendUnit> implements OmsSupSuspendUnitService {


	@Autowired
	private OmsSupSuspendUnitMapper omsSupSuspendUnitMapper;
	/**
	 * <b>查询暂停单位信息</b>
	 * @param omsSupSuspendUnit
	 * @param page
	 * @return
	 */
	public Page<OmsSupSuspendUnit> getSuspendUnitInfo(Page<OmsSupSuspendUnit> page, OmsSupSuspendUnit omsSupSuspendUnit) {
		QueryWrapper<OmsSupSuspendUnit> queryWrapper = new QueryWrapper<OmsSupSuspendUnit>();
		queryWrapper
				.between(omsSupSuspendUnit.getSuspendStratTimeQuery() != null && omsSupSuspendUnit.getSuspendEndTimeQuery() != null,
						"SUSPEND_TIME", omsSupSuspendUnit.getSuspendStratTimeQuery(), omsSupSuspendUnit.getSuspendEndTimeQuery())
				.like(omsSupSuspendUnit.getUnit() != null && omsSupSuspendUnit.getUnit() != "",
				"UNIT",omsSupSuspendUnit.getUnit());
		PageHelper.startPage((int) page.getCurrent(), (int) page.getSize());
		List<OmsSupSuspendUnit> resultList = omsSupSuspendUnitMapper.selectList(queryWrapper);
		PageInfo<OmsSupSuspendUnit> pageInfo = new PageInfo<OmsSupSuspendUnit>(resultList);
		page.setPages(pageInfo.getPages());
		page.setTotal(pageInfo.getTotal());
		page.setPages(pageInfo.getPages());
		page.setRecords(resultList);
		return page;
	}


	/**
	 * <b>修改暂停审批单位信息(允许审批)</b>
	 * @param list
	 * @return
	 */
	@Transactional(rollbackFor=Exception.class)
	public void updateSuspendUnitInfo(List<OmsSupSuspendUnit> list) {
		boolean flag = updateBatchById(list);
		if(!flag){
			throw new CustomMessageException("修改暂停状态失败");
		}
	}



}
