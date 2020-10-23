package com.hxoms.modules.passportCard.omsCerTransferExpiredLicense.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hxoms.modules.passportCard.initialise.entity.CfCertificate;
import com.hxoms.modules.passportCard.printGetQrCode.entity.parameterEntity.QrCode;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.Map;
/**
 * <b>功能描述: 过期证照转存业务层接口</b>
 * @Param:
 * @Return:
 * @Author: luoshuai
 * @Date: 2020/8/5 10:24
 */
public interface OmsCerTransferExpiredLicenseService {

	/**
	 * <b>功能描述: 查询过期证照信息</b>
	 * @Param: [page,list,expiredQueryStartTime,expiredQueryEndTime,cfCertificate]
	 * @Return: com.hxoms.common.utils.Result
	 * @Author: luoshuai
	 * @Date: 2020/8/17 14:16
	 */
	Page<Map<String, Object>> getTransferExpiredLicenseInfo(Page<Map<String,Object>> page, List<String> list, List<String> idList,Date expiredQueryStartTime, Date expiredQueryEndTime, CfCertificate cfCertificate);


	/**
	 * <b>功能描述: 过期证照转存（导出）</b>
	 * @Param: [list,expiredQueryStartTime,expiredQueryEndTime,cfCertificate]
	 * @Return: com.hxoms.common.utils.Result
	 * @Author: luoshuai
	 * @Date: 2020/8/18 14:16
	 */
	void getTransferExpiredLicenseOut(List<String> list, List<String> idList,Date expiredQueryStartTime, Date expiredQueryEndTime, CfCertificate cfCertificate, HttpServletResponse response);


	/**
	 * <b>功能描述: 转存</b>
	 * @Param: [list]
	 * @Return: com.hxoms.common.utils.Result
	 * @Author: luoshuai
	 * @Date: 2020/8/18 14:16
	 */
	List<CfCertificate> getTransferExpiredLicenseDeposit(List<CfCertificate> list);


	/**
	 * <b>功能描述: 转存（保存转存信息），修改证照保存状态</b>
	 * @Param: [list]
	 * @Return: com.hxoms.common.utils.Result
	 * @Author: luoshuai
	 * @Date: 2020/8/18 14:16
	 */
	void getTransferExpiredLicenseSave(List<CfCertificate> list);


	/**
	 * <b>功能描述: 打印二维码</b>
	 * @Param: [list]
	 * @Return: com.hxoms.common.utils.Result
	 * @Author: luoshuai
	 * @Date: 2020/9/11 14:16
	 */
	QrCode getTransferExpiredLicenseQrCode(List<CfCertificate> list);
}
