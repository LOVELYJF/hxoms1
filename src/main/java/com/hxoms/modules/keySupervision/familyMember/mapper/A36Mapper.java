package com.hxoms.modules.keySupervision.familyMember.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hxoms.modules.keySupervision.familyMember.entity.A36;

import java.util.List;

public interface A36Mapper extends BaseMapper<A36> {


	/**
	 * <b>查询家庭成员信息</b>
	 * @param a0100
	 * @return
	 */
	List<A36> selectFamilyMember(String a0100);


	/**
	 * <b>查询家庭成员政治面貌信息</b>
	 * @param a3600
	 * @return
	 */
	List<String> selectPiliticalAffi(String a3600);


	/**
	 * <b>查询家庭成员主键</b>
	 * @param a0100
	 * @return
	 */
	List<String> selectA3600List(String a0100);


	/**
	 * <b>查询家庭成员身份证号码集合</b>
	 * @param a0100
	 * @return
	 */
	List<String> selectIdCardList(String a0100);

	/**
	* @description:修改家庭成员信息
	* @author:杨波
	* @date:2020-09-22
	*  * @param a36
	* @return:
	**/
	void updateList(List<A36> a36);
}