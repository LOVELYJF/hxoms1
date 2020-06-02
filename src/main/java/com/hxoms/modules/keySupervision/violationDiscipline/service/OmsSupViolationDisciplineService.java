package com.hxoms.modules.keySupervision.violationDiscipline.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hxoms.modules.keySupervision.violationDiscipline.entity.OmsSupViolationDiscipline;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * <b>违反外事纪律模块业务层接口</b>
 * @author luoshuai
 * @date 2020/5/18 14:53
 */
public interface OmsSupViolationDisciplineService {

	/**
	 * <b>查询违反外事纪律人员信息</b>
	 * @param idList
	 * @param omsSupViolationDiscipline
	 * @param page
	 * @return
	 */
	Page<OmsSupViolationDiscipline> getViolationDisciplineInfo(Page<OmsSupViolationDiscipline> page,
	                                                           OmsSupViolationDiscipline omsSupViolationDiscipline, List<String> idList);


	/**
	 * <b>新增违反外事纪律人员信息</b>
	 * @param omsSupViolationDiscipline
	 * @return
	 */
	void addViolationDisciplineInfo(OmsSupViolationDiscipline omsSupViolationDiscipline);


	/**
	 * <b>修改违反外事人员信息</b>
	 * @param omsSupViolationDiscipline
	 * @return
	 */
	void updateViolationDisciplineInfo(OmsSupViolationDiscipline omsSupViolationDiscipline);


	/**
	 * <b>删除违反外事人员信息</b>
	 * @param id
	 * @return
	 */
	void deleteViolationDisciplineInfo(String id);


	/**
	 * <b>导出违反外事纪律人员信息</b>
	 * @param list
	 * @return
	 */
	void getViolationDisciplineInfoOut(List<OmsSupViolationDiscipline> list, HttpServletResponse response);
}
