package com.hxoms.modules.keySupervision.suspendApproval.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hxoms.common.exception.CustomMessageException;
import com.hxoms.common.utils.UserInfoUtil;
import com.hxoms.modules.keySupervision.suspendApproval.entity.OmsSupSuspendUnit;
import com.hxoms.modules.keySupervision.suspendApproval.mapper.OmsSupSuspendUnitMapper;
import com.hxoms.modules.keySupervision.suspendApproval.service.OmsSupSuspendUnitService;
import com.hxoms.support.sysdict.entity.SysDictItem;
import com.hxoms.support.sysdict.mapper.SysDictItemMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
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
	@Autowired
	private SysDictItemMapper sysDictItemMapper;
	/**
	 * <b>查询暂停单位信息</b>
	 * @param omsSupSuspendUnit
	 * @param page
	 * @return
	 */
	public Page<OmsSupSuspendUnit> getSuspendUnitInfo(Page<OmsSupSuspendUnit> page, OmsSupSuspendUnit omsSupSuspendUnit) {
		QueryWrapper<OmsSupSuspendUnit> queryWrapper = new QueryWrapper<OmsSupSuspendUnit>();
		queryWrapper.eq(omsSupSuspendUnit.getStatus() != null && omsSupSuspendUnit.getStatus() != "",
				"STATUS",omsSupSuspendUnit.getStatus())
				.between(omsSupSuspendUnit.getSuspendStratTimeQuery() != null && omsSupSuspendUnit.getSuspendEndTimeQuery() != null,
						"SUSPEND_TIME", omsSupSuspendUnit.getSuspendStratTimeQuery(), omsSupSuspendUnit.getSuspendEndTimeQuery())
				.orderByDesc("SUSPEND_TIME");

		PageHelper.startPage((int) page.getCurrent(), (int) page.getSize());
		List<OmsSupSuspendUnit> resultList = omsSupSuspendUnitMapper.selectList(queryWrapper);
		PageInfo<OmsSupSuspendUnit> pageInfo = new PageInfo<OmsSupSuspendUnit>(resultList);
		page.setPages(pageInfo.getPages());
		page.setTotal(pageInfo.getTotal());
		page.setRecords(resultList);
		return page;
	}


	/**
	 * <b>修改暂停审批单位信息(允许审批)</b>
	 * @param idList
	 * @return
	 */
	@Transactional(rollbackFor=Exception.class)
	public void updateSuspendUnitInfo(List<String> idList) {
		QueryWrapper<OmsSupSuspendUnit> queryWrapper = new QueryWrapper<OmsSupSuspendUnit>();
		queryWrapper.in(idList != null && idList.size() > 0, "ID", idList);
		OmsSupSuspendUnit omsSupSuspendUnit = new OmsSupSuspendUnit();
		omsSupSuspendUnit.setStatus("1");
		omsSupSuspendUnit.setModifyTime(new Date());
		omsSupSuspendUnit.setModifyUser(UserInfoUtil.getUserInfo().getUserName());
		int count = omsSupSuspendUnitMapper.update(omsSupSuspendUnit, queryWrapper);
		if(count < 0){
			throw new CustomMessageException("修改审批状态失败");
		}
	}


	/**
	 * <b>查询审批管理状态</b>
	 * @return
	 */
	public List<SysDictItem> getApprovalStatus() {
		List<SysDictItem> list = sysDictItemMapper.selectSysdictItemListByDictCode("CGSPGL");
		if(list != null && list.size() > 0){
			return list;
		}
		return new ArrayList<SysDictItem>();
	}


}
