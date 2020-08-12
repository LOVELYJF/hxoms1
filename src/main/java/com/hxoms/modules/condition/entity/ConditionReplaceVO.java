package com.hxoms.modules.condition.entity;

/**
 * @desc: 约束条件替换实体类
 * @author: lijing
 * @date: 2020-06-12
 */
public class ConditionReplaceVO {
    //人员id
    private String a0100;
    //备案信息id
    private String procpersonId;
    //经办人id
    private String handleId;
    //申请表Id
    private String applyId;
    //组团单位 TODO
    //审批单位 TODO
    //干部监督处 TODO

    public String getA0100() {
        return a0100;
    }

    public void setA0100(String a0100) {
        this.a0100 = a0100;
    }

    public String getProcpersonId() {
        return procpersonId;
    }

    public void setProcpersonId(String procpersonId) {
        this.procpersonId = procpersonId;
    }

    public String getHandleId() {
        return handleId;
    }

    public void setHandleId(String handleId) {
        this.handleId = handleId;
    }

    public String getApplyId() {
        return applyId;
    }

    public void setApplyId(String applyId) {
        this.applyId = applyId;
    }
}
