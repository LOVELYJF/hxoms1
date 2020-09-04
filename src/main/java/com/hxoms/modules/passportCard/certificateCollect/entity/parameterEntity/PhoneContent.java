package com.hxoms.modules.passportCard.certificateCollect.entity.parameterEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Desc：TODO
 * @Author: wangyunquan
 * @Date: 2020/9/4
 */
@ApiModel(value = "电话催缴内容")
public class PhoneContent {
    @ApiModelProperty(value = "内容模板")
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
