package com.hxoms.modules.passportCard.omsCerApplyLendingLicense.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hxoms.modules.passportCard.omsCerApplyLendingLicense.entity.OmsCerApplyLendingLicense;

import java.util.List;
import java.util.Map;

/**
 * <b>功能描述: 借出证照申请审批业务层接口</b>
 * @Param:
 * @Return:
 * @Author: luoshuai
 * @Date: 2020/8/11 16:09
 */
public interface OmsCerApplyLendingLicenseApprovalService {

	/**
	 * <b>功能描述: 查询借出证照申请记录</b>
	 * @Param: [page]
	 * @Return: com.hxoms.common.utils.Result
	 * @Author: luoshuai
	 * @Date: 2020/8/11 16:29
	 */
	Page<Map<String, Object>> getApplyLendingLicenseApprovalRecord(Page<Map<String, Object>> page);


	/**
	 * <b>功能描述: 录入审批结果</b>
	 * @Param: [list,omsCerApplyLendingLicense]
	 * @Return: com.hxoms.common.utils.Result
	 * @Author: luoshuai
	 * @Date: 2020/8/11 16:29
	 */
	void updateApplyLendingLicenseApprovalResult(List<String> list, OmsCerApplyLendingLicense omsCerApplyLendingLicense);
}
