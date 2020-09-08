package com.hxoms.modules.passportCard.omsCerCancellateLicense.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hxoms.modules.passportCard.initialise.entity.CfCertificate;
import com.hxoms.modules.passportCard.omsCerCancellateLicense.entity.OmsCerCancellateLicense;
import com.hxoms.modules.passportCard.omsCerCancellateLicense.entity.OmsCerCancellateRecords;
import com.hxoms.support.sysdict.entity.SysDictItem;

import java.util.List;
import java.util.Map;

/**
 * <b>功能描述: 注销证照申请业务层</b>
 * @Param:
 * @Return:
 * @Author: luoshuai
 * @Date: 2020/8/5 10:24
 */
public interface OmsCerCancellateLicenseApplyService {

	/**
	 * <b>功能描述: 填写注销申请（查询）</b>
	 * @Param: [cfCertificate]
	 * @Return: com.hxoms.common.utils.Result
	 * @Author: luoshuai
	 * @Date: 2020/8/5 10:29
	 */
	List<Map<String, Object>> getCancellateLicense(CfCertificate cfCertificate);


	/**
	 * <b>功能描述: 填写注销申请进行下一步</b>
	 * @Param: [list]
	 * @Return: com.hxoms.common.utils.Result
	 * @Author: luoshuai
	 * @Date: 2020/8/5 11:50
	 */
	Map<String,Object> saveCancellateLicenseChoose(List<OmsCerCancellateLicense> list);


	/**
	 * <b>功能描述: 查询注销证照申请列表</b>
	 * @Param: [omsCerCancellateLicense,page]
	 * @Return: com.hxoms.common.utils.Result
	 * @Author: luoshuai
	 * @Date: 2020/8/5 13:37
	 */
	Page<OmsCerCancellateLicense> getCancellateLicenseApply(Page<OmsCerCancellateLicense> page, OmsCerCancellateLicense omsCerCancellateLicense);


	/**
	 * <b>功能描述: 删除注销证照申请</b>
	 * @Param: [id]
	 * @Return: void
	 * @Author: luoshuai
	 * @Date: 2020/8/5 15:04
	 */
	void deleteCancellateLicenseApply(String id);


	/**
	 * <b>功能描述: 撤销注销证照申请</b>
	 * @Param: [list]
	 * @Return: com.hxoms.common.utils.Result
	 * @Author: luoshuai
	 * @Date: 2020/8/5 15:09
	 */
	void removeCancellateLicenseApply(List<OmsCerCancellateLicense> list);


	/**
	 * <b>功能描述: 更改申请状态</b>
	 * @Param: [omsCerCancellateLicense]
	 * @Return: com.hxoms.common.utils.Result
	 * @Author: luoshuai
	 * @Date: 2020/8/5 15:09
	 */
	void updateCancellateLicenseApplyStatus(OmsCerCancellateLicense omsCerCancellateLicense);


	/**
	 * <b>功能描述: 查询证照注销申请状态</b>
	 * @Param: []
	 * @Return: com.hxoms.common.utils.Result
	 * @Author: luoshuai
	 * @Date: 2020/8/5 15:09
	 */
	List<SysDictItem> getCancellateLicenseApplyStatus();


	/**
	 * <b>功能描述: 查询证照注销原因</b>
	 * @Param: []
	 * @Return: com.hxoms.common.utils.Result
	 * @Author: luoshuai
	 * @Date: 2020/8/5 15:09
	 */
	List<SysDictItem> getCancellateLicenseApplyReason();


	/**
	 * <b>功能描述: 查询证照注销方式</b>
	 * @Param: []
	 * @Return: com.hxoms.common.utils.Result
	 * @Author: luoshuai
	 * @Date: 2020/8/5 15:09
	 */
	List<SysDictItem> getCancellateLicenseApplyWay();


	/**
	 * <b>功能描述: 查询审批记录</b>
	 * @Param: [omsCerCancellateLicense]
	 * @Return: java.util.List<com.hxoms.modules.passportCard.omsCerCancellateLicense.entity.OmsCerCancellateRecords>
	 * @Author: luoshuai
	 * @Date: 2020/9/3 9:15
	 */
	List<OmsCerCancellateRecords> getCerCancellateLicenseRecord(OmsCerCancellateLicense omsCerCancellateLicense);
}
