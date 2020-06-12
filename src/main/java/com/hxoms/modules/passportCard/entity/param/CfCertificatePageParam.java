package com.hxoms.modules.passportCard.entity.param;

import com.hxoms.modules.passportCard.entity.CfCertificate;

public class CfCertificatePageParam extends CfCertificate {

    /**页码*/
    private Integer pageNum;
    /**分页大小*/
    private Integer pageSize;

    private String incumbencyStatus;

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

    @Override
    public String getIncumbencyStatus() {
        return incumbencyStatus;
    }

    @Override
    public void setIncumbencyStatus(String incumbencyStatus) {
        this.incumbencyStatus = incumbencyStatus;
    }
}