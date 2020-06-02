package com.hxoms.modules.keySupervision.patrolUnit.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hxoms.common.utils.Result;
import com.hxoms.modules.keySupervision.patrolUnit.entity.OmsSupPatrolUnit;
import com.hxoms.modules.keySupervision.patrolUnit.service.OmsSupPatrolUnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * <b>被巡视单位信息控制层</b>
 * @author luoshuai
 * @date 2020/5/19 9:15
 */
@RestController
@RequestMapping("/patrolUnit")
public class OmsSupPatrolUnitController {


	@Autowired
	private OmsSupPatrolUnitService omsSupPatrolUnitService;
	/**
	 * <b>查询被巡视单位信息</b>
	 * @param omsSupPatrolUnit
	 * @param page
	 * @return
	 */
	@GetMapping("/getPatrolUnitInfo")
	public Result getPatrolUnitInfo(Page<OmsSupPatrolUnit> page, OmsSupPatrolUnit omsSupPatrolUnit){
		page = omsSupPatrolUnitService.getPatrolUnitInfo(page,omsSupPatrolUnit);
		return Result.success(page);
	}


	/**
	 * <b>增加被巡视单位</b>
	 * @param omsSupPatrolUnit
	 * @return
	 */
	@PostMapping("/addPatrolUnitInfo")
	public Result addPartrolUnitInfo(OmsSupPatrolUnit omsSupPatrolUnit){
		omsSupPatrolUnitService.addPatrolUnitInfo(omsSupPatrolUnit);
		return Result.success();
	}


	/**
	 * <b>修改被巡视单位信息</b>
	 * @param omsSupPatrolUnit
	 * @return
	 */
	@PostMapping("/updatePatrolUnitInfo")
	public Result updatePatrolUnitInfo(OmsSupPatrolUnit omsSupPatrolUnit){
		omsSupPatrolUnitService.updatePatrolUnitInfo(omsSupPatrolUnit);
		return Result.success();
	}


	/**
	 * <b>删除被巡视单位</b>
	 * @param omsSupPatrolUnit
	 * @return
	 */
	@PostMapping("/deletePatrolUnitInfo")
	public Result deletePatrolUnitInfo(OmsSupPatrolUnit omsSupPatrolUnit){
		omsSupPatrolUnitService.deletePatrolUnitInfo(omsSupPatrolUnit);
		return Result.success();
	}
}
