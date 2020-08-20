package com.hxoms.modules.passportCard.initialise.entity.parameterEntity;

import com.hxoms.modules.passportCard.certificateCollect.entity.CfCertificateCollection;

import java.util.List;

/**
 * @Desc：TODO
 * @Author: wangyunquan
 * @Date: 2020/8/19
 */
public class CfCertificateCollectionApplyList {
    //催缴任务集合
    private List<CfCertificateCollection> cfCertificateCollectionList;

    public List<CfCertificateCollection> getCfCertificateCollectionList() {
        return cfCertificateCollectionList;
    }

    public void setCfCertificateCollectionList(List<CfCertificateCollection> cfCertificateCollectionList) {
        this.cfCertificateCollectionList = cfCertificateCollectionList;
    }
}
