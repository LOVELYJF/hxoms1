package com.hxoms.modules.passportCard.printGetQrCode.entity.parameterEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Desc：TODO
 * @Author: wangyunquan
 * @Date: 2020/9/4
 */
@ApiModel(value = "打印二维码申请")
public class CreateQrCodeApply {
    //证件领取表id
    @ApiModelProperty(value = "数据列表id")
    private String getId;
    //姓名
    @ApiModelProperty(value = "姓名")
    private String name;
    //证件类型
    @ApiModelProperty(value = "证件类型")
    private Integer zjlx;
    //证件号码
    @ApiModelProperty(value = "证件号码")
    private String zjhm;

    public String getGetId() {
        return getId;
    }

    public void setGetId(String getId) {
        this.getId = getId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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
}
