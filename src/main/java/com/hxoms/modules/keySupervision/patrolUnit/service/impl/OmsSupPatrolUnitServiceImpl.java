package com.hxoms.modules.keySupervision.patrolUnit.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hxoms.common.exception.CustomMessageException;
import com.hxoms.common.utils.UUIDGenerator;
import com.hxoms.common.utils.UserInfoUtil;
import com.hxoms.modules.keySupervision.patrolUnit.entity.OmsSupPatrolUnit;
import com.hxoms.modules.keySupervision.patrolUnit.mapper.OmsSupPatrolUnitMapper;
import com.hxoms.modules.keySupervision.patrolUnit.service.OmsSupPatrolUnitService;
import com.hxoms.support.b01.entity.B01;
import com.hxoms.support.b01.mapper.B01Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <b>被巡视单位信息业务层接口实现类</b>
 * @author luoshuai
 * @date 2020/5/19 9:17
 */
@Service
public class OmsSupPatrolUnitServiceImpl implements OmsSupPatrolUnitService {


	@Autowired
	private OmsSupPatrolUnitMapper omsSupPatrolUnitMapper;
	@Autowired
	private B01Mapper b01Mapper;
	/**
	 * <b>查询被巡视单位信息</b>
	 * @param omsSupPatrolUnit
	 * @param page
	 * @return
	 */
	public Page<OmsSupPatrolUnit> getPatrolUnitInfo(Page<OmsSupPatrolUnit> page, OmsSupPatrolUnit omsSupPatrolUnit) {
		QueryWrapper<OmsSupPatrolUnit> queryWrapper = new QueryWrapper<OmsSupPatrolUnit>();
		queryWrapper
				.eq("PU_STATUS", "1")
				.between(omsSupPatrolUnit.getPatrolStartTimeQuery() != null && omsSupPatrolUnit.getPatrolEndTimeQuery() != null,
						"PATROL_START_TIME", omsSupPatrolUnit.getPatrolStartTimeQuery(), omsSupPatrolUnit.getPatrolEndTimeQuery())
				.like(omsSupPatrolUnit.getUnit() != null && omsSupPatrolUnit.getUnit() != "",
						"UNIT", omsSupPatrolUnit.getUnit());

		PageHelper.startPage((int) page.getCurrent(), (int) page.getSize());
		List<OmsSupPatrolUnit> resultList = omsSupPatrolUnitMapper.selectList(queryWrapper);
		PageInfo<OmsSupPatrolUnit> pageInfo = new PageInfo<OmsSupPatrolUnit>(resultList);
		page.setPages(pageInfo.getPages());
		page.setTotal(pageInfo.getTotal());
		page.setRecords(resultList);
		return page;
	}



	/**
	 * <b>增加被巡视单位</b>
	 * @param omsSupPatrolUnit
	 * @return
	 */
	@Transactional(rollbackFor=Exception.class)
	public void addPatrolUnitInfo(OmsSupPatrolUnit omsSupPatrolUnit) {

		omsSupPatrolUnit.setId(UUIDGenerator.getPrimaryKey());
		omsSupPatrolUnit.setCreateTime(new Date());
		omsSupPatrolUnit.setCreateUser(UserInfoUtil.getUserInfo().getUserName());
		omsSupPatrolUnit.setPuStatus("1");
		int count = omsSupPatrolUnitMapper.insert(omsSupPatrolUnit);
		if(count <= 0){
			throw new CustomMessageException("增加被巡视单位失败");
		}
	}


	/**
	 * <b>修改被巡视单位信息</b>
	 * @param omsSupPatrolUnit
	 * @return
	 */
	@Transactional(rollbackFor=Exception.class)
	public void updatePatrolUnitInfo(OmsSupPatrolUnit omsSupPatrolUnit) {
		//根据主键修改
		omsSupPatrolUnit.setModifyTime(new Date());
		omsSupPatrolUnit.setModifyUser(UserInfoUtil.getUserInfo().getUserName());
		int count = omsSupPatrolUnitMapper.updateById(omsSupPatrolUnit);
		if(count <= 0){
			throw new CustomMessageException("修改被巡视单位失败");
		}
	}


	/**
	 * <b>删除被巡视单位</b>
	 * @param omsSupPatrolUnit
	 * @return
	 */
	@Transactional(rollbackFor=Exception.class)
	public void removePatrolUnitInfo(OmsSupPatrolUnit omsSupPatrolUnit) {
		omsSupPatrolUnit.setPuStatus("0");
		omsSupPatrolUnit.setModifyTime(new Date());
		omsSupPatrolUnit.setModifyUser(UserInfoUtil.getUserInfo().getUserName());
		int count = omsSupPatrolUnitMapper.updateById(omsSupPatrolUnit);
		if(count <= 0){
			throw new CustomMessageException("删除被巡视单位失败");
		}
	}
}
