package com.hxoms.modules.passportCard.certificateManage.entity.parameterEntity;

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
@ApiModel(value = "界面保存证照")
public class CerInfoSave {

    @NotBlank(message = "姓名不能为空")
    @ApiModelProperty(value="姓名",required = true)
    private String name;

    @NotBlank(message = "性别不能为空")
    @ApiModelProperty(value="性别",required = true)
    private String sex;

    @NotNull(message = "出生日期不能为空")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    @ApiModelProperty(value="出生日期",required = true)
    private Date csrq;

    @NotBlank(message = "国籍不能为空")
    @ApiModelProperty(value="国籍",required = true)
    private String gj;

    @NotBlank(message = "出生地点不能为空")
    @ApiModelProperty(value="出生地点",required = true)
    private String csdd;

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

    @NotBlank(message = "签发机关不能为空")
    @ApiModelProperty(value="签发机关",required = true)
    private String qfjg;


    @ApiModelProperty(value="签发地点")
    private String qfdd;

    @NotNull(message = "签发日期不能为空")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    @ApiModelProperty(value="签发日期",required = true)
    private Date qfrq;

    @NotNull(message = "有效期至不能为空")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    @ApiModelProperty(value="有效期至",required = true)
    private Date yxqz;

    @NotNull(message = "备案信息表ID不能为空")
    @ApiModelProperty(value="备案信息表ID",required = true)
    private String omsId;

    @NotBlank(message = "人员主键不能为空")
    @ApiModelProperty(value="人员主键",required = true)
    private String a0100;

    @NotBlank(message = "身份证号码不能为空")
    @ApiModelProperty(value="身份证号码",required = true)
    private String a0184;

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

    public String getOmsId() {
        return omsId;
    }

    public void setOmsId(String omsId) {
        this.omsId = omsId;
    }

    public String getA0100() {
        return a0100;
    }

    public void setA0100(String a0100) {
        this.a0100 = a0100;
    }

    public String getA0184() {
        return a0184;
    }

    public void setA0184(String a0184) {
        this.a0184 = a0184;
    }
}
