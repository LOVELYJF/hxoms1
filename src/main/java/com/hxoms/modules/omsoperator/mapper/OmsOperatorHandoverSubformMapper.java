package com.hxoms.modules.omsoperator.mapper;

import com.hxoms.modules.omsoperator.entity.OmsOperatorHandoverSubform;
import org.apache.ibatis.annotations.Param;

public interface OmsOperatorHandoverSubformMapper {
    int deleteByPrimaryKey(String id);

    int insert(OmsOperatorHandoverSubform record);

    int insertSelective(@Param("record") OmsOperatorHandoverSubform record);

    OmsOperatorHandoverSubform selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(OmsOperatorHandoverSubform record);

    int updateByPrimaryKey(OmsOperatorHandoverSubform record);

    /**
     * 功能描述: <br>
     * 〈通过主表id及业务id查询子表记录〉
     * @Param: [zbId, ywId]
     * @Return: com.hxoms.modules.omsoperator.entity.OmsOperatorHandoverSubform
     * @Author: 李逍遥
     * @Date: 2020/9/10 11:38
     */
    OmsOperatorHandoverSubform getAllDataByHandoverId(@Param("zbId") String zbId, @Param("ywId") String ywId);
}