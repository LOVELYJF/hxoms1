package com.hxoms.modules.keySupervision.nakedOfficial.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hxoms.modules.keySupervision.nakedOfficial.entity.OmsSupNakedSign;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * <b>裸官信息业务层接口</b>
 * @author luoshuai
 * @date 2020/5/8 22:21
 */
public interface OmsSupNakedSignService extends IService<OmsSupNakedSign> {

	/**
	 * <b>查询裸官信息</b>
	 * @param omsSupNakedSign
	 * @param idList
	 * @param page
	 * @return
	 */
	Page<OmsSupNakedSign> getNakedOfficialList(Page<OmsSupNakedSign> page, OmsSupNakedSign omsSupNakedSign, List<String> idList);


	/**
	 * <b>添加裸官信息</b>
	 * @param omsSupNakedSign
	 * @return
	 */
	void addOmsNaked(OmsSupNakedSign omsSupNakedSign);


	/**
	 * <b>修改裸官信息</b>
	 * @param omsSupNakedSign
	 * @return
	 */
	void updateOmsNaked(OmsSupNakedSign omsSupNakedSign);


	/**
	 * <b>取消裸官标识</b>
	 * @param omsSupNakedSign
	 * @return
	 */
	void removeOmsNaked(OmsSupNakedSign omsSupNakedSign);


	/**
	 * <b>导出裸官信息</b>
	 * @param list
	 * @return
	 */
	void getNakedOfficialOut(List<OmsSupNakedSign> list, HttpServletResponse response);
}