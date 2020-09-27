package com.hxoms.modules.passportCard.admintorGet.entity.parameterEntiry;

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
 * @Date: 2020/9/14
 */
@ApiModel(value = "管理员取证申请")
public class AdminGetCerApply {

    @NotBlank(message = "备案信息表ID不能为空")
    @ApiModelProperty(value="备案信息表ID",required = true)
    private String omsId;

    @NotBlank(message = "证照领取表ID不能为空")
    @ApiModelProperty(value="证照领取表ID",required = true)
    private String cerId;

    @NotBlank(message = "姓名不能为空")
    @ApiModelProperty(value="姓名",required = true)
    private String name;

    @NotBlank(message = "机构编码不能为空")
    @ApiModelProperty(value="机构编码",required = true)
    private String rfB0000;

    @NotNull(message = "证件类型不能为空")
    @ApiModelProperty(value="证件类型(1:护照,2:港澳通行证,4:台湾通行证)",required = true)
    private Integer zjlx;

    @NotBlank(message = "证件号码不能为空")
    @ApiModelProperty(value="证件号码",required = true)
    private String zjhm;

    @NotBlank(message = "取证原因不能为空")
    @ApiModelProperty(value="取证原因",required = true)
    private String getCause;

    @NotBlank(message = "取证人不能为空")
    @ApiModelProperty(value="取证人",required = true)
    private String operator;

    @JsonFormat(pattern = "yyyy.MM.dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy.MM.dd HH:mm:ss")
    @ApiModelProperty(value="取证时间",required = true)
    @NotNull(message = "取证时间不能为空")
    private Date operateTime;

    public String getOmsId() {
        return omsId;
    }

    public void setOmsId(String omsId) {
        this.omsId = omsId;
    }

    public String getCerId() {
        return cerId;
    }

    public void setCerId(String cerId) {
        this.cerId = cerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRfB0000() {
        return rfB0000;
    }

    public void setRfB0000(String rfB0000) {
        this.rfB0000 = rfB0000;
    }

    public Integer getZjlx() {
        return zjlx;
    }

    public void setZjlx(Integer zjlx) {
        this.zjlx = zjlx;
    }

    public String getZjhm() {
        return zjhm;
    }

    public void setZjhm(String zjhm) {
        this.zjhm = zjhm;
    }

    public String getGetCause() {
        return getCause;
    }

    public void setGetCause(String getCause) {
        this.getCause = getCause;
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
