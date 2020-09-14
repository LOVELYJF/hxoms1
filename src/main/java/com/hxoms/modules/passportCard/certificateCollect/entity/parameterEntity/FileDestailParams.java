package com.hxoms.modules.passportCard.certificateCollect.entity.parameterEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;

/**
 * @desc: 查询文件详情参数
 * @author: lijing
 * @date: 2020-05-22
 */
@ApiModel(value = "查询文件详情参数")
public class FileDestailParams {
    //文件id
    @NotBlank(message = "文件id不能不空")
    @ApiModelProperty(value = "文件id", required = true)
    private String fileId;
    //文件类型
    @NotBlank(message = "文件类型不能不空")
    @ApiModelProperty(value = "文件类型(oms_cer_certificateCollect_cpd:证件催缴打印呈批单,oms_cer_certificateCollect_bzh:证件催缴打印标准函)", required = true)
    private String tableCode;

    //isEdit 是否编辑  1编辑  0查看
    @NotBlank(message = "是否编辑不能不空")
    @ApiModelProperty(value = "是否编辑  1编辑  0查看", required = true)
    private String isEdit;
    //申请id
    @NotBlank(message = "申请id不能不空")
    @ApiModelProperty(value = "申请id", required = true)
    private String applyID;

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getTableCode() {
        return tableCode;
    }

    public void setTableCode(String tableCode) {
        this.tableCode = tableCode;
    }

    public String getIsEdit() {
        return isEdit;
    }

    public void setIsEdit(String isEdit) {
        this.isEdit = isEdit;
    }

    public String getApplyID() {
        return applyID;
    }

    public void setApplyID(String applyID) {
        this.applyID = applyID;
    }
}
