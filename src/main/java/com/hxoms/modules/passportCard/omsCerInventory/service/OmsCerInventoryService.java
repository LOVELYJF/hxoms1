package com.hxoms.modules.passportCard.omsCerInventory.service;

import com.hxoms.modules.passportCard.counterGet.entity.OmsCerGetTask;
import com.hxoms.modules.passportCard.initialise.entity.CfCertificate;
import com.hxoms.modules.passportCard.omsCerInventory.entity.OmsCerInventory;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * <b>功能描述: 证照盘点业务层接口</b>
 * @Param:
 * @Return:
 * @Author: luoshuai
 * @Date: 2020/8/19 11:47
 */
public interface OmsCerInventoryService {

	/**
	 * <b>功能描述: （开始盘点）</b>
	 * @Param: [omsCerInventory]
	 * @Return: com.hxoms.common.utils.Result
	 * @Author: luoshuai
	 * @Date: 2020/8/19 14:38
	 */
	void insertCerInventoryInfoForCabinet(OmsCerInventory omsCerInventory);


	/**
	 * <b>功能描述: 统计盘点结果（证照机）证照前后保存状态不一致的</b>
	 * @Param: [omsCerInventory]
	 * @Return: com.hxoms.common.utils.Result
	 * @Author: luoshuai
	 * @Date: 2020/8/19 14:38
	 */
	Map<String, Object> GetCerInventoryResultForCabinet(OmsCerInventory omsCerInventory);


	/**
	 * <b>功能描述: 保存盘点结果（证照机）</b>
	 * @Param: [list]
	 * @Return: com.hxoms.common.utils.Result
	 * @Author: luoshuai
	 * @Date: 2020/8/19 14:38
	 */
	void updateCerInventoryResultForCabinet(List<OmsCerInventory> list);


	/**
	 * <b>功能描述: 导出盘点结果（证照机）</b>
	 * @Param: [omsCerInventory]
	 * @Return: com.hxoms.common.utils.Result
	 * @Author: luoshuai
	 * @Date: 2020/8/19 14:38
	 */
	void getCerInventoryResultForCabinetOut(OmsCerInventory omsCerInventory, HttpServletResponse response);



	/**
	 * <b>功能描述: 开始盘点（柜台）</b>
	 * @Param: [omsCerInventory]
	 * @Return: com.hxoms.common.utils.Result
	 * @Author: luoshuai
	 * @Date: 2020/8/20 14:38
	 */
	List<Map<String, Object>> insertCerInventoryInfoForCounter(OmsCerInventory omsCerInventory);


	/**
	 * <b>功能描述: 保存盘点结果（柜台）</b>
	 * @Param: [list]
	 * @Return: com.hxoms.common.utils.Result
	 * @Author: luoshuai
	 * @Date: 2020/8/20 14:38
	 */
	Map<String, Integer> updateCerInventoryResultForCounter(List<OmsCerInventory> list);


	/**
	 * <b>功能描述: 导出盘点结果（柜台）</b>
	 * @Param: [omsCerInventory]
	 * @Return: com.hxoms.common.utils.Result
	 * @Author: luoshuai
	 * @Date: 2020/8/20 14:38
	 */
	void getCerInventoryResultForCounterOut(OmsCerInventory omsCerInventory, HttpServletResponse response);


	/**
	 * <b>功能描述: 总体盘点结果统计查询</b>
	 * @Param: [omsCerInventory]
	 * @Return: com.hxoms.common.utils.Result
	 * @Author: luoshuai
	 * @Date: 2020/8/20 14:38
	 */
	Map<String,Object> GetCerInventoryResult(List<String> list,OmsCerInventory omsCerInventory);


	/**
	 * <b>功能描述: 总体查询导出盘点结果</b>
	 * @Param: [omsCerInventory]
	 * @Return: com.hxoms.common.utils.Result
	 * @Author: luoshuai
	 * @Date: 2020/8/20 14:38
	 */
	void getCerInventoryResultOut(List<String> list, OmsCerInventory omsCerInventory, HttpServletResponse response);


	/**
	 * <b>功能描述: 查询所有的柜子集合</b>
	 * @Param: []
	 * @Return: com.hxoms.common.utils.Result
	 * @Author: luoshuai
	 * @Date: 2020/8/20 14:38
	 */
	List<String> getCerLicenseMachine();


	/**
	 * <b>功能描述: 存取记录</b>
	 * @Param: [cfCertificate]
	 * @Return: com.hxoms.common.utils.Result
	 * @Author: luoshuai
	 * @Date: 2020/8/20 14:38
	 */
	List<Map<String, Object>> getCerAccessRecord(CfCertificate cfCertificate);


	/**
	 * <b>功能描述: 补领取记录</b>
	 * @Param: [omsCerGetTask]
	 * @Return: com.hxoms.common.utils.Result
	 * @Author: luoshuai
	 * @Date: 2020/8/24 14:38
	 */
	void saveRepairCollectionRecord(OmsCerGetTask omsCerGetTask);
}
