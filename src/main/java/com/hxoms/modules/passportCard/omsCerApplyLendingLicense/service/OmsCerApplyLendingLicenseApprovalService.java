package com.hxoms.modules.passportCard.omsCerApplyLendingLicense.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hxoms.modules.passportCard.omsCerApplyLendingLicense.entity.OmsCerApplyLendingLicense;
import com.hxoms.modules.passportCard.omsCerTransferOutLicense.entity.OmsCerTransferOutLicense;

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
	 * <b>功能描述: 查询年份对应的批次号结构树</b>
	 * @Param: []
	 * @Return: com.hxoms.common.utils.Result
	 * @Author: luoshuai
	 * @Date: 2020/8/21 15:07
	 */
	List<OmsCerApplyLendingLicense> getBatchByYear();



	/**
	 * <b>功能描述: 查询借出证照申请记录</b>
	 * @Param: [page,documentNum]
	 * @Return: com.hxoms.common.utils.Result
	 * @Author: luoshuai
	 * @Date: 2020/8/11 16:29
	 */
	Page<Map<String, Object>> getApplyLendingLicenseApprovalRecord(Page<Map<String, Object>> page, String documentNum);


	/**
	 * <b>功能描述: 录入审批结果</b>
	 * @Param: [list]
	 * @Return: com.hxoms.common.utils.Result
	 * @Author: luoshuai
	 * @Date: 2020/8/11 16:29
	 */
	void updateApplyLendingLicenseApprovalResult(List<OmsCerApplyLendingLicense> list);


	/**
	 * <b>功能描述: 打印呈批单</b>
	 * @Param: [list]
	 * @Return: com.hxoms.common.utils.Result
	 * @Author: luoshuai
	 * @Date: 2020/9/10 11:45
	 */
	Map<String, Object> getApplyLendingLicenseApprovalBill(List<OmsCerApplyLendingLicense> list);


	/**
	 * <b>功能描述: 打印请示表</b>
	 * @Param: [list]
	 * @Return: com.hxoms.common.utils.Result
	 * @Author: luoshuai
	 * @Date: 2020/9/10 11:45
	 */
	Map<String, Object> getApplyLendingLicenseApprovalRequest(List<OmsCerApplyLendingLicense> list);
}
