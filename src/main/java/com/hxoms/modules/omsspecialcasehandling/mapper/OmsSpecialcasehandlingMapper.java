package com.hxoms.modules.omsspecialcasehandling.mapper;

import com.hxoms.modules.omsspecialcasehandling.entity.OmsSpecialcasehandling;

import java.util.LinkedHashMap;
import java.util.List;

public interface OmsSpecialcasehandlingMapper {
    int deleteByPrimaryKey(String id);

    int insert(OmsSpecialcasehandling record);

    int insertSelective(OmsSpecialcasehandling record);

    OmsSpecialcasehandling selectByPrimaryKey(String name);

    int updateByPrimaryKeySelective(OmsSpecialcasehandling record);

    int updateByPrimaryKey(OmsSpecialcasehandling record);

    List<LinkedHashMap<String, Object>> getAllSpecialCase();
}