package com.hxoms.modules.passportCard.initialise.entity.parameterEntity;

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
 * @Date: 2020/8/30
 */
@ApiModel(value = "存疑处理请求信息(证照信息为准)")
public class QureyDealRequestInfoEx {

    @NotBlank(message = "主键不能为空")
    @ApiModelProperty(value="主键",required = true)
    private String id;

    @NotBlank(message = "姓名不能为空")
    @ApiModelProperty(value="姓名",required = true)
    private String name;

    @NotBlank(message = "性别不能为空")
    @ApiModelProperty(value="性别(1:男,2:女)",required = true)
    private String sex;


    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    @ApiModelProperty(value="出生日期",required = true)
    @NotNull(message = "出生日期不能为空")
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

    @NotBlank(message = "证件号码不能为空")
    @ApiModelProperty(value="证件号码",required = true)
    private String zjhm;

    @NotBlank(message = "签发机关不能为空")
    @ApiModelProperty(value="签发机关",required = true)
    private String qfjg;

    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    @ApiModelProperty(value="签发日期",required = true)
    @NotNull(message = "签发日期不能为空")
    private Date qfrq;

    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    @ApiModelProperty(value="有效期至",required = true)
    @NotNull(message = "有效期至不能为空")
    private Date yxqz;

    @NotBlank(message = "异常处理结论不能为空")
    @ApiModelProperty(value="异常处理结论",required = true)
    private String exceptionConclusion;

    @NotBlank(message = "异常处理人不能为空")
    @ApiModelProperty(value="异常处理人",required = true)
    private String exceptionHandler;


    @DateTimeFormat(pattern = "yyyy.MM.dd")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @ApiModelProperty(value="异常处理时间",required = true)
    @NotNull(message = "异常处理时间不能为空")
    private Date exceptionSolvedate;

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

    public String getExceptionConclusion() {
        return exceptionConclusion;
    }

    public void setExceptionConclusion(String exceptionConclusion) {
        this.exceptionConclusion = exceptionConclusion;
    }

    public String getExceptionHandler() {
        return exceptionHandler;
    }

    public void setExceptionHandler(String exceptionHandler) {
        this.exceptionHandler = exceptionHandler;
    }

    public Date getExceptionSolvedate() {
        return exceptionSolvedate;
    }

    public void setExceptionSolvedate(Date exceptionSolvedate) {
        this.exceptionSolvedate = exceptionSolvedate;
    }
}
