package com.hxoms.modules.omsregcadre.entity.paramentity;

import com.hxoms.modules.omsregcadre.entity.OmsRegProcpersoninfo;
import com.hxoms.modules.omsregcadre.entity.OmsRegYearcheckinfo;

/**
 * 登记备案信息参数实体类
 * @author lijiaojiao
 */
public class OmsRegYearCheckIPagParam extends OmsRegYearcheckinfo {

    /**页码*/
    private Integer pageNum;
    /**分页大小*/
    private Integer pageSize;

    private String year;

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

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }
}