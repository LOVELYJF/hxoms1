package com.hxoms.modules.passportCard.omsCerCancellateLicense.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hxoms.modules.passportCard.omsCerCancellateLicense.entity.OmsCerCancellateApply;
import com.hxoms.support.b01.entity.B01;

public interface OmsCerCancellateApplyMapper extends BaseMapper<OmsCerCancellateApply> {


	/**
	 * <b>功能描述: 根据申请主键查询申请单单位信息</b>
	 * @Param: [applyId]
	 * @Return: com.hxoms.support.b01.entity.B01
	 * @Author: luoshuai
	 * @Date: 2020/9/4 15:51
	 */
	B01 getB0100ByApplyId(String applyId);
}