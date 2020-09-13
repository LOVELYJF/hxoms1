package com.hxoms.modules.passportCard.certificateCollect.entity.parameterEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Desc：TODO
 * @Author: wangyunquan
 * @Date: 2020/9/13
 */
@ApiModel(value = "打印文件")
public class PrintFile {
    //文件id
    @ApiModelProperty(value = "文件id")
    private String fileId;
    //文件正面内容
    @ApiModelProperty(value = "文件正面内容")
    private String frontContent;
    //文件反面内容
    @ApiModelProperty(value = "文件反面内容")
    private String bankContent;
    //类型
    @ApiModelProperty(value = "类型")
    private String fileType;
    //签字盖章描述
    @ApiModelProperty(value = "签字盖章描述")
    private String sealDesc;
    //打印份数
    @ApiModelProperty(value = "打印份数")
    private Integer printNum;

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

    public String getSealDesc() {
        return sealDesc;
    }

    public void setSealDesc(String sealDesc) {
        this.sealDesc = sealDesc;
    }

    public Integer getPrintNum() {
        return printNum;
    }

    public void setPrintNum(Integer printNum) {
        this.printNum = printNum;
    }
}
