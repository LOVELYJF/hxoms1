package com.hxoms.modules.file.entity.paramentity;

/**
 * @desc: 查询请示文件参数
 * @author: lijing
 * @date: 2020-05-22
 */
public class AbroadAskFileParams {
    //tableCode 类型 oms_pub_apply因公 oms_pri_apply因私
    private String tableCode;
    //isEdit 是否编辑  1编辑  0查看
    private String isEdit;
    //申请id
    private String applyID;

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
