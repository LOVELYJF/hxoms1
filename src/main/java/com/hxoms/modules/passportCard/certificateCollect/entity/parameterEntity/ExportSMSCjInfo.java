package com.hxoms.modules.passportCard.certificateCollect.entity.parameterEntity;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @Desc：TODO
 * @Author: wangyunquan
 * @Date: 2020/9/3
 */
@ApiModel(value = "导出短信催缴名单")
public class ExportSMSCjInfo {
    //机构编码
    @ApiModelProperty(value = "机构编码",required = true)
    private String rfB0000;
    //工作单位
    @ApiModelProperty(value = "工作单位",required = true)
    private String workUnit;
    //经办人信息
    @ApiModelProperty(value = "经办人信息",required = true)
    List<HandlerInfo> handlerInfoList;
    //短信信息
    @ApiModelProperty(value = "短信信息",required = true)
    private String smsInfo;

    public String getRfB0000() {
        return rfB0000;
    }

    public void setRfB0000(String rfB0000) {
        this.rfB0000 = rfB0000;
    }

    public String getWorkUnit() {
        return workUnit;
    }

    public void setWorkUnit(String workUnit) {
        this.workUnit = workUnit;
    }

    public List<HandlerInfo> getHandlerInfoList() {
        return handlerInfoList;
    }

    public void setHandlerInfoList(List<HandlerInfo> handlerInfoList) {
        this.handlerInfoList = handlerInfoList;
    }

    public String getSmsInfo() {
        return smsInfo;
    }

    public void setSmsInfo(String smsInfo) {
        this.smsInfo = smsInfo;
    }
}
