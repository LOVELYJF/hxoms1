package com.hxoms.modules.keySupervision.caseInfo.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hxoms.common.utils.Result;
import com.hxoms.modules.keySupervision.caseInfo.entity.OmsSupCaseInfo;
import com.hxoms.modules.keySupervision.caseInfo.mapper.OmsSupCaseInfoMapper;
import com.hxoms.modules.keySupervision.caseInfo.service.OmsSupCaseInfoService;
import com.hxoms.modules.keySupervision.nakedOfficial.controller.base.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <b>立案信息控制层</b>
 * @author luoshuai
 * @date 2020/5/14 16:45
 */
@RestController
@RequestMapping("/caseInfo")
public class OmsSupCaseInfoController extends BaseController {


	@Autowired
	private OmsSupCaseInfoService omsSupCaseInfoService;

	/**
	 * <b>根据用户输入查询立案信息</b>
	 * @param idList
	 * @param omsSupCaseInfo
	 * @param page
	 * @return
	 */
	@GetMapping("/getCasePersonInfo")
	public Result getCasePersonInfo(Page<OmsSupCaseInfo> page, @RequestParam(value = "idList",required = false) List<String> idList,
	                                OmsSupCaseInfo omsSupCaseInfo){
		page = omsSupCaseInfoService.getCasePersonInfo(page,omsSupCaseInfo,idList);
		return Result.success(page);
	}



	/**
	 * <b>增加立案信息</b>
	 * @param omsSupCaseInfo
	 * @return
	 */
	@PostMapping("/addCaseInfo")
	public Result addCaseInfo(OmsSupCaseInfo omsSupCaseInfo){
		omsSupCaseInfoService.addCaseInfo(omsSupCaseInfo);
		return Result.success();
	}


	/**
	 * <b>保存并转到处分信息</b>
	 * @param omsSupCaseInfo
	 * @return
	 */
	@PostMapping("/addToDisciplinary")
	public Result addToDisciplinary(OmsSupCaseInfo omsSupCaseInfo){
		omsSupCaseInfoService.addToDisciplinary(omsSupCaseInfo);
		return Result.success();
	}


	/**
	 * <b保存修改的立案信息</b>
	 * @param omsSupCaseInfo
	 * @return
	 */
	@PostMapping("/updateSaveCaseInfo")
	public Result updateSaveCaseInfo(OmsSupCaseInfo omsSupCaseInfo){
		omsSupCaseInfoService.updateSaveCaseInfo(omsSupCaseInfo);
		return Result.success();
	}

	/**
	 * <b保存修改的立案信息并转到处分信息</b>
	 * @param omsSupCaseInfo
	 * @return
	 */
	@PostMapping("/updateCaseInfoToDisciplinary")
	public Result updateCaseInfoToDisciplinary(OmsSupCaseInfo omsSupCaseInfo){
		omsSupCaseInfoService.updateCaseInfoToDisciplinary(omsSupCaseInfo);
		return Result.success();
	}


	/**
	 * <b>删除立案信息</b>
	 * @param omsSupCaseInfo
	 * @return
	 */
	@PostMapping("/removeCaseInfo")
	public Result removeCaseInfo(OmsSupCaseInfo omsSupCaseInfo){
		omsSupCaseInfoService.removeCaseInfo(omsSupCaseInfo);
		return Result.success();
	}



	/**
	 * <b>导出立案信息</b>
	 * @param list
	 * @return
	 */
	@PostMapping("/getCaseInfoOut")
	public void getCaseInfoOut(@RequestBody(required = false) List<OmsSupCaseInfo> list){
		omsSupCaseInfoService.getCaseInfoOut(list,response);
	}
}
