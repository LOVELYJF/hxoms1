package com.hxoms.modules.omsoperator.mapper;

import com.hxoms.modules.omsoperator.entity.OmsOperatorApproval;
import com.hxoms.modules.privateabroad.entity.CountStatusResult;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;
import java.util.List;

public interface OmsOperatorApprovalMapper {
    int deleteByPrimaryKey(String id);

    int insert(OmsOperatorApproval record);

    int insertSelective(OmsOperatorApproval record);

    OmsOperatorApproval selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(OmsOperatorApproval record);

    int updateByPrimaryKey(OmsOperatorApproval record);

    List<OmsOperatorApproval> selectByUserId(String userId);
    /**
     * 功能描述: <br>
     * 〈经办人——基本数据流程统计〉
     * @Param: []
     * @Return: java.util.List<com.hxoms.modules.privateabroad.entity.CountStatusResult>
     * @Author: 李逍遥
     * @Date: 2020/7/17 15:37
     */
    List<CountStatusResult> selectCountStatus(@Param("orgId") String orgId);


    /**
     * 功能描述: <br>
     * 〈经办人交接流程统计〉
     * @Param: [orgId]
     * @Return: java.util.List<com.hxoms.modules.privateabroad.entity.CountStatusResult>
     * @Author: 李逍遥
     * @Date: 2020/7/20 18:10
     */
    List<CountStatusResult> selectCountStatusByHandover(String orgId);

    /**
     * 功能描述: <br>
     * 〈经办人交接流程页面展示〉
     * @Param: [orgId]
     * @Return: java.util.List<java.util.LinkedHashMap<java.lang.String,java.lang.Object>>
     * @Author: 李逍遥
     * @Date: 2020/7/23 14:56
     */
    List<LinkedHashMap<String, Object>> getAllOperatorHandoverByOrgId(@Param("orgId") String orgId);
}