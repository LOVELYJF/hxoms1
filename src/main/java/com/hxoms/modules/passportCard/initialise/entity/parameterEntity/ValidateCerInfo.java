package com.hxoms.modules.passportCard.initialise.entity.parameterEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @Desc：TODO
 * @Author: wangyunquan
 * @Date: 2020/8/29
 */
@ApiModel(value = "证照验证参数")
public class ValidateCerInfo {

    @ApiModelProperty(value="姓名",required = true)
    private String name;

    @ApiModelProperty(value="性别",required = true)
    private String sex;

    @DateTimeFormat(pattern = "yyyy.MM.dd")
    @ApiModelProperty(value="出生日期",required = true)
    private Date csrq;

    @ApiModelProperty(value="国籍",required = true)
    private String gj;

    @ApiModelProperty(value="出生地点",required = true)
    private String csdd;

    @ApiModelProperty(value="证件类型(1:护照,2:港澳通行证,4:台湾通行证)",required = true)
    private Integer zjlx;

    @ApiModelProperty(value="证件形式(0:本式证照,1:卡式证照)",required = true)
    private String zjxs;

    @ApiModelProperty(value="芯片类型(0:自带,1:粘贴)",required = true)
    private String xplx;

    @ApiModelProperty(value="证件号码",required = true)
    private String zjhm;

    @ApiModelProperty(value="签发机关",required = true)
    private String qfjg;

    @ApiModelProperty(value="签发地点")
    private String qfdd;

    @DateTimeFormat(pattern = "yyyy.MM.dd")
    @ApiModelProperty(value="签发日期",required = true)
    private Date qfrq;

    @DateTimeFormat(pattern = "yyyy.MM.dd")
    @ApiModelProperty(value="有效期至",required = true)
    private Date yxqz;

    @ApiModelProperty(value="保管单位(0:干部监督处,1:省委统战部(台办))")
    private String surelyUnit;

    @ApiModelProperty(value="保管方式(0:证照机,1:柜台)")
    private String surelyWay;

    @ApiModelProperty(value="柜台编号")
    private Integer counterNum;

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

    public Date getCsrq() {
        return csrq;
    }

    public void setCsrq(Date csrq) {
        this.csrq = csrq;
    }

    public String getGj() {
        return gj;
    }

    public void setGj(String gj) {
        this.gj = gj;
    }

    public String getCsdd() {
        return csdd;
    }

    public void setCsdd(String csdd) {
        this.csdd = csdd;
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

    public String getQfjg() {
        return qfjg;
    }

    public void setQfjg(String qfjg) {
        this.qfjg = qfjg;
    }

    public String getQfdd() {
        return qfdd;
    }

    public void setQfdd(String qfdd) {
        this.qfdd = qfdd;
    }

    public Date getQfrq() {
        return qfrq;
    }

    public void setQfrq(Date qfrq) {
        this.qfrq = qfrq;
    }

    public Date getYxqz() {
        return yxqz;
    }

    public void setYxqz(Date yxqz) {
        this.yxqz = yxqz;
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
