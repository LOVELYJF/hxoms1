package com.hxoms.modules.passportCard.admintorGet.entity.parameterEntiry;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

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
    //职务
    @ApiModelProperty(value = "职务")
    private String post;
    //证件类型
    @ApiModelProperty(value = "证件类型")
    private Integer zjlx;
    //证件类型名称
    @ApiModelProperty(value = "证件类型名称")
    private String zjlxName;
    //证件号码
    @ApiModelProperty(value = "证件号码")
    private String zjhm;
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

}
