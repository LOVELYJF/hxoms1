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


    private int num;
    private String id;
    private String omsId;
    @ApiModelProperty(value="姓")
    private String surname;
    @ApiModelProperty(value="名")
    private String name;
    @ApiModelProperty(value="性别")
    private String sex;
    @ApiModelProperty(value="单位")
    private String workUnit;
    @ApiModelProperty(value="职务")
    private String post;
    @ApiModelProperty(value="计划出境时间")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date abroadTime;
    @ApiModelProperty(value="计划入境时间")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date returnTime;
    @ApiModelProperty(value="计划前往地")
    private String goCountry;
    @ApiModelProperty(value="事由")
    private String abroadReasons;
    @ApiModelProperty(value="申请id")
    private String priapplyId;
    @ApiModelProperty(value="实际出境时间")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date realAbroadTime;
    @ApiModelProperty(value="实际入境时间")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date realReturnTime;
    @ApiModelProperty(value="出入境时间")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
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
    @ApiModelProperty(value="类别")
    private String leibie;

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOmsId() {
        return omsId;
    }

    public void setOmsId(String omsId) {
        this.omsId = omsId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
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

    public Date getRealAbroadTime() {
        return realAbroadTime;
    }

    public void setRealAbroadTime(Date realAbroadTime) {
        this.realAbroadTime = realAbroadTime;
    }

    public Date getRealReturnTime() {
        return realReturnTime;
    }

    public void setRealReturnTime(Date realReturnTime) {
        this.realReturnTime = realReturnTime;
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

    public String getAbroadReasons() {
        return abroadReasons;
    }

    public void setAbroadReasons(String abroadReasons) {
        this.abroadReasons = abroadReasons;
    }

    public String getLeibie() {
        return leibie;
    }

    public void setLeibie(String leibie) {
        this.leibie = leibie;
    }
}