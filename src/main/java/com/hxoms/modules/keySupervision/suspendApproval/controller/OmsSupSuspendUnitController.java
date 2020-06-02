package com.hxoms.modules.keySupervision.suspendApproval.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hxoms.common.utils.Result;
import com.hxoms.modules.keySupervision.suspendApproval.entity.OmsSupSuspendUnit;
import com.hxoms.modules.keySupervision.suspendApproval.service.OmsSupSuspendUnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <b>暂停出入境管理审批模块控制器</b>
 * @author luoshuai
 * @date 2020/5/19 14:17
 */
@RestController
@RequestMapping("/suspendUnit")
public class OmsSupSuspendUnitController {



	@Autowired
	private OmsSupSuspendUnitService omsSupSuspendUnitService;
	/**
	 * <b>查询暂停审批单位信息</b>
	 * @param omsSupSuspendUnit
	 * @param page
	 * @return
	 */
	@GetMapping("/getSuspendUnitInfo")
	public Result getSuspendUnitInfo(Page<OmsSupSuspendUnit> page, OmsSupSuspendUnit omsSupSuspendUnit){
		page = omsSupSuspendUnitService.getSuspendUnitInfo(page,omsSupSuspendUnit);
		return Result.success(page);
	}


	/**
	 * <b>修改暂停审批单位信息(允许审批)</b>
	 * @param list
	 * @return
	 */
	@PostMapping("/updateSuspendUnitInfo")
	public Result updateSuspendUnitInfo(@RequestBody List<OmsSupSuspendUnit> list){
		omsSupSuspendUnitService.updateSuspendUnitInfo(list);
		return Result.success();
	}

}














