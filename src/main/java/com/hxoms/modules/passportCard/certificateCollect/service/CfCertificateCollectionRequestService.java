package com.hxoms.modules.passportCard.certificateCollect.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.hxoms.modules.passportCard.certificateCollect.entity.CfCertificateCollectionRequest;
import com.hxoms.modules.passportCard.certificateCollect.entity.parameterEntity.CfCertificateCollectionRequestParam;
import com.hxoms.modules.passportCard.certificateCollect.entity.parameterEntity.CjRecord;

import java.util.List;

public interface CfCertificateCollectionRequestService extends IService<CfCertificateCollectionRequest> {

    PageInfo<CfCertificateCollectionRequest> selectCfCertificateCollectionRequestPage(CfCertificateCollectionRequestParam cfCertificateCollectionRequestParam);

    /**
     * @Desc: 催缴记录查询
     * @Author: wangyunquan
     * @Param: [id]
     * @Return: com.hxoms.modules.passportCard.certificateCollect.entity.parameterEntity.RequestList<com.hxoms.modules.passportCard.certificateCollect.entity.parameterEntity.CjRecord>
     * @Date: 2020/9/9
     */
    List<CjRecord> selectCjRecord(String id);
}
