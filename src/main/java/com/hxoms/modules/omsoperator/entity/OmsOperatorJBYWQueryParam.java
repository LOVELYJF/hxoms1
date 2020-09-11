package com.hxoms.modules.omsoperator.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
 * 功能描述: <br>
 * 〈经办人经办业务查询参数〉
 * @Param:
 * @Return:
 * @Author: 李逍遥
 * @Date: 2020/9/11 9:38
 */
public class OmsOperatorJBYWQueryParam {
    /**页码*/
    private Integer pageNum;
    /**分页大小*/
    private Integer pageSize;
    /** 业务发生时间*/
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date startTime;

    /** 业务结束时间*/
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date endTime;

    /** 业务类别集合*/
    private List<String> businessType;

    /** 经办人ID*/
    private String operatorId;


    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public List<String> getBusinessType() {
        return businessType;
    }

    public void setBusinessType(List<String> businessType) {
        this.businessType = businessType;
    }

    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }
}
