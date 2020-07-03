package com.hxoms.modules.keySupervision.dismissed.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hxoms.modules.keySupervision.dismissed.entity.OmsSupDismissed;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * <b> 免职撤职模块业务层接口</b>
 * @author luoshuai
 * @date 2020/5/18 17:33
 */
public interface OmsSupDismissedService {

	/**
	 * <b>查询免职撤职信息</b>
	 * @param idList
	 * @param omsSupDismissed
	 * @param page
	 * @return
	 */
	Page<OmsSupDismissed> getDismissedInfo(Page<OmsSupDismissed> page, OmsSupDismissed omsSupDismissed, List<String> idList);


	/**
	 * <b>增加免职撤职信息</b>
	 * @param omsSupDismissed
	 * @return
	 */
	void addDismissedInfo(OmsSupDismissed omsSupDismissed);


	/**
	 * <b>修改免职撤职信息</b>
	 * @param omsSupDismissed
	 * @return
	 */
	void updateDismissedInfo(OmsSupDismissed omsSupDismissed);


	/**
	 * <b>删除免职撤职信息</b>
	 * @param omsSupDismissed
	 * @return
	 */
	void removeDismissedInfo(OmsSupDismissed omsSupDismissed);


	/**
	 * <b>导出免职撤职人员信息</b>
	 * @param idList
	 * @param response
	 * @param omsSupDismissed
	 * @return
	 */
	void getDismissedInfoOut(List<String> idList, OmsSupDismissed omsSupDismissed, HttpServletResponse response);
}
