package com.hxoms.modules.passportCard.certificateCollect.entity.parameterEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * @Desc：TODO
 * @Author: wangyunquan
 * @Date: 2020/9/9
 */
@ApiModel(value = "短信催缴信息")
public class SMSCjInfo {
    //机构编码
    @ApiModelProperty(value = "机构编码",required = true)
    @NotBlank(message = "机构编码不能为空")
    private String rfB0000;
    //工作单位
    @ApiModelProperty(value = "工作单位",required = true)
    @NotBlank(message = "工作单位不能为空")
    private String workUnit;
    //催缴人员
    @ApiModelProperty(value = "催缴人员集合",required = true)
    @NotEmpty(message = "催缴人员集合Size不能为0")
    @Valid
    private List<SMSCjPersonInfo> smsCjPersonInfoList;

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

    public List<SMSCjPersonInfo> getSmsCjPersonInfoList() {
        return smsCjPersonInfoList;
    }

    public void setSmsCjPersonInfoList(List<SMSCjPersonInfo> smsCjPersonInfoList) {
        this.smsCjPersonInfoList = smsCjPersonInfoList;
    }
}
