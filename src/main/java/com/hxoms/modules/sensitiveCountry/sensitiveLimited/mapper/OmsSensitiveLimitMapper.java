package com.hxoms.modules.sensitiveCountry.sensitiveLimited.mapper;

import com.hxoms.modules.sensitiveCountry.sensitiveLimited.entity.OmsSensitiveLimit;

import java.util.List;
import java.util.Map;


public interface OmsSensitiveLimitMapper {

    /**
     * <b>查询敏感性地区的父级</b>
     * @param map
     * @return
     */
    List<OmsSensitiveLimit> selectOmsSensitiveLimit(Map<String, Object> map);

    /**
     * <b>查询限制性内容</b>
     * @param map
     * @return
     */
    List<Map<String,String>> selectOmsSensitive(Map<String,Object> map);

    /**
     * <b>添加限制性内容</b>
     * @param map
     * @return
     */
    int addSensitiveLimit(Map<String, Object> map);

    /**
     * <b>删除敏感性国家地区内容</b>
     * @param map
     * @return
     */
    int deleteSensitiveLimit(Map<String, Object> map);


    /**
     * <b>功能描述: 查询限制性国家</b>
     * @Param: [sensitiveLimitId]
     * @Return: java.util.List<java.lang.Integer>
     * @Author: luoshuai
     * @Date: 2020/7/14 20:17
     */
	List<Integer> getSensitiveMaintain(String sensitiveLimitId);


	/**
	 * <b>功能描述: 删除当前敏感信息的国家信息</b>
	 * @Param: [sensitiveLimitId]
	 * @Return: void
	 * @Author: luoshuai
	 * @Date: 2020/9/12 13:52
	 */
    void updateSensitiveMaintain(String sensitiveLimitId);


    /**
     * <b>功能描述: 删除敏感信息上的国家</b>
     * @Param: [map]
     * @Return: void
     * @Author: luoshuai
     * @Date: 2020/9/12 14:20
     */
	void updateSensitiveMaintainToDelete(Map<String, Object> map);
}
