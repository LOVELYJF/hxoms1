package com.hxoms.modules.passportCard.certificateCollect.service;

import com.github.pagehelper.PageInfo;
import com.hxoms.modules.passportCard.certificateCollect.entity.CfCertificateCollectionRequest;
import com.hxoms.modules.passportCard.certificateCollect.entity.parameterEntity.CfCertificateCollectionRequestParam;

public interface CfCertificateCollectionRequestService {

    PageInfo<CfCertificateCollectionRequest> selectCfCertificateCollectionRequestPage(CfCertificateCollectionRequestParam cfCertificateCollectionRequestParam);

    int insertSelective(CfCertificateCollectionRequest cfCertificateCollectionRequest);

    boolean saveOrUpdate(CfCertificateCollectionRequest cfCertificateCollectionRequest);
}
