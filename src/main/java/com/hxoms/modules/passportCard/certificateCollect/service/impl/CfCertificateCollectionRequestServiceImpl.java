package com.hxoms.modules.passportCard.certificateCollect.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hxoms.modules.passportCard.certificateCollect.entity.CfCertificateCollectionRequest;
import com.hxoms.modules.passportCard.certificateCollect.entity.parameterEntity.CfCertificateCollectionRequestParam;
import com.hxoms.modules.passportCard.certificateCollect.mapper.CfCertificateCollectionRequestMapper;
import com.hxoms.modules.passportCard.certificateCollect.service.CfCertificateCollectionRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CfCertificateCollectionRequestServiceImpl extends ServiceImpl<CfCertificateCollectionRequestMapper,CfCertificateCollectionRequest> implements CfCertificateCollectionRequestService {

    @Autowired
    private CfCertificateCollectionRequestMapper cfCertificateCollectionRequestMapper;

    @Override
    public PageInfo<CfCertificateCollectionRequest> selectCfCertificateCollectionRequestPage(CfCertificateCollectionRequestParam cfCertificateCollectionRequestParam) {
        PageHelper.startPage(cfCertificateCollectionRequestParam.getPageNum()==null? 0 : cfCertificateCollectionRequestParam.getPageNum(),
                cfCertificateCollectionRequestParam.getPageSize()==null? 10 : cfCertificateCollectionRequestParam.getPageSize());
        List<CfCertificateCollectionRequest> cfCertificateCollectionRequestList = cfCertificateCollectionRequestMapper.selectCfCertificateCollectionRequestPage(cfCertificateCollectionRequestParam);

        PageInfo<CfCertificateCollectionRequest> pageInfo = new PageInfo(cfCertificateCollectionRequestList);

        return pageInfo;
    }
}
