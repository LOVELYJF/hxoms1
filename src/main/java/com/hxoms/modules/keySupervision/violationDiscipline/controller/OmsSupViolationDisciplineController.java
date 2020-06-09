package com.hxoms.modules.keySupervision.violationDiscipline.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hxoms.common.utils.Result;
import com.hxoms.modules.keySupervision.nakedOfficial.controller.base.BaseController;
import com.hxoms.modules.keySupervision.violationDiscipline.entity.OmsSupViolationDiscipline;
import com.hxoms.modules.keySupervision.violationDiscipline.service.OmsSupViolationDisciplineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <b>违反外事纪律模块控制器</b>
 * @author luoshuai
 * @date 2020/5/18 14:52
 */
@RestController
@RequestMapping("/violationDiscipline")
public class OmsSupViolationDisciplineController extends BaseController {

	@Autowired
	private OmsSupViolationDisciplineService omsSupViolationDisciplineService;


	/**
	 * <b>查询违反外事纪律人员信息</b>
	 * @param idList
	 * @param omsSupViolationDiscipline
	 * @param page
	 * @return
	 */
	@GetMapping("/getViolationDiscipline")
	public Result getViolationDiscipline(Page<OmsSupViolationDiscipline> page, @RequestParam(value = "idList",required = false) List<String> idList,
	                                     OmsSupViolationDiscipline omsSupViolationDiscipline){
		//查询违反外事纪律人员信息
		page = omsSupViolationDisciplineService.getViolationDisciplineInfo(page,omsSupViolationDiscipline,idList);
		return Result.success(page);
	}


	/**
	 * <b>新增违反外事纪律人员信息</b>
	 * @param omsSupViolationDiscipline
	 * @return
	 */
	@PostMapping("/addViolationDiscipline")
	private Result addViolationDiscipline(OmsSupViolationDiscipline omsSupViolationDiscipline){
		omsSupViolationDisciplineService.addViolationDisciplineInfo(omsSupViolationDiscipline);
		return Result.success();
	}


	/**
	 * <b>修改违反外事人员信息</b>
	 * @param omsSupViolationDiscipline
	 * @return
	 */
	@PostMapping("/updateViolationDiscipline")
	private Result updateViolationDiscipline(OmsSupViolationDiscipline omsSupViolationDiscipline){
		omsSupViolationDisciplineService.updateViolationDisciplineInfo(omsSupViolationDiscipline);
		return Result.success();
	}


	/**
	 * <b>删除违反外事人员信息</b>
	 * @param omsSupViolationDiscipline
	 * @return
	 */
	@PostMapping("/removeViolationDiscipline")
	private Result removeViolationDiscipline(OmsSupViolationDiscipline omsSupViolationDiscipline){
		omsSupViolationDisciplineService.removeViolationDiscipline(omsSupViolationDiscipline);
		return Result.success();
	}


	/**
	 * <b>导出违反外事纪律人员信息</b>
	 * @param list
	 * @return
	 */
	@PostMapping("/getViolationDisciplineInfoOut")
	public void getViolationDisciplineInfoOut(@RequestBody(required = false) List<OmsSupViolationDiscipline> list){
		omsSupViolationDisciplineService.getViolationDisciplineInfoOut(list,response);
	}
}
