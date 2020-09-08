package com.hxoms.modules.passportCard.counterGet.entity.parameterEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;

/**
 * @Desc：TODO
 * @Author: wangyunquan
 * @Date: 2020/8/18
 */
@ApiModel(value = "领取证照")
public class CerGetTaskQueryParam {
    //姓名
    @NotBlank(message = "姓名不能为空")
    @ApiModelProperty(value="姓名",required = true)
    private String name;
    //身份证号
    @NotBlank(message = "身份证号不能为空")
    @ApiModelProperty(value="身份证号",required = true)
    private String idNo;
    //经办人Id
    @NotBlank(message = "经办人Id不能为空")
    @ApiModelProperty(value = "经办人Id",required = true)
    private String operatId;
    //二维码id
    @NotBlank(message = "二维码id不能为空")
    @ApiModelProperty(value = "二维码id",required = true)
    private String qrCodeId;
    //是否显示过期证照，Y：是，N：否
    @ApiModelProperty(value = "是否显示过期证照，Y：是，N：否")
    private String overFlag;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    public String getOperatId() {
        return operatId;
    }

    public void setOperatId(String operatId) {
        this.operatId = operatId;
    }

    public String getQrCodeId() {
        return qrCodeId;
    }

    public void setQrCodeId(String qrCodeId) {
        this.qrCodeId = qrCodeId;
    }

    public String getOverFlag() {
        return overFlag;
    }

    public void setOverFlag(String overFlag) {
        this.overFlag = overFlag;
    }
}
