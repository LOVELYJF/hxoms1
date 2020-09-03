package com.hxoms.modules.omsregcadre.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hxoms.common.hxannotation.ColumnAnnotation;
import com.hxoms.common.hxannotation.IdAnnotation;
import com.hxoms.common.hxannotation.TableAnnotation;
import com.hxoms.modules.privateabroad.entity.OmsPriApply;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 出入境记录返回类
 * @author lijiaojiao
 */
public class OmsEntryexitRecordVO{

    @ApiModelProperty(value="姓名")
    private String name;
    @ApiModelProperty(value="性别")
    private String sex;
    @ApiModelProperty(value="单位")
    private String workUnit;
    @ApiModelProperty(value="职务")
    private String post;
    @ApiModelProperty(value="计划出境时间")
    private Date abroadTime;
    @ApiModelProperty(value="计划入境时间")
    private Date returnTime;
    @ApiModelProperty(value="计划前往地")
    private String goCountry;
    @ApiModelProperty(value="申请id")
    private String priapplyId;
    @ApiModelProperty(value="实际出入境时间")
    private Date ogeDate;
    @ApiModelProperty(value="出入境状态 出1，入2")
    private Integer ogeStatus;
    @ApiModelProperty(value="实际前往地")
    private String destination;
    @ApiModelProperty(value="证件种类")
    private Integer idType;
    @ApiModelProperty(value="出入口岸")
    private String entraceExit;
    @ApiModelProperty(value="比对结果")
    private String comparisonResult;
    @ApiModelProperty(value="证件号码")
    private String idNumber;
    @ApiModelProperty(value="未经申请延期回国总数")
    private Integer wjsqyqcount;
    @ApiModelProperty(value="未经审批出国境总数")
    private Integer wjspcount;
    @ApiModelProperty(value="擅自变更目的地总数")
    private Integer szbgcount;
    @ApiModelProperty(value="已比对的因私申请总数")
    private Integer yssqcount;
    @ApiModelProperty(value="已比对的出入境记录总数")
    private Integer crjcount;

    public Integer getWjsqyqcount() {
        return wjsqyqcount;
    }

    public void setWjsqyqcount(Integer wjsqyqcount) {
        this.wjsqyqcount = wjsqyqcount;
    }

    public Integer getWjspcount() {
        return wjspcount;
    }

    public void setWjspcount(Integer wjspcount) {
        this.wjspcount = wjspcount;
    }

    public Integer getSzbgcount() {
        return szbgcount;
    }

    public void setSzbgcount(Integer szbgcount) {
        this.szbgcount = szbgcount;
    }

    public Integer getYssqcount() {
        return yssqcount;
    }

    public void setYssqcount(Integer yssqcount) {
        this.yssqcount = yssqcount;
    }

    public Integer getCrjcount() {
        return crjcount;
    }

    public void setCrjcount(Integer crjcount) {
        this.crjcount = crjcount;
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

    public String getWorkUnit() {
        return workUnit;
    }

    public void setWorkUnit(String workUnit) {
        this.workUnit = workUnit;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public Date getAbroadTime() {
        return abroadTime;
    }

    public void setAbroadTime(Date abroadTime) {
        this.abroadTime = abroadTime;
    }

    public Date getReturnTime() {
        return returnTime;
    }

    public void setReturnTime(Date returnTime) {
        this.returnTime = returnTime;
    }

    public String getGoCountry() {
        return goCountry;
    }

    public void setGoCountry(String goCountry) {
        this.goCountry = goCountry;
    }

    public String getPriapplyId() {
        return priapplyId;
    }

    public void setPriapplyId(String priapplyId) {
        this.priapplyId = priapplyId;
    }

    public Date getOgeDate() {
        return ogeDate;
    }

    public void setOgeDate(Date ogeDate) {
        this.ogeDate = ogeDate;
    }

    public Integer getOgeStatus() {
        return ogeStatus;
    }

    public void setOgeStatus(Integer ogeStatus) {
        this.ogeStatus = ogeStatus;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public Integer getIdType() {
        return idType;
    }

    public void setIdType(Integer idType) {
        this.idType = idType;
    }

    public String getEntraceExit() {
        return entraceExit;
    }

    public void setEntraceExit(String entraceExit) {
        this.entraceExit = entraceExit;
    }

    public String getComparisonResult() {
        return comparisonResult;
    }

    public void setComparisonResult(String comparisonResult) {
        this.comparisonResult = comparisonResult;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }
}