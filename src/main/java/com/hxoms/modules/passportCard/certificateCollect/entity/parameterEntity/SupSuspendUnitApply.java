package com.hxoms.modules.passportCard.certificateCollect.entity.parameterEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * @Desc：TODO
 * @Author: wangyunquan
 * @Date: 2020/8/31
 */
@ApiModel(value = "锁定出国申请")
public class SupSuspendUnitApply {
    @ApiModelProperty(value = "单位名称",required = true)
    private String unit;
    @ApiModelProperty(value = "单位编码",required = true)
    private String b0100;
    @ApiModelProperty(value = "暂停开始时间",required = true)
    private Date suspendTime;
    @ApiModelProperty(value = "暂停时长（月）",required = true)
    private String pauseTime;
    @ApiModelProperty(value = "暂停原因")
    private String suspendReason;

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getB0100() {
        return b0100;
    }

    public void setB0100(String b0100) {
        this.b0100 = b0100;
    }

    public Date getSuspendTime() {
        return suspendTime;
    }

    public void setSuspendTime(Date suspendTime) {
        this.suspendTime = suspendTime;
    }

    public String getPauseTime() {
        return pauseTime;
    }

    public void setPauseTime(String pauseTime) {
        this.pauseTime = pauseTime;
    }

    public String getSuspendReason() {
        return suspendReason;
    }

    public void setSuspendReason(String suspendReason) {
        this.suspendReason = suspendReason;
    }
}
