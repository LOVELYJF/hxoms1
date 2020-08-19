package com.hxoms.modules.passportCard.omsCerApplyLendingLicense.controller;

import com.hxoms.common.utils.Result;
import com.hxoms.modules.keySupervision.nakedOfficial.controller.base.BaseController;
import com.hxoms.modules.passportCard.omsCerApplyLendingLicense.entity.OmsCerApplyLendingLicense;
import com.hxoms.modules.passportCard.omsCerApplyLendingLicense.service.OmsCerApplyLendingLicenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * <b>功能描述: 借出证照申请控制层</b>
 * @Param:
 * @Return:
 * @Author: luoshuai
 * @Date: 2020/8/10 17:53
 */
@RestController
@RequestMapping("/ApplyLendingLicense")
public class OmsCerApplyLendingLicenseController extends BaseController {

	@Autowired
	private OmsCerApplyLendingLicenseService omsCerApplyLendingLicenseService;
	/**
	 * <b>功能描述: 根据主键查询该人员的证照信息(填写申请)</b>
	 * @Param: [a0100]
	 * @Return: com.hxoms.common.utils.Result
	 * @Author: luoshuai
	 * @Date: 2020/8/11 8:41
	 */
	@GetMapping("/getCfCertificateByA0100")
	public Result getCfCertificateByA0100(String a0100){
		List<Map<String,Object>> list = omsCerApplyLendingLicenseService.getCfCertificateByA0100(a0100);
		return Result.success(list);
	}


	/**
	 * <b>功能描述: 保存申请借出的证照信息</b>
	 * @Param: [omsCerApplyLendingLicense]
	 * @Return: com.hxoms.common.utils.Result
	 * @Author: luoshuai
	 * @Date: 2020/8/11 8:41
	 */
	@PostMapping("/saveApplyLendingLicenseInfo")
	public Result saveApplyLendingLicenseInfo(OmsCerApplyLendingLicense omsCerApplyLendingLicense){
		omsCerApplyLendingLicenseService.saveApplyLendingLicenseInfo(omsCerApplyLendingLicense);
		return Result.success();
	}


	/**
	 * <b>功能描述: 查询证照借出申请信息（首页查询）</b>
	 * @Param: [omsCerApplyLendingLicense]
	 * @Return: com.hxoms.common.utils.Result
	 * @Author: luoshuai
	 * @Date: 2020/8/11 8:41
	 */
	@GetMapping("/selectApplyLendingLicenseInfo")
	public Result selectApplyLendingLicenseInfo(OmsCerApplyLendingLicense omsCerApplyLendingLicense){
		List<Map<String,Object>> list = omsCerApplyLendingLicenseService.selectApplyLendingLicenseInfo(omsCerApplyLendingLicense);
		return Result.success(list);
	}

	/**
	 * <b>功能描述: 借出证照申请导出</b>
	 * @Param: [omsCerApplyLendingLicense]
	 * @Return: com.hxoms.common.utils.Result
	 * @Author: luoshuai
	 * @Date: 2020/8/11 14:41
	 */
	@PostMapping("/getApplyLendingLicenseInfoOut")
	public void getApplyLendingLicenseInfoOut(OmsCerApplyLendingLicense omsCerApplyLendingLicense){
		omsCerApplyLendingLicenseService.getApplyLendingLicenseInfoOut(omsCerApplyLendingLicense,response);
	}


	/**
	 * <b>功能描述: 提交申请</b>
	 * @Param: [list]
	 * @Return: com.hxoms.common.utils.Result
	 * @Author: luoshuai
	 * @Date: 2020/8/18 14:41
	 */
	@PostMapping("/updateApplyLendingLicenseCommit")
	public Result updateApplyLendingLicenseCommit(@RequestParam(value = "list",required = false) List<String> list){
		omsCerApplyLendingLicenseService.updateApplyLendingLicenseCommit(list);
		return Result.success();
	}
}












