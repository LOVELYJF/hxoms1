package com.hxoms.modules.passportCard.certificateCollect.entity.parameterEntity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @Desc：TODO
 * @Author: wangyunquan
 * @Date: 2020/9/1
 */
@ApiModel(value = "催缴内容参数")
public class CjContentParam {
    //主键
    @ApiModelProperty(value = "主键",required = true)
    private String id;
    //姓名
    @ApiModelProperty(value = "姓名",required = true)
    private String name;
    //证照类型编码
    @ApiModelProperty(value = "证照类型编码",required = true)
    private Integer zjlx;
    //证照类型名称
    @ApiModelProperty(value = "证照类型名称",required = true)
    private String zjlxName;
    //证照号码
    @ApiModelProperty(value = "证照号码",required = true)
    private String zjhm;
    //应归还时间
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @ApiModelProperty(value = "应归还时间",required = true)
    private Date returnDate;
    //工作单位
    @ApiModelProperty(value = "工作单位",required = true)
    private String workUnit;
    //机构编码
    @ApiModelProperty(value = "机构编码",required = true)
    private String rfB0000;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getZjlx() {
        return zjlx;
    }

    public void setZjlx(Integer zjlx) {
        this.zjlx = zjlx;
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

    public String getWorkUnit() {
        return workUnit;
    }

    public void setWorkUnit(String workUnit) {
        this.workUnit = workUnit;
    }

    public String getRfB0000() {
        return rfB0000;
    }

    public void setRfB0000(String rfB0000) {
        this.rfB0000 = rfB0000;
    }
}
