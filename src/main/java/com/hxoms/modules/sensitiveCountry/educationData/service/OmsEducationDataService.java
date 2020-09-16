package com.hxoms.modules.sensitiveCountry.educationData.service;

import com.hxoms.modules.sensitiveCountry.educationData.entity.OmsSensitiveEducateData;
import com.hxoms.modules.sensitiveCountry.sensitiveLimited.entity.OmsSensitiveLimit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * <b>行前教育资料模块控制器</b>
 * @author luoshuai
 * @date 2020/9/14  10:45
 */
public interface OmsEducationDataService {

	/**
	 * <b>功能描述: 查询敏感性信息（左侧机构树）</b>
	 * @Param: []
	 * @Return: com.hxoms.common.utils.Result
	 * @Author: luoshuai
	 * @Date: 2020/9/14 8:59
	 */
	List<OmsSensitiveLimit> getSensitiveCountryForEducation();


	/**
	 * <b>功能描述: 查询国家对应的教育资料</b>
	 * @Param: []
	 * @Return: com.hxoms.common.utils.Result
	 * @Author: luoshuai
	 * @Date: 2020/9/15 8:59
	 */
	List<OmsSensitiveEducateData> getSensitiveCountryEducateData(String countryId);


	/**
	 * <b>功能描述: 删除文件</b>
	 * @Param: [omsSensitiveEducateData]
	 * @Return: com.hxoms.common.utils.Result
	 * @Author: luoshuai
	 * @Date: 2020/9/15 8:59
	 */
	String deleteSensitiveCountryEducateData(OmsSensitiveEducateData omsSensitiveEducateData);


	/**
	 * <b>功能描述: 下载文件</b>
	 * @Param: [filePath, response]
	 * @Return: javax.servlet.http.HttpServletResponse
	 * @Author: luoshuai
	 * @Date: 2020/9/15 17:19
	 */
	HttpServletResponse getSensitiveCountryEducateOut(String filePath, HttpServletResponse response) throws Exception;


	/**
	 * 批量下载
	 * @param filepathList (格式xx,xx2,xx3） 文件路径
	 * @param request
	 * @param response
	 */
	HttpServletResponse downloadPlanFile(List<String> filepathList, HttpServletRequest request, HttpServletResponse response);
}
