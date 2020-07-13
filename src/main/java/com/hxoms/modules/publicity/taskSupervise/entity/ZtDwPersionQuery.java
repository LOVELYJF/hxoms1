package com.hxoms.modules.publicity.taskSupervise.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @Desc：组团单位查询接收参数
 * @Author: wangyunquan
 * @Date: 2020/6/24
 */
public class ZtDwPersionQuery {
    //用户id
    private String id;
    //组团名称
    private String ztDwName;
    //申请状态
    private String sqzt;
    //起始时间
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date startDate;
    //终止时间
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date endDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getZtDwName() {
        return ztDwName;
    }

    public void setZtDwName(String ztDwName) {
        this.ztDwName = ztDwName;
    }

    public String getSqzt() {
        return sqzt;
    }

    public void setSqzt(String sqzt) {
        this.sqzt = sqzt;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
