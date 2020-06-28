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
	 * @param sensitiveLimitId
	 * @return
	 */
	List<Country> getCountryInfo(String nameZh, String sensitiveLimitId);

	/**
	 * <b>增加敏感国家信息</b>
	 * @param countryId
	 * @param sensitiveLimitId
	 * @return
	 */
	void addCountryInfo(Integer countryId, String sensitiveLimitId);

	/**
	 * <b>删除敏感国家信息</b>
	 * @param countryId
	 * @param sensitiveLimitId
	 * @return
	 */
	void deleteCountryInfo(Integer countryId, String sensitiveLimitId);
}
