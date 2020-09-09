package com.hxoms.modules.passportCard.certificateCollect.entity.parameterEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @Desc：TODO
 * @Author: wangyunquan
 * @Date: 2020/7/2
 */
@ApiModel(value = "导出短信催缴名单请求参数")
public class ExportRequestPara {
    //标题
    @NotBlank(message = "标题不能为空")
    @ApiModelProperty(value = "标题")
    private List<String> titleList;
    //催缴名单数据
    @NotBlank(message = "催缴名单数据集合Size不能为0")
    @Valid
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
