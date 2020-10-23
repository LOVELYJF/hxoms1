package com.hxoms.modules.passportCard.omsCerTransferExpiredLicense.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hxoms.common.utils.Result;
import com.hxoms.modules.keySupervision.nakedOfficial.controller.base.BaseController;
import com.hxoms.modules.passportCard.initialise.entity.CfCertificate;
import com.hxoms.modules.passportCard.omsCerTransferExpiredLicense.service.OmsCerTransferExpiredLicenseService;
import com.hxoms.modules.passportCard.printGetQrCode.entity.parameterEntity.QrCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <b>功能描述: 过期证照转存控制层</b>
 * @Param:
 * @Return:
 * @Author: luoshuai
 * @Date: 2020/8/5 10:24
 */
@RestController
@RequestMapping("/transferExpiredLicense")
public class OmsCerTransferExpiredLicenseController extends BaseController {


		@Autowired
		private OmsCerTransferExpiredLicenseService omsCerTransferExpiredLicenseService;
		/**
		 * <b>功能描述: 查询过期证照信息</b>
		 * @Param: [page,list,expiredQueryStartTime,expiredQueryEndTime,cfCertificate]
		 * @Return: com.hxoms.common.utils.Result
		 * @Author: luoshuai
		 * @Date: 2020/8/17 14:16
		 */
		@GetMapping("/getTransferExpiredLicenseInfo")
		public Result getTransferExpiredLicenseInfo(Page<Map<String,Object>> page,
													@RequestParam(value = "list",required = false) List<String> list,
													@RequestParam(value = "idList",required = false) List<String> idList,
		                                            @DateTimeFormat(pattern = "yyyy.MM.dd") Date expiredQueryStartTime,
		                                            @DateTimeFormat(pattern = "yyyy.MM.dd") Date expiredQueryEndTime,
		                                            CfCertificate cfCertificate){
			page = omsCerTransferExpiredLicenseService.getTransferExpiredLicenseInfo(page,list,idList,expiredQueryStartTime,expiredQueryEndTime,cfCertificate);
			return Result.success(page);
		}



	/**
	 * <b>功能描述: 过期证照转存（导出）</b>
	 * @Param: [list,expiredQueryStartTime,expiredQueryEndTime,cfCertificate]
	 * @Return: com.hxoms.common.utils.Result
	 * @Author: luoshuai
	 * @Date: 2020/8/18 14:16
	 */
	@PostMapping("/getTransferExpiredLicenseOut")
	public void getTransferExpiredLicenseOut(@RequestParam(value = "list",required = false) List<String> list,
	                                         @RequestParam(value = "idList",required = false) List<String> idList,
	                                         @DateTimeFormat(pattern = "yyyy.MM.dd") Date expiredQueryStartTime,
	                                         @DateTimeFormat(pattern = "yyyy.MM.dd") Date expiredQueryEndTime,
	                                         CfCertificate cfCertificate){
		omsCerTransferExpiredLicenseService.getTransferExpiredLicenseOut(list,idList,expiredQueryStartTime,expiredQueryEndTime,cfCertificate,response);

	}


	/**
	 * <b>功能描述: 转存（打开转存页面的转存）</b>
	 * @Param: [list]
	 * @Return: com.hxoms.common.utils.Result
	 * @Author: luoshuai
	 * @Date: 2020/8/18 14:16
	 */
	@PostMapping("/getTransferExpiredLicenseDeposit")
	public Result getTransferExpiredLicenseDeposit(@RequestBody List<CfCertificate> list){
		list = omsCerTransferExpiredLicenseService.getTransferExpiredLicenseDeposit(list);
		return Result.success(list);
	}



	/**
	 * <b>功能描述: 转存（保存转存信息），修改证照保存状态</b>
	 * @Param: [list]
	 * @Return: com.hxoms.common.utils.Result
	 * @Author: luoshuai
	 * @Date: 2020/8/18 14:16
	 */
	@PostMapping("/getTransferExpiredLicenseSave")
	public Result getTransferExpiredLicenseSave(@RequestBody List<CfCertificate> list){
		omsCerTransferExpiredLicenseService.getTransferExpiredLicenseSave(list);
		return Result.success();
	}


	/**
	 * <b>功能描述: 打印二维码</b>
	 * @Param: [list]
	 * @Return: com.hxoms.common.utils.Result
	 * @Author: luoshuai
	 * @Date: 2020/9/11 14:16
	 */
	@PostMapping("/getTransferExpiredLicenseQrCode")
	public Result getTransferExpiredLicenseQrCode(@RequestBody List<CfCertificate> list){
		QrCode qrCode = omsCerTransferExpiredLicenseService.getTransferExpiredLicenseQrCode(list);
		return Result.success(qrCode);
	}

}
