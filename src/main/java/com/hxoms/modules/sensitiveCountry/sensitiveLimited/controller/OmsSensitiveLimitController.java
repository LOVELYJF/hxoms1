package com.hxoms.modules.sensitiveCountry.sensitiveLimited.controller;


import com.hxoms.common.utils.Result;
import com.hxoms.modules.sensitiveCountry.sensitiveLimited.entity.OmsSensitiveLimit;
import com.hxoms.modules.sensitiveCountry.sensitiveLimited.service.OmsSensitiveLimitService;
import com.hxoms.support.sysdict.entity.SysDictItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <b>敏感性国家模块控制器</b>
 * @author luoshuai
 * @date 2020/5/22  10:45
 */
@RestController
@RequestMapping("/sensitiveCountry")
public class OmsSensitiveLimitController {


	@Autowired
	private OmsSensitiveLimitService omsSensitiveLimitService;

	/**
	 * <b>查询因公因私限制内容</b>
	 * @return
	 */
	@GetMapping("/getSensitiveLimitInfo")
	public Result getSensitiveInfo(){
		List<SysDictItem> list = omsSensitiveLimitService.getSensitiveInfo();
		return Result.success(list);
	}


	/**
	 * <b>查询各个限制地区的限制内容</b>
	 * @param pubPri
	 * @return
	 */
	@GetMapping("/getSensitiveCountryLimitInfo")
	public Result getSensitiveCountryLimitInfo(String pubPri){
		List<OmsSensitiveLimit> list = omsSensitiveLimitService.getSensitiveCountryLimitInfo(pubPri);
		return Result.success(list);
	}


	/**
	 * <b>添加限制性内容</b>
	 * @param sensitiveItem
	 * @param sensitiveLimitId
	 * @param pubPri
	 * @return
	 */
	@PostMapping("/addSensitiveLimit")
	public Result addSensitiveCountryLimit(@RequestParam(value = "sensitiveItem",required = true) String sensitiveItem,
	                                       @RequestParam(value = "sensitiveLimitId",required = true) String sensitiveLimitId,
	                                       @RequestParam(value = "pubPri",required = true) String pubPri){
		omsSensitiveLimitService.addSensitiveLimit(sensitiveItem, sensitiveLimitId, pubPri);
		return Result.success();
	}


	/**
	 * <b>移除限制性内容</b>
	 * @param sensitiveItem
	 * @param sensitiveLimitId
	 * @param pubPri
	 * @return
	 */
	@PostMapping("/deleteSensitiveLimit")
	public Result deleteSensitiveLimit(@RequestParam(value = "sensitiveItem",required = true) String sensitiveItem,
	                                       @RequestParam(value = "sensitiveLimitId",required = true) String sensitiveLimitId,
	                                       @RequestParam(value = "pubPri",required = true) String pubPri){
		omsSensitiveLimitService.deleteSensitiveLimit(sensitiveItem, sensitiveLimitId, pubPri);
		return Result.success();
	}

}
