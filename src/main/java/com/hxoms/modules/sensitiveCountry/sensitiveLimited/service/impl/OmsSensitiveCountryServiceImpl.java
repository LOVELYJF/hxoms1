package com.hxoms.modules.sensitiveCountry.sensitiveLimited.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hxoms.common.exception.CustomMessageException;
import com.hxoms.common.utils.UUIDGenerator;
import com.hxoms.modules.sensitiveCountry.sensitiveLimited.entity.OmsSensitiveCountry;
import com.hxoms.modules.sensitiveCountry.sensitiveLimited.mapper.OmsSensitiveCountryMapper;
import com.hxoms.modules.sensitiveCountry.sensitiveLimited.service.OmsSensitiveCountryService;
import com.hxoms.support.sysdict.entity.SysDictItem;
import com.hxoms.support.sysdict.mapper.SysDictItemMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <b>敏感性国家模块业务层接口实现类</b>
 * @author luoshuai
 * @date 2020/5/22  10:46
 */
@Service
public class OmsSensitiveCountryServiceImpl implements OmsSensitiveCountryService {


	@Autowired
	private OmsSensitiveCountryMapper omsSensitiveCountryMapper;
	@Autowired
	private SysDictItemMapper sysDictItemMapper;
	/**
	 * <b>查询因公因私限制内容</b>
	 * @param pubPri
	 * @return
	 */
	public List<SysDictItem> getSensitiveInfo(String pubPri) {
		if(pubPri.equals("1")){
			pubPri = "YGCGLY";
		}else if(pubPri.equals("0")){
			pubPri = "YSCGLY";
		}
		List<SysDictItem> list = sysDictItemMapper.selectSensitiveLimit(pubPri);
		return list;
	}


	/**
	 * <b>查询各个限制地区的限制内容</b>
	 * @param pubPri
	 * @return
	 */
	public List<OmsSensitiveCountry> getSensitiveCountryLimitInfo(String pubPri) {
		//查询父级节点
		QueryWrapper<OmsSensitiveCountry> queryWrapper = new QueryWrapper<OmsSensitiveCountry>();
		queryWrapper.eq(pubPri != null && pubPri != "","PUB_PRI", pubPri)
					.isNotNull("PARENT_ID");
		List<OmsSensitiveCountry> list = omsSensitiveCountryMapper.selectList(queryWrapper);

		//查询子节点
		if(list != null && list.size() > 0){
			for(OmsSensitiveCountry omsSensitiveCountry : list){
				QueryWrapper<OmsSensitiveCountry> wrapper = new QueryWrapper<OmsSensitiveCountry>();
				wrapper.eq(pubPri != null && pubPri != "","PUB_PRI", pubPri)
						.eq("UPPER_LEVEL", omsSensitiveCountry.getParentId());
				List<OmsSensitiveCountry> childList = omsSensitiveCountryMapper.selectList(wrapper);
				omsSensitiveCountry.setList(childList);
			}
		}
		return list;
	}


	/**
	 * <b>添加限制性内容</b>
	 * @param omsSensitiveCountry
	 * @return
	 */
	@Transactional(rollbackFor=Exception.class)
	public void addSensitiveCountryLimit(OmsSensitiveCountry omsSensitiveCountry) {
		//查询插入的限制内容是否应存在
		QueryWrapper<OmsSensitiveCountry> queryWrapper = new QueryWrapper<OmsSensitiveCountry>();
		queryWrapper.eq(omsSensitiveCountry.getPubPri() != null && omsSensitiveCountry.getPubPri() != "",
				"PUB_PRI", omsSensitiveCountry.getPubPri())
				.eq(omsSensitiveCountry.getName() != null && omsSensitiveCountry.getName() != "",
						"NAME", omsSensitiveCountry.getName())
				.eq(omsSensitiveCountry.getParentId() != null,
						"UPPER_LEVEL", omsSensitiveCountry.getParentId());
		OmsSensitiveCountry queryOmsSenstiveCountry = omsSensitiveCountryMapper.selectOne(queryWrapper);
		if(queryOmsSenstiveCountry == null){
			omsSensitiveCountry.setUpperLevel(omsSensitiveCountry.getParentId());
			omsSensitiveCountry.setParentId(null);
			omsSensitiveCountry.setId(UUIDGenerator.getPrimaryKey());
			int count = omsSensitiveCountryMapper.insert(omsSensitiveCountry);
			if(count < 1){
				throw new CustomMessageException("添加失败");
			}
		}
	}


	/**
	 * <b>移除限制性内容</b>
	 * @param omsSensitiveCountry
	 * @return
	 */
	@Transactional(rollbackFor=Exception.class)
	public void deleteSensitiveLimit(OmsSensitiveCountry omsSensitiveCountry) {
		QueryWrapper<OmsSensitiveCountry> queryWrapper = new QueryWrapper<OmsSensitiveCountry>();
		queryWrapper.eq(omsSensitiveCountry.getPubPri() != null && omsSensitiveCountry.getPubPri() != "",
				"PUB_PRI", omsSensitiveCountry.getPubPri())
				.eq(omsSensitiveCountry.getName() != null && omsSensitiveCountry.getName() != "",
						"NAME",omsSensitiveCountry.getName())
				.eq(omsSensitiveCountry.getParentId() != null,
						"UPPER_LEVEL", omsSensitiveCountry.getParentId());
		int count = omsSensitiveCountryMapper.delete(queryWrapper);
		if(count < 1){
			throw new CustomMessageException("移除失败");
		}
	}





}














