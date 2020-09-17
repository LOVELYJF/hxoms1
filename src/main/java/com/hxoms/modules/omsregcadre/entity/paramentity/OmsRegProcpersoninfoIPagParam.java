package com.hxoms.modules.omsregcadre.entity.paramentity;

import com.hxoms.modules.omsregcadre.entity.OmsRegProcpersoninfo;

import java.util.List;

/**
 * 登记备案信息参数实体类
 * @author lijiaojiao
 */
public class OmsRegProcpersoninfoIPagParam extends OmsRegProcpersoninfo {

    /**页码*/
    private Integer pageNum;
    /**分页大小*/
    private Integer pageSize;

    private List<String> ids;

    public List<String> getIds() {
        return ids;
    }

    public void setIds(List<String> ids) {
        this.ids = ids;
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