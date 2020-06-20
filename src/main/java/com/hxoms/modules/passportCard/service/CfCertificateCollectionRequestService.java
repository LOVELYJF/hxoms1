package com.hxoms.modules.passportCard.service;

import com.github.pagehelper.PageInfo;
import com.hxoms.modules.passportCard.entity.CfCertificateCollection;
import com.hxoms.modules.passportCard.entity.CfCertificateCollectionRequest;
import com.hxoms.modules.passportCard.entity.param.CfCertificateCollectionRequestParam;

public interface CfCertificateCollectionRequestService {

    PageInfo<CfCertificateCollectionRequest> selectCfCertificateCollectionRequestPage(CfCertificateCollectionRequestParam cfCertificateCollectionRequestParam);

    int insertSelective(CfCertificateCollectionRequest cfCertificateCollectionRequest);

    boolean saveOrUpdate(CfCertificateCollectionRequest cfCertificateCollectionRequest);
}
