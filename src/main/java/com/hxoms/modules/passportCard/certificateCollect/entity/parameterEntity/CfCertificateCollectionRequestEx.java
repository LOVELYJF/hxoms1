package com.hxoms.modules.passportCard.certificateCollect.entity.parameterEntity;

import com.hxoms.modules.passportCard.certificateCollect.entity.CfCertificateCollectionRequest;

/**
 * @Desc：TODO
 * @Author: wangyunquan
 * @Date: 2020/8/12
 */
public class CfCertificateCollectionRequestEx extends CfCertificateCollectionRequest {
    private String allCjResult;

    public String getAllCjResult() {
        return allCjResult;
    }

    public void setAllCjResult(String allCjResult) {
        this.allCjResult = allCjResult;
    }
}
