package com.hxoms.modules.passportCard.service.impl;

import com.hxoms.modules.passportCard.entity.CfCertificateCollection;
import com.hxoms.modules.passportCard.mapper.CfCertificateCollectionMapper;
import com.hxoms.modules.passportCard.service.CfCertificateCollectionService;

import java.util.List;

public class CfCertificateCollectionServiceImpl implements CfCertificateCollectionService {


    private CfCertificateCollectionMapper cfCertificateCollectionMapper;
    @Override
    public int insert(CfCertificateCollection record) {
        return cfCertificateCollectionMapper.insert(record);
    }

    @Override
    public int insertSelective(CfCertificateCollection record) {
        return cfCertificateCollectionMapper.insertSelective(record);
    }

    @Override
    public List<CfCertificateCollection> selectByCfcertificateCollection(CfCertificateCollection cfCertificateCollection) {
        return cfCertificateCollectionMapper.selectByCfcertificateCollection(cfCertificateCollection);
    }
}
