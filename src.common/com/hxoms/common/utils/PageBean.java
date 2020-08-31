package com.hxoms.common.utils;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @Desc：分页Bean
 * @Author: wangyunquan
 * @Date: 2020/7/3
 */
@ApiModel(value = "分页实体类")
public class PageBean<E> {
    /**
     * 当前页码
     */
    @ApiModelProperty(value = "当前页码",required = true)
    private int pageNum=1;
    /**
     * 每页数量
     */
    @ApiModelProperty(value = "每页数量",required = true)
    private int pageSize=10;
    /**
     * 记录总数
     */
    @ApiModelProperty(value = "记录总数")
    private long totalSize;
    /**
     * 页码总数
     */
    @ApiModelProperty(value = "页码总数")
    private int totalPages;
    /**
     * 数据模型
     */
    @ApiModelProperty(value = "数据模型")
    private List<E> content;

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public long getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(long totalSize) {
        this.totalSize = totalSize;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public List<E> getContent() {
        return content;
    }

    public void setContent(List<E> content) {
        this.content = content;
    }
}
