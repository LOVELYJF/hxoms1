package com.hxoms.modules.keySupervision.suspendApproval.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hxoms.common.exception.CustomMessageException;
import com.hxoms.modules.keySupervision.suspendApproval.entity.OmsSupSuspendPerson;
import com.hxoms.modules.keySupervision.suspendApproval.mapper.OmsSupSuspendPersonMapper;
import com.hxoms.modules.keySupervision.suspendApproval.service.OmsSupSuspendPersonService;
import com.hxoms.support.b01.mapper.B01Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * <b>暂停出入境管理审批模块业务层接口实现类</b>
 * @author luoshuai
 * @date 2020/5/20 17:04
 */
@Service
public class OmsSupSuspendPersonServiceImpl extends ServiceImpl<OmsSupSuspendPersonMapper,OmsSupSuspendPerson> implements OmsSupSuspendPersonService{


	@Autowired
	private B01Mapper b01Mapper;
	@Autowired
	private OmsSupSuspendPersonMapper omsSupSuspendPersonMapper;
	/**
	 * <b>查询暂停审批人员信息</b>
	 * @param page
	 * @param idList
	 * @return
	 */
	public Page<OmsSupSuspendPerson> getSuspendPersonInfo(Page<OmsSupSuspendPerson> page, List<String> idList,
	                                                      OmsSupSuspendPerson omsSupSuspendPerson) {
		//根据工作单位代码查询工作单位名称
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("idList", idList);
		List<String> list = b01Mapper.selectOrgByList(map);
		QueryWrapper<OmsSupSuspendPerson> queryWrapper = new QueryWrapper<OmsSupSuspendPerson>();
		queryWrapper.in(list != null && list.size() > 0,"WORK_UNIT", list)
				.eq(omsSupSuspendPerson.getName() != null && omsSupSuspendPerson.getName() != "",
						"NAME", omsSupSuspendPerson.getName())
				.eq(omsSupSuspendPerson.getStatus() != null && omsSupSuspendPerson.getStatus() != "",
						"STATUS", omsSupSuspendPerson.getStatus())
				.between(omsSupSuspendPerson.getSuspendStratTimeQuery() != null && omsSupSuspendPerson.getSuspendEndTimeQuery() != null,
						"SUSPEND_TIME", omsSupSuspendPerson.getSuspendStratTimeQuery(), omsSupSuspendPerson.getSuspendEndTimeQuery());


		PageHelper.startPage((int) page.getCurrent(), (int) page.getSize());
		List<OmsSupSuspendPerson> resultList = omsSupSuspendPersonMapper.selectList(queryWrapper);
		PageInfo<OmsSupSuspendPerson> pageInfo = new PageInfo<OmsSupSuspendPerson>(resultList);
		page.setPages(pageInfo.getPages());
		page.setTotal(pageInfo.getTotal());
		page.setPages(pageInfo.getPages());
		page.setRecords(resultList);
		return page;
	}


	/**
	 * <b>修改暂停审批人员信息(允许审批)</b>
	 * @param list
	 * @return
	 */
	@Transactional(rollbackFor=Exception.class)
	public void updateSuspendPersonInfo(List<OmsSupSuspendPerson> list) {
		boolean flag = updateBatchById(list);
		if(!flag){
			throw new CustomMessageException("操作失败");
		}
	}

}