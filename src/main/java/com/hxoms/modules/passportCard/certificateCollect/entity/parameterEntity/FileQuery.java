package com.hxoms.modules.passportCard.certificateCollect.entity.parameterEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;

/**
 * @Desc：TODO
 * @Author: wangyunquan
 * @Date: 2020/9/13
 */
@ApiModel(value = "打印文件查询")
public class FileQuery {
    //文件类型
    @NotBlank(message = "文件类型不能不空")
    @ApiModelProperty(value = "文件类型(oms_cer_certificateCollect_cpd:证件催缴打印呈批单,oms_cer_certificateCollect_bzh:证件催缴打印标准函)", required = true)
    private String tableCode;
    //申请人备案表id
    @NotBlank(message = "申请人备案表id不能不空")
    @ApiModelProperty(value = "申请人备案表id", required = true)
    private String procpersonId;
    //申请id
    @NotBlank(message = "申请id不能不空")
    @ApiModelProperty(value = "申请id", required = true)
    private String applyId;

    public String getTableCode() {
        return tableCode;
    }

    public void setTableCode(String tableCode) {
        this.tableCode = tableCode;
    }

    public String getProcpersonId() {
        return procpersonId;
    }

    public void setProcpersonId(String procpersonId) {
        this.procpersonId = procpersonId;
    }

    public String getApplyId() {
        return applyId;
    }

    public void setApplyId(String applyId) {
        this.applyId = applyId;
    }
}
