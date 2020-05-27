package com.hxoms.modules.omsregcadre.entity.paramentity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hxoms.common.hxannotation.ColumnAnnotation;
import com.hxoms.common.hxannotation.IdAnnotation;
import com.hxoms.common.hxannotation.TableAnnotation;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 出入境记录记录参数实体类
 * @author lijiaojiao
 */
public class OmsEntryexitRecordIPagParam {

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
    //数据来源
    private String dataSource;
    //出入境状态 出1，入2
    private String ogeStatus;

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

    public String getDataSource() {
        return dataSource;
    }

    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
    }

    public String getOgeStatus() {
        return ogeStatus;
    }

    public void setOgeStatus(String ogeStatus) {
        this.ogeStatus = ogeStatus;
    }
}