package com.hxoms.modules.sensitiveCountry.sensitiveMaintain.service;

import com.hxoms.modules.country.entity.Country;

import java.util.List;

/**
 * <b>敏感国家维护业务层接口</b>
 * @author luoshuai
 * @date 2020/06/28
 */
public interface OmsSensitiveMaintainService {

	/**
	 * <b>查询国家信息</b>
	 * @param nameZh
	 * @return
	 */
	List<Country> getCountryInfo(String nameZh);

	/**
	 * <b>功能描述: 查询限制性国家信息</b>
	 * @Param: [sensitiveLimitId]
	 * @Return: java.util.List<java.lang.Integer>
	 * @Author: luoshuai
	 * @Date: 2020/7/14 20:10
	 */
	List<Integer> getSensitiveMaintain(String sensitiveLimitId);

	/**
	 * <b>保存敏感国家信息</b>
	 * @param countryIdList
	 * @param sensitiveLimitId
	 * @return
	 */
	void addCountryInfo(List<Integer> countryIdList, String sensitiveLimitId);


}
