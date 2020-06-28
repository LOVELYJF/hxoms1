package com.hxoms.modules.sensitiveCountry.sensitiveMaintain.controller;

import com.hxoms.common.utils.Result;
import com.hxoms.modules.country.entity.Country;
import com.hxoms.modules.sensitiveCountry.sensitiveMaintain.service.OmsSensitiveMaintainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
	 * <b>查询国家信息</b>
	 * @param nameZh
	 * @param sensitiveLimitId
	 * @return
	 */
	@GetMapping("/getCountryInfo")
	public Result getCountryInfo(String nameZh,String sensitiveLimitId){
		List<Country> list = omsSensitiveMaintainService.getCountryInfo(nameZh,sensitiveLimitId);
		return Result.success(list);
	}


	/**
	 * <b>增加敏感国家信息</b>
	 * @param countryId
	 * @param sensitiveLimitId
	 * @return
	 */
	@PostMapping("/addCountryInfo")
	public Result addCountryInfo(Integer countryId,String sensitiveLimitId){
		omsSensitiveMaintainService.addCountryInfo(countryId,sensitiveLimitId);
		return Result.success();
	}

	/**
	 * <b>删除敏感国家信息</b>
	 * @param countryId
	 * @param sensitiveLimitId
	 * @return
	 */
	@PostMapping("/deleteCountryInfo")
	public Result deleteCountryInfo(Integer countryId,String sensitiveLimitId){
		omsSensitiveMaintainService.deleteCountryInfo(countryId,sensitiveLimitId);
		return Result.success();
	}

}












