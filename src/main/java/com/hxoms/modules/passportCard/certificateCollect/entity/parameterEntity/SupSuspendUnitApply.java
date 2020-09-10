package com.hxoms.modules.passportCard.certificateCollect.entity.parameterEntity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @Desc：TODO
 * @Author: wangyunquan
 * @Date: 2020/8/31
 */
@ApiModel(value = "锁定出国申请")
public class SupSuspendUnitApply {
    @NotBlank(message = "单位名称不能为空")
    @ApiModelProperty(value = "单位名称",required = true)
    private String unit;

    @NotBlank(message = "机构编码不能为空")
    @ApiModelProperty(value = "机构编码",required = true)
    private String b0100;

    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    @NotNull(message = "暂停开始时间不能为空")
    @ApiModelProperty(value = "暂停开始时间",required = true)
    private Date suspendTime;

    @NotBlank(message = "暂停时长不能为空")
    @ApiModelProperty(value = "暂停时长（月）",required = true)
    private String pauseTime;

    @NotBlank(message = "暂停原因不能为空")
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
