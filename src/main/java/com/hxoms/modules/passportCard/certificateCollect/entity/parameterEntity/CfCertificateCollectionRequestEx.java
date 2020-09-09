package com.hxoms.modules.passportCard.certificateCollect.entity.parameterEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @Desc：TODO
 * @Author: wangyunquan
 * @Date: 2020/8/12
 */
@ApiModel(value = "保存催缴结果提交信息")
public class CfCertificateCollectionRequestEx{

    @NotBlank(message = "id不能为空")
    @ApiModelProperty(value="数据列表id",required = true)
    private String cerCjId;

    @NotNull(message = "证件类型不能为空")
    @ApiModelProperty(value="证件类型(1:护照,2:港澳通行证,4:台湾通行证)",required = true)
    private Integer zjlx;

    @NotBlank(message = "证件号码不能为空")
    @ApiModelProperty(value="证件号码",required = true)
    private String zjhm;

    @NotBlank(message = "催缴方式不能为空")
    @ApiModelProperty(value="催缴方式(0:电话催缴,1:短信催缴)",required = true)
    private String cjWay;

    @NotBlank(message = "催缴内容不能为空")
    @ApiModelProperty(value="催缴内容",required = true)
    private String cjMessage;

    @NotBlank(message = "催缴结果不能为空")
    @ApiModelProperty(value="催缴结果",required = true)
    private String cjResult;

    @NotBlank(message = "经办人用户ID不能为空")
    @ApiModelProperty(value="经办人用户ID",required = true)
    private String userId;

    @NotBlank(message = "经办人姓名不能为空")
    @ApiModelProperty(value="经办人姓名",required = true)
    private String name;

    @NotBlank(message = "经办人电话不能为空")
    @ApiModelProperty(value="经办人电话",required = true)
    private String phone;

    @NotBlank(message = "催缴人不能为空")
    @ApiModelProperty(value="催缴人",required = true)
    private String cjPerson;

    @NotNull(message = "催缴时间不能为空")
    @ApiModelProperty(value="催缴时间",required = true)
    private Date cjTime;

    @NotBlank(message = "催缴情况不能为空")
    @ApiModelProperty(value = "催缴情况",required = true)
    private String allCjResult;

    public String getCerCjId() {
        return cerCjId;
    }

    public void setCerCjId(String cerCjId) {
        this.cerCjId = cerCjId;
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

    public String getCjWay() {
        return cjWay;
    }

    public void setCjWay(String cjWay) {
        this.cjWay = cjWay;
    }

    public String getCjMessage() {
        return cjMessage;
    }

    public void setCjMessage(String cjMessage) {
        this.cjMessage = cjMessage;
    }

    public String getCjResult() {
        return cjResult;
    }

    public void setCjResult(String cjResult) {
        this.cjResult = cjResult;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCjPerson() {
        return cjPerson;
    }

    public void setCjPerson(String cjPerson) {
        this.cjPerson = cjPerson;
    }

    public Date getCjTime() {
        return cjTime;
    }

    public void setCjTime(Date cjTime) {
        this.cjTime = cjTime;
    }

    public String getAllCjResult() {
        return allCjResult;
    }

    public void setAllCjResult(String allCjResult) {
        this.allCjResult = allCjResult;
    }
}
