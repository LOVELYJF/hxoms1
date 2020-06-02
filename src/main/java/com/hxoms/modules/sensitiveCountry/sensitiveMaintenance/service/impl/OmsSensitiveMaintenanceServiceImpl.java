package com.hxoms.modules.sensitiveCountry.sensitiveMaintenance.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hxoms.common.exception.CustomMessageException;
import com.hxoms.common.utils.UUIDGenerator;
import com.hxoms.modules.sensitiveCountry.sensitiveLimited.entity.OmsSensitiveCountry;
import com.hxoms.modules.sensitiveCountry.sensitiveLimited.mapper.OmsSensitiveCountryMapper;
import com.hxoms.modules.sensitiveCountry.sensitiveMaintenance.service.OmsSensitiveMaintenanceService;
import com.hxoms.support.sysdict.entity.SysDictItem;
import com.hxoms.support.sysdict.mapper.SysDictItemMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * <b>敏感性国家维护业务层接口实现类</b>
 * @author luoshuai
 * @date 2020/5/25 17:49
 */
@Service
public class OmsSensitiveMaintenanceServiceImpl implements OmsSensitiveMaintenanceService {


	@Autowired
	private OmsSensitiveCountryMapper omsSensitiveCountryMapper;
	@Autowired
	private SysDictItemMapper sysDictItemMapper;

	/**
	 * <b>查询所有的国家</b>
	 * @return
	 */
	public List<SysDictItem> getAreaList() {
		List<SysDictItem> list = sysDictItemMapper.selectItemCodeByDictCode("GJMC");
		return list;
	}

	/**
	 * <b>查询各个限制地区</b>
	 * @return
	 */
	public List<OmsSensitiveCountry> getSensitiveCountryMaintainInfo() {
		//查询所有地区存在的
		QueryWrapper<OmsSensitiveCountry> queryWrapper = new QueryWrapper<OmsSensitiveCountry>();
		queryWrapper.isNotNull("AREA");
		List<OmsSensitiveCountry> list = omsSensitiveCountryMapper.selectList(queryWrapper);
		if(list != null && list.size() > 0){
			return list;
		}
		return new ArrayList<OmsSensitiveCountry>();
	}


	/**
	 * <b>添加敏感地区</b>
	 * @return
	 */
	@Transactional(rollbackFor=Exception.class)
	public void addSensitiveCountryMaintainInfo(OmsSensitiveCountry omsSensitiveCountry) {
		//查询敏感地区是否存在
		QueryWrapper<OmsSensitiveCountry> queryWrapper = new QueryWrapper<OmsSensitiveCountry>();
		queryWrapper.eq(omsSensitiveCountry.getArea() != null && omsSensitiveCountry.getArea() != "",
				"AREA", omsSensitiveCountry.getArea())
				.eq(omsSensitiveCountry.getParentId() != null,
						"UPPER_LEVEL", omsSensitiveCountry.getParentId());
		OmsSensitiveCountry queryOmsSenstiveCountry = omsSensitiveCountryMapper.selectOne(queryWrapper);
		if(queryOmsSenstiveCountry == null){
			omsSensitiveCountry.setUpperLevel(omsSensitiveCountry.getParentId());
			omsSensitiveCountry.setParentId(null);
			omsSensitiveCountry.setId(UUIDGenerator.getPrimaryKey());
			int count = omsSensitiveCountryMapper.insert(omsSensitiveCountry);
			if(count < 0){
				throw new CustomMessageException("操作失败");
			}
		}

	}


	/**
	 * <b>移除敏感地区</b>
	 * @return
	 */
	@Transactional(rollbackFor=Exception.class)
	public void removeSensitiveCountryMaintainInfo(OmsSensitiveCountry omsSensitiveCountry) {
		QueryWrapper<OmsSensitiveCountry> queryWrapper = new QueryWrapper<OmsSensitiveCountry>();
		queryWrapper.eq(omsSensitiveCountry.getParentId() != null,
				"UPPER_LEVEL", omsSensitiveCountry.getParentId())
				.eq(omsSensitiveCountry.getArea() != null && omsSensitiveCountry.getArea() != "",
						"AREA", omsSensitiveCountry.getArea());
		int count = omsSensitiveCountryMapper.delete(queryWrapper);
		if(count < 0){
			throw new CustomMessageException("操作失败");
		}
	}
}
