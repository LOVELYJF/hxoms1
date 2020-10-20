package com.hxoms.modules.passportCard.admintorGet.entity.parameterEntiry;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @Desc：TODO
 * @Author: wangyunquan
 * @Date: 2020/10/19
 */
@ApiModel(value = "管理员取证证照信息")
public class CerInfo {
    @ApiModelProperty(value = "备案表id")
    private String omsId;

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "性别")
    private String sex;

    @ApiModelProperty(value = "出生日期")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    @JsonFormat(pattern = "yyyy.MM.dd",timezone="GMT+8")
    private Date birthDate;

    @ApiModelProperty(value = "在职状态(1在职 2辞职 3退休 4去世 5开除 6调出 7.省管变中管 8 未匹配 9其它)")
    private String incumbencyStatus;

    @ApiModelProperty(value = "在职状态名称")
    private String incumbencyStatusName;

    @ApiModelProperty(value = "机构编码")
    private String rfB0000;

    @ApiModelProperty(value = "工作单位")
    private String workUnit;

    @ApiModelProperty(value = "职务")
    private String post;

    @ApiModelProperty(value = "证照表id")
    private String cerId;

    @ApiModelProperty(value = "证件类型(1:护照,2:港澳通行证,4:台湾通行证)")
    private Integer zjlx;

    @ApiModelProperty(value = "证件类型名称")
    private String zjlxName;

    @ApiModelProperty(value = "证件号码")
    private String zjhm;

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

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getIncumbencyStatus() {
        return incumbencyStatus;
    }

    public void setIncumbencyStatus(String incumbencyStatus) {
        this.incumbencyStatus = incumbencyStatus;
    }

    public String getIncumbencyStatusName() {
        return incumbencyStatusName;
    }

    public void setIncumbencyStatusName(String incumbencyStatusName) {
        this.incumbencyStatusName = incumbencyStatusName;
    }

    public String getRfB0000() {
        return rfB0000;
    }

    public void setRfB0000(String rfB0000) {
        this.rfB0000 = rfB0000;
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

    public String getCerId() {
        return cerId;
    }

    public void setCerId(String cerId) {
        this.cerId = cerId;
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
}
