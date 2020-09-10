package com.hxoms.modules.passportCard.exitEntryManage.entity.paramterEntity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @Desc：TODO
 * @Author: wangyunquan
 * @Date: 2020/9/9
 */
@ApiModel(value = "出入库信息")
public class CerExitEntryInfo {

    @ApiModelProperty(value="主键")
    private String id;

    @ApiModelProperty(value="证照领取表ID")
    private String getId;

    @ApiModelProperty(value="状态(0:取出,1:归还)")
    private String status;

    @ApiModelProperty(value="状态名称")
    private String statusName;

    @ApiModelProperty(value="存取方式(0:证照机,1:柜台)")
    private String mode;

    @ApiModelProperty(value="存取方式名称")
    private String modeName;

    @ApiModelProperty(value="操作人")
    private String operator;

    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    @ApiModelProperty(value="操作时间")
    private Date operateTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGetId() {
        return getId;
    }

    public void setGetId(String getId) {
        this.getId = getId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getModeName() {
        return modeName;
    }

    public void setModeName(String modeName) {
        this.modeName = modeName;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public Date getOperateTime() {
        return operateTime;
    }

    public void setOperateTime(Date operateTime) {
        this.operateTime = operateTime;
    }
}
