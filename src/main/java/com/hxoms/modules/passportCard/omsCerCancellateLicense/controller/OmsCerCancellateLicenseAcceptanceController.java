package com.hxoms.modules.passportCard.omsCerCancellateLicense.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hxoms.common.utils.Result;
import com.hxoms.modules.keySupervision.nakedOfficial.controller.base.BaseController;
import com.hxoms.modules.passportCard.omsCerCancellateLicense.entity.OmsCerCancellateLicense;
import com.hxoms.modules.passportCard.omsCerCancellateLicense.service.OmsCerCancellateLicenseAcceptanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * <b>功能描述: 注销证照受理控制层</b>
 * @Param:
 * @Return:
 * @Author: luoshuai
 * @Date: 2020/8/5 16:24
 */
@RestController
@RequestMapping("/cancellateAcceptance")
public class OmsCerCancellateLicenseAcceptanceController extends BaseController {

	@Autowired
	private OmsCerCancellateLicenseAcceptanceService omsCerCancellateLicenseAcceptanceService;
	/**
	 * <b>功能描述: 注销证照受理查询(按单位主键，姓名，证件号码，证件类型,申请时间，申请状态查询)
	 * 和处领导审批处的查询为同一接口</b>
	 * @Param: [page,idList,omsCerCancellateLicense]
	 * @Return: java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
	 * @Author: luoshuai
	 * @Date: 2020/8/5 16:52
	 */
	@GetMapping("/getCerCancellateLicenseAcceptance")
	public Result getCerCancellateLicenseAcceptance(Page<Map<String,Object>> page, @RequestParam(value = "idList",required = false) List<String> idList,
	                                                OmsCerCancellateLicense omsCerCancellateLicense){
		page = omsCerCancellateLicenseAcceptanceService.getCerCancellateLicenseAcceptance(page, idList, omsCerCancellateLicense);
		return Result.success(page);
	}

	/**
	 * <b>功能描述: 强制注销（监督处强制注销某个人的证照信息）进行下一步</b>
	 * @Param: [list]
	 * @Return: org.apache.ibatis.annotations.Result
	 * @Author: luoshuai
	 * @Date: 2020/8/7 16:43
	 */
	@PostMapping("/getCerCancellateLicenseForce")
	public Result getCerCancellateLicenseForce(@RequestBody List<OmsCerCancellateLicense> list){
		omsCerCancellateLicenseAcceptanceService.getCerCancellateLicenseForce(list);
		return Result.success();
	}

	/**
	 * <b>功能描述: 注销证照受理-修改</b>
	 * @Param: [omsCerCancellateLicense]
	 * @Return: org.apache.ibatis.annotations.Result
	 * @Author: luoshuai
	 * @Date: 2020/8/7 16:43
	 */
	@PostMapping("/updateCerCancellateLicenseAcceptance")
	public Result updateCerCancellateLicenseAcceptance(OmsCerCancellateLicense omsCerCancellateLicense){
		omsCerCancellateLicenseAcceptanceService.updateCerCancellateLicenseAcceptance(omsCerCancellateLicense);
		return Result.success();
	}


	/**
	 * <b>功能描述: 注销证照受理申请--下一步
	 *      在此处进行判断是自行注销且材料通过的不用处领导审批，直接将证照状态改为注销，申请状态改为已办结
	 * </b>
	 * @Param: [omsCerCancellateLicense]
	 * @Return: org.apache.ibatis.annotations.Result
	 * @Author: luoshuai
	 * @Date: 2020/8/7 16:43
	 */
	@PostMapping("/updateCerCancellateLicenseAcceptanceNext")
	public Result updateCerCancellateLicenseAcceptanceNext(OmsCerCancellateLicense omsCerCancellateLicense){
		omsCerCancellateLicenseAcceptanceService.updateCerCancellateLicenseAcceptanceNext(omsCerCancellateLicense);
		return Result.success();
	}


	/**
	 * <b>功能描述: 处领导审批(可以批量审批)</b>
	 * @Param: [list,omsCerCancellateLicense]
	 * @Return: org.apache.ibatis.annotations.Result
	 * @Author: luoshuai
	 * @Date: 2020/8/10 16:43
	 */
	@PostMapping("/updateCerCancellateLicenseApproval")
	public Result updateCerCancellateLicenseApproval(@RequestParam(value = "list",required = false) List<String> list,
	                                                 OmsCerCancellateLicense omsCerCancellateLicense){
		omsCerCancellateLicenseAcceptanceService.updateCerCancellateLicenseApproval(list,omsCerCancellateLicense);
		return Result.success();
	}

	/**
	 * <b>功能描述: 部领导审批</b>
	 * @Param: [omsCerCancellateLicense]
	 * @Return: org.apache.ibatis.annotations.Result
	 * @Author: luoshuai
	 * @Date: 2020/8/10 16:48
	 */
	@PostMapping("/updateCerCancellateLicenseApprovalMinister")
	public Result updateCerCancellateLicenseApprovalMinister(OmsCerCancellateLicense omsCerCancellateLicense){
		omsCerCancellateLicenseAcceptanceService.updateCerCancellateLicenseApprovalMinister(omsCerCancellateLicense);
		return Result.success();
	}

	/**
	 * <b>功能描述: 完成注销(公安厅意见)
	 *     分两个按钮，第一个通过后生成证照领取任务，
	 *     第二个领取任务完成后再次完成注销，状态改为已办结
	 * </b>
	 * @Param: [omsCerCancellateLicense]
	 * @Return: org.apache.ibatis.annotations.Result
	 * @Author: luoshuai
	 * @Date: 2020/8/10 11:48
	 */
	@PostMapping("/updateCerCancellateLicenseApprovalComplete")
	public Result updateCerCancellateLicenseApprovalComplete(OmsCerCancellateLicense omsCerCancellateLicense){
		omsCerCancellateLicenseAcceptanceService.updateCerCancellateLicenseApprovalComplete(omsCerCancellateLicense);
		return Result.success();
	}


	/**
	 * <b>功能描述: 受理审批导出</b>
	 * @Param: [idList,omsCerCancellateLicense]
	 * @Return: org.apache.ibatis.annotations.Result
	 * @Author: luoshuai
	 * @Date: 2020/8/10 11:48
	 */
	@GetMapping("/getCerCancellateLicenseApprovalOut")
	public Result getCerCancellateLicenseApprovalOut(@RequestParam(value = "idList",required = false) List<String> idList,
	                                                 OmsCerCancellateLicense omsCerCancellateLicense){
		omsCerCancellateLicenseAcceptanceService.getCerCancellateLicenseApprovalOut(idList, omsCerCancellateLicense,response);
		return Result.success();
	}



	/**
	 * <b>功能描述: 查询审批记录</b>
	 * @Param: [omsCerCancellateLicense]
	 * @Return: org.apache.ibatis.annotations.Result
	 * @Author: luoshuai
	 * @Date: 2020/8/7 16:43
	 */
	@GetMapping("/getCerCancellateLicenseRecord")
	public Result getCerCancellateLicenseRecord(OmsCerCancellateLicense omsCerCancellateLicense){
		omsCerCancellateLicense = omsCerCancellateLicenseAcceptanceService.getCerCancellateLicenseRecord(omsCerCancellateLicense);
		return Result.success(omsCerCancellateLicense);
	}
}












