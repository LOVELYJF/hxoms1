package com.hxoms.modules.passportCard.initialise.entity.parameterEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @Desc：TODO
 * @Author: wangyunquan
 * @Date: 2020/8/19
 */
@ApiModel(value = "催缴任务集合")
public class CfCertificateCollectionApplyList {
    //催缴任务集合
    @ApiModelProperty(value = "催缴任务集合信息")
    private List<CertificateCollectionApply> certificateCollectionApplyList;

    public List<CertificateCollectionApply> getCertificateCollectionApplyList() {
        return certificateCollectionApplyList;
    }

    public void setCertificateCollectionApplyList(List<CertificateCollectionApply> certificateCollectionApplyList) {
        this.certificateCollectionApplyList = certificateCollectionApplyList;
    }
}
