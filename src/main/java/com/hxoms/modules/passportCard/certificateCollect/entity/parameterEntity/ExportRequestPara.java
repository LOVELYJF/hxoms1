package com.hxoms.modules.passportCard.certificateCollect.entity.parameterEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @Desc：TODO
 * @Author: wangyunquan
 * @Date: 2020/7/2
 */
@ApiModel(value = "导出短信催缴名单请求参数")
public class ExportRequestPara {
    //标题
    @ApiModelProperty(value = "标题")
    private List<String> titleList;
    //催缴名单数据
    @ApiModelProperty(value = "催缴名单数据")
    private List<ExportSMSCjInfo> exportSMSCjInfoList;

    public List<String> getTitleList() {
        return titleList;
    }

    public void setTitleList(List<String> titleList) {
        this.titleList = titleList;
    }

    public List<ExportSMSCjInfo> getExportSMSCjInfoList() {
        return exportSMSCjInfoList;
    }

    public void setExportSMSCjInfoList(List<ExportSMSCjInfo> exportSMSCjInfoList) {
        this.exportSMSCjInfoList = exportSMSCjInfoList;
    }
}
