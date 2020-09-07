package com.hxoms.modules.passportCard.getTaskQuery.entity.parameterEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Desc：TODO
 * @Author: wangyunquan
 * @Date: 2020/9/5
 */
@ApiModel(value = "通知内容模板")
public class NoticeContent {
    @ApiModelProperty("机构编码")
    private String rfB0000;

    @ApiModelProperty("内容模板")
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
