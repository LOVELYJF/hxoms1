package com.hxoms.modules.passportCard.certificateCollect.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hxoms.modules.passportCard.certificateCollect.entity.CfCertificateCollectionRequest;
import com.hxoms.modules.passportCard.certificateCollect.entity.parameterEntity.CfCertificateCollectionRequestParam;

import java.util.List;

public interface CfCertificateCollectionRequestMapper extends BaseMapper<CfCertificateCollectionRequest> {


    //批量查询
    List<CfCertificateCollectionRequest> selectCfCertificateCollectionRequestPage(CfCertificateCollectionRequestParam cfCertificateCollectionRequestParam);

}