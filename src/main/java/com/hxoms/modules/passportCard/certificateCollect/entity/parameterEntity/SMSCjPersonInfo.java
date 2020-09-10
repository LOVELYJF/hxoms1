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
 * @Date: 2020/9/9
 */
@ApiModel(value = "短信催缴人员")
public class SMSCjPersonInfo {
    //id
    @NotBlank(message = "id不能为空")
    @ApiModelProperty(value = "id",required = true)
    private String id;
    //状态
    @NotBlank(message = "状态不能为空")
    @ApiModelProperty(value = "状态",required = true)
    private String cjStatus;
    //姓名
    @NotBlank(message = "姓名不能为空")
    @ApiModelProperty(value = "姓名",required = true)
    private String name;
    //证件类型名称
    @NotBlank(message = "证件类型名称不能为空")
    @ApiModelProperty(value = "证件类型名称",required = true)
    private String zjlxName;

    //证件号码
    @NotBlank(message = "证件号码不能为空")
    @ApiModelProperty(value = "证件号码",required = true)
    private String zjhm;
    //归还时间
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    @NotNull(message = "归还时间不能为空")
    @ApiModelProperty(value = "归还时间",required = true)
    private Date returnDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCjStatus() {
        return cjStatus;
    }

    public void setCjStatus(String cjStatus) {
        this.cjStatus = cjStatus;
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

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }
}
