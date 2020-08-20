package com.hxoms.modules.country.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hxoms.modules.country.entity.Country;

import java.util.List;
import java.util.Map;

public interface CountryMapper extends BaseMapper<Country> {

	/**
	 * <b>查询国家信息</b>
	 * @param map
	 * @return
	 */
	List<Country> getCountryInfo(Map<String,Object> map);


	/**
	 * <b>查询敏感国家是否存在</b>
	 * @param map
	 * @return
	 */
	List<Map<String, Object>> selectSensitiveCountry(Map<String, Object> map);


	/**
	 * <b>保存敏感国家信息</b>
	 * @param map
	 * @return
	 */
	int addSensitiveMaintain(Map<String, Object> map);

	/**
	 * <b>删除敏感国家信息</b>
	 * @param sensitiveLimitId
	 * @return
	 */
	int deleteSensitiveMaintain(String sensitiveLimitId);

	/**
	 * 根据国家名称查询对应id
	 * @param name
	 * @return
	 */
	String selectIdByName(String name);
}