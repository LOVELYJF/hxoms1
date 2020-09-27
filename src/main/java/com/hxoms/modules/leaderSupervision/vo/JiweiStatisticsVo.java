package com.hxoms.modules.leaderSupervision.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import oracle.jdbc.driver.DatabaseError;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @authore:wjf
 * @data 2020/9/23 11:06
 * @Description:
 ***/
public class JiweiStatisticsVo {

    private Integer pageNum;  /**页码*/

    private Integer pageSize;   /**分页大小*/

    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date beginDate; /** 征求纪委开始 时间 **/

    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date endDate; /** 征求纪委 介绍时间 **/

    private String jiWeiType; /** 1 同意  2 不同意  3 不回复  **/


    private String bussinessType; /** 因公 因私 **/








    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }


    public String getJiWeiType() {
        return jiWeiType;
    }

    public void setJiWeiType(String jiWeiType) {
        this.jiWeiType = jiWeiType;
    }

    public String getBussinessType() {
        return bussinessType;
    }

    public void setBussinessType(String bussinessType) {
        this.bussinessType = bussinessType;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
