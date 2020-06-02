package com.hxoms.modules.keySupervision.dismissed.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hxoms.common.utils.Result;
import com.hxoms.modules.keySupervision.dismissed.entity.OmsSupDismissed;
import com.hxoms.modules.keySupervision.dismissed.service.OmsSupDismissedService;
import com.hxoms.modules.keySupervision.nakedOfficial.controller.base.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <b> 免职撤职模块控制器</b>
 * @author luoshuai
 * @date 2020/5/18 17:33
 */
@RestController
@RequestMapping("/dissmissed")
public class OmsSupDissmissedController extends BaseController {

	@Autowired
	private OmsSupDismissedService omsSupDismissedService;
	/**
	 * <b>查询免职撤职信息</b>
	 * @param idList
	 * @param omsSupDismissed
	 * @param page
	 * @return
	 */
	@GetMapping("/getDismissedInfo")
	public Result getDismissedInfo(Page<OmsSupDismissed> page, @RequestParam(value = "idList",required = false) List<String> idList,
	                               OmsSupDismissed omsSupDismissed){
		page = omsSupDismissedService.getDismissedInfo(page,omsSupDismissed,idList);
		return Result.success(page);
	}


	/**
	 * <b>增加免职撤职信息</b>
	 * @param omsSupDismissed
	 * @return
	 */
	@PostMapping("/addDismissedInfo")
	public Result addDisciplinaryInfo(OmsSupDismissed omsSupDismissed){
		omsSupDismissedService.addDismissedInfo(omsSupDismissed);
		return Result.success();
	}


	/**
	 * <b>修改免职撤职信息</b>
	 * @param omsSupDismissed
	 * @return
	 */
	@PostMapping("/updateDismissedInfo")
	public Result updateDismissedInfo(OmsSupDismissed omsSupDismissed){
		omsSupDismissedService.updateDismissedInfo(omsSupDismissed);
		return Result.success();
	}



	/**
	 * <b>删除免职撤职信息</b>
	 * @param id
	 * @return
	 */
	@PostMapping("/deleteDismissedInfo")
	public Result deleteDismissedInfo(String id){
		omsSupDismissedService.deleteDismissedInfo(id);
		return Result.success();
	}


	/**
	 * <b>导出免职撤职人员信息</b>
	 * @param list
	 * @return
	 */
	@PostMapping("/getDismissedInfoOut")
	public void getDismissedInfoOut(@RequestBody(required = false) List<OmsSupDismissed> list){
		omsSupDismissedService.getDismissedInfoOut(list,response);
	}
}
