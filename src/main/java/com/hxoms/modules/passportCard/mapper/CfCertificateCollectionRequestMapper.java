package com.hxoms.modules.passportCard.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hxoms.modules.passportCard.entity.CfCertificateCollectionRequest;
import com.hxoms.modules.passportCard.entity.param.CfCertificateCollectionRequestParam;

import java.util.List;

public interface CfCertificateCollectionRequestMapper extends BaseMapper<CfCertificateCollectionRequest> {


    //批量查询
    List<CfCertificateCollectionRequest> selectCfCertificateCollectionRequestPage(CfCertificateCollectionRequestParam cfCertificateCollectionRequestParam);

    //批量增加
    int insertSelective(CfCertificateCollectionRequest cfCertificateCollectionRequest);


    CfCertificateCollectionRequest selectCfCertificateCollectionRequestById(String id);

    boolean updateCfCertificateCollectionRequest(CfCertificateCollectionRequest cfCertificateCollectionRequest);

}