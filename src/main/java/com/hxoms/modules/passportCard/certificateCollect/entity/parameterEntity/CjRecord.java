package com.hxoms.modules.passportCard.certificateCollect.entity.parameterEntity;

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
@ApiModel(value = "催缴记录")
public class CjRecord {

    @ApiModelProperty(value="主键")
    private String id;

    @ApiModelProperty(value="催缴方式(0:电话催缴,1:短信催缴)")
    private String cjWay;

    @ApiModelProperty(value="催缴内容")
    private String cjMessage;

    @ApiModelProperty(value="催缴结果")
    private String cjResult;

    @ApiModelProperty(value="经办人姓名")
    private String name;

    @ApiModelProperty(value="经办人电话")
    private String phone;

    @ApiModelProperty(value="催缴人")
    private String cjPerson;

    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    @ApiModelProperty(value="催缴时间")
    private Date cjTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
}
