package com.hxoms.modules.omsoperator.mapper;

import com.hxoms.modules.omsoperator.entity.OmsOperatorHandover;

import java.util.LinkedHashMap;
import java.util.List;

public interface OmsOperatorHandoverMapper {
    int deleteByPrimaryKey(String id);

    int insert(OmsOperatorHandover record);

    int insertSelective(OmsOperatorHandover record);

    OmsOperatorHandover selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(OmsOperatorHandover record);

    int updateByPrimaryKey(OmsOperatorHandover record);

    List<LinkedHashMap<String, Object>> selectByUserId(String userId);
}