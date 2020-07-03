package com.hxoms.modules.keySupervision.disciplinaryAction.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hxoms.modules.keySupervision.disciplinaryAction.entity.OmsSupDisciplinary;
import com.hxoms.modules.keySupervision.majorLeader.entity.OmsSupMajorLeader;
import com.hxoms.support.sysdict.entity.SysDictItem;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * <b>处分信息模块业务层接口</b>
 * @author luoshuai
 * @date 2020/5/15 11:00
 */
public interface OmsSupDisciplinaryService {

	/**
	 * <b>查询处分信息</b>
	 * @param idList
	 * @param omsSupDisciplinary
	 * @param page
	 * @return
	 */
	Page<OmsSupDisciplinary> getDisciplinaryInfo(Page<OmsSupDisciplinary> page, OmsSupDisciplinary omsSupDisciplinary, List<String> idList);


	/**
	 * <b>增加处分信息</b>
	 * @param omsSupDisciplinary
	 * @return
	 */
	void addDisciplinaryInfo(OmsSupDisciplinary omsSupDisciplinary);


	/**
	 * <b>查询处分类型</b>
	 * @return
	 */
	List<SysDictItem> getDisciplinaryActionType();


	/**
	 * <b>修改处分信息</b>
	 * @param omsSupDisciplinary
	 * @return
	 */
	void updateDisciplinaryInfo(OmsSupDisciplinary omsSupDisciplinary);


	/**
	 * <b>删除处分信息</b>
	 * @param omsSupDisciplinary
	 * @return
	 */
	void removeDisciplinaryInfo(OmsSupDisciplinary omsSupDisciplinary);


	/**
	 * <b>导出处分信息</b>
	 * @param idList
	 * @param omsSupDisciplinary
	 * @param response
	 * @return
	 */
	void getDisciplinaryInfoOut(List<String> idList, OmsSupDisciplinary omsSupDisciplinary, HttpServletResponse response);
}
