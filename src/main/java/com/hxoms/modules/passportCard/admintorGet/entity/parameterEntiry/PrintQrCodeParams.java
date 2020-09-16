package com.hxoms.modules.passportCard.admintorGet.entity.parameterEntiry;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;

/**
 * @Desc：TODO
 * @Author: wangyunquan
 * @Date: 2020/9/15
 */
@ApiModel(value = "打印二维码请求参数")
public class PrintQrCodeParams {
    @NotBlank(message = "id不能为空")
    @ApiModelProperty(value = "主键",required = true)
    private String id;
    @NotBlank(message = "证照状态不能为空")
    @ApiModelProperty(value = "证照状态",required = true)
    private String cardStatus;
    @NotBlank(message = "证照类型名称不能为空")
    @ApiModelProperty(value = "证照类型名称",required = true)
    private String zjlxName;
    @NotBlank(message = "证照号码不能为空")
    @ApiModelProperty(value = "证照号码",required = true)
    private String zjhm;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCardStatus() {
        return cardStatus;
    }

    public void setCardStatus(String cardStatus) {
        this.cardStatus = cardStatus;
    }

    public String getZjlxName() {
        return zjlxName;
    }

    public void setZjlxName(String zjlxName) {
        this.zjlxName = zjlxName;
    }

    public String getZjhm() {
        return zjhm;
    }

    public void setZjhm(String zjhm) {
        this.zjhm = zjhm;
    }
}
