package com.hxoms.modules.passportCard.printGetQrCode.entity.parameterEntity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @Desc：TODO
 * @Author: wangyunquan
 * @Date: 2020/8/20
 */
@ApiModel(value = "打印二维码列表")
public class CanGetCerInfo {
    //领取表id
    @ApiModelProperty(value = "领取表id")
    private String id;
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
    @JsonFormat(pattern = "yyyy.MM.dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    @ApiModelProperty(value = "出生日期")
    private Date csrq;
    //职务
    @ApiModelProperty(value = "职务")
    private String post;
    //状态
    @ApiModelProperty(value = "状态")
    private String getStatus;
    //证件类型
    @ApiModelProperty(value = "证件类型")
    private Integer zjlx;
    //证件类型名称
    @ApiModelProperty(value = "证件类型名称")
    private String zjlxName;
    //证件号码
    @ApiModelProperty(value = "证件号码")
    private String zjhm;
    //有效期至
    @JsonFormat(pattern = "yyyy.MM.dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    @ApiModelProperty(value = "有效期至")
    private Date yxqz;
    //审批表
    @ApiModelProperty(value = "审批表")
    private String spb;
    //保管方式
    @ApiModelProperty(value = "保管方式(0:证照机,1:柜台)")
    private String surelyWay;
    //保管方式名称
    @ApiModelProperty(value = "保管方式名称")
    private String surelyWayName;

    //机柜编号
    @ApiModelProperty(value = "机柜编号")
    private String cabinetNum;

    //机柜位置
    @ApiModelProperty(value = "机柜位置")
    private String place;

    //柜台编号
    @ApiModelProperty(value = "柜台编号")
    private String counterNum;

    //来源
    @ApiModelProperty(value = "来源")
    private String dataSource;
    //来源名称
    @ApiModelProperty(value = "来源名称")
    private String dataSourceName;
    //业务日期
    @JsonFormat(pattern = "yyyy.MM.dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    @ApiModelProperty(value = "业务日期")
    private Date happenDate;
    //生成日期
    @JsonFormat(pattern = "yyyy.MM.dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    @ApiModelProperty(value = "生成日期")
    private Date createTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getSurelyWayName() {
        return surelyWayName;
    }

    public void setSurelyWayName(String surelyWayName) {
        this.surelyWayName = surelyWayName;
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

    public String getCounterNum() {
        return counterNum;
    }

    public void setCounterNum(String counterNum) {
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

    public Date getHappenDate() {
        return happenDate;
    }

    public void setHappenDate(Date happenDate) {
        this.happenDate = happenDate;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
