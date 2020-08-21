package com.hxoms.modules.passportCard.omsCerTransferOutLicense.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hxoms.modules.passportCard.omsCerTransferOutLicense.entity.OmsCerTransferOutLicense;

import java.util.List;
import java.util.Map;

/**
 * <b>功能描述: 转出证照业务层接口</b>
 * @Param:
 * @Return:
 * @Author: luoshuai
 * @Date: 2020/8/10 16:24
 */
public interface OmsCerTransferOutLicenseService {

	/**
	 * <b>功能描述: 查询转出证照申请信息</b>
	 * @Param: [page,omsCerTransferOutLicense]
	 * @Return: com.hxoms.common.utils.Result
	 * @Author: luoshuai
	 * @Date: 2020/8/10 15:07
	 */
	Page<Map<String, Object>> selectTransferOutLicenseInfo(Page<Map<String,Object>> page ,OmsCerTransferOutLicense omsCerTransferOutLicense);


	/**
	 * <b>功能描述: 查询转出证照申请的原单位信息</b>
	 * @Param: []
	 * @Return: com.hxoms.common.utils.Result
	 * @Author: luoshuai
	 * @Date: 2020/8/10 15:07
	 */
	List<OmsCerTransferOutLicense> getTransferOutWorkUnitInfo();


	/**
	 * <b>功能描述: 查询年份对应的批次号结构树</b>
	 * @Param: []
	 * @Return: com.hxoms.common.utils.Result
	 * @Author: luoshuai
	 * @Date: 2020/8/17 15:07
	 */
	List<OmsCerTransferOutLicense> getBatchByYear();


	/**
	 * <b>功能描述: 打印后将年月日作为批次号，并记录移交人接手人</b>
	 * @Param: [list,omsCerTransferOutLicense]
	 * @Return: com.hxoms.common.utils.Result
	 * @Author: luoshuai
	 * @Date: 2020/8/10 15:07
	 */
	void updateTransferOutRecord(List<String> list, OmsCerTransferOutLicense omsCerTransferOutLicense);
}
