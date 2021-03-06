package com.hxoms.modules.keySupervision.caseInfo.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hxoms.modules.keySupervision.caseInfo.entity.OmsSupCaseInfo;
import com.hxoms.modules.keySupervision.majorLeader.entity.OmsSupMajorLeader;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * <b>立案信息业务层接口</b>
 * @author luoshuai
 * @date 2020/5/14 16:45
 */
public interface OmsSupCaseInfoService {

	/**
	 * <b>根据用户输入查询立案信息</b>
	 * @param idList
	 * @param omsSupCaseInfo
	 * @param page
	 * @return
	 */
	Page<OmsSupCaseInfo> getCasePersonInfo(Page<OmsSupCaseInfo> page,OmsSupCaseInfo omsSupCaseInfo, List<String> idList);


	/**
	 * <b>增加立案信息</b>
	 * @param omsSupCaseInfo
	 * @return
	 */
	String addCaseInfo(OmsSupCaseInfo omsSupCaseInfo);


	/**
	 * <b>保存并转到处分信息</b>
	 * @param omsSupCaseInfo
	 * @return
	 */
	void addToDisciplinary(OmsSupCaseInfo omsSupCaseInfo);


	/**
	 * <b保存修改的立案信息</b>
	 * @param omsSupCaseInfo
	 * @return
	 */
	void updateSaveCaseInfo(OmsSupCaseInfo omsSupCaseInfo);

	/**
	 * <b保存修改的立案信息并转到处分信息</b>
	 * @param omsSupCaseInfo
	 * @return
	 */
	void updateCaseInfoToDisciplinary(OmsSupCaseInfo omsSupCaseInfo);


	/**
	 * <b>删除立案信息</b>
	 * @param omsSupCaseInfo
	 * @return
	 */
	void removeCaseInfo(OmsSupCaseInfo omsSupCaseInfo);


	/**
	 * <b>导出立案信息</b>
	 * @param idList
	 * @param omsSupCaseInfo
	 * @param response
	 * @return
	 */
	void getCaseInfoOut(List<String> idList, OmsSupCaseInfo omsSupCaseInfo, HttpServletResponse response);

}
