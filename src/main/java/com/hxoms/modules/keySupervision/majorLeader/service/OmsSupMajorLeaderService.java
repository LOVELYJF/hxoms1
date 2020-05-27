package com.hxoms.modules.keySupervision.majorLeader.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hxoms.modules.keySupervision.majorLeader.entity.OmsSupMajorLeader;
import com.hxoms.modules.keySupervision.nakedOfficial.entity.OmsSupNakedSign;

import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.util.List;

/**
 * <b>主要领导信息业务层接口</b>
 * @author luoshuai
 * @date 2020/5/10 18:29
 */
public interface OmsSupMajorLeaderService {

	/**
	 * <b>查询主要领导信息</b>
	 * @param omsSupMajorLeader
	 * @param idList
	 * @param page
	 * @return
	 */
	Page<OmsSupMajorLeader> getMajorLeader(Page<OmsSupMajorLeader> page,List<String> idList, OmsSupMajorLeader omsSupMajorLeader);


	/**
	 * <b>添加主要领导信息</b>
	 * @param omsSupMajorLeader
	 * @return
	 */
	void addMajorLeader(OmsSupMajorLeader omsSupMajorLeader);


	/**
	 * <b>取消主要领导标识</b>
	 * @param omsSupMajorLeader
	 * @return
	 */
	void removeMajorLeader(OmsSupMajorLeader omsSupMajorLeader);


	/**
	 * <b>导出主要领导信息</b>
	 * @param list
	 * @return
	 */
	void getMajorLeaderInfoOut(List<OmsSupMajorLeader> list, HttpServletResponse response);


	/**
	 * <b>自动识别主要领导（每个单位前两名）</b>
	 * @return
	 */
	void addMajorLeaderAuto();
}
