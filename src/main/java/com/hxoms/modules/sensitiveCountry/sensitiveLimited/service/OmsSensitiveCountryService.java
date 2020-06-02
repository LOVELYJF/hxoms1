package com.hxoms.modules.sensitiveCountry.sensitiveLimited.service;

import com.hxoms.modules.sensitiveCountry.sensitiveLimited.entity.OmsSensitiveCountry;
import com.hxoms.support.sysdict.entity.SysDictItem;

import java.util.List;

/**
 * <b>敏感性国家模块业务层接口</b>
 * @author luoshuai
 * @date 2020/5/22  10:46
 */
public interface OmsSensitiveCountryService {
	/**
	 * <b>查询因公因私限制内容</b>
	 * @param pubPri
	 * @return
	 */
	List<SysDictItem> getSensitiveInfo(String pubPri);


	/**
	 * <b>查询各个限制地区的限制内容</b>
	 * @param pub
	 * @return
	 */
	List<OmsSensitiveCountry> getSensitiveCountryLimitInfo(String pub);


	/**
	 * <b>添加限制性内容</b>
	 * @param omsSensitiveCountry
	 * @return
	 */
	void addSensitiveCountryLimit(OmsSensitiveCountry omsSensitiveCountry);


	/**
	 * <b>移除限制性内容</b>
	 * @param omsSensitiveCountry
	 * @return
	 */
	void deleteSensitiveLimit(OmsSensitiveCountry omsSensitiveCountry);



}
