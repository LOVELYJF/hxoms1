package com.hxoms.modules.keySupervision.familyMember.Controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hxoms.common.utils.Result;
import com.hxoms.modules.country.entity.Country;
import com.hxoms.modules.keySupervision.familyMember.entity.A36;
import com.hxoms.modules.keySupervision.familyMember.service.OmsSupFamilyMemberService;
import com.hxoms.modules.keySupervision.nakedOfficial.entity.OmsSupNakedSign;
import com.hxoms.support.leaderInfo.entity.A01;
import com.hxoms.support.sysdict.entity.SysDictItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * <b>家庭成员控制层</b>
 * @author luoshuai
 * @date 2020/5/10 18:28
 */
@RestController
@RequestMapping("/familyMember")
public class OmsSupFamilyMemberController {

	@Autowired
	private OmsSupFamilyMemberService omsSupFamilyMemberService;
	/**
	 * <b>查询家庭成员关系列表</b>
	 * @return
	 */
	@GetMapping("/getFamilyMemberRelationship")
	public Result getFamilyMemberRelationship(){
		List<SysDictItem> list = omsSupFamilyMemberService.getFamilyMemberRelationship();
		return Result.success(list);
	}


	/**
	 * <b>查询政治面貌集合</b>
	 * @return
	 */
	@GetMapping("/getPoliticalAffi")
	public Result getPoliticalAffi(){
		List<SysDictItem> list = omsSupFamilyMemberService.getPoliticalAffi();
		return Result.success(list);
	}


	/**
	 * <b>查询人员现状</b>
	 * @return
	 */
	@GetMapping("/getPersonStatus")
	public Result getPersonStatus(){
		List<SysDictItem> list = omsSupFamilyMemberService.getPersonStatus();
		return Result.success(list);
	}


	/**
	 * <b>查询国籍</b>
	 * @return
	 */
	@GetMapping("/getNationality")
	public Result getNationality(){
		List<Country> list = omsSupFamilyMemberService.getNationality();
		return Result.success(list);
	}


	/**
	 * <b>查询移居类别</b>
	 * @return
	 */
	@GetMapping("/getMigrationCategory")
	public Result getMigrationCategory(){
		List<SysDictItem> list = omsSupFamilyMemberService.getMigrationCategory();
		return Result.success(list);
	}


	/**
	 * <b>查询人员基本信息</b>
	 * @param page
	 * @param idList
	 * @param name
	 * @return
	 */
	@GetMapping("/getPersonInfoForfamily")
	public Result getPersonInfoForfamily(Page<Map<String,Object>> page, @RequestParam(value = "idList",required = false) List<String> idList,
	                                     String name){
		page = omsSupFamilyMemberService.getPersonInfoForfamily(page,idList,name);
		return Result.success(page);
	}


	/**
	 * <b>查询家庭成员信息</b>
	 * @param a0100
	 * @param page
	 * @return
	 */
	@GetMapping("/getFamilyMemberInfo")
	public Result getFamilyMemberInfo(Page<A36> page, String a0100){
		page = omsSupFamilyMemberService.getFamilyMember(page, a0100);
		return Result.success(page);

	}



	/**
	 * <b>对家庭成员进行登记备案</b>
	 * @param list
	 * @return
	 */
	@PostMapping("/addToRegistration")
	public Result addToRegistration(@RequestBody List<A36> list){
		omsSupFamilyMemberService.addToRegistration(list);
		return Result.success();
	}


	/**
	 * <b>当取消裸官在限制性岗位的时候撤销家庭成员登记备案</b>
	 * @param a0100
	 * @return
	 */
	@PostMapping("/removeToRegistration")
	public Result removeToRegistration(String a0100){
		omsSupFamilyMemberService.removeToRegistration(a0100);
		return Result.success();
	}

}
