package com.hxoms.modules.passportCard.certificateCollect.entity.parameterEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @Desc：TODO
 * @Author: wangyunquan
 * @Date: 2020/8/12
 */
@ApiModel(value = "催缴任务查询条件")
public class CfCertificateCjQueryParam {
    //姓名
    @ApiModelProperty(value = "姓名")
    private String name;
    //单位
    @ApiModelProperty(value = "单位")
    private String workUnit;
    //催缴状态
    @ApiModelProperty(value = "催缴状态")
    private String cjStatus;
    //来源
    @ApiModelProperty(value = "来源")
    private String dataSource;
    //归还起始日期
    @ApiModelProperty(value = "归还起始日期")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date returnStartDate;
    //归还终止日期
    @ApiModelProperty(value = "归还终止日期")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date returnEndDate;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWorkUnit() {
        return workUnit;
    }

    public void setWorkUnit(String workUnit) {
        this.workUnit = workUnit;
    }

    public String getCjStatus() {
        return cjStatus;
    }

    public void setCjStatus(String cjStatus) {
        this.cjStatus = cjStatus;
    }

    public String getDataSource() {
        return dataSource;
    }

    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
    }

    public Date getReturnStartDate() {
        return returnStartDate;
    }

    public void setReturnStartDate(Date returnStartDate) {
        this.returnStartDate = returnStartDate;
    }

    public Date getReturnEndDate() {
        return returnEndDate;
    }

    public void setReturnEndDate(Date returnEndDate) {
        this.returnEndDate = returnEndDate;
    }
}
