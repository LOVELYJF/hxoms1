package com.hxoms.modules.keySupervision.nakedOfficial.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hxoms.common.utils.Result;
import com.hxoms.modules.keySupervision.nakedOfficial.controller.base.BaseController;
import com.hxoms.modules.keySupervision.nakedOfficial.entity.OmsSupNakedSign;
import com.hxoms.modules.keySupervision.nakedOfficial.service.OmsSupNakedSignService;
import com.hxoms.support.sysdict.entity.SysDictItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <b>裸官信息控制层</b>
 * @author luoshuai
 * @date 2020/5/8 18:51
 */
@RestController
@RequestMapping("/nakedSign")
public class OmsSupNakedSignController extends BaseController {

	@Autowired
	private OmsSupNakedSignService omsNakedOfficialService;

	/**
	 * <b>查询裸官信息</b>
	 * @param idList
	 * @param omsSupNakedSign
	 * @param page
	 * @return
	 */
	@GetMapping("/getOmsNakedSign")
	public Result getOmsNakedSign( Page<OmsSupNakedSign> page,@RequestParam(value = "idList",required = false) List<String> idList,
	                              OmsSupNakedSign omsSupNakedSign){
		//查询裸官基本信息
		page = omsNakedOfficialService.getNakedOfficialList(page,omsSupNakedSign,idList);
		return Result.success(page);
	}

	/**
	 * <b>添加裸官信息</b>
	 * @param omsSupNakedSign
	 * @return
	 */
	@PostMapping("/addOmsNaked")
	public Result addOmsNaked(OmsSupNakedSign omsSupNakedSign){
		omsNakedOfficialService.addOmsNaked(omsSupNakedSign);
		return Result.success();
	}


	/**
	 * <b>修改裸官信息</b>
	 * @param omsSupNakedSign
	 * @return
	 */
	@PostMapping("/updateOmsNaked")
	public Result updateOmsNaked(OmsSupNakedSign omsSupNakedSign){
		omsNakedOfficialService.updateOmsNaked(omsSupNakedSign);
		return Result.success();
	}


	/**
	 * <b>取消裸官标识</b>
	 * @param omsSupNakedSign
	 * @return
	 */
	@PostMapping("/removeOmsNaked")
	public Result removeOmsNaked(OmsSupNakedSign omsSupNakedSign){
		omsNakedOfficialService.removeOmsNaked(omsSupNakedSign);
		return Result.success();
	}


	/**
	 * <b>导出裸官信息</b>
	 * @param idList
	 * @param omsSupNakedSign
	 * @return
	 */
	@PostMapping("/getNakedOfficialInfoOut")
	public void getNakedOfficialInfoOut(@RequestParam(value = "idList",required = false) List<String> idList,
	                                    OmsSupNakedSign omsSupNakedSign){
		omsNakedOfficialService.getNakedOfficialOut(idList,omsSupNakedSign,response);
	}


	/**
	 * <b>查询限制性岗位</b>
	 * @return
	 */
	@GetMapping("/getXzxgwInfo")
	public Result getNakedOfficialInfoOut(){
		List<SysDictItem> list = omsNakedOfficialService.getXzxgwInfo();
		return Result.success(list);
	}

}
