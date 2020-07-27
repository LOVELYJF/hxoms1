package com.hxoms.modules.sensitiveCountry.sensitiveMaintain.service.impl;

import com.hxoms.common.exception.CustomMessageException;
import com.hxoms.modules.country.entity.Country;
import com.hxoms.modules.country.mapper.CountryMapper;
import com.hxoms.modules.sensitiveCountry.sensitiveLimited.mapper.OmsSensitiveLimitMapper;
import com.hxoms.modules.sensitiveCountry.sensitiveMaintain.service.OmsSensitiveMaintainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
	@Autowired
	private OmsSensitiveLimitMapper omsSensitiveLimitMapper;
	/**
	 * <b>查询国家信息</b>
	 * @param nameZh
	 * @return
	 */
	public List<Country> getCountryInfo(String nameZh) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("nameZh", nameZh);
		List<Country> list = countryMapper.getCountryInfo(map);
		return list;
	}


	/**
	 * <b>功能描述: 查询限制性国家信息</b>
	 * @Param: [sensitiveLimitId]
	 * @Return: java.util.List<java.lang.Integer>
	 * @Author: luoshuai
	 * @Date: 2020/7/14 20:11
	 */
	public List<Integer> getSensitiveMaintain(String sensitiveLimitId) {
		List<Integer> list = omsSensitiveLimitMapper.getSensitiveMaintain(sensitiveLimitId);
		if(list != null && list.size() > 0){
			return list;
		}
		return new ArrayList<Integer>();
	}


	/**
	 * <b>保存敏感国家信息</b>
	 * @param countryIdList
	 * @param sensitiveLimitId
	 * @return
	 */
	public void addCountryInfo(List<Integer> countryIdList, String sensitiveLimitId) {
		//查询当前限制性对应的内容有哪些
		List<Integer> list = omsSensitiveLimitMapper.getSensitiveMaintain(sensitiveLimitId);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("sensitiveLimitId", sensitiveLimitId);
		if (list == null || list.size() < 1) {
			if (countryIdList != null && countryIdList.size() > 0) {
				for (Integer id : countryIdList) {
					map.put("id", id);
					int result = countryMapper.addSensitiveMaintain(map);
					if (result < 1) {
						throw new CustomMessageException("保存失败");
					}
				}
			}
		} else {
			//将当前的限制性国家删除
			int count = countryMapper.deleteSensitiveMaintain(sensitiveLimitId);
			if (count > 0) {
				if (countryIdList != null && countryIdList.size() > 0) {
					for (Integer id : countryIdList) {
						map.put("id", id);
						int result = countryMapper.addSensitiveMaintain(map);
						if (result < 1) {
							throw new CustomMessageException("保存失败");
						}
					}
				}
			}
		}
	}
}
