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
 * @Date: 2020/9/10
 */
@ApiModel(value = "发通知（单位）集合")
public class SendNotice {
    //机构编码
    @NotBlank(message = "机构编码不能为空")
    @ApiModelProperty(value = "机构编码")
    private String rfB0000;
    //工作单位
    @NotBlank(message = "工作单位不能为空")
    @ApiModelProperty(value = "工作单位")
    private String workUnit;

    //人员
    @ApiModelProperty(value = "人员集合",required = true)
    @NotEmpty(message = "人员集合Size不能为0")
    @Valid
    List<CjContentParam> cjContentParamList;

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

    public List<CjContentParam> getCjContentParamList() {
        return cjContentParamList;
    }

    public void setCjContentParamList(List<CjContentParam> cjContentParamList) {
        this.cjContentParamList = cjContentParamList;
    }
}
