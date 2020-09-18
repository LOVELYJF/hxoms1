package com.hxoms.modules.passportCard.counterReturn.entity.parameterEntity;

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
 * @Date: 2020/8/29
 */
@ApiModel(value = "证照验证参数")
public class ReadCerInfo {


    @ApiModelProperty(value="柜台号码表id")
    private String counterNumId;

    @NotBlank(message = "姓名不能为空")
    @ApiModelProperty(value="姓名",required = true)
    private String name;

    @NotNull(message = "出生日期不能为空")
    @JsonFormat(pattern = "yyyy.MM.dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    @ApiModelProperty(value="出生日期",required = true)
    private Date csrq;

    @NotNull(message = "证件类型不能为空")
    @ApiModelProperty(value="证件类型(1:护照,2:港澳通行证,4:台湾通行证)",required = true)
    private Integer zjlx;

    @NotBlank(message = "证件形式不能为空")
    @ApiModelProperty(value="证件形式(0:本式,1:卡式)",required = true)
    private String zjxs;

    @NotBlank(message = "芯片类型不能为空")
    @ApiModelProperty(value="芯片类型(0:自带,1:粘贴)",required = true)
    private String xplx;

    @NotBlank(message = "证件号码不能为空")
    @ApiModelProperty(value="证件号码",required = true)
    private String zjhm;

    @ApiModelProperty(value="证照状态(0:正常,1:过期,2:注销,3:验证失败,4:已验证,5:待验证,6:借出,7:待领取,8:已领取)")
    private String cardStatus;

    @ApiModelProperty(value="证照状态名称")
    private String cardStatusName;

    @ApiModelProperty(value="保管单位(0:干部监督处,1:省委统战部(台办))")
    private String surelyUnit;

    @ApiModelProperty(value="保管方式(0:证照机,1:柜台)")
    private String surelyWay;

    @ApiModelProperty(value="柜台编号")
    private Integer counterNum;

    public String getCounterNumId() {
        return counterNumId;
    }

    public void setCounterNumId(String counterNumId) {
        this.counterNumId = counterNumId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCsrq() {
        return csrq;
    }

    public void setCsrq(Date csrq) {
        this.csrq = csrq;
    }

    public Integer getZjlx() {
        return zjlx;
    }

    public void setZjlx(Integer zjlx) {
        this.zjlx = zjlx;
    }

    public String getZjxs() {
        return zjxs;
    }

    public void setZjxs(String zjxs) {
        this.zjxs = zjxs;
    }

    public String getXplx() {
        return xplx;
    }

    public void setXplx(String xplx) {
        this.xplx = xplx;
    }

    public String getZjhm() {
        return zjhm;
    }

    public void setZjhm(String zjhm) {
        this.zjhm = zjhm;
    }

    public String getCardStatus() {
        return cardStatus;
    }

    public void setCardStatus(String cardStatus) {
        this.cardStatus = cardStatus;
    }

    public String getCardStatusName() {
        return cardStatusName;
    }

    public void setCardStatusName(String cardStatusName) {
        this.cardStatusName = cardStatusName;
    }

    public String getSurelyUnit() {
        return surelyUnit;
    }

    public void setSurelyUnit(String surelyUnit) {
        this.surelyUnit = surelyUnit;
    }

    public String getSurelyWay() {
        return surelyWay;
    }

    public void setSurelyWay(String surelyWay) {
        this.surelyWay = surelyWay;
    }

    public Integer getCounterNum() {
        return counterNum;
    }

    public void setCounterNum(Integer counterNum) {
        this.counterNum = counterNum;
    }
}
