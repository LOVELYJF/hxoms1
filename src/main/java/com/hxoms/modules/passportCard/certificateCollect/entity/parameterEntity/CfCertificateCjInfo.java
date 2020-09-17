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
@ApiModel(value = "催缴列表信息")
public class CfCertificateCjInfo {
    //主键
    @ApiModelProperty(value = "主键")
    private String id;
    //备案表id
    @ApiModelProperty(value = "备案表id")
    private String omsId;
    //姓名
    @ApiModelProperty(value = "姓名")
    private String name;
    //性别
    @ApiModelProperty(value = "性别")
    private String sex;
    //出生日期
    @JsonFormat(pattern = "yyyy.MM.dd",timezone="GMT+8")
    @ApiModelProperty(value = "出生日期")
    private Date csrq;
    //身份证号
    @ApiModelProperty(value = "身份证号")
    private String idnumberGb;
    //户口所在地
    @ApiModelProperty(value = "户口所在地")
    private String  registeResidence;
    //工作单位编码
    @ApiModelProperty(value = "工作单位编码")
    private String rfB0000;
    //工作单位
    @ApiModelProperty(value = "工作单位")
    private String workUnit;
    //职务（级）或职称
    @ApiModelProperty(value = "职务（级）或职称")
    private String post;
    //人事主管单位
    @ApiModelProperty(value = "人事主管单位")
    private String  personManager;
    //状态
    @ApiModelProperty(value = "状态")
    private String cjStatus;
    //状态
    @ApiModelProperty(value = "状态名称")
    private String cjStatusName;
    //工作单位联系人
    @ApiModelProperty(value = "工作单位联系人")
    private String handler;
    //工作单位联系人联系电话
    @ApiModelProperty(value = "工作单位联系人联系电话")
    private String handlerPhone;
    //证照类型编码
    @ApiModelProperty(value = "证照类型编码")
    private Integer zjlx;
    //证照类型名称
    @ApiModelProperty(value = "证照类型名称")
    private String zjlxName;
    //证照号码
    @ApiModelProperty(value = "证照号码")
    private String zjhm;
    //来源编码
    @ApiModelProperty(value = "来源编码")
    private String dataSource;
    //来源名称
    @ApiModelProperty(value = "来源名称")
    private String dataSourceName;
    //业务发生时间
    @JsonFormat(pattern = "yyyy.MM.dd",timezone="GMT+8")
    @ApiModelProperty(value = "业务发生时间")
    private Date happenDate;
    //应归还时间
    @JsonFormat(pattern = "yyyy.MM.dd",timezone="GMT+8")
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

    public Date getCsrq() {
        return csrq;
    }

    public void setCsrq(Date csrq) {
        this.csrq = csrq;
    }

    public String getIdnumberGb() {
        return idnumberGb;
    }

    public void setIdnumberGb(String idnumberGb) {
        this.idnumberGb = idnumberGb;
    }

    public String getRegisteResidence() {
        return registeResidence;
    }

    public void setRegisteResidence(String registeResidence) {
        this.registeResidence = registeResidence;
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

    public String getPersonManager() {
        return personManager;
    }

    public void setPersonManager(String personManager) {
        this.personManager = personManager;
    }

    public String getCjStatus() {
        return cjStatus;
    }

    public void setCjStatus(String cjStatus) {
        this.cjStatus = cjStatus;
    }

    public String getCjStatusName() {
        return cjStatusName;
    }

    public void setCjStatusName(String cjStatusName) {
        this.cjStatusName = cjStatusName;
    }

    public String getHandler() {
        return handler;
    }

    public void setHandler(String handler) {
        this.handler = handler;
    }

    public String getHandlerPhone() {
        return handlerPhone;
    }

    public void setHandlerPhone(String handlerPhone) {
        this.handlerPhone = handlerPhone;
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

    public String getDataSource() {
        return dataSource;
    }

    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
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
