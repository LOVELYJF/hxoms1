package com.hxoms.modules.omsoperator.mapper;

import com.hxoms.modules.omsoperator.entity.OmsOperatorHandover;
import com.hxoms.modules.omsoperator.entity.OmsOperatorHandoverSubformVO;
import com.hxoms.modules.omsoperator.entity.OmsOperatorJBYWQueryParam;
import com.hxoms.modules.omsoperator.entity.OmsPriDelayVO;
import com.hxoms.modules.omsregcadre.entity.OmsRegRevokeapply;
import com.hxoms.modules.passportCard.counterGet.entity.parameterEntity.OmsCerGetTaskVO;
import com.hxoms.modules.passportCard.omsCerCancellateLicense.entity.OmsCerCancellateLicense;
import com.hxoms.modules.privateabroad.entity.OmsPriApplyVO;
import org.apache.ibatis.annotations.Param;

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

    /** 获取该经办人未办结的撤销登记备案申请列表*/
    List<OmsRegRevokeapply> selectOmsRegRevokeapplyByStatusAndName(@Param("userId") String userId, @Param("status") String status);

    /** 获取该经办人未归还的证照列表*/
    List<OmsCerGetTaskVO> selectOmsCerGetTaskByStatusAndName(@Param("userId") String userId, @Param("status") String status);

    /** 获取该经办人未办结的注销证照列表*/
    List<OmsCerCancellateLicense> selectCerCancellateLicenseByStatusAndName(@Param("userId") String userId, @Param("status") String status);
    /** 管理人员确定列表*/
    List<OmsOperatorHandoverSubformVO> getAllOperatorHandoverByOrgid(@Param("orgId") String orgId);

    /** 经办人交接页面展示  */
    List<OmsOperatorHandoverSubformVO> selectByOperatorId(@Param("operatorId") String operatorId, @Param("handoverStatus") String handoverStatus);
    /** 接手人确认页面展示 */
    List<OmsOperatorHandoverSubformVO> selectByHandoverId(@Param("handoverId") String handoverId, @Param("handoverStatus") String handoverStatus);

    /** 查询该经办人及接手人是否已经有记录了
     * @return*/
    OmsOperatorHandover selectByOperatorIdAndHandover(@Param("operatorId") String operatorId, @Param("handoverId") String handoverId);

    /**
     * 功能描述: <br>
     * 〈经办人经办的注销证照列表〉
     * @Param: [omsOperatorJBYWQueryParam]
     * @Return: java.util.List<com.hxoms.modules.passportCard.omsCerCancellateLicense.entity.OmsCerCancellateLicense>
     * @Author: 李逍遥
     * @Date: 2020/9/11 10:51
     */
    List<OmsCerCancellateLicense> selectCerCancellateLicenseByParam(@Param("omsOperatorJBYWQueryParam") OmsOperatorJBYWQueryParam omsOperatorJBYWQueryParam);

    /**
     * 功能描述: <br>
     * 〈经办人经办的证照领取列表〉
     * @Param: [omsOperatorJBYWQueryParam]
     * @Return: java.util.List<com.hxoms.modules.passportCard.counterGet.entity.parameterEntity.OmsCerGetTaskVO>
     * @Author: 李逍遥
     * @Date: 2020/9/11 11:12
     */
    List<OmsCerGetTaskVO> selectOmsCerGetTaskByParam(@Param("omsOperatorJBYWQueryParam") OmsOperatorJBYWQueryParam omsOperatorJBYWQueryParam);

    /**
     * 功能描述: <br>
     * 〈经办人经办的撤销登记备案列表〉
     * @Param: [omsOperatorJBYWQueryParam]
     * @Return: java.util.List<com.hxoms.modules.omsregcadre.entity.OmsRegRevokeapply>
     * @Author: 李逍遥
     * @Date: 2020/9/11 11:23
     */
    List<OmsRegRevokeapply> selectOmsRegRevokeapplyByParam(@Param("omsOperatorJBYWQueryParam") OmsOperatorJBYWQueryParam omsOperatorJBYWQueryParam);

    /**
     * 功能描述: <br>
     * 〈经办人经办的因私出国境备案列表〉
     * @Param: [omsOperatorJBYWQueryParam]
     * @Return: java.util.List<com.hxoms.modules.privateabroad.entity.OmsPriApplyVO>
     * @Author: 李逍遥
     * @Date: 2020/9/11 11:33
     */
    List<OmsPriApplyVO> selectOmsPriApplyByParam(@Param("omsOperatorJBYWQueryParam") OmsOperatorJBYWQueryParam omsOperatorJBYWQueryParam);

    /**
     * 功能描述: <br>
     * 〈经办人经办的延期回国备案列表〉
     * @Param: [omsOperatorJBYWQueryParam]
     * @Return: java.util.List<com.hxoms.modules.omsoperator.entity.OmsPriDelayVO>
     * @Author: 李逍遥
     * @Date: 2020/9/11 11:37
     */
    List<OmsPriDelayVO> selectOmsPriDelayApplyByParam(@Param("omsOperatorJBYWQueryParam") OmsOperatorJBYWQueryParam omsOperatorJBYWQueryParam);

    /**
     * 功能描述: <br>
     * 〈通过条件查询经办人交接记录〉
     * @Param: [omsOperatorJBYWQueryParam]
     * @Return: java.util.List<com.hxoms.modules.omsoperator.entity.OmsOperatorHandoverSubformVO>
     * @Author: 李逍遥
     * @Date: 2020/9/11 16:47
     */
    //List<OmsOperatorHandoverSubformVO> getOperatorWBJYW(@Param("omsOperatorJBYWQueryParam") OmsOperatorJBYWQueryParam omsOperatorJBYWQueryParam);
}