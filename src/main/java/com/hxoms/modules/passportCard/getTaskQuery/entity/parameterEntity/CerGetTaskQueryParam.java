package com.hxoms.modules.passportCard.getTaskQuery.entity.parameterEntity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @Desc：TODO
 * @Author: wangyunquan
 * @Date: 2020/8/18
 */
@ApiModel(value = "领取证照查询参数")
public class CerGetTaskQueryParam {
    //机构Id
    @ApiModelProperty(value = "机构Id")
    private String rfB0000;
    //姓名
    @ApiModelProperty(value = "姓名")
    private String name;
    //领取状态
    @ApiModelProperty(value = "领取状态")
    private String getStatus;
    //数据来源
    @ApiModelProperty(value = "数据来源")
    private String dataSource;
    //创建起始日期
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    @ApiModelProperty(value = "创建起始日期")
    private Date createStartTime;
    //创建终止日期
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    @ApiModelProperty(value = "创建终止日期")
    private Date createEndTime;
    //领取起始日期
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    @ApiModelProperty(value = "领取起始日期")
    private Date getStartTime;
    //领取终止日期
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    @ApiModelProperty(value = "领取终止日期")
    private Date getEndTime;

    public String getRfB0000() {
        return rfB0000;
    }

    public void setRfB0000(String rfB0000) {
        this.rfB0000 = rfB0000;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGetStatus() {
        return getStatus;
    }

    public void setGetStatus(String getStatus) {
        this.getStatus = getStatus;
    }

    public String getDataSource() {
        return dataSource;
    }

    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
    }

    public Date getCreateStartTime() {
        return createStartTime;
    }

    public void setCreateStartTime(Date createStartTime) {
        this.createStartTime = createStartTime;
    }

    public Date getCreateEndTime() {
        return createEndTime;
    }

    public void setCreateEndTime(Date createEndTime) {
        this.createEndTime = createEndTime;
    }

    public Date getGetStartTime() {
        return getStartTime;
    }

    public void setGetStartTime(Date getStartTime) {
        this.getStartTime = getStartTime;
    }

    public Date getGetEndTime() {
        return getEndTime;
    }

    public void setGetEndTime(Date getEndTime) {
        this.getEndTime = getEndTime;
    }
}
