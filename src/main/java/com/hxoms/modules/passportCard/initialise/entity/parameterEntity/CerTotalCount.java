package com.hxoms.modules.passportCard.initialise.entity.parameterEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Desc：TODO
 * @Author: wangyunquan
 * @Date: 2020/10/19
 */
@ApiModel(value = "证件个数信息")
public class CerTotalCount {
    @ApiModelProperty(value = "证件总个数")
    private Integer totalCount;
    @ApiModelProperty(value = "待验证个数")
    private Integer noValiCount;

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public Integer getNoValiCount() {
        return noValiCount;
    }

    public void setNoValiCount(Integer noValiCount) {
        this.noValiCount = noValiCount;
    }
}
