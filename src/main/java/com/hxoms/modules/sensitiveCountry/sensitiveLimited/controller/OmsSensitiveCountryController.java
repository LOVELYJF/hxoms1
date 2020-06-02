package com.hxoms.modules.sensitiveCountry.sensitiveLimited.controller;


import com.hxoms.common.utils.Result;
import com.hxoms.modules.sensitiveCountry.sensitiveLimited.entity.OmsSensitiveCountry;
import com.hxoms.modules.sensitiveCountry.sensitiveLimited.service.OmsSensitiveCountryService;
import com.hxoms.support.sysdict.entity.SysDictItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <b>敏感性国家模块控制器</b>
 * @author luoshuai
 * @date 2020/5/22  10:45
 */
@RestController
@RequestMapping("/sensitiveCountry")
public class OmsSensitiveCountryController {


	@Autowired
	private OmsSensitiveCountryService omsSensitiveCountryService;
	/**
	 * <b>查询因公因私限制内容</b>
	 * @param pubPri
	 * @return
	 */
	@GetMapping("/getSensitiveLimitInfo")
	public Result getSensitiveInfo(String pubPri){
		List<SysDictItem> list = omsSensitiveCountryService.getSensitiveInfo(pubPri);
		return Result.success(list);
	}


	/**
	 * <b>查询各个限制地区的限制内容</b>
	 * @param pubPri
	 * @return
	 */
	@GetMapping("/getSensitiveCountryLimitInfo")
	public Result getSensitiveCountryLimitInfo(String pubPri){
		List<OmsSensitiveCountry> list = omsSensitiveCountryService.getSensitiveCountryLimitInfo(pubPri);
		return Result.success(list);
	}


	/**
	 * <b>添加限制性内容</b>
	 * @param omsSensitiveCountry
	 * @return
	 */
	@PostMapping("/addSensitiveLimit")
	public Result addSensitiveCountryLimit(OmsSensitiveCountry omsSensitiveCountry){
		omsSensitiveCountryService.addSensitiveCountryLimit(omsSensitiveCountry);
		return Result.success();
	}


	/**
	 * <b>移除限制性内容</b>
	 * @param omsSensitiveCountry
	 * @return
	 */
	@PostMapping("/deleteSensitiveLimit")
	public Result deleteSensitiveLimit(OmsSensitiveCountry omsSensitiveCountry){
		omsSensitiveCountryService.deleteSensitiveLimit(omsSensitiveCountry);
		return Result.success();
	}


}
