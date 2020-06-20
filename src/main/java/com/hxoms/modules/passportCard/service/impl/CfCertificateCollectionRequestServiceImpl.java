package com.hxoms.modules.passportCard.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hxoms.common.utils.UUIDGenerator;
import com.hxoms.modules.passportCard.entity.CfCertificateCollectionRequest;
import com.hxoms.modules.passportCard.entity.param.CfCertificateCollectionRequestParam;
import com.hxoms.modules.passportCard.mapper.CfCertificateCollectionRequestMapper;
import com.hxoms.modules.passportCard.service.CfCertificateCollectionRequestService;
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

    @Override
    public int insertSelective(CfCertificateCollectionRequest cfCertificateCollectionRequest) {
        return cfCertificateCollectionRequestMapper.insertSelective(cfCertificateCollectionRequest);
    }

    @Override
    public boolean saveOrUpdate(CfCertificateCollectionRequest cfCertificateCollectionRequest) {
        boolean flag= false;
        CfCertificateCollectionRequest cf = cfCertificateCollectionRequestMapper.selectCfCertificateCollectionRequestById(cfCertificateCollectionRequest.getId());
        if(cf == null){
            cfCertificateCollectionRequest.setId(UUIDGenerator.getPrimaryKey());
            cfCertificateCollectionRequestMapper.insertSelective(cfCertificateCollectionRequest);
            flag = true;
        }else{
            cfCertificateCollectionRequestMapper.updateCfCertificateCollectionRequest(cfCertificateCollectionRequest);
            flag = true;
        }
        return flag;
    }
}
