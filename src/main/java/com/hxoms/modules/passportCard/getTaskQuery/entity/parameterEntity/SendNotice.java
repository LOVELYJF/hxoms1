package com.hxoms.modules.passportCard.getTaskQuery.entity.parameterEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;

/**
 * @Desc：TODO
 * @Author: wangyunquan
 * @Date: 2020/9/5
 */
@ApiModel(value = "发通知")
public class SendNotice {
    //机构编码
    @NotBlank(message = "机构编码不能为空")
    @ApiModelProperty(value = "机构编码",required = true)
    private String rfB0000;
    //通知内容
    @NotBlank(message = "通知内容不能为空")
    @ApiModelProperty(value = "通知内容",required = true)
    private String content;

    public String getRfB0000() {
        return rfB0000;
    }

    public void setRfB0000(String rfB0000) {
        this.rfB0000 = rfB0000;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
