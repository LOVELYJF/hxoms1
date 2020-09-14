package com.hxoms.modules.passportCard.certificateCollect.entity.parameterEntity;

import com.hxoms.common.hxannotation.ColumnAnnotation;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "打印文件信息")
public class OmsCreateFile {

    @ApiModelProperty(value = "主键")
    private String id;

    @ApiModelProperty(value = "文件ID")
    private String fileId;

    @ApiModelProperty(value = "申请表ID")
    private String applyId;

    @ColumnAnnotation(FieldName = "FRONT_CONTENT",   FieldDescription="可编辑文件（正面，第一页）")
    @ApiModelProperty(value = "可编辑文件（正面，第一页）")
    private String frontContent;

    @ColumnAnnotation(FieldName = "BANK_CONTENT",   FieldDescription="可编辑文件（反面，第二页）")
    @ApiModelProperty(value = "可编辑文件（反面，第二页）")
    private String bankContent;

    @ApiModelProperty(value = "文件类别（1系统 2非涉密人员 3涉密人员 4涉密人员（重要，一般不是本单位））")
    private String fileType;

    @ApiModelProperty(value = "文件类型(oms_cer_certificateCollect_cpd:证件催缴打印呈批单,oms_cer_certificateCollect_bzh:证件催缴打印标准函)", required = true)
    private String tableCode;

    @ColumnAnnotation(FieldName = "PRINT_NUM",   FieldDescription="打印份数")
    @ApiModelProperty(value = "打印份数")
    private Integer printNum;

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

    public String getApplyId() {
        return applyId;
    }

    public void setApplyId(String applyId) {
        this.applyId = applyId;
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

    public Integer getPrintNum() {
        return printNum;
    }

    public void setPrintNum(Integer printNum) {
        this.printNum = printNum;
    }
}