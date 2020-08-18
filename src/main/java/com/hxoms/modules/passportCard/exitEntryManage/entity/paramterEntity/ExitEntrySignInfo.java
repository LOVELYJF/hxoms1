package com.hxoms.modules.passportCard.exitEntryManage.entity.paramterEntity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * @Desc：TODO
 * @Author: wangyunquan
 * @Date: 2020/8/17
 */
public class ExitEntrySignInfo {
    //领取表id
    private String id;
    //领取人
    private String getPeopol;
    //领取时间
    private String getTime;
    //姓名
    private String name;
    //职务
    private String post;
    //证件类型
    private int zjlx;
    //证件号码
    private String zjhm;
    @JsonFormat(pattern = "yyyy.MM.dd")
    //有效期
    private Date yxqz;

    private String  cerGetEleSign;
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGetPeopol() {
        return getPeopol;
    }

    public void setGetPeopol(String getPeopol) {
        this.getPeopol = getPeopol;
    }

    public String getGetTime() {
        return getTime;
    }

    public void setGetTime(String getTime) {
        this.getTime = getTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public int getZjlx() {
        return zjlx;
    }

    public void setZjlx(int zjlx) {
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
