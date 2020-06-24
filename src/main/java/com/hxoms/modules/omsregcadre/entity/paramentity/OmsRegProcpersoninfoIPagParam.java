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
    //数据类型1干部   0公安
    private String dataType;
    //备案机构
    private String rfB0000;

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

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getRfB0000() {
        return rfB0000;
    }

    public void setRfB0000(String rfB0000) {
        this.rfB0000 = rfB0000;
    }
}