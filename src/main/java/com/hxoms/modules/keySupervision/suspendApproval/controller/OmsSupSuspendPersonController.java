package com.hxoms.modules.keySupervision.suspendApproval.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hxoms.common.utils.Result;
import com.hxoms.modules.keySupervision.suspendApproval.entity.OmsSupSuspendPerson;
import com.hxoms.modules.keySupervision.suspendApproval.service.OmsSupSuspendPersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <b>暂停出入境管理审批模块控制器</b>
 * @author luoshuai
 * @date 2020/5/20 17:04
 */
@RestController
@RequestMapping("/suspendPerson")
public class OmsSupSuspendPersonController {


	@Autowired
	private OmsSupSuspendPersonService omsSupSuspendPersonService;
	/**
	 * <b>查询暂停审批人员信息</b>
	 * @param page
	 * @param idList
	 * @param omsSupSuspendPerson
	 * @return
	 */
	@GetMapping("/getSuspendPersonInfo")
	public Result getSuspendPersonInfo(Page<OmsSupSuspendPerson> page, @RequestParam(value = "idList") List<String> idList,
	                                   OmsSupSuspendPerson omsSupSuspendPerson){
		page = omsSupSuspendPersonService.getSuspendPersonInfo(page,idList,omsSupSuspendPerson);
		return Result.success(page);
	}


	/**
	 * <b>修改暂停审批人员信息(允许审批)</b>
	 * @param list
	 * @return
	 */
	@PostMapping("/updateSuspendPersonInfo")
	public Result updateSuspendPersonInfo(@RequestBody List<OmsSupSuspendPerson> list){
		omsSupSuspendPersonService.updateSuspendPersonInfo(list);
		return Result.success();
	}
}
