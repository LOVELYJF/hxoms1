package com.hxoms.modules.passportCard.certificateCollect.entity.parameterEntity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * @Desc：TODO
 * @Author: wangyunquan
 * @Date: 2020/8/12
 */
@ApiModel(value = "电话催缴列表信息")
public class CfCertificatePhoneCjInfo {
    //主键
    @ApiModelProperty(value = "主键")
    private String id;
    //姓名
    @ApiModelProperty(value = "姓名")
    private String name;
    //性别
    @ApiModelProperty(value = "性别")
    private String sex;
    //出生日期
    @JsonFormat(pattern = "yyyy.MM.dd")
    @ApiModelProperty(value = "出生日期")
    private Date csrq;
    //机构编码
    @ApiModelProperty(value = "机构编码")
    private String rfB0000;
    //工作单位
    @ApiModelProperty(value = "工作单位")
    private String workUnit;
    //职务（级）或职称
    @ApiModelProperty(value = "职务（级）或职称")
    private String post;
    //证照类型编码
    @ApiModelProperty(value = "证照类型编码")
    private Integer zjlx;
    //证照类型名称
    @ApiModelProperty(value = "证照类型名称")
    private String zjlxName;
    //证照号码
    @ApiModelProperty(value = "证照号码")
    private String zjhm;
    //来源名称
    @ApiModelProperty(value = "来源名称")
    private String dataSourceName;
    //业务发生时间
    @JsonFormat(pattern = "yyyy.MM.dd")
    @ApiModelProperty(value = "业务发生时间")
    private Date happenDate;
    //应归还时间
    @JsonFormat(pattern = "yyyy.MM.dd")
    @ApiModelProperty(value = "应归还时间")
    private Date returnDate;
    //催缴情况
    @ApiModelProperty(value = "催缴情况")
    private String cjResult;

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

    public String getDataSourceName() {
        return dataSourceName;
    }

    public void setDataSourceName(String dataSourceName) {
        this.dataSourceName = dataSourceName;
    }

    public Date getHappenDate() {
        return happenDate;
    }

    public void setHappenDate(Date happenDate) {
        this.happenDate = happenDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public String getCjResult() {
        return cjResult;
    }

    public void setCjResult(String cjResult) {
        this.cjResult = cjResult;
    }
}
