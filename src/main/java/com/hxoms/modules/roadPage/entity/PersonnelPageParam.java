package com.hxoms.modules.roadPage.entity;

import io.swagger.annotations.ApiModelProperty;

public class PersonnelPageParam {
    @ApiModelProperty("分页开始")
    private Integer pageNum;
    @ApiModelProperty("分页结束")
    private Integer pageSize;
    @ApiModelProperty("人员情况标识: 1全省经办人 2重点管理人员 3一把手 4纪委不回复意见人员 5退休人员 6核心涉密人员")
    private Integer orderIndex=1;
    @ApiModelProperty("人员情况名称：全省经办人,重点管理人员,一把手,纪委不回复意见人员,退休人员,核心涉密人员")
    private String nodeName;

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

    public Integer getOrderIndex() {
        return orderIndex;
    }

    public void setOrderIndex(Integer orderIndex) {
        this.orderIndex = orderIndex;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }
}
