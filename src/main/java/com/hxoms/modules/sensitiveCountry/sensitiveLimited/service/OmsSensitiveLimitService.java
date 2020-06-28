package com.hxoms.modules.sensitiveCountry.sensitiveLimited.service;

import com.hxoms.modules.sensitiveCountry.sensitiveLimited.entity.OmsSensitiveLimit;
import com.hxoms.support.sysdict.entity.SysDictItem;

import java.util.List;

/**
 * <b>敏感性国家模块业务层接口</b>
 * @author luoshuai
 * @date 2020/5/22  10:46
 */
public interface OmsSensitiveLimitService {
	/**
	 * <b>查询因公因私限制内容</b>
	 * @return
	 */
	List<SysDictItem> getSensitiveInfo();


	/**
	 * <b>查询各个限制地区的限制内容</b>
	 * @param pub
	 * @return
	 */
	List<OmsSensitiveLimit> getSensitiveCountryLimitInfo(String pub);

	/**
	 * <b>添加限制性内容</b>
	 * @param sensitiveItem
	 * @param sensitiveLimitId
	 * @param pubPri
	 */
	void addSensitiveLimit(String sensitiveItem, String sensitiveLimitId, String pubPri);


	/**
	 * <b>删除限制性内容</b>
	 * @param sensitiveItem
	 * @param sensitiveLimitId
	 * @param pubPri
	 */
	void deleteSensitiveLimit(String sensitiveItem, String sensitiveLimitId, String pubPri);





}
