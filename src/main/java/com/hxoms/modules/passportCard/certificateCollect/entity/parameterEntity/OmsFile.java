package com.hxoms.modules.passportCard.certificateCollect.entity.parameterEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;

/**
 * @Desc：TODO
 * @Author: wangyunquan
 * @Date: 2020/9/13
 */
@ApiModel(value = "模板信息")
public class OmsFile {

    @NotBlank(message = "id不能为空")
    @ApiModelProperty(value = "模板主键",required = true)
    private String id;

    @ApiModelProperty(value = "文件ID")
    private String fileId;

    @ApiModelProperty(value = "可编辑文件（正面，第一页）",required = true)
    private String frontContent;

    @ApiModelProperty(value = "可编辑文件（反面，第二页）")
    private String bankContent;

    @ApiModelProperty(value = "文件类别（1系统 2非涉密人员 3涉密人员 4涉密人员（重要，一般不是本单位））")
    private String fileType;

    @ApiModelProperty(value = "文件类型(oms_cer_certificateCollect_cpd:证件催缴打印呈批单,oms_cer_certificateCollect_bzh:证件催缴打印标准函)")
    private String tableCode;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getFrontContent() {
        return frontContent;
    }

    public void setFrontContent(String frontContent) {
        this.frontContent = frontContent;
    }

    public String getBankContent() {
        return bankContent;
    }

    public void setBankContent(String bankContent) {
        this.bankContent = bankContent;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getTableCode() {
        return tableCode;
    }

    public void setTableCode(String tableCode) {
        this.tableCode = tableCode;
    }
}
