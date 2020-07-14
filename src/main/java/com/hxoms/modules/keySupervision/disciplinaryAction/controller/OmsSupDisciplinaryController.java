package com.hxoms.modules.keySupervision.disciplinaryAction.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hxoms.common.utils.Result;
import com.hxoms.modules.keySupervision.disciplinaryAction.entity.OmsSupDisciplinary;
import com.hxoms.modules.keySupervision.disciplinaryAction.service.OmsSupDisciplinaryService;
import com.hxoms.modules.keySupervision.majorLeader.entity.OmsSupMajorLeader;
import com.hxoms.modules.keySupervision.nakedOfficial.controller.base.BaseController;
import com.hxoms.support.sysdict.entity.SysDictItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <b>处分信息模块控制器</b>
 * @author luoshuai
 * @date 2020/5/15 10:59
 */
@RestController
@RequestMapping("/disciplinary")
public class OmsSupDisciplinaryController extends BaseController {

	@Autowired
	private OmsSupDisciplinaryService omsSupDisciplinaryService;

	/**
	 * <b>查询处分信息</b>
	 * @param idList
	 * @param omsSupDisciplinary
	 * @param page
	 * @return
	 */
	@GetMapping("/getDisciplinaryInfo")
	public Result getDisciplinaryInfo(Page<OmsSupDisciplinary> page, @RequestParam(value = "idList",required = false) List<String> idList,
	                                  OmsSupDisciplinary omsSupDisciplinary){
		page = omsSupDisciplinaryService.getDisciplinaryInfo(page,omsSupDisciplinary,idList);
		return Result.success(page);
	}


	/**
	 * <b>增加处分信息</b>
	 * @param omsSupDisciplinary
	 * @return
	 */
	@PostMapping("/addDisciplinaryInfo")
	public Result addDisciplinaryInfo(OmsSupDisciplinary omsSupDisciplinary){
		omsSupDisciplinaryService.addDisciplinaryInfo(omsSupDisciplinary);
		return Result.success();
	}



	/**
	 * <b>查询处分类型</b>
	 * @return
	 */
	@GetMapping("/getDisciplinaryActionType")
	public Result getDisciplinaryActionType(){
		List<SysDictItem> list = omsSupDisciplinaryService.getDisciplinaryActionType();
		return Result.success(list);
	}


	/**
	 * <b>修改处分信息</b>
	 * @param omsSupDisciplinary
	 * @return
	 */
	@PostMapping("/updateDisciplinaryInfo")
	public Result updateDisciplinaryInfo(OmsSupDisciplinary omsSupDisciplinary){
		omsSupDisciplinaryService.updateDisciplinaryInfo(omsSupDisciplinary);
		return Result.success();
	}


	/**
	 * <b>删除处分信息</b>
	 * @param omsSupDisciplinary
	 * @return
	 */
	@PostMapping("/removeDisciplinaryInfo")
	public Result removeDisciplinaryInfo(OmsSupDisciplinary omsSupDisciplinary){
		omsSupDisciplinaryService.removeDisciplinaryInfo(omsSupDisciplinary);
		return Result.success();
	}


	/**
	 * <b>导出处分信息</b>
	 * @param idList
	 * @param omsSupDisciplinary
	 * @return
	 */
	@PostMapping("/getDisciplinaryInfoOut")
	public void getDisciplinaryInfoOut(@RequestParam(value = "idList",required = false) List<String> idList,
	                                   OmsSupDisciplinary omsSupDisciplinary){
		omsSupDisciplinaryService.getDisciplinaryInfoOut(idList,omsSupDisciplinary,response);
	}


	/**
	 * <b>功能描述: 根据处分类型和处分时间计算影响期</b>
	 * @Param: [omsSupDisciplinary]
	 * @Return: com.hxoms.common.utils.Result
	 * @Author: luoshuai
	 * @Date: 2020/7/14 9:28
	 */
	@GetMapping("getInfluenceAndTime")
	public Result getInfluenceAndTime(OmsSupDisciplinary omsSupDisciplinary){
		omsSupDisciplinary =  omsSupDisciplinaryService.getInfluenceAndTime(omsSupDisciplinary);
		return Result.success(omsSupDisciplinary);
	}

}
