package com.hxoms.modules.passportCard.counterReturn.entity.parameterEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @Desc：TODO
 * @Author: wangyunquan
 * @Date: 2020/9/17
 */
@ApiModel(value = "因私申请查询")
public class PriApplyQueryParams {
    @NotBlank(message = "备案表id不能为空")
    @ApiModelProperty(value = "备案表id")
    private String omsId;

    @NotNull(message = "证件类型不能为空")
    @ApiModelProperty(value = "证件类型")
    private Integer zjlx;

    @NotBlank(message = "证件号码不能为空")
    @ApiModelProperty(value = "证件号码")
    private String zjhm;

    public String getOmsId() {
        return omsId;
    }

    public void setOmsId(String omsId) {
        this.omsId = omsId;
    }

    public Integer getZjlx() {
        return zjlx;
    }

    public void setZjlx(Integer zjlx) {
        this.zjlx = zjlx;
    }

    public String getZjhm() {
        return zjhm;
    }

    public void setZjhm(String zjhm) {
        this.zjhm = zjhm;
    }
}
