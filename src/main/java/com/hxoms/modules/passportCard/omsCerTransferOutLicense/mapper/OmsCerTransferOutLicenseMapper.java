package com.hxoms.modules.passportCard.omsCerTransferOutLicense.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hxoms.modules.passportCard.omsCerTransferOutLicense.entity.OmsCerTransferOutLicense;

import java.util.List;
import java.util.Map;

public interface OmsCerTransferOutLicenseMapper extends BaseMapper<OmsCerTransferOutLicense> {


	/**
	 * <b>功能描述: 查询转出证照申请信息</b>
	 * @Param: [omsCerTransferOutLicense]
	 * @Return: com.hxoms.common.utils.Result
	 * @Author: luoshuai
	 * @Date: 2020/8/10 15:07
	 */
	List<Map<String, Object>> selectTransferOutLicenseInfo(Map<String, Object> map);


	/**
	 * <b>功能描述: 查询转出证照申请的原单位信息</b>
	 * @Param: []
	 * @Return: com.hxoms.common.utils.Result
	 * @Author: luoshuai
	 * @Date: 2020/8/10 15:07
	 */
	List<OmsCerTransferOutLicense> getTransferOutWorkUnitInfo();


	/**
	 * <b>功能描述: 根据年份批次号</b>
	 * @Param: []
	 * @Return: com.hxoms.common.utils.Result
	 * @Author: luoshuai
	 * @Date: 2020/8/17 15:07
	 */
	List<OmsCerTransferOutLicense> getBatchByYear(String year);


	/**
	 * <b>功能描述: 查询年份集合</b>
	 * @Param: []
	 * @Return: java.util.List<com.hxoms.modules.passportCard.omsCerTransferOutLicense.entity.OmsCerTransferOutLicense>
	 * @Author: luoshuai
	 * @Date: 2020/8/17 10:35
	 */
	List<OmsCerTransferOutLicense> selectYearList();
}