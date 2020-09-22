package com.hxoms.modules.sensitiveCountry.sensitiveLimited.service.impl;

import com.hxoms.common.exception.CustomMessageException;
import com.hxoms.modules.sensitiveCountry.sensitiveLimited.entity.OmsSensitiveLimit;
import com.hxoms.modules.sensitiveCountry.sensitiveLimited.mapper.OmsSensitiveLimitMapper;
import com.hxoms.modules.sensitiveCountry.sensitiveLimited.service.OmsSensitiveLimitService;
import com.hxoms.support.sysdict.entity.SysDictItem;
import com.hxoms.support.sysdict.mapper.SysDictItemMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <b>敏感性国家模块业务层接口实现类</b>
 * @author luoshuai
 * @date 2020/5/22  10:46
 */
@Service
public class OmsSensitiveLimitServiceImpl implements OmsSensitiveLimitService {


	@Autowired
	private SysDictItemMapper sysDictItemMapper;
	@Autowired
	private OmsSensitiveLimitMapper omsSensitiveLimitMapper;
	/**
	 * <b>查询因公因私限制内容</b>
	 * @return
	 */
	public List<SysDictItem> getSensitiveInfo() {
		List<SysDictItem> list = sysDictItemMapper.selectSensitiveLimit();
		return list;
	}


	/**
	 * <b>查询各个限制地区的限制内容</b>
	 * @param pubPri
	 * @return
	 */
	public List<OmsSensitiveLimit> getSensitiveCountryLimitInfo(String pubPri) {
		//查询父节点
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("dictCode", "MGGJ");
		List<OmsSensitiveLimit> list = omsSensitiveLimitMapper.selectOmsSensitiveLimit(map);

		//查询子节点
		Map<String,Object> map1 = new HashMap<String, Object>();
		map1.put("pubPri", pubPri);
		List<Map<String,String>> omsSensitiveList = omsSensitiveLimitMapper.selectOmsSensitive(map1);

		if(omsSensitiveList != null && omsSensitiveList.size() > 0){
			for(OmsSensitiveLimit omsSensitiveLimit : list){
				List<OmsSensitiveLimit> list1 = new ArrayList<OmsSensitiveLimit>();
				for(int i = 0 ; i < omsSensitiveList.size() ; i++){
					if(omsSensitiveList.get(i).get("sensitiveLimitId").equals(omsSensitiveLimit.getId())){
						String sensitiveItem = omsSensitiveList.get(i).get("sensitiveItem");
						//根据主键查询对应的限制内容
						map.put("dictCode",null);
						map.put("itemId", sensitiveItem);
						List<OmsSensitiveLimit> childList = omsSensitiveLimitMapper.selectOmsSensitiveLimit(map);
						for(OmsSensitiveLimit omsSensitiveLimit1 : childList){
							omsSensitiveLimit1.setParentItemCode(omsSensitiveLimit.getId());
						}
						list1.add(childList.get(0));
					}
					omsSensitiveLimit.setList(list1);
				}
			}
		}

		return list;
	}


	/**
	 * <b>添加限制性内容</b>
	 * @param sensitiveItem
	 * @param sensitiveLimitId
	 * @param pubPri
	 */
	@Transactional(rollbackFor=Exception.class)
	public void addSensitiveLimit(List<String> sensitiveItem, String sensitiveLimitId, String pubPri) {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("sensitiveLimitId", sensitiveLimitId);
		map.put("pubPri", pubPri);

		if(sensitiveItem != null && sensitiveItem.size() > 0){
			for(String item : sensitiveItem){
				map.put("sensitiveItem", item);
				List<Map<String,String>> omsSensitiveList = omsSensitiveLimitMapper.selectOmsSensitive(map);
				if(omsSensitiveList.size() < 1 || omsSensitiveList == null){
					int count = omsSensitiveLimitMapper.addSensitiveLimit(map);
					if(count < 1){
						throw new CustomMessageException("添加失败");
					}
				}
			}
		}else {
			throw new CustomMessageException("请选择要添加的项");
		}
	}


	/**
	 * <b>删除限制性内容</b>
	 * @param sensitiveItem
	 * @param sensitiveLimitId
	 * @param pubPri
	 */
	@Transactional(rollbackFor=Exception.class)
	public void deleteSensitiveLimit(List<String> sensitiveItem, String sensitiveLimitId, String pubPri) {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("sensitiveItem", sensitiveItem);
		map.put("sensitiveLimitId", sensitiveLimitId);
		map.put("pubPri", pubPri);
		int count  = omsSensitiveLimitMapper.deleteSensitiveLimit(map);
		if(count < 1){
			throw new CustomMessageException("删除失败");
		}
	}
}














