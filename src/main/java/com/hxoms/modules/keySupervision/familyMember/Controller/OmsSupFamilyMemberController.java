package com.hxoms.modules.keySupervision.familyMember.Controller;


import com.alibaba.fastjson.JSONArray;
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
	 * @return
	 */
	@GetMapping("/getFamilyMemberInfo")
	public Result getFamilyMemberInfo(String a0100){
		List<A36> list = omsSupFamilyMemberService.getFamilyMember(a0100);
		return Result.success(list);

	}



	/**
	 * <b>对家庭成员进行登记备案</b>
	 * @param list
	 * @return
	 */
	@PostMapping("/addToRegistration")
	public Result addToRegistration(String list){
        List<A36> a36S= JSONArray.parseArray(list,A36.class);
		Result result=omsSupFamilyMemberService.addToRegistration(a36S);
		return result;
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

	/**
	 * <b>添加家庭成员</b>
	 * @param a36
	 * @return
	 */
	@PostMapping("/insertFamilyMember")
	public Result insertFamilyMember(A36 a36){
		omsSupFamilyMemberService.insertFamilyMember(a36);
		return Result.success();
	}
	/**
	 * @description:修改家庭成员信息
	 * @author:杨波
	 * @date:2020-09-22
	 *  * @param a36
	 * @return:
	 **/
	@PostMapping("/updateList")
	public Result updateList(String a36) {
		List<A36> a36S= JSONArray.parseArray(a36,A36.class);
		omsSupFamilyMemberService.updateList(a36S);
		return Result.success();
	}
}
