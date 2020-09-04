package com.hxoms.modules.omsregcadre.entity.paramentity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hxoms.modules.omsregcadre.entity.OmsEntryexitRecord;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 出入境记录记录参数实体类
 * @author lijiaojiao
 */
public class OmsEntryexitRecordIPagParam extends OmsEntryexitRecord {

    /**页码*/
    private Integer pageNum;
    /**分页大小*/
    private Integer pageSize;
    //出入境开始时间
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date ogeStartDate;
    //出入境结束时间
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date ogeEndDate;
    //根据年份查询对应比对数据
    private String year;

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
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

    public Date getOgeStartDate() {
        return ogeStartDate;
    }

    public void setOgeStartDate(Date ogeStartDate) {
        this.ogeStartDate = ogeStartDate;
    }

    public Date getOgeEndDate() {
        return ogeEndDate;
    }

    public void setOgeEndDate(Date ogeEndDate) {
        this.ogeEndDate = ogeEndDate;
    }

}