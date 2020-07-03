package com.hxoms.modules.roadPage.service;

import com.github.pagehelper.PageInfo;
import com.hxoms.modules.passportCard.entity.CfCertificate;
import com.hxoms.modules.passportCard.entity.param.CfCertificatePageParam;
import com.hxoms.modules.roadPage.entity.OnbgoApprovalBaen;

import java.util.List;
import java.util.Map;

public interface CertificateStatisticsService {


    List<Map<String,Object>> getLicenceStatistic();

    PageInfo<CfCertificate> getListCfCertificate(CfCertificatePageParam cfCertificatePageParam);

    Map<String,Object> getOnbgoApproval(String pwh, String sqzt);



}


