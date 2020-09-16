package com.hxoms.modules.passportCard.admintorGet.entity.parameterEntiry;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
 * @Desc：TODO
 * @Author: wangyunquan
 * @Date: 2020/8/13
 */
@ApiModel(value = "查询条件")
public class AdmintorGetQueryParam {
    //姓名
    @ApiModelProperty(value = "姓名")
    private String name;
    //机构
    @ApiModelProperty(value = "机构")
    private String rfB0000;
    //证件类型
    @ApiModelProperty(value="证件类型(1:护照,2:港澳通行证,4:台湾通行证)")
    private Integer zjlx;
    //证件号码
    @ApiModelProperty(value = "证件号码")
    private String zjhm;
    //证照状态
    @ApiModelProperty(value="证照状态(0:正常,1:过期,2:注销,3:验证失败,4:已验证,5:待验证,6:借出,7:待领取,8:已领取)")
    private String cardStatus;

    @ApiModelProperty(value = "取证人")
    private String operator;

    @DateTimeFormat(pattern = "yyyy.MM.dd")
    @JsonFormat(pattern = "yyyy.MM.dd",timezone="GMT+8")
    @ApiModelProperty(value = "取证起始时间")
    private Date operateStartTime;

    @DateTimeFormat(pattern = "yyyy.MM.dd")
    @JsonFormat(pattern = "yyyy.MM.dd",timezone="GMT+8")
    @ApiModelProperty(value = "取证终止时间")
    private Date operateEndTime;
    //姓名集合
    @ApiModelProperty(value="姓名集合")
    private List<String> nameList;

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

    public String getCardStatus() {
        return cardStatus;
    }

    public void setCardStatus(String cardStatus) {
        this.cardStatus = cardStatus;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public Date getOperateStartTime() {
        return operateStartTime;
    }

    public void setOperateStartTime(Date operateStartTime) {
        this.operateStartTime = operateStartTime;
    }

    public Date getOperateEndTime() {
        return operateEndTime;
    }

    public void setOperateEndTime(Date operateEndTime) {
        this.operateEndTime = operateEndTime;
    }

    public List<String> getNameList() {
        return nameList;
    }

    public void setNameList(List<String> nameList) {
        this.nameList = nameList;
    }
}
