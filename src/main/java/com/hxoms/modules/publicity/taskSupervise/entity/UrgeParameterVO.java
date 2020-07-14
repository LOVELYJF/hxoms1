package com.hxoms.modules.publicity.taskSupervise.entity;

/**
 * @Desc：催办业务参数
 * @Author: wangyunquan
 * @Date: 2020/6/29
 */
public class UrgeParameterVO {
    //机构编码
    private String orgId;
    //单位名称
    private String workUnit;
    //姓名
    private String name;
    //天数
    private String days;
    //申请状态
    private String sqzt;

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getWorkUnit() {
        return workUnit;
    }

    public void setWorkUnit(String workUnit) {
        this.workUnit = workUnit;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public String getSqzt() {
        return sqzt;
    }

    public void setSqzt(String sqzt) {
        this.sqzt = sqzt;
    }
}
