package com.hxoms.modules.sensitiveCountry.educationData.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hxoms.common.exception.CustomMessageException;
import com.hxoms.common.utils.FileUploadTool;
import com.hxoms.common.utils.UUIDGenerator;
import com.hxoms.common.utils.UtilDateTime;
import com.hxoms.modules.country.entity.Country;
import com.hxoms.modules.country.mapper.CountryMapper;
import com.hxoms.modules.sensitiveCountry.educationData.entity.OmsSensitiveEducateData;
import com.hxoms.modules.sensitiveCountry.educationData.mapper.OmsSensitiveEducateDataMapper;
import com.hxoms.modules.sensitiveCountry.educationData.service.OmsEducationDataService;
import com.hxoms.modules.sensitiveCountry.sensitiveLimited.entity.OmsSensitiveLimit;
import com.hxoms.modules.sensitiveCountry.sensitiveLimited.mapper.OmsSensitiveLimitMapper;
import javafx.scene.chart.XYChart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * <b>行前教育资料模块控制器</b>
 * @author luoshuai
 * @date 2020/9/14  10:45
 */
@Service
public class OmsEducationDataServiceImpl implements OmsEducationDataService {

	@Autowired
	private OmsSensitiveLimitMapper omsSensitiveLimitMapper;
	@Autowired
	private CountryMapper countryMapper;
	@Autowired
	private OmsSensitiveEducateDataMapper omsSensitiveEducateDataMapper;
	@Autowired
	private FileUploadTool fileUploadTool;
	/**
	 * <b>功能描述: 查询敏感性信息（左侧机构树）</b>
	 * @Param: []
	 * @Return: com.hxoms.common.utils.Result
	 * @Author: luoshuai
	 * @Date: 2020/9/14 8:59
	 */
	public List<OmsSensitiveLimit> getSensitiveCountryForEducation() {
		//查询父节点
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("dictCode", "MGGJ");
		List<OmsSensitiveLimit> list = omsSensitiveLimitMapper.selectOmsSensitiveLimit(map);

		for(OmsSensitiveLimit omsSensitiveLimit : list){
			List<Country> countryList = countryMapper.selectCountryInfo(omsSensitiveLimit.getId());
			if(countryList != null && countryList.size() > 0){
				omsSensitiveLimit.setList(countryList);
			}
		}
		OmsSensitiveLimit omsSensitiveLimit = new OmsSensitiveLimit();
		omsSensitiveLimit.setId(UUIDGenerator.getPrimaryKey());
		omsSensitiveLimit.setItemName("已撤销国家");
		omsSensitiveLimit.setShortName("已撤销国家");
		//查询已撤销的国家信息
		List<Country> list1 = countryMapper.selectOmsSensitiveCountry();
		omsSensitiveLimit.setList(list1);
		list.add(omsSensitiveLimit);
		return list;
	}


	/**
	 * <b>功能描述: 查询国家对应的教育资料</b>
	 * @Param: []
	 * @Return: com.hxoms.common.utils.Result
	 * @Author: luoshuai
	 * @Date: 2020/9/15 8:59
	 */
	public List<OmsSensitiveEducateData> getSensitiveCountryEducateData(String countryId) {
		QueryWrapper<OmsSensitiveEducateData> queryWrapper = new QueryWrapper<OmsSensitiveEducateData>();
		if (countryId == null || countryId == "") {
			throw new CustomMessageException("参数错误");
		}
		queryWrapper.eq("COUNTRY_ID",countryId)
					.orderByDesc("UPLOAD_TIME");
		List<OmsSensitiveEducateData> list = omsSensitiveEducateDataMapper.selectList(queryWrapper);
		return list;
	}



	/**
	 * <b>功能描述: 删除文件</b>
	 * @Param: [omsSensitiveEducateData]
	 * @Return: com.hxoms.common.utils.Result
	 * @Author: luoshuai
	 * @Date: 2020/9/15 8:59
	 */
	public String deleteSensitiveCountryEducateData(OmsSensitiveEducateData omsSensitiveEducateData) {
		if(omsSensitiveEducateData.getId() == null || omsSensitiveEducateData.getId() == "" ||
				omsSensitiveEducateData.getFilePath() == null || omsSensitiveEducateData.getFilePath() == ""){
			throw new CustomMessageException("参数错误");
		}
		int count = omsSensitiveEducateDataMapper.deleteById(omsSensitiveEducateData.getId());
		String result = null;
		if(count > 0){
			result = fileUploadTool.deleteFile(omsSensitiveEducateData.getFilePath());
		}
		return result;
	}



	/**
	 * <b>功能描述: 下载文件</b>
	 * @Param: [filePath, response]
	 * @Return: javax.servlet.http.HttpServletResponse
	 * @Author: luoshuai
	 * @Date: 2020/9/15 17:19
	 */
	public HttpServletResponse getSensitiveCountryEducateOut(String filePath, HttpServletResponse response) throws Exception{
		HttpServletResponse response1 = fileUploadTool.downLoad(filePath, response);
		return response1;
	}


	/**
	 * 批量下载
	 * @param filepathList (格式xx,xx2,xx3） 文件路径
	 * @param request
	 * @param response
	 */
	public HttpServletResponse downloadPlanFile(List<String> filepathList, HttpServletRequest request, HttpServletResponse response) {
		HttpServletResponse response1 = fileUploadTool.downLoadBatch(filepathList,request,response);
		return response1;
	}

}
