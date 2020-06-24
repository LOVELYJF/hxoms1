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
}
