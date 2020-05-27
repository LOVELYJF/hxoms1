package com.hxoms.modules.omsregcadre.entity.paramentity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 省管变非省管参数实体类
 * @author lijiaojiao
 */
public class OmsPmChangeNonpmIPageParam {

    /**页码*/
    private Integer pageNum;
    /**分页大小*/
    private Integer pageSize;
    //开始时间
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date startDate;
    //结束时间
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date endDate;
    /**
     * 状态(填写0，已上报1（经办人上报干部监督处）
     * 已审批2（干部监督处已审批）
     * 已抽取3（登记备案工作已经抽取数据）
     * 已备案4（登记备案结果已确认）
     * 退回5（资料不全，干部监督处退回）、
     * 拒批9（干部监督处不允许撤销）)
     */
    private String status;

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
