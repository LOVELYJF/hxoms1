package com.hxoms.modules.passportCard.deviceInteraction.entity.parameterEntiry;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * @Desc：TODO
 * @Author: wangyunquan
 * @Date: 2020/10/12
 */
@ApiModel(value = "证件领取通知对象")
public class CerGetNotice {
    @NotBlank(message = "领取人姓名不能为空")
    @ApiModelProperty(value = "领取人姓名",required = true)
    private String name;
    @NotBlank(message = "领取人身份证号码不能为空")
    @ApiModelProperty(value = "领取人身份证号码",required = true)
    private String idNo;

    @ApiModelProperty(value = "证件信息集合",required = true)
    @NotEmpty(message = "证件信息集合Size不能为0")
    @Valid
    private List<CerGetEntity> cerInfo;

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

    public List<CerGetEntity> getCerInfo() {
        return cerInfo;
    }

    public void setCerInfo(List<CerGetEntity> cerInfo) {
        this.cerInfo = cerInfo;
    }
}
