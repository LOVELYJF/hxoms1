package com.hxoms.modules.passportCard.certificateCollect.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hxoms.modules.passportCard.certificateCollect.entity.CfCertificateCollection;

import java.util.List;

public interface CfCertificateCollectionService extends IService<CfCertificateCollection> {

    /**
     * @Desc: 生成催缴任务
     * @Author: wangyunquan
     * @Param: [cfCertificateCollectionList]
     * @Return: void
     * @Date: 2020/8/11
     */
    void createCjTask(List<CfCertificateCollection> cfCertificateCollectionList);

    //条件查询
    List<CfCertificateCollection> selectByCfcertificateCollection(CfCertificateCollection cfCertificateCollection);

}
