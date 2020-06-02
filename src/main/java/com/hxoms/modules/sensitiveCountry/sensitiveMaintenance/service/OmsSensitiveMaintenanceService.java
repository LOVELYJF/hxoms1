package com.hxoms.modules.sensitiveCountry.sensitiveMaintenance.service;

import com.hxoms.modules.sensitiveCountry.sensitiveLimited.entity.OmsSensitiveCountry;
import com.hxoms.support.sysdict.entity.SysDictItem;

import java.util.List;

/**
 * <b>敏感性国家维护业务层接口</b>
 * @author luoshuai
 * @date 2020/5/25 17:49
 */
public interface OmsSensitiveMaintenanceService {

	/**
	 * <b>查询所有的国家</b>
	 * @return
	 */
	List<SysDictItem> getAreaList();


	/**
	 * <b>查询各个限制地区</b>
	 * @return
	 */
	List<OmsSensitiveCountry> getSensitiveCountryMaintainInfo();


	/**
	 * <b>添加敏感地区</b>
	 * @return
	 */
	void addSensitiveCountryMaintainInfo(OmsSensitiveCountry omsSensitiveCountry);


	/**
	 * <b>移除敏感地区</b>
	 * @return
	 */
	void removeSensitiveCountryMaintainInfo(OmsSensitiveCountry omsSensitiveCountry);

}
