package com.hxoms.modules.passportCard.getTaskQuery.entity.parameterEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;

/**
 * @Desc：TODO
 * @Author: wangyunquan
 * @Date: 2020/9/5
 */
@ApiModel(value = "发通知内容获取请求参数")
public class SendNoticeContentParam {
    //机构编码
    @NotBlank(message = "机构编码不能为空")
    @ApiModelProperty(value = "机构编码",required = true)
    private String rfB0000;
    //姓名
    @NotBlank(message = "姓名不能为空")
    @ApiModelProperty(value = "姓名",required = true)
    private String name;
    //状态
    @NotBlank(message = "领取状态不能为空")
    @ApiModelProperty(value = "领取状态(0:未领取,1:已领取)",required = true)
    private String getStatus;
    //证件类型名称
    @NotBlank(message = "证件类型名称不能为空")
    @ApiModelProperty(value = "证件类型名称",required = true)
    private String zjlxName;
    //证件号码
    @NotBlank(message = "证件号码不能为空")
    @ApiModelProperty(value = "证件号码",required = true)
    private String zjhm;

    public String getRfB0000() {
        return rfB0000;
    }

    public void setRfB0000(String rfB0000) {
        this.rfB0000 = rfB0000;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGetStatus() {
        return getStatus;
    }

    public void setGetStatus(String getStatus) {
        this.getStatus = getStatus;
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
