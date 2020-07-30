package com.hxoms.modules.passportCard.certificateCollect.service.impl;

import com.hxoms.modules.passportCard.certificateCollect.entity.CfCertificateCollection;
import com.hxoms.modules.passportCard.certificateCollect.mapper.CfCertificateCollectionMapper;
import com.hxoms.modules.passportCard.certificateCollect.service.CfCertificateCollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CfCertificateCollectionServiceImpl implements CfCertificateCollectionService {


    @Autowired
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
