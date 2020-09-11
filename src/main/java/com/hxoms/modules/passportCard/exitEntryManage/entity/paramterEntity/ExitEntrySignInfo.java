package com.hxoms.modules.passportCard.exitEntryManage.entity.paramterEntity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @Desc：TODO
 * @Author: wangyunquan
 * @Date: 2020/8/17
 */
@ApiModel(value = "签名信息")
public class ExitEntrySignInfo {
    //领取表id
    @ApiModelProperty(value = "领取表id")
    private String id;
    //领取人
    @ApiModelProperty(value = "领取人")
    private String getPeople;
    //领取时间
    @ApiModelProperty(value = "领取时间")
    @JsonFormat(pattern = "yyyy.MM.dd",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date getTime;
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
    //证件号码
    @ApiModelProperty(value = "证件号码")
    private String zjhm;
    //有效期
    @JsonFormat(pattern = "yyyy.MM.dd",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    @ApiModelProperty(value = "有效期")
    private Date yxqz;
    //签名
    @ApiModelProperty(value = "签名")
    private String  cerGetEleSign;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGetPeople() {
        return getPeople;
    }

    public void setGetPeople(String getPeople) {
        this.getPeople = getPeople;
    }

    public Date getGetTime() {
        return getTime;
    }

    public void setGetTime(Date getTime) {
        this.getTime = getTime;
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

    public String getCerGetEleSign() {
        return cerGetEleSign;
    }

    public void setCerGetEleSign(String cerGetEleSign) {
        this.cerGetEleSign = cerGetEleSign;
    }
}
