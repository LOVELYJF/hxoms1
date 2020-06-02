package com.hxoms.modules.sensitiveCountry.sensitiveMaintenance.controller;

import com.hxoms.common.utils.Result;
import com.hxoms.modules.sensitiveCountry.sensitiveLimited.entity.OmsSensitiveCountry;
import com.hxoms.modules.sensitiveCountry.sensitiveMaintenance.service.OmsSensitiveMaintenanceService;
import com.hxoms.support.sysdict.entity.SysDictItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <b>敏感性国家维护控制器</b>
 * @author luoshuai
 * @date 2020/5/25 17:49
 */
@RestController
@RequestMapping("/sensitiveMaintenance")
public class OmsSensitiveMaintenanceController {


	@Autowired
	private OmsSensitiveMaintenanceService omsSensitiveMaintenanceService;

	/**
	 * <b>查询所有的国家</b>
	 * @return
	 */
	@GetMapping("/getAreaList")
	public Result getAreaList(){
		List<SysDictItem> list = omsSensitiveMaintenanceService.getAreaList();
		return Result.success(list);
	}

	/**
	 * <b>查询各个限制地区</b>
	 * @return
	 */
	@GetMapping("/getSensitiveCountryMaintainInfo")
	public Result getSensitiveCountryMaintainInfo(){
		List<OmsSensitiveCountry> list = omsSensitiveMaintenanceService.getSensitiveCountryMaintainInfo();
		return Result.success(list);
	}


	/**
	 * <b>添加敏感地区</b>
	 * @return
	 */
	@PostMapping("/addSensitiveCountryMaintainInfo")
	public Result addSensitiveCountryMaintainInfo(OmsSensitiveCountry omsSensitiveCountry){
		omsSensitiveMaintenanceService.addSensitiveCountryMaintainInfo(omsSensitiveCountry);
		return Result.success();
	}


	/**
	 * <b>移除敏感地区</b>
	 * @return
	 */
	@PostMapping("/removeSensitiveCountryMaintainInfo")
	public Result removeSensitiveCountryMaintainInfo(OmsSensitiveCountry omsSensitiveCountry){
		omsSensitiveMaintenanceService.removeSensitiveCountryMaintainInfo(omsSensitiveCountry);
		return Result.success();
	}
}
