package com.hxoms.modules.selfestimate.entity.paramentity;

/**
 * @desc: 自评项结果参数
 * @author: lijing
 * @date: 2020-06-12
 */
public class ResultListParam {
    //申请id
    private String applyId;
    //文件ID
    private String selffileId;
    //类型(因公，因私，延期回国)
    private String type;
    //操作人类型（经办人，干部监督处）
    private String personType;

    public String getApplyId() {
        return applyId;
    }

    public void setApplyId(String applyId) {
        this.applyId = applyId;
    }

    public String getSelffileId() {
        return selffileId;
    }

    public void setSelffileId(String selffileId) {
        this.selffileId = selffileId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPersonType() {
        return personType;
    }

    public void setPersonType(String personType) {
        this.personType = personType;
    }
}
