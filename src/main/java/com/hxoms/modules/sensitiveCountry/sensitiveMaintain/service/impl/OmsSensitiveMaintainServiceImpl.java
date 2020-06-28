package com.hxoms.modules.sensitiveCountry.sensitiveMaintain.service.impl;

import com.hxoms.common.exception.CustomMessageException;
import com.hxoms.modules.country.entity.Country;
import com.hxoms.modules.country.mapper.CountryMapper;
import com.hxoms.modules.sensitiveCountry.sensitiveMaintain.service.OmsSensitiveMaintainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <b>敏感国家维护业务层接口实现类</b>
 * @author luoshuai
 * @date 2020/06/28
 */
@Service
public class OmsSensitiveMaintainServiceImpl implements OmsSensitiveMaintainService {


	@Autowired
	private CountryMapper countryMapper;
	/**
	 * <b>查询国家信息</b>
	 * @param nameZh
	 * @param sensitiveLimitId
	 * @return
	 */
	public List<Country> getCountryInfo(String nameZh, String sensitiveLimitId) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("nameZh", nameZh);
		map.put("sensitiveLimitId",sensitiveLimitId);
		List<Country> list = countryMapper.getCountryInfo(map);
		return list;
	}


	/**
	 * <b>增加敏感国家信息</b>
	 * @param countryId
	 * @param sensitiveLimitId
	 * @return
	 */
	public void addCountryInfo(Integer countryId, String sensitiveLimitId) {
		//首先查询该敏感国家是否存在
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("countryId", countryId);
		map.put("sensitiveLimitId",sensitiveLimitId);
		List<Map<String,Object>> list = countryMapper.selectSensitiveCountry(map);
		if(list == null || list.size() < 1){
			int country = countryMapper.addCountryInfo(map);
			if(country < 1){
				throw new CustomMessageException("添加失败");
			}
		}
	}


	/**
	 * <b>删除敏感国家信息</b>
	 * @param countryId
	 * @param sensitiveLimitId
	 * @return
	 */
	public void deleteCountryInfo(Integer countryId, String sensitiveLimitId) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("countryId", countryId);
		map.put("sensitiveLimitId",sensitiveLimitId);
		int country = countryMapper.deleteCountryInfo(map);
		if(country < 1){
			throw new CustomMessageException("删除失败");
		}
	}
}
