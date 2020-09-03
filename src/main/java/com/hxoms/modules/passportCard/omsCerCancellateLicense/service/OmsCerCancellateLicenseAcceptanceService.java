package com.hxoms.modules.passportCard.omsCerCancellateLicense.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hxoms.modules.passportCard.omsCerCancellateLicense.entity.OmsCerCancellateLicense;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * <b>功能描述: 注销证照受理业务层</b>
 * @Param:
 * @Return:
 * @Author: luoshuai
 * @Date: 2020/8/5 16:24
 */
public interface OmsCerCancellateLicenseAcceptanceService {

	/**
	 * <b>功能描述: 注销证照受理查询(按单位主键，姓名，证件号码，证件类型,申请时间，申请状态查询)
	 * 和处领导审批处的查询为同一接口</b>
	 * @Param: [page,idList,omsCerCancellateLicense]
	 * @Return: java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
	 * @Author: luoshuai
	 * @Date: 2020/8/5 16:52
	 */
	Page<Map<String, Object>> getCerCancellateLicenseAcceptance(Page<Map<String, Object>> page, List<String> idList,
	                                                            OmsCerCancellateLicense omsCerCancellateLicense);


	/**
	 * <b>功能描述: 强制注销（监督处强制注销某个人的证照信息）进行下一步</b>
	 * @Param: [list]
	 * @Return: org.apache.ibatis.annotations.Result
	 * @Author: luoshuai
	 * @Date: 2020/8/7 16:43
	 */
	void getCerCancellateLicenseForce(List<OmsCerCancellateLicense> list);

	/**
	 * <b>功能描述: 注销证照受理-修改</b>
	 * @Param: [omsCerCancellateLicense]
	 * @Return: org.apache.ibatis.annotations.Result
	 * @Author: luoshuai
	 * @Date: 2020/8/7 16:43
	 */
	void updateCerCancellateLicenseAcceptance(OmsCerCancellateLicense omsCerCancellateLicense);

	/**
	 * <b>功能描述: 处领导审批(可以批量审批)</b>
	 * @Param: [list,omsCerCancellateLicense]
	 * @Return: org.apache.ibatis.annotations.Result
	 * @Author: luoshuai
	 * @Date: 2020/8/10 16:43
	 */
	void updateCerCancellateLicenseApproval(List<String> list, OmsCerCancellateLicense omsCerCancellateLicense);


	/**
	 * <b>功能描述: 部领导审批</b>
	 * @Param: [omsCerCancellateLicense]
	 * @Return: org.apache.ibatis.annotations.Result
	 * @Author: luoshuai
	 * @Date: 2020/8/10 16:48
	 */
	void updateCerCancellateLicenseApprovalMinister(OmsCerCancellateLicense omsCerCancellateLicense);


	/**
	 * <b>功能描述: 完成注销(公安厅意见)
	 *     分两个按钮，第一个通过后生成证照领取任务，
	 *     第二个领取任务完成后再次完成注销，状态改为已办结
	 * </b>
	 * @Param: [omsCerCancellateLicense]
	 * @Return: org.apache.ibatis.annotations.Result
	 * @Author: luoshuai
	 * @Date: 2020/8/10 11:48
	 */
	void updateCerCancellateLicenseApprovalComplete(OmsCerCancellateLicense omsCerCancellateLicense);


	/**
	 * <b>功能描述: 受理审批导出</b>
	 * @Param: [idList,omsCerCancellateLicense]
	 * @Return: org.apache.ibatis.annotations.Result
	 * @Author: luoshuai
	 * @Date: 2020/8/10 11:48
	 */
	void getCerCancellateLicenseApprovalOut(List<String> idList, OmsCerCancellateLicense omsCerCancellateLicense, HttpServletResponse response);


	/**
	 * <b>功能描述: 注销证照受理申请--下一步
	 *      在此处进行判断是自行注销且材料通过的不用处领导审批，直接将证照状态改为注销，申请状态改为已办结
	 * </b>
	 * @Param: [omsCerCancellateLicense]
	 * @Return: org.apache.ibatis.annotations.Result
	 * @Author: luoshuai
	 * @Date: 2020/8/7 16:43
	 */
	void updateCerCancellateLicenseAcceptanceNext(OmsCerCancellateLicense omsCerCancellateLicense);

}
