package com.hxoms.modules.roadPage.mapper;

import com.hxoms.modules.passportCard.initialise.entity.CfCertificate;
import com.hxoms.modules.passportCard.initialise.entity.parameterEntity.CfCertificatePageParam;
import com.hxoms.modules.publicity.entity.OmsPubApply;
import com.hxoms.modules.roadPage.entity.OnbgoApprovalBaen;

import java.util.List;
import java.util.Map;

public interface CertificateStatisticsMapper {

    /**
     *统计：护照（1），港澳通行证（2），台湾通行证（4）
     * @return
     */
    List<Map<String,Object>> getPassportHongKongTaiwan();

    /**
     * 已过期数量（证照有效期小于等于当前日期）
     * @return
     */
    Map<String, Object> getExpireqty(int whereDayqty);

    /**
     * 半年内过（有效期小于当前日期）
     * @return
     */
    Map<String,Object> getHalfYearExpireqty(int whereDayqty);

    int getNotturnedinqty(int zjlx);

    List<CfCertificate> getListCfCertificate(CfCertificatePageParam cfCertificatePageParam);

    //因公出国（境）预审批
    List<OnbgoApprovalBaen> selectQiongtaiWord();

    //因公出国（境）预审批
    List<OmsPubApply> selectOmsSubApplyPwh(String pwh);

    //因私出国（境）进度
    List<Map<String,Object>> getFprgoSchedule();


}
