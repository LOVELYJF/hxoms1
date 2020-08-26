package com.hxoms.modules.passportCard.certificateCollect.entity.parameterEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Desc：TODO
 * @Author: wangyunquan
 * @Date: 2020/8/12
 */
@ApiModel(value = "机构单位")
public class OrganUnit {
    //机构单位编码
    @ApiModelProperty(value = "机构单位编码")
    private String rfB0000;
    //机构单位名称
    @ApiModelProperty(value = "机构单位名称")
    private String workUnit;

    public String getRfB0000() {
        return rfB0000;
    }

    public void setRfB0000(String rfB0000) {
        this.rfB0000 = rfB0000;
    }

    public String getWorkUnit() {
        return workUnit;
    }

    public void setWorkUnit(String workUnit) {
        this.workUnit = workUnit;
    }
}
