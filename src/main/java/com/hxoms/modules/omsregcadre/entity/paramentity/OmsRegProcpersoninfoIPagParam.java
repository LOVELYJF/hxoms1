package com.hxoms.modules.omsregcadre.entity.paramentity;

import com.hxoms.modules.omsregcadre.entity.OmsRegProcpersoninfo;

/**
 * 登记备案信息参数实体类
 * @author lijiaojiao
 */
public class OmsRegProcpersoninfoIPagParam extends OmsRegProcpersoninfo {

    /**页码*/
    private Integer pageNum;
    /**分页大小*/
    private Integer pageSize;

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