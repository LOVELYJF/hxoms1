package com.hxoms.modules.passportCard.certificateCollect.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hxoms.modules.passportCard.certificateCollect.entity.CfCertificateCollection;

import java.util.List;

public interface CfCertificateCollectionMapper extends BaseMapper<CfCertificateCollection> {
    //条件查询
    List<CfCertificateCollection> selectByCfcertificateCollection(CfCertificateCollection cfCertificateCollection);
}