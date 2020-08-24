package com.hxoms.modules.passportCard.omsCerInventory.controller;

import com.hxoms.common.utils.Result;
import com.hxoms.modules.keySupervision.nakedOfficial.controller.base.BaseController;
import com.hxoms.modules.passportCard.counterGet.entity.OmsCerGetTask;
import com.hxoms.modules.passportCard.initialise.entity.CfCertificate;
import com.hxoms.modules.passportCard.initialise.entity.parameterEntity.CfCertificateValidate;
import com.hxoms.modules.passportCard.omsCerInventory.entity.OmsCerInventory;
import com.hxoms.modules.passportCard.omsCerInventory.service.OmsCerInventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * <b>功能描述: 证照盘点控制层</b>
 * @Param:
 * @Return:
 * @Author: luoshuai
 * @Date: 2020/8/19 11:47
 */
@RestController
@RequestMapping("/cerInventory")
public class OmsCerInventoryController extends BaseController {


	@Autowired
	private OmsCerInventoryService omsCerInventoryService;
	/**
	 * <b>功能描述: 开始盘点（证照机）</b>
	 * @Param: [omsCerInventory]
	 * @Return: com.hxoms.common.utils.Result
	 * @Author: luoshuai
	 * @Date: 2020/8/19 14:38
	 */
	@PostMapping("/insertCerInventoryInfoForCabinet")
	public Result insertCerInventoryInfoForCabinet(OmsCerInventory omsCerInventory){
		omsCerInventoryService.insertCerInventoryInfoForCabinet(omsCerInventory);
		return Result.success();
	}



	/**
	 * <b>功能描述: 统计盘点结果（证照机）证照前后保存状态不一致的</b>
	 * @Param: [omsCerInventory]
	 * @Return: com.hxoms.common.utils.Result
	 * @Author: luoshuai
	 * @Date: 2020/8/19 14:38
	 */
	@PostMapping("/GetCerInventoryResultForCabinet")
	public Result GetCerInventoryResultForCabinet(OmsCerInventory omsCerInventory){
		List<Map<String,Object>> list = omsCerInventoryService.GetCerInventoryResultForCabinet(omsCerInventory);
		return Result.success(list);
	}


	/**
	 * <b>功能描述: 保存盘点结果（证照机）</b>
	 * @Param: [list]
	 * @Return: com.hxoms.common.utils.Result
	 * @Author: luoshuai
	 * @Date: 2020/8/19 14:38
	 */
	@PostMapping("/updateCerInventoryResultForCabinet")
	public Result updateCerInventoryResultForCabinet(@RequestParam(value = "list",required = false) List<OmsCerInventory> list){
		omsCerInventoryService.updateCerInventoryResultForCabinet(list);
		return Result.success();
	}


	/**
	 * <b>功能描述: 导出盘点结果（证照机）</b>
	 * @Param: [omsCerInventory]
	 * @Return: com.hxoms.common.utils.Result
	 * @Author: luoshuai
	 * @Date: 2020/8/19 14:38
	 */
	@PostMapping("/getCerInventoryResultForCabinetOut")
	public void getCerInventoryResultForCabinetOut(OmsCerInventory omsCerInventory){
		omsCerInventoryService.getCerInventoryResultForCabinetOut(omsCerInventory,response);
	}



	/**
	 * <b>功能描述: 盘点情况统计（证照机）返回前后统计数量</b>
	 * @Param: [omsCerInventory]
	 * @Return: com.hxoms.common.utils.Result
	 * @Author: luoshuai
	 * @Date: 2020/8/20 14:38
	 */
	@GetMapping("/getCerInventoryStatisticsNum")
	public Result getCerInventoryStatisticsNum(OmsCerInventory omsCerInventory){
		Map<String,Integer> map =  omsCerInventoryService.getCerInventoryStatisticsNum(omsCerInventory);
		return Result.success(map);
	}






//	=======================================############################==============================================
//
//										证照存取记录 及 补领取任务
//
//	=======================================############################==============================================



	/**
	 * <b>功能描述: 开始盘点（柜台）</b>
	 * @Param: [omsCerInventory]
	 * @Return: com.hxoms.common.utils.Result
	 * @Author: luoshuai
	 * @Date: 2020/8/20 14:38
	 */
	@PostMapping("/insertCerInventoryInfoForCounter")
	public Result insertCerInventoryInfoForCounter(OmsCerInventory omsCerInventory){
		List<Map<String,Object>> list = omsCerInventoryService.insertCerInventoryInfoForCounter(omsCerInventory);
		return Result.success(list);
	}



	/**
	 * <b>功能描述: 保存盘点结果（柜台）</b>
	 * @Param: [list]
	 * @Return: com.hxoms.common.utils.Result
	 * @Author: luoshuai
	 * @Date: 2020/8/20 14:38
	 */
	@PostMapping("/updateCerInventoryResultForCounter")
	public Result updateCerInventoryResultForCounter(@RequestParam(value = "list",required = false) List<OmsCerInventory> list){
		Map<String,Integer> map = omsCerInventoryService.updateCerInventoryResultForCounter(list);
		return Result.success(map);
	}



	/**
	 * <b>功能描述: 导出盘点结果（柜台）</b>
	 * @Param: [omsCerInventory]
	 * @Return: com.hxoms.common.utils.Result
	 * @Author: luoshuai
	 * @Date: 2020/8/20 14:38
	 */
	@PostMapping("/getCerInventoryResultForCounterOut")
	public void getCerInventoryResultForCounterOut(OmsCerInventory omsCerInventory){
		omsCerInventoryService.getCerInventoryResultForCounterOut(omsCerInventory,response);
	}



	/**
	 * <b>功能描述: 总体盘点结果统计查询</b>
	 * @Param: [omsCerInventory]
	 * @Return: com.hxoms.common.utils.Result
	 * @Author: luoshuai
	 * @Date: 2020/8/20 14:38
	 */
	@PostMapping("/GetCerInventoryResult")
	public Result GetCerInventoryResult(@RequestParam(value = "list",required = true) List<String> list, OmsCerInventory omsCerInventory){
		Map<String,Object> map = omsCerInventoryService.GetCerInventoryResult(list,omsCerInventory);
		return Result.success(map);
	}


	/**
	 * <b>功能描述: 总体查询导出盘点结果</b>
	 * @Param: [omsCerInventory]
	 * @Return: com.hxoms.common.utils.Result
	 * @Author: luoshuai
	 * @Date: 2020/8/20 14:38
	 */
	@PostMapping("/getCerInventoryResultOut")
	public void getCerInventoryResultOut(@RequestParam(value = "list",required = true) List<String> list, OmsCerInventory omsCerInventory){
		omsCerInventoryService.getCerInventoryResultOut(list,omsCerInventory,response);
	}


	/**
	 * <b>功能描述: 查询所有的柜子集合</b>
	 * @Param: []
	 * @Return: com.hxoms.common.utils.Result
	 * @Author: luoshuai
	 * @Date: 2020/8/20 14:38
	 */
	@GetMapping("/getCerLicenseMachine")
	public Result getCerLicenseMachine(){
		List<String> list =  omsCerInventoryService.getCerLicenseMachine();
		return Result.success(list);
	}


	/**
	 * <b>功能描述: 存取记录</b>
	 * @Param: [cfCertificate]
	 * @Return: com.hxoms.common.utils.Result
	 * @Author: luoshuai
	 * @Date: 2020/8/20 14:38
	 */
	@GetMapping("/getCerAccessRecord")
	public Result getCerAccessRecord(CfCertificate cfCertificate){
		List<Map<String,Object>> list =  omsCerInventoryService.getCerAccessRecord(cfCertificate);
		return Result.success(list);
	}


	/**
	 * <b>功能描述: 补领取记录</b>
	 * @Param: [omsCerGetTask,mode]
	 * @Return: com.hxoms.common.utils.Result
	 * @Author: luoshuai
	 * @Date: 2020/8/24 14:38
	 */
	@PostMapping("/saveRepairCollectionRecord")
	public Result saveRepairCollectionRecord(OmsCerGetTask omsCerGetTask,String mode){
		omsCerInventoryService.saveRepairCollectionRecord(omsCerGetTask,mode);
		return Result.success();
	}
}














