package com.hxoms.modules.passportCard.omsCerApplyLendingLicense.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hxoms.modules.passportCard.omsCerApplyLendingLicense.entity.OmsCerApplyLendingLicense;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * <b>功能描述: 借出证照申请业务层接口</b>
 * @Param:
 * @Return:
 * @Author: luoshuai
 * @Date: 2020/8/10 17:53
 */
public interface OmsCerApplyLendingLicenseService {

	/**
	 * <b>功能描述: 根据主键查询该人员的证照信息</b>
	 * @Param: [a0100]
	 * @Return: com.hxoms.common.utils.Result
	 * @Author: luoshuai
	 * @Date: 2020/8/11 8:41
	 */
	List<Map<String, Object>> getCfCertificateByA0100(String a0100);

	/**
	 * <b>功能描述: 保存申请借出的证照信息</b>
	 * @Param: [list]
	 * @Return: com.hxoms.common.utils.Result
	 * @Author: luoshuai
	 * @Date: 2020/8/11 8:41
	 */
	void saveApplyLendingLicenseInfo(List<OmsCerApplyLendingLicense> list);

	/**
	 * <b>功能描述: 查询证照借出申请信息</b>
	 * @Param: [omsCerApplyLendingLicense]
	 * @Return: com.hxoms.common.utils.Result
	 * @Author: luoshuai
	 * @Date: 2020/8/11 8:41
	 */
	List<Map<String, Object>> selectApplyLendingLicenseInfo(OmsCerApplyLendingLicense omsCerApplyLendingLicense);

	/**
	 * <b>功能描述: 借出证照申请导出</b>
	 * @Param: [omsCerApplyLendingLicense]
	 * @Return: com.hxoms.common.utils.Result
	 * @Author: luoshuai
	 * @Date: 2020/8/11 14:41
	 */
	void getApplyLendingLicenseInfoOut(OmsCerApplyLendingLicense omsCerApplyLendingLicense, HttpServletResponse response);


	/**
	 * <b>功能描述: 提交申请</b>
	 * @Param: [list]
	 * @Return: com.hxoms.common.utils.Result
	 * @Author: luoshuai
	 */
	void updateApplyLendingLicenseCommit(List<String> list);


	/**
	 * <b>功能描述: 撤销申请借出的证照</b>
	 * @Param: [omsCerApplyLendingLicense]
	 * @Return: com.hxoms.common.utils.Result
	 * @Author: luoshuai
	 * @Date: 2020/8/11 8:41
	 */
	void updateApplyLendingLicenseRevoke(OmsCerApplyLendingLicense omsCerApplyLendingLicense);
}
