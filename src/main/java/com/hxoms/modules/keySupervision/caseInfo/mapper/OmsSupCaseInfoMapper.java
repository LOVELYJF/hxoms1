package com.hxoms.modules.keySupervision.caseInfo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hxoms.modules.keySupervision.caseInfo.entity.OmsSupCaseInfo;

public interface OmsSupCaseInfoMapper extends BaseMapper<OmsSupCaseInfo> {

	/**
	 * <b>功能描述: 查询立案文书号</b>
	 * @Param: []
	 * @Return: java.lang.String
	 * @Author: luoshuai
	 * @Date: 2020/10/12 10:31
	 */
	String selectDocumentNum();
}