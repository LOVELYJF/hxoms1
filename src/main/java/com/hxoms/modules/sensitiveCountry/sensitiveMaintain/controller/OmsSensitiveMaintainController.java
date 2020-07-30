package com.hxoms.modules.sensitiveCountry.sensitiveMaintain.controller;

import com.hxoms.common.utils.Result;
import com.hxoms.modules.country.entity.Country;
import com.hxoms.modules.sensitiveCountry.sensitiveLimited.entity.OmsSensitiveLimit;
import com.hxoms.modules.sensitiveCountry.sensitiveMaintain.service.OmsSensitiveMaintainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <b>敏感国家维护控制器</b>
 * @author luoshuai
 * @date 2020/06/28
 */
@RestController
@RequestMapping("/sensitiveMaintain")
public class OmsSensitiveMaintainController {


	@Autowired
	private OmsSensitiveMaintainService omsSensitiveMaintainService;
	/**
	 * <b>查询限制性信息</b>
	 * @return
	 */
	@GetMapping("/getOmsSensitiveLimit")
	public Result getOmsSensitiveLimit(){
		List<OmsSensitiveLimit> list = omsSensitiveMaintainService.getOmsSensitiveLimit();
		return Result.success(list);
	}


	/**
	 * <b>查询国家信息</b>
	 * @param nameZh
	 * @return
	 */
	@GetMapping("/getCountryInfo")
	public Result getCountryInfo(String nameZh){
		List<Country> list = omsSensitiveMaintainService.getCountryInfo(nameZh);
		return Result.success(list);
	}


	/**
	 * <b>功能描述: 查询限制性国家信息</b>
	 * @Param: [sensitiveLimitId]
	 * @Return: com.hxoms.common.utils.Result
	 * @Author: luoshuai
	 * @Date: 2020/7/14 20:10
	 */
	@GetMapping("/getSensitiveMaintain")
	public Result getSensitiveMaintain(String sensitiveLimitId){
		List<Integer> list = omsSensitiveMaintainService.getSensitiveMaintain(sensitiveLimitId);
		return Result.success(list);
	}



	/**
	 * <b>保存敏感国家信息</b>
	 * @param countryIdList
	 * @param sensitiveLimitId
	 * @return
	 */
	@PostMapping("/addCountryInfo")
	public Result addCountryInfo(@RequestParam(value = "countryIdList",required = false) List<String> countryIdList,
	                             @RequestParam(value = "sensitiveLimitId",required = true) String sensitiveLimitId){
		omsSensitiveMaintainService.addCountryInfo(countryIdList,sensitiveLimitId);
		return Result.success();
	}


}












