package com.hxoms.modules.sensitiveCountry.educationData.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.pagehelper.PageInfo;
import com.hxoms.common.utils.FileUploadTool;
import com.hxoms.common.utils.Result;
import com.hxoms.common.utils.UserInfoUtil;
import com.hxoms.modules.sensitiveCountry.educationData.entity.FileEntity;
import com.hxoms.modules.sensitiveCountry.educationData.entity.OmsSensitiveEducateData;
import com.hxoms.modules.sensitiveCountry.educationData.service.OmsEducationDataService;
import com.hxoms.modules.sensitiveCountry.sensitiveLimited.entity.OmsSensitiveLimit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * <b>行前教育资料模块控制器</b>
 * @author luoshuai
 * @date 2020/9/14  10:45
 */
@RestController
@RequestMapping("/educationData")
public class OmsEducationDataController {
	@Autowired
	private FileUploadTool fileUploadTool;


	@Autowired
	private OmsEducationDataService omsEducationDataService;
	/**
	 * <b>功能描述: 查询敏感性信息（左侧机构树）</b>
	 * @Param: []
	 * @Return: com.hxoms.common.utils.Result
	 * @Author: luoshuai
	 * @Date: 2020/9/14 8:59
	 */
	@GetMapping("/getSensitiveCountryForEducation")
	public Result getSensitiveCountryForEducation(){
		List<OmsSensitiveLimit> list = omsEducationDataService.getSensitiveCountryForEducation();
		return Result.success(list);
	}


	/**
	 * <b>功能描述: 查询国家对应的教育资料</b>
	 * @Param: []
	 * @Return: com.hxoms.common.utils.Result
	 * @Author: luoshuai
	 * @Date: 2020/9/15 8:59
	 */
	@GetMapping("/getSensitiveCountryEducateData")
	public Result getSensitiveCountryEducateData(Page<OmsSensitiveEducateData> page, String countryId){
		PageInfo<OmsSensitiveEducateData> list = omsEducationDataService.getSensitiveCountryEducateData(page,countryId);
		return Result.success(list);
	}


	/**
	 * <b>功能描述: 删除文件</b>
	 * @Param: [omsSensitiveEducateData]
	 * @Return: com.hxoms.common.utils.Result
	 * @Author: luoshuai
	 * @Date: 2020/9/15 8:59
	 */
	@PostMapping("/deleteSensitiveCountryEducateData")
	public Result deleteSensitiveCountryEducateData(OmsSensitiveEducateData omsSensitiveEducateData){
		String result = omsEducationDataService.deleteSensitiveCountryEducateData(omsSensitiveEducateData);
		return Result.success(result);
	}



	/**
	 * <b>功能描述: 文件上传</b>
	 * @Param: [multipartFile, request, map, countryId]
	 * @Return: com.hxoms.common.utils.Result
	 * @Author: luoshuai
	 * @Date: 2020/9/14 20:53
	 */
	@PostMapping(value = "/uploadFile")
	public Result upload(@RequestParam(value = "file", required = false) MultipartFile multipartFile,
	                     HttpServletRequest request, Map map, String countryId) {
		String a = UserInfoUtil.getUserInfo().getUserName();
		String message = "";
		FileEntity entity = new FileEntity();
		try {
			entity = fileUploadTool.createFile(multipartFile, request,countryId);
			if (entity != null) {
				message = "上传成功";
				map.put("entity", entity);
				map.put("result", message);
			} else {
				message = "上传失败";
				map.put("result", message);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Result.success(map);
	}



	/**
	 * <b>功能描述: 下载文件</b>
	 * @Param: []
	 * @Return: com.hxoms.common.utils.Result
	 * @Author: luoshuai
	 * @Date: 2020/9/14 8:59
	 */
	@GetMapping("/getSensitiveCountryEducateOut")
	public void getSensitiveCountryEducateOut(String filePath,HttpServletResponse response) throws Exception{
		omsEducationDataService.getSensitiveCountryEducateOut(filePath,response);
	}


	/**
	 * 批量下载
	 * @param filepathList (格式xx,xx2,xx3） 文件路径
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "downloadPlanFile")
	public void downloadPlanFile(@RequestBody List<String> filepathList,
	                             HttpServletRequest request,HttpServletResponse response){
		omsEducationDataService.downloadPlanFile(filepathList,request,response);
	}
}
