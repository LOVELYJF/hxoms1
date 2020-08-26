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
@ApiModel(value = "证照信息")
public class CfCertificateCjInfo {
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
    @ApiModelProperty(value = "出生日期")
    @JsonFormat(pattern = "yyyy.MM.dd")
    private Date csrq;
    //身份证号
    @ApiModelProperty(value = "身份证号")
    private String idnumberGa;
    //户口所在地
    @ApiModelProperty(value = "户口所在地")
    private String  registeResidence;
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
    //工作单位联系人
    @ApiModelProperty(value = "工作单位联系人")
    private String handler;
    //工作单位联系人联系电话
    @ApiModelProperty(value = "工作单位联系人联系电话")
    private String handlerPhone;
    //证照类型
    @ApiModelProperty(value = "证照类型")
    private String zjlx;
    //证照号码
    @ApiModelProperty(value = "证照号码")
    private String zjhm;
    //来源
    @ApiModelProperty(value = "来源")
    private String dataSource;
    //业务发生时间
    @ApiModelProperty(value = "业务发生时间")
    @JsonFormat(pattern = "yyyy.MM.dd")
    private Date happenDate;
    //应归还时间
    @ApiModelProperty(value = "应归还时间")
    @JsonFormat(pattern = "yyyy.MM.dd")
    private Date returnDate;
    //催缴情况
    @ApiModelProperty(value = "催缴情况")
    private String cjResult;
    //追加催缴结果
    @ApiModelProperty(value = "追加催缴结果")
    private String addToCjResult;

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

    public String getIdnumberGa() {
        return idnumberGa;
    }

    public void setIdnumberGa(String idnumberGa) {
        this.idnumberGa = idnumberGa;
    }

    public String getRegisteResidence() {
        return registeResidence;
    }

    public void setRegisteResidence(String registeResidence) {
        this.registeResidence = registeResidence;
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

    public String getZjlx() {
        return zjlx;
    }

    public void setZjlx(String zjlx) {
        this.zjlx = zjlx;
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

    public String getAddToCjResult() {
        return addToCjResult;
    }

    public void setAddToCjResult(String addToCjResult) {
        this.addToCjResult = addToCjResult;
    }
}
