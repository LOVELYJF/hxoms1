package com.hxoms.modules.keySupervision.majorLeader.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hxoms.common.utils.Result;
import com.hxoms.modules.keySupervision.majorLeader.entity.OmsSupMajorLeader;
import com.hxoms.modules.keySupervision.majorLeader.service.OmsSupMajorLeaderService;
import com.hxoms.modules.keySupervision.nakedOfficial.controller.base.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <b>主要领导信息控制层</b>
 * @author luoshuai
 * @date 2020/5/10 18:28
 */
@RestController
@RequestMapping("/majorLeader")
public class OmsSupMajorLeaderController extends BaseController {

	@Autowired
	private OmsSupMajorLeaderService omsSupMajorLeaderService;

	/**
	 * <b>查询主要领导信息</b>
	 * @param omsSupMajorLeader
	 * @param idList
	 * @param page
	 * @return
	 */
	@GetMapping("/getMajorLeader")
	public Result getMajorLeader(Page<OmsSupMajorLeader> page, @RequestParam(value = "idList",required = false) List<String> idList,
	                             OmsSupMajorLeader omsSupMajorLeader){
		//查询主要领导基本信息
		page = omsSupMajorLeaderService.getMajorLeader(page,idList, omsSupMajorLeader);
		return Result.success(page);
	}

	/**
	 * <b>添加主要领导信息</b>
	 * @param omsSupMajorLeader
	 * @return
	 */
	@PostMapping("/addMajorLeader")
	public Result addMajorLeader(OmsSupMajorLeader omsSupMajorLeader){
		omsSupMajorLeaderService.addMajorLeader(omsSupMajorLeader);
		return Result.success();
	}


	/**
	 * <b>根据ID取消主要领导标识</b>
	 * @param omsSupMajorLeader
	 * @return
	 */
	@PostMapping("/removeMajorLeader")
	public Result removeMajorLeader(OmsSupMajorLeader omsSupMajorLeader){
		omsSupMajorLeaderService.removeMajorLeader(omsSupMajorLeader);
		return Result.success();
	}


	/**
	 * <b>自动识别主要领导（每个单位前两名）</b>
	 * @return
	 */
	@GetMapping("/addMajorLeaderAuto")
	public Result addMajorLeaderAuto(){
		omsSupMajorLeaderService.addMajorLeaderAuto();
		return Result.success();
	}



	/**
	 * <b>导出主要领导信息</b>
	 * @param list
	 * @return
	 */
	@GetMapping("/getMajorLeaderInfoOut")
	public void getMajorLeaderInfoOut(@RequestBody(required = false) List<OmsSupMajorLeader> list){
		omsSupMajorLeaderService.getMajorLeaderInfoOut(list,response);
	}


}
