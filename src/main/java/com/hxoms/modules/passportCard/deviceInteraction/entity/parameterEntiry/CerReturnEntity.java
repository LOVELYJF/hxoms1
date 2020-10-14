package com.hxoms.modules.passportCard.deviceInteraction.entity.parameterEntiry;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @Desc：TODO
 * @Author: wangyunquan
 * @Date: 2020/10/13
 */
@ApiModel(value = "证件归还集合")
public class CerReturnEntity {
    @ApiModelProperty(value = "证件类型（1：护照，2：港澳通行证，4：台湾通行证)")
    @NotNull(message = "证件类型不能为空")
    private Integer zjlx;
    @ApiModelProperty(value = "证件号码")
    @NotBlank(message = "证件号码不能为空")
    private String zjhm;
    @ApiModelProperty(value = "机柜编号")
    @NotBlank(message = "机柜编号不能为空")
    private String cabinetNum;
    @ApiModelProperty(value = "机柜位置")
    @NotBlank(message = "机柜位置不能为空")
    private String place;

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

    public String getCabinetNum() {
        return cabinetNum;
    }

    public void setCabinetNum(String cabinetNum) {
        this.cabinetNum = cabinetNum;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }
}
