package com.hxoms.modules.passportCard.exitEntryManage.entity.paramterEntity;

import java.util.Date;

public class CerExitEntryInfoExport {
    private String statusName;//进出方式
    private Date operateTime;//存取日期
    private String operator;//存取人
    private String modeName;//存取方式

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public Date getOperateTime() {
        return operateTime;
    }

    public void setOperateTime(Date operateTime) {
        this.operateTime = operateTime;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getModeName() {
        return modeName;
    }

    public void setModeName(String modeName) {
        this.modeName = modeName;
    }
}
