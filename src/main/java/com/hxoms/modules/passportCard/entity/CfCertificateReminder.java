package com.hxoms.modules.passportCard.entity;


/**
 * 证件催缴的名单
 */
public class CfCertificateReminder extends CfCertificate{

    /**
     * 来源
     */
    private String cfSource;

    /**
     * 上缴状态
     */
    private String status;

    /**页码*/
    private Integer pageNum;
    /**分页大小*/
    private Integer pageSize;

    public String getCfSource() {
        return cfSource;
    }

    public void setCfSource(String cfSource) {
        this.cfSource = cfSource;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
