package com.hxoms.modules.file.entity.paramentity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @desc: 查询文件详情参数
 * @author: lijing
 * @date: 2020-05-22
 */
@ApiModel(value = "查询文件详情参数")
public class AbroadFileDestailParams {
    //文件ID
    @ApiModelProperty(value = "文件ID", required = true)
    private String fileId;
    //tableCode 类型 因公 因私 延期出国
    @ApiModelProperty(value = "类型 因公 因私 延期出国", required = true)
    private String tableCode;
    //isEdit 是否编辑  1编辑  0查看
    @ApiModelProperty(value = "是否编辑  1编辑  0查看", required = true)
    private String isEdit;
    //申请id
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
