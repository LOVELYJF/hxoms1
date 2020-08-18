package com.hxoms.modules.passportCard.omsCerCancellateLicense.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hxoms.modules.passportCard.omsCerCancellateLicense.entity.OmsCerCancellateLicense;

import java.util.List;
import java.util.Map;

public interface OmsCerCancellateLicenseMapper extends BaseMapper<OmsCerCancellateLicense> {

	/**
	 * <b>功能描述: 注销证照受理查询</b>
	 * @Param: [omsCerCancellateLicense]
	 * @Return: java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
	 * @Author: luoshuai
	 * @Date: 2020/8/5 16:52
	 */
	List<Map<String, Object>> getCerCancellateLicenseAcceptance(Map<String, Object> map);


	/**
	 * <b>功能描述: 处领导审批(可以批量审批)</b>
	 * @Param: [list,omsCerCancellateLicense]
	 * @Return: org.apache.ibatis.annotations.Result
	 * @Author: luoshuai
	 * @Date: 2020/8/7 16:43
	 */
	int updateCerCancellateLicenseApproval(Map<String, Object> map);
}