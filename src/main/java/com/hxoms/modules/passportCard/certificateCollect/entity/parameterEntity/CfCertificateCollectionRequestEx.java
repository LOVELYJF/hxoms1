package com.hxoms.modules.passportCard.certificateCollect.entity.parameterEntity;

import com.hxoms.modules.passportCard.certificateCollect.entity.CfCertificateCollectionRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Desc：TODO
 * @Author: wangyunquan
 * @Date: 2020/8/12
 */
@ApiModel(value = "催缴结果")
public class CfCertificateCollectionRequestEx extends CfCertificateCollectionRequest {
    @ApiModelProperty(value = "催缴情况")
    private String allCjResult;

    public String getAllCjResult() {
        return allCjResult;
    }

    public void setAllCjResult(String allCjResult) {
        this.allCjResult = allCjResult;
    }
}
