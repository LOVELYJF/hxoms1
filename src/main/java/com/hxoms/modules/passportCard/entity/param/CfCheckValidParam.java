package com.hxoms.modules.passportCard.entity.param;

import com.hxoms.modules.passportCard.entity.CfCheckValid;

public class CfCheckValidParam extends CfCheckValid {

    private Integer pageNum;

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
