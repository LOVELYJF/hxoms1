package com.hxoms.modules.file.entity.paramentity;

/**
 * @desc: 查询文件详情参数
 * @author: lijing
 * @date: 2020-05-22
 */
public class AbroadFileDestailParams {
    //文件ID
    private String fileId;
    //tableCode 类型 因公 因私 延期出国
    private String tableCode;
    //isEdit 是否编辑  1编辑  0查看
    private String isEdit;
    //申请id
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
