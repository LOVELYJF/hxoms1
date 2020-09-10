package com.hxoms.modules.passportCard.certificateCollect.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hxoms.modules.passportCard.certificateCollect.entity.CfCertificateCollectionRequest;
import com.hxoms.modules.passportCard.certificateCollect.entity.parameterEntity.CfCertificateCollectionRequestParam;
import com.hxoms.modules.passportCard.certificateCollect.entity.parameterEntity.CjRecord;

import java.util.List;

public interface CfCertificateCollectionRequestMapper extends BaseMapper<CfCertificateCollectionRequest> {


    //批量查询
    List<CfCertificateCollectionRequest> selectCfCertificateCollectionRequestPage(CfCertificateCollectionRequestParam cfCertificateCollectionRequestParam);

    /**
     * @Desc: 催缴记录查询
     * @Author: wangyunquan
     * @Param: [id]
     * @Return: java.util.List<com.hxoms.modules.passportCard.certificateCollect.entity.parameterEntity.CjRecord>
     * @Date: 2020/9/9
     */
    List<CjRecord> selectCjRecord(String cerCjId);
}