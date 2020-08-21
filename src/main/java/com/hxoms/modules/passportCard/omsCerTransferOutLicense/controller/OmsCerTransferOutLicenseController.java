package com.hxoms.modules.passportCard.omsCerTransferOutLicense.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hxoms.common.utils.Result;
import com.hxoms.modules.passportCard.omsCerTransferOutLicense.entity.OmsCerTransferOutLicense;
import com.hxoms.modules.passportCard.omsCerTransferOutLicense.service.OmsCerTransferOutLicenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * <b>功能描述: 转出证照控制层</b>
 * @Param:
 * @Return:
 * @Author: luoshuai
 * @Date: 2020/8/10 16:24
 */
@RestController
@RequestMapping("/TransferOutLicense")
public class OmsCerTransferOutLicenseController {


	@Autowired
	private OmsCerTransferOutLicenseService omsCerTransferOutLicenseService;
	/**
	 * <b>功能描述: 查询转出证照申请信息</b>
	 * @Param: [page,omsCerTransferOutLicense]
	 * @Return: com.hxoms.common.utils.Result
	 * @Author: luoshuai
	 * @Date: 2020/8/10 15:07
	 */
	@GetMapping("/selectTransferOutLicenseInfo")
	public Result selectTransferOutLicenseInfo(Page<Map<String,Object>> page ,OmsCerTransferOutLicense omsCerTransferOutLicense){
		page = omsCerTransferOutLicenseService.selectTransferOutLicenseInfo(page,omsCerTransferOutLicense);
		return Result.success(page);
	}


	/**
	 * <b>功能描述: 查询转出证照申请的原单位信息</b>
	 * @Param: []
	 * @Return: com.hxoms.common.utils.Result
	 * @Author: luoshuai
	 * @Date: 2020/8/10 15:07
	 */
	@GetMapping("/getTransferOutWorkUnitInfo")
	public Result getTransferOutWorkUnitInfo(){
		List<OmsCerTransferOutLicense> list = omsCerTransferOutLicenseService.getTransferOutWorkUnitInfo();
		return Result.success(list);
	}


	/**
	 * <b>功能描述: 查询年份对应的批次号结构树</b>
	 * @Param: []
	 * @Return: com.hxoms.common.utils.Result
	 * @Author: luoshuai
	 * @Date: 2020/8/17 15:07
	 */
	@GetMapping("/getBatchByYear")
	public Result getBatchByYear(){
		List<OmsCerTransferOutLicense> list = omsCerTransferOutLicenseService.getBatchByYear();
		return Result.success(list);
	}



	/**
	 * <b>功能描述: 打印后将年月日作为批次号，并记录移交人接手人</b>
	 * @Param: [list,omsCerTransferOutLicense]
	 * @Return: com.hxoms.common.utils.Result
	 * @Author: luoshuai
	 * @Date: 2020/8/10 15:07
	 */
	@PostMapping("/updateTransferOutRecord")
	public Result updateTransferOutRecord(@RequestParam(value = "list",required = true) List<String> list,
	                                      OmsCerTransferOutLicense omsCerTransferOutLicense){
		omsCerTransferOutLicenseService.updateTransferOutRecord(list,omsCerTransferOutLicense);
		return Result.success();
	}
}













