package com.hxoms.modules.passportCard.counterGet.entity.parameterEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;

/**
 * @Desc：TODO
 * @Author: wangyunquan
 * @Date: 2020/9/8
 */
@ApiModel(value = "领取确认证照")
public class GetConfirm {
    @NotBlank(message = "id不能为空")
    @ApiModelProperty(value = "列表id",required = true)
    private String id;

    @NotBlank(message = "证照表id不能为空")
    @ApiModelProperty(value = "证照表id，列表cerId",required = true)
    private String cerId;

    @NotBlank(message = "经办人id不能为空")
    @ApiModelProperty(value = "经办人id",required = true)
    private String getPeople;

    @NotBlank(message = "签名不能为空")
    @ApiModelProperty(value = "签名",required = true)
    private String cerGetEleSign;

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

    public String getGetPeople() {
        return getPeople;
    }

    public void setGetPeople(String getPeople) {
        this.getPeople = getPeople;
    }

    public String getCerGetEleSign() {
        return cerGetEleSign;
    }

    public void setCerGetEleSign(String cerGetEleSign) {
        this.cerGetEleSign = cerGetEleSign;
    }
}
