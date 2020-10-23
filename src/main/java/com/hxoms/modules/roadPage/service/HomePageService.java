package com.hxoms.modules.roadPage.service;

import com.github.pagehelper.PageInfo;
import com.hxoms.message.message.entity.Message;
import com.hxoms.modules.omsregcadre.entity.OmsRegProcpersoninfo;
import com.hxoms.modules.passportCard.initialise.entity.CfCertificate;
import com.hxoms.modules.privateabroad.entity.OmsPriApplyVO;
import com.hxoms.modules.roadPage.entity.PersonnelPageParam;

import java.util.List;
import java.util.Map;

public interface HomePageService {
    /**
     * 人员情况列表
     * @param personnelPageParam
     * @return
     */
    PageInfo<OmsRegProcpersoninfo> selectPersonnelRoster(PersonnelPageParam personnelPageParam);

    /**
     * 因私出国实时情况查询
     * @return
     */
    List<Integer> selectPrivateRealTimeState();

    /**
     *  因私出国实时情况查询详情
     * @param type
     * @return
     */
    List<OmsPriApplyVO> selectPrivateRealTimeStateDestail(String type);

    /**
     * 征求意见概况
     * @return
     */
    Map<String, Integer> selectSeekAdviceCount();

    /**
     * 征求意见概况详情
     * @return
     */
    List<OmsPriApplyVO> selectSeekAdviceDestail(String type);

    /**
     * 因私出国境报警
     * @return
     */
    Map<String, Integer> selectPriPoliceCount();

    /**
     * 因私出国境报警
     * @return
     */
    List<OmsPriApplyVO> selectPriPoliceDestail(String type);

    /**
     * 证照统计
     * @return
     */
    Map<String, Integer> selectCertificateCount();

    /**
     * 证照详情
     * @param type
     * @return
     */
    List<CfCertificate> selectCertificateList(String type);

    /**
     * 证照上缴统计
     * @return
     */
    Map<String, List<Integer>> selectCertificateTurninCount();

    /**
     * 证照上缴统计详情
     * @param certificateType
     * @param turninType
     * @return
     */
    List<CfCertificate> selectCertificateTurninList(String certificateType, String turninType);

    /**
     * 证照借出统计
     * @return
     */
    List<Integer> selectCertificateLendCount();

    /**
     * 证照借出统计详情
     * @param type 借出数量  到入境时间数量  超期数量
     * @return
     */
    List<CfCertificate> selectCertificateLendList(String type);
}
