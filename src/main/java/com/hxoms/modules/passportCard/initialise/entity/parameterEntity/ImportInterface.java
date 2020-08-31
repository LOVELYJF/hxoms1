package com.hxoms.modules.passportCard.initialise.entity.parameterEntity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * @Desc：TODO
 * @Author: wangyunquan
 * @Date: 2020/8/29
 */
@ApiModel(value = "导入界面展示信息")
public class ImportInterface {
    @ApiModelProperty(value="姓名",required = true)
    private String name;

    @ApiModelProperty(value="性别",required = true)
    private String sex;

    @ApiModelProperty(value="证件类型(1:护照,2:港澳通行证,4:台湾通行证)",required = true)
    private Integer zjlx;

    @ApiModelProperty(value="证件号码",required = true)
    private String zjhm;

    @JsonFormat(pattern = "yyyy.MM.dd")
    @ApiModelProperty(value="有效期至",required = true)
    private Date yxqz;

    @JsonFormat(pattern = "yyyy.MM.dd")
    @ApiModelProperty(value="出生日期",required = true)
    private Date csrq;

    @ApiModelProperty(value="签发机关",required = true)
    private String qfjg;

    @JsonFormat(pattern = "yyyy.MM.dd")
    @ApiModelProperty(value="签发日期",required = true)
    private Date qfrq;

    @ApiModelProperty(value="出生地点",required = true)
    private String csdd;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
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

    public Date getYxqz() {
        return yxqz;
    }

    public void setYxqz(Date yxqz) {
        this.yxqz = yxqz;
    }

    public Date getCsrq() {
        return csrq;
    }

    public void setCsrq(Date csrq) {
        this.csrq = csrq;
    }

    public String getQfjg() {
        return qfjg;
    }

    public void setQfjg(String qfjg) {
        this.qfjg = qfjg;
    }

    public Date getQfrq() {
        return qfrq;
    }

    public void setQfrq(Date qfrq) {
        this.qfrq = qfrq;
    }

    public String getCsdd() {
        return csdd;
    }

    public void setCsdd(String csdd) {
        this.csdd = csdd;
    }
}
