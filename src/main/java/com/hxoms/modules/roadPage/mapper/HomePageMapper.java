package com.hxoms.modules.roadPage.mapper;

import com.hxoms.modules.passportCard.initialise.entity.CfCertificate;
import com.hxoms.modules.privateabroad.entity.OmsPriApplyVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface HomePageMapper {
    /**
     * 已出国
     * @return
     */
    Integer selectCountGoAbroad();

    /**
     * 已超期
     * @return
     */
    Integer selectCountTimeOut();

    /**
     * 已回国未归还证照
     * @return
     */
    Integer selectCountNoGiveBack();

    /**
     * 已出国
     * @return
     */
    List<OmsPriApplyVO> selectGoAbroadList();

    /**
     * 已超期
     * @return
     */
    List<OmsPriApplyVO> selectTimeOutList();

    /**
     * 已回国未归还证照
     * @return
     */
    List<OmsPriApplyVO> selectNoGiveBackList();

    /**
     * 征求意见概况
     * @return
     */
    Map<String, Integer> selectSeekAdviceCount();

    /**
     * 征求意见概况详情
     * @return
     */
    List<OmsPriApplyVO> selectSeekAdviceDestail(@Param("type") String type);

    /**
     * 超出回国时间10天内，未申请延期
     * @return
     */
    int selectPoliceCountDelay();

    /**
     * 超出回国日期10天内，未归还证照
     * @return
     */
    int selectPoliceCountReturnCadre();

    /**
     * 实际目的地与申请审批目的地不一致
     * @return
     */
    List<OmsPriApplyVO> selectPoliceDifferentCountry();

    /**
     * 核心涉密人员申请出国
     * @return
     */
    int selectCountCorePerson();

    /**
     * 超出回国时间10天内，未申请延期
     * @return
     */
    List<OmsPriApplyVO> selectPoliceDelayList();

    /**
     * 超出回国日期10天内，未归还证照
     * @return
     */
    List<OmsPriApplyVO> selectPoliceReturnCadreList();

    /**
     * 核心涉密人员申请出国
     * @return
     */
    List<OmsPriApplyVO> selectCorePersonList();

    /**
     *
     * @param type 1到入境时间2超期
     * @return
     */
    Integer selectCertificateLendCount(@Param("type") String type);

    /**
     *
     * @param type 1到入境时间2超期
     */
    List<CfCertificate> selectCertificateLendList(@Param("type") String type);
}
