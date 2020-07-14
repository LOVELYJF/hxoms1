package com.hxoms.modules.omsoperator.mapper;

import com.hxoms.modules.omsoperator.entity.OmsOperatorHandover;
import com.hxoms.modules.omsoperator.entity.OmsOperatorHandoverSubformVO;
import com.hxoms.modules.omsoperator.entity.OmsPriDelayVO;
import com.hxoms.modules.privateabroad.entity.OmsPriApplyVO;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;
import java.util.List;

public interface OmsOperatorHandoverMapper {
    int deleteByPrimaryKey(String id);

    int insert(OmsOperatorHandover record);

    int insertSelective(OmsOperatorHandover record);

    OmsOperatorHandover selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(OmsOperatorHandover record);

    int updateByPrimaryKey(OmsOperatorHandover record);

    List<OmsOperatorHandoverSubformVO> selectByUserId(@Param("userId") String userId);

    /** 获取该经办人未办结的因私出国申请列表*/
    List<OmsPriApplyVO> selectOmsPriApplyByStatusAndName(@Param("name") String name, @Param("status") int status);

    /** 获取该经办人未办结状态的延期回国列表*/
    List<OmsPriDelayVO> selectOmsPriDelayApplyByStatusAndName(@Param("userId") String userId, @Param("status") int status);

    /** 管理人员确定列表*/
    List<OmsOperatorHandoverSubformVO> getAllOperatorHandoverByOrgid(@Param("orgId") String orgId);

}