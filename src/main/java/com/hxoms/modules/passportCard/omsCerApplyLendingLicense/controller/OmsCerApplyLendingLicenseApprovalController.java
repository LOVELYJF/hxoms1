package com.hxoms.modules.passportCard.omsCerApplyLendingLicense.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hxoms.common.utils.Result;
import com.hxoms.modules.passportCard.omsCerApplyLendingLicense.entity.OmsCerApplyLendingLicense;
import com.hxoms.modules.passportCard.omsCerApplyLendingLicense.service.OmsCerApplyLendingLicenseApprovalService;
import com.hxoms.modules.passportCard.omsCerApplyLendingLicense.service.OmsCerApplyLendingLicenseService;
import com.hxoms.modules.passportCard.omsCerTransferOutLicense.entity.OmsCerTransferOutLicense;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * <b>功能描述: 借出证照申请审批控制层</b>
 * @Param:
 * @Return:
 * @Author: luoshuai
 * @Date: 2020/8/11 16:09
 */
@RestController
@RequestMapping("/ApplyLendingLicenseApproval")
public class OmsCerApplyLendingLicenseApprovalController {

	@Autowired
	private OmsCerApplyLendingLicenseService omsCerApplyLendingLicenseService;
	@Autowired
	private OmsCerApplyLendingLicenseApprovalService omsCerApplyLendingLicenseApprovalService;
	/**
	 * <b>功能描述: 查询年份对应的批次号结构树</b>
	 * @Param: []
	 * @Return: com.hxoms.common.utils.Result
	 * @Author: luoshuai
	 * @Date: 2020/8/21 15:07
	 */
	@GetMapping("/getBatchByYear")
	public Result getBatchByYear(){
		List<OmsCerApplyLendingLicense> list = omsCerApplyLendingLicenseApprovalService.getBatchByYear();
		return Result.success(list);
	}



	/**
	 * <b>功能描述: 查询借出证照申请记录</b>
	 * @Param: [page,documentNum]
	 * @Return: com.hxoms.common.utils.Result
	 * @Author: luoshuai
	 * @Date: 2020/8/11 16:29
	 */
	@GetMapping("/getApplyLendingLicenseApprovalRecord")
	public Result getApplyLendingLicenseApprovalRecord(Page<Map<String,Object>> page,String documentNum){
		page = omsCerApplyLendingLicenseApprovalService.getApplyLendingLicenseApprovalRecord(page,documentNum);
		return Result.success(page);
	}


	/**
	 * <b>功能描述: 录入审批结果</b>
	 * @Param: [list,omsCerApplyLendingLicense]
	 * @Return: com.hxoms.common.utils.Result
	 * @Author: luoshuai
	 * @Date: 2020/8/11 16:29
	 */
	@PostMapping("/updateApplyLendingLicenseApprovalResult")
	public Result updateApplyLendingLicenseApprovalResult(@RequestParam(value = "list",required = true) List<String> list,
	                                                   OmsCerApplyLendingLicense omsCerApplyLendingLicense){
		omsCerApplyLendingLicenseApprovalService.updateApplyLendingLicenseApprovalResult(list,omsCerApplyLendingLicense);
		return Result.success();
	}
}
