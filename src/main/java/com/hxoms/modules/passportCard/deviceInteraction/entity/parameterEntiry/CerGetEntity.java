package com.hxoms.modules.passportCard.deviceInteraction.entity.parameterEntiry;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;

/**
 * @Desc：TODO
 * @Author: wangyunquan
 * @Date: 2020/10/12
 */
@ApiModel(value = "领取证件信息")
public class CerGetEntity {
    @NotBlank(message = "领取Id不能为空")
    @ApiModelProperty(value = "领取Id",required = true)
    private String id;
    @NotBlank(message = "证件号码不能为空")
    @ApiModelProperty(value = "证件号码",required = true)
    private String zjhm;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getZjhm() {
        return zjhm;
    }

    public void setZjhm(String zjhm) {
        this.zjhm = zjhm;
    }
}
