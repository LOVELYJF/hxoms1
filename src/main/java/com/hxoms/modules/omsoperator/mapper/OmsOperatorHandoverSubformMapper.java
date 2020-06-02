package com.hxoms.modules.omsoperator.mapper;

import com.hxoms.modules.omsoperator.entity.OmsOperatorHandoverSubform;

public interface OmsOperatorHandoverSubformMapper {
    int deleteByPrimaryKey(String id);

    int insert(OmsOperatorHandoverSubform record);

    int insertSelective(OmsOperatorHandoverSubform record);

    OmsOperatorHandoverSubform selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(OmsOperatorHandoverSubform record);

    int updateByPrimaryKey(OmsOperatorHandoverSubform record);
}