package com.hxoms.modules.passportCard.exitEntryManage.entity.paramterEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @Desc：TODO
 * @Author: wangyunquan
 * @Date: 2020/9/9
 */
@ApiModel(value = "证件信息")
public class CerInfo {
    //证件类型
    @NotNull(message = "证件类型不能为空")
    @ApiModelProperty(value = "证件类型", required = true)
    private Integer zjlx;
    //证件号码
    @NotBlank(message = "证件号码不能为空")
    @ApiModelProperty(value = "证件号码", required = true)
    private String zjhm;
    //状态
    @ApiModelProperty(value = "状态(0:取出,1:归还,2:全选)")
    private String status;


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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
