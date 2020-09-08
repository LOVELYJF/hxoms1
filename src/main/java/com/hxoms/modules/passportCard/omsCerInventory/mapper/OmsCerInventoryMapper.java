package com.hxoms.modules.passportCard.omsCerInventory.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hxoms.modules.passportCard.omsCerInventory.entity.OmsCerInventory;

import java.util.List;
import java.util.Map;

public interface OmsCerInventoryMapper extends BaseMapper<OmsCerInventory> {


    /**
     * <b>功能描述: 证照机盘点查询保管状态前后不一样的证照</b>
     * @Param: [map]
     * @Return: java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     * @Author: luoshuai
     * @Date: 2020/8/19 17:23
     */
    List<Map<String, Object>> selectCerInventoryResultForCabinet(Map<String, Object> map);


	/**
	 * <b>功能描述: 盘点情况统计（证照机）返回前后统计数量</b>
	 * @Param: [omsCerInventory]
	 * @Return: com.hxoms.common.utils.Result
	 * @Author: luoshuai
	 * @Date: 2020/8/20 14:38
	 */
	Map<String, Integer> getCerInventoryStatisticsNum(Map<String, Object> map);


	/**
	 * <b>功能描述: 总体盘点结果统计查询</b>
	 * @Param: [omsCerInventory]
	 * @Return: com.hxoms.common.utils.Result
	 * @Author: luoshuai
	 * @Date: 2020/8/20 14:38
	 */
	List<Map<String, Object>> GetCerInventoryResult(Map<String, Object> map);


	/**
	 * <b>功能描述: 查询证照主键集合</b>
	 * @Param: [map]
	 * @Return: java.util.List<java.lang.String>
	 * @Author: luoshuai
	 * @Date: 2020/9/7 15:59
	 */
	List<String> selectOmsCerIdList(Map<String, Object> map);
}