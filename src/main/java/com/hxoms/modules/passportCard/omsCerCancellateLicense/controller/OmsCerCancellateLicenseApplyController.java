package com.hxoms.modules.passportCard.omsCerCancellateLicense.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hxoms.common.utils.Result;
import com.hxoms.modules.passportCard.initialise.entity.CfCertificate;
import com.hxoms.modules.passportCard.omsCerCancellateLicense.entity.OmsCerCancellateLicense;
import com.hxoms.modules.passportCard.omsCerCancellateLicense.service.OmsCerCancellateLicenseApplyService;
import com.hxoms.support.sysdict.entity.SysDictItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <b>功能描述: 注销证照申请控制层</b>
 * @Param:
 * @Return:
 * @Author: luoshuai
 * @Date: 2020/8/5 10:24
 */
@RestController
@RequestMapping("/cancellateLicense")
public class OmsCerCancellateLicenseApplyController {

	@Autowired
	private OmsCerCancellateLicenseApplyService omsCerCancellateLicenseApplyService;
	/**
	 * <b>功能描述: 填写注销申请（查询）</b>
	 * @Param: [cfCertificate]
	 * @Return: com.hxoms.common.utils.Result
	 * @Author: luoshuai
	 * @Date: 2020/8/5 10:29
	 */
	@GetMapping("/getCancellateLicense")
	public Result getCancellateLicense(CfCertificate cfCertificate){
		List<Map<String,Object>> list =  omsCerCancellateLicenseApplyService.getCancellateLicense(cfCertificate);
		return Result.success(list);
	}


	/**
	 * <b>功能描述: 填写注销申请进行下一步</b>
	 * @Param: [list]
	 * @Return: com.hxoms.common.utils.Result
	 * @Author: luoshuai
	 * @Date: 2020/8/5 11:50
	 */
	@PostMapping("/saveCancellateLicenseChoose")
	public Result saveCancellateLicenseChoose(@RequestBody List<OmsCerCancellateLicense> list){
		Map<String,Object> map = omsCerCancellateLicenseApplyService.saveCancellateLicenseChoose(list);
		return Result.success(map);
	}

	/**
	 * <b>功能描述: 查询注销证照申请列表</b>
	 * @Param: [omsCerCancellateLicense,page]
	 * @Return: com.hxoms.common.utils.Result
	 * @Author: luoshuai
	 * @Date: 2020/8/5 13:37
	 */
	@GetMapping("/getCancellateLicenseApply")
	public Result getCancellateLicenseApply(Page<OmsCerCancellateLicense> page, OmsCerCancellateLicense omsCerCancellateLicense){
		page =  omsCerCancellateLicenseApplyService.getCancellateLicenseApply(page,omsCerCancellateLicense);
		return Result.success(page);
	}


	/**
	 * <b>功能描述: 删除注销证照申请</b>
	 * @Param: [id]
	 * @Return: com.hxoms.common.utils.Result
	 * @Author: luoshuai
	 * @Date: 2020/8/5 15:03
	 */
	@PostMapping("/deleteCancellateLicenseApply")
	public Result deleteCancellateLicenseApply(String id){
		omsCerCancellateLicenseApplyService.deleteCancellateLicenseApply(id);
		return Result.success();
	}


	/**
	 * <b>功能描述: 撤销注销证照申请</b>
	 * @Param: [list]
	 * @Return: com.hxoms.common.utils.Result
	 * @Author: luoshuai
	 * @Date: 2020/8/5 15:09
	 */
	@PostMapping("/removeCancellateLicenseApply")
	public Result removeCancellateLicenseApply(@RequestBody List<OmsCerCancellateLicense> list){
		omsCerCancellateLicenseApplyService.removeCancellateLicenseApply(list);
		return Result.success();
	}


	/**
	 * <b>功能描述: 更改申请状态</b>
	 * @Param: [list]
	 * @Return: com.hxoms.common.utils.Result
	 * @Author: luoshuai
	 * @Date: 2020/8/5 15:09
	 */
	@PostMapping("/updateCancellateLicenseApplyStatus")
	public Result updateCancellateLicenseApplyStatus(@RequestBody List<OmsCerCancellateLicense> list){
		omsCerCancellateLicenseApplyService.updateCancellateLicenseApplyStatus(list);
		return Result.success();
	}


	/**
	 * <b>功能描述: 查询证照注销申请状态</b>
	 * @Param: []
	 * @Return: com.hxoms.common.utils.Result
	 * @Author: luoshuai
	 * @Date: 2020/8/5 15:09
	 */
	@GetMapping("/getCancellateLicenseApplyStatus")
	public Result getCancellateLicenseApplyStatus(){
		List<SysDictItem> list = omsCerCancellateLicenseApplyService.getCancellateLicenseApplyStatus();
		return Result.success(list);
	}


	/**
	 * <b>功能描述: 查询证照注销原因</b>
	 * @Param: []
	 * @Return: com.hxoms.common.utils.Result
	 * @Author: luoshuai
	 * @Date: 2020/8/5 15:09
	 */
	@GetMapping("/getCancellateLicenseApplyReason")
	public Result getCancellateLicenseApplyReason(){
		List<SysDictItem> list = omsCerCancellateLicenseApplyService.getCancellateLicenseApplyReason();
		return Result.success(list);
	}


	/**
	 * <b>功能描述: 查询证照注销方式</b>
	 * @Param: []
	 * @Return: com.hxoms.common.utils.Result
	 * @Author: luoshuai
	 * @Date: 2020/8/5 15:09
	 */
	@GetMapping("/getCancellateLicenseApplyWay")
	public Result getCancellateLicenseApplyWay(){
		List<SysDictItem> list = omsCerCancellateLicenseApplyService.getCancellateLicenseApplyWay();
		return Result.success(list);
	}


}



















