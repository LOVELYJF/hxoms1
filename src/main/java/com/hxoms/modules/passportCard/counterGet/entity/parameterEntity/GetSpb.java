package com.hxoms.modules.passportCard.counterGet.entity.parameterEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;

/**
 * @Desc：TODO
 * @Author: wangyunquan
 * @Date: 2020/9/8
 */
@ApiModel(value = "领取确认审批表")
public class GetSpb {
    @NotBlank(message = "id不能为空")
    @ApiModelProperty(value = "列表id",required = true)
    private String id;

    @NotBlank(message = "证照表id不能为空")
    @ApiModelProperty(value = "证照表id",required = true)
    private String cerId;

    @NotBlank(message = "签名不能为空")
    @ApiModelProperty(value = "签名",required = true)
    private String  spbGetEleSign;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCerId() {
        return cerId;
    }

    public void setCerId(String cerId) {
        this.cerId = cerId;
    }

    public String getSpbGetEleSign() {
        return spbGetEleSign;
    }

    public void setSpbGetEleSign(String spbGetEleSign) {
        this.spbGetEleSign = spbGetEleSign;
    }
}
