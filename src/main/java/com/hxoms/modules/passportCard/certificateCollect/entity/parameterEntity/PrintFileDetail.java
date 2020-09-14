package com.hxoms.modules.passportCard.certificateCollect.entity.parameterEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @Desc：TODO
 * @Author: wangyunquan
 * @Date: 2020/9/13
 */
@ApiModel(value = "打印文件详细信息")
public class PrintFileDetail {
    @ApiModelProperty(value = "模板信息")
    private OmsFile omsFile;

    @ApiModelProperty(value = "打印文件信息")
    private OmsCreateFile omsCreateFile;

    @ApiModelProperty(value = "关键词信息")
    private List<OmsReplaceKeywords> omsReplaceKeywordsList;
    public OmsFile getOmsFile() {
        return omsFile;
    }

    public void setOmsFile(OmsFile omsFile) {
        this.omsFile = omsFile;
    }

    public OmsCreateFile getOmsCreateFile() {
        return omsCreateFile;
    }

    public void setOmsCreateFile(OmsCreateFile omsCreateFile) {
        this.omsCreateFile = omsCreateFile;
    }

    public List<OmsReplaceKeywords> getOmsReplaceKeywordsList() {
        return omsReplaceKeywordsList;
    }

    public void setOmsReplaceKeywordsList(List<OmsReplaceKeywords> omsReplaceKeywordsList) {
        this.omsReplaceKeywordsList = omsReplaceKeywordsList;
    }
}
