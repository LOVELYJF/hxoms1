package com.hxoms.modules.passportCard.certificateCollect.service;

import com.hxoms.modules.passportCard.certificateCollect.entity.CfCertificateCollection;

import java.util.List;

public interface CfCertificateCollectionService {

    //单条增加
    int insert(CfCertificateCollection record);

    //批量增加
    int insertSelective(CfCertificateCollection record);

    //条件查询
    List<CfCertificateCollection> selectByCfcertificateCollection(CfCertificateCollection cfCertificateCollection);

}
