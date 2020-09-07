package com.hxoms.modules.passportCard.getTaskQuery.entity.parameterEntity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @Desc：TODO
 * @Author: wangyunquan
 * @Date: 2020/8/18
 */
@ApiModel(value = "证照领取查询列表")
public class CerGetTaskInfo {
    //主键ID
    @ApiModelProperty(value = "主键ID")
    private String id;
    //证照表ID
    @ApiModelProperty(value = "证照表ID")
    private String cerId;
    //机构编码
    @ApiModelProperty(value = "机构编码")
    private String rfB0000;
    //工作单位
    @ApiModelProperty(value = "工作单位")
    private String workUnit;
    //姓名
    @ApiModelProperty(value = "姓名")
    private String name;
    //性别
    @ApiModelProperty(value = "性别")
    private String sex;
    //出生日期
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    @ApiModelProperty(value = "出生日期")
    private Date csrq;
    //职务（级）或职称
    @ApiModelProperty(value = "职务（级）或职称")
    private String post;
    //领取状态
    @ApiModelProperty(value = "领取状态")
    private String getStatus;
    //领取状态名称
    @ApiModelProperty(value = "领取状态名称")
    private String getStatusName;
    //证照类型
    @ApiModelProperty(value = "证照类型")
    private Integer zjlx;
    //证照类型名称
    @ApiModelProperty(value = "证照类型名称")
    private String zjlxName;
    //证件号码
    @ApiModelProperty(value = "证件号码")
    private String zjhm;
    //有效期至
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    @ApiModelProperty(value = "有效期至")
    private Date yxqz;
    //证照状态
    @ApiModelProperty(value = "证照状态")
    private String cardStatus;
    //审批表
    @ApiModelProperty(value = "审批表")
    private String spb;
    //保管方式
    @ApiModelProperty(value = "保管方式")
    private String surelyWay;
    //机柜
    @ApiModelProperty(value = "机柜")
    private String cabinetNum;
    //位置
    @ApiModelProperty(value = "位置")
    private String place;
    //柜台编号
    @ApiModelProperty(value = "柜台编号")
    private Integer counterNum;
    //来源
    @ApiModelProperty(value = "来源")
    private String dataSource;
    //来源名称
    @ApiModelProperty(value = "来源名称")
    private String dataSourceName;
    //任务产生日期
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    @ApiModelProperty(value = "任务产生日期")
    private Date createTime;
    //领取时间
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    @ApiModelProperty(value = "领取时间")
    private Date getTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCerId() {
        return cerId;
    }

    public void setCerId(String cerId) {
        this.cerId = cerId;
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

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getGetStatus() {
        return getStatus;
    }

    public void setGetStatus(String getStatus) {
        this.getStatus = getStatus;
    }

    public String getGetStatusName() {
        return getStatusName;
    }

    public void setGetStatusName(String getStatusName) {
        this.getStatusName = getStatusName;
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

    public Date getYxqz() {
        return yxqz;
    }

    public void setYxqz(Date yxqz) {
        this.yxqz = yxqz;
    }

    public String getCardStatus() {
        return cardStatus;
    }

    public void setCardStatus(String cardStatus) {
        this.cardStatus = cardStatus;
    }

    public String getSpb() {
        return spb;
    }

    public void setSpb(String spb) {
        this.spb = spb;
    }

    public String getSurelyWay() {
        return surelyWay;
    }

    public void setSurelyWay(String surelyWay) {
        this.surelyWay = surelyWay;
    }

    public String getCabinetNum() {
        return cabinetNum;
    }

    public void setCabinetNum(String cabinetNum) {
        this.cabinetNum = cabinetNum;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public Integer getCounterNum() {
        return counterNum;
    }

    public void setCounterNum(Integer counterNum) {
        this.counterNum = counterNum;
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getGetTime() {
        return getTime;
    }

    public void setGetTime(Date getTime) {
        this.getTime = getTime;
    }
}
