package com.hxoms.modules.passportCard.certificateCollect.entity.parameterEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;

/**
 * @Desc：TODO
 * @Author: wangyunquan
 * @Date: 2020/9/13
 */
@ApiModel(value = "文件信息")
public class PrintFile {
    //文件id
    @NotBlank(message = "文件id不能为空")
    @ApiModelProperty(value = "模板文件id",required = true)
    private String id;

    //文件id
    @NotBlank(message = "申请id不能为空")
    @ApiModelProperty(value = "申请id",required = true)
    private String applyId;

    //文件类型
    @NotBlank(message = "文件类型不能为空")
    @ApiModelProperty(value = "文件类型",required = true)
    private String tableCode;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getApplyId() {
        return applyId;
    }

    public void setApplyId(String applyId) {
        this.applyId = applyId;
    }

    public String getTableCode() {
        return tableCode;
    }

    public void setTableCode(String tableCode) {
        this.tableCode = tableCode;
    }
}
