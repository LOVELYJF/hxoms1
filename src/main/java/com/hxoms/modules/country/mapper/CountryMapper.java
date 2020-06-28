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
	 * <b>增加敏感国家信息</b>
	 * @param map
	 * @return
	 */
	int addCountryInfo(Map<String, Object> map);

	/**
	 * <b>查询敏感国家是否存在</b>
	 * @param map
	 * @return
	 */
	List<Map<String, Object>> selectSensitiveCountry(Map<String, Object> map);

	/**
	 * <b>删除敏感国家</b>
	 * @param map
	 * @return
	 */
	int deleteCountryInfo(Map<String, Object> map);
}