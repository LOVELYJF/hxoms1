package com.hxoms.modules.passportCard.counterReturn.entity.parameterEntity;

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
 * @Date: 2020/9/17
 */
@ApiModel(value = "因私申请信息")
public class PriApplyInfo {
    @ApiModelProperty(value = "主键",required = true)
    @NotBlank(message = "id不能为空")
    private String id;
    @ApiModelProperty(value = "姓名")
    private String name;
    @ApiModelProperty(value = "工作单位")
    private String workUnit;
    @ApiModelProperty(value = "涉密等级(0:非涉密,1:一般,2:重要,3:核心)")
    private String classificationLevel;
    @ApiModelProperty(value = "出国事由")
    private String abroadReasons;
    @ApiModelProperty(value = "证件类型(1:护照,2:港澳通行证,4:台湾通行证)")
    private Integer zjlx;
    @ApiModelProperty(value = "证件号码")
    private String zjhm;
    @JsonFormat(pattern = "yyyy.MM.dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    @ApiModelProperty(value = "出国时间")
    private Date abroadTime;
    @JsonFormat(pattern = "yyyy.MM.dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    @ApiModelProperty(value = "实际出国时间", required = true)
    @NotNull(message = "实际出国时间不能为空")
    private Date realAbroadTime;
    @JsonFormat(pattern = "yyyy.MM.dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    @ApiModelProperty(value = "预计回国时间")
    private Date returnTime;
    @JsonFormat(pattern = "yyyy.MM.dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    @ApiModelProperty(value = "实际回国时间", required = true)
    @NotNull(message = "实际回国时间不能为空")
    private Date realReturnTime;
    @ApiModelProperty(value = "批准在外停留时间")
    private Integer outsideTime;
    @NotNull(message = "实际停留时间不能为空")
    @ApiModelProperty(value = "实际停留时间", required = true)
    private Integer realOutsideTime;
    @ApiModelProperty(value = "批准所赴国家")
    private String goCountry;
    @NotBlank(message = "实际所赴国家不能为空")
    @ApiModelProperty(value = "实际所赴国家", required = true)
    private String realGoCountry;
    @ApiModelProperty(value = "途径国家")
    private String realPassCountry;
    @ApiModelProperty(value = "说明情况")
    private String description;
    @ApiModelProperty(value = "是否出入境", required = true)
    @NotBlank(message = "是否出入境不能为空")
    private String isAbroda;

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

    public String getWorkUnit() {
        return workUnit;
    }

    public void setWorkUnit(String workUnit) {
        this.workUnit = workUnit;
    }

    public String getClassificationLevel() {
        return classificationLevel;
    }

    public void setClassificationLevel(String classificationLevel) {
        this.classificationLevel = classificationLevel;
    }

    public String getAbroadReasons() {
        return abroadReasons;
    }

    public void setAbroadReasons(String abroadReasons) {
        this.abroadReasons = abroadReasons;
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

    public Date getAbroadTime() {
        return abroadTime;
    }

    public void setAbroadTime(Date abroadTime) {
        this.abroadTime = abroadTime;
    }

    public Date getRealAbroadTime() {
        return realAbroadTime;
    }

    public void setRealAbroadTime(Date realAbroadTime) {
        this.realAbroadTime = realAbroadTime;
    }

    public Date getReturnTime() {
        return returnTime;
    }

    public void setReturnTime(Date returnTime) {
        this.returnTime = returnTime;
    }

    public Date getRealReturnTime() {
        return realReturnTime;
    }

    public void setRealReturnTime(Date realReturnTime) {
        this.realReturnTime = realReturnTime;
    }

    public Integer getOutsideTime() {
        return outsideTime;
    }

    public void setOutsideTime(Integer outsideTime) {
        this.outsideTime = outsideTime;
    }

    public Integer getRealOutsideTime() {
        return realOutsideTime;
    }

    public void setRealOutsideTime(Integer realOutsideTime) {
        this.realOutsideTime = realOutsideTime;
    }

    public String getGoCountry() {
        return goCountry;
    }

    public void setGoCountry(String goCountry) {
        this.goCountry = goCountry;
    }

    public String getRealGoCountry() {
        return realGoCountry;
    }

    public void setRealGoCountry(String realGoCountry) {
        this.realGoCountry = realGoCountry;
    }

    public String getRealPassCountry() {
        return realPassCountry;
    }

    public void setRealPassCountry(String realPassCountry) {
        this.realPassCountry = realPassCountry;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIsAbroda() {
        return isAbroda;
    }

    public void setIsAbroda(String isAbroda) {
        this.isAbroda = isAbroda;
    }
}
