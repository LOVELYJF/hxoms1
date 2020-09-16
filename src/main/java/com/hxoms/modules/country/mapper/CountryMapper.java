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


	/**
	 * <b>功能描述: 查询国家信息</b>
	 * @Param: [id]
	 * @Return: com.hxoms.modules.country.entity.Country
	 * @Author: luoshuai
	 * @Date: 2020/9/14 9:13
	 */
	List<Country> selectCountryInfo(String id);

	/**
	 * <b>功能描述: 查询已撤销国家信息</b>
	 * @Param: []
	 * @Return: java.util.List<com.hxoms.modules.country.entity.Country>
	 * @Author: luoshuai
	 * @Date: 2020/9/14 9:27
	 */
	List<Country> selectOmsSensitiveCountry();
}