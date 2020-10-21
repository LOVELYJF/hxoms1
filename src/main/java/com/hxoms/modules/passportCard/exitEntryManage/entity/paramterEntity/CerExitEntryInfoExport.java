package com.hxoms.modules.passportCard.exitEntryManage.entity.paramterEntity;

import java.util.Date;

public class CerExitEntryInfoExport {
    private String workunit;//工作单位
    private String name;//姓名
    private String zjlxName;//证件类型
    private String zjhm;//证件号码
    private String statusName;//进出方式
    private Date operateTime;//存取日期
    private String operator;//存取人
    private String modeName;//存取方式

    public String getWorkunit() {
        return workunit;
    }

    public void setWorkunit(String workunit) {
        this.workunit = workunit;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getZjlxName() {
        return zjlxName;
    }

    public void setZjlxName(String zjlxName) {
        this.zjlxName = zjlxName;
    }

    public String getZjhm() {
        return zjhm;
    }

    public void setZjhm(String zjhm) {
        this.zjhm = zjhm;
    }

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
