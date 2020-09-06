package com.hxoms.modules.passportCard.getTaskQuery.entity.parameterEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Desc：TODO
 * @Author: wangyunquan
 * @Date: 2020/9/5
 */
@ApiModel(value = "发通知内容获取请求参数")
public class SendNoticeContentParam {
    //机构编码
    @ApiModelProperty(value = "机构编码",required = true)
    private String rfB0000;
    //姓名
    @ApiModelProperty(value = "姓名",required = true)
    private String name;
    //状态
    @ApiModelProperty(value = "状态",required = true)
    private String getStatusName;
    //证件类型名称
    @ApiModelProperty(value = "证件类型名称",required = true)
    private String zjlxName;
    //证件号码
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

    public String getGetStatusName() {
        return getStatusName;
    }

    public void setGetStatusName(String getStatusName) {
        this.getStatusName = getStatusName;
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
