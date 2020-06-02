package com.hxoms.modules.keySupervision.familyMember.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hxoms.modules.keySupervision.familyMember.entity.A36;
import com.hxoms.support.sysdict.entity.SysDictItem;

import java.util.List;

/**
 * <b>家庭成员控制层</b>
 * @author luoshuai
 * @date 2020/5/10 18:28
 */
public interface OmsSupFamilyMemberService {


	/**
	 * <b>查询家庭成员关系列表</b>
	 * @return
	 */
	List<SysDictItem> getFamilyMemberRelationship();


	/**
	 * <b>查询政治面貌集合</b>
	 * @return
	 */
	List<SysDictItem> getPoliticalAffi();




	/**
	 * <b>查询家庭成员信息</b>
	 * @param a0100
	 * @param page
	 * @return
	 */
	Page<A36> getFamilyMember(Page page,String a0100);


	/**
	 * <b>更新家庭成员信息</b>
	 * @param list
	 * @return
	 */
	void updateFamilyMemberInfo(List<A36> list);

	/**
	 * <b>对家庭成员进行登记备案</b>
	 * @param list
	 * @return
	 */
	void addToRegistration(List<A36> list);



	/**
	 * <b>撤销家庭成员登记备案</b>
	 * @param a0100
	 * @return
	 */
	void removeToRegistration(String a0100);

	/**
	 * <b>查询人员现状</b>
	 * @return
	 */
	List<SysDictItem> getPersonStatus();


	/**
	 * <b>查询国籍</b>
	 * @return
	 */
	List<SysDictItem> getNationality();


	/**
	 * <b>查询移居类别</b>
	 * @return
	 */
	List<SysDictItem> getMigrationCategory();
}