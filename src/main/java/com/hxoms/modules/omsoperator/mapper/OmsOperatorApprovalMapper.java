package com.hxoms.modules.omsoperator.mapper;

import com.hxoms.modules.omsoperator.entity.OmsOperatorApproval;

public interface OmsOperatorApprovalMapper {
    int deleteByPrimaryKey(String id);

    int insert(OmsOperatorApproval record);

    int insertSelective(OmsOperatorApproval record);

    OmsOperatorApproval selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(OmsOperatorApproval record);

    int updateByPrimaryKey(OmsOperatorApproval record);

    OmsOperatorApproval selectByUserId(String userId);


}