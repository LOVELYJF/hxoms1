package com.hxoms.modules.passportCard.deviceInteraction.entity.parameterEntiry;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;

/**
 * @Desc：TODO
 * @Author: wangyunquan
 * @Date: 2020/10/10
 */
@ApiModel(value = "查询可领取证件条件")
public class QrCodeInfo {
    @ApiModelProperty(value = "二维码内容",required =true)
    @NotBlank(message = "二维码内容不能为空")
    private String qrCodeId;
    @ApiModelProperty(value = "姓名",required =true)
    @NotBlank(message = "姓名不能为空")
    private String name;
    @ApiModelProperty(value = "身份证号码",required =true)
    @NotBlank(message = "身份证号码不能为空")
    private String idNo;
    @ApiModelProperty(value = "设备编号",required =true)
    @NotBlank(message = "设备编号不能为空")
    private String deviceSn;

    public String getQrCodeId() {
        return qrCodeId;
    }

    public void setQrCodeId(String qrCodeId) {
        this.qrCodeId = qrCodeId;
    }

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

    public String getDeviceSn() {
        return deviceSn;
    }

    public void setDeviceSn(String deviceSn) {
        this.deviceSn = deviceSn;
    }
}
