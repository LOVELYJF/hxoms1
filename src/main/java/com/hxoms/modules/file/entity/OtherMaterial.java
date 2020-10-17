package com.hxoms.modules.file.entity;/*
 * @description:其它材料处理方式
 * @author 杨波
 * @date:2020-10-15
 */

public class OtherMaterial implements Comparable<OtherMaterial> {
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Integer getIsRequired() {
        return isRequired;
    }

    public void setIsRequired(Integer isRequired) {
        this.isRequired = isRequired;
    }

    public Integer getSortId() {
        return sortId;
    }

    public void setSortId(Integer sortId) {
        this.sortId = sortId;
    }

    //omsfile的id
    private String id;
    //文件名
    private String fileName;
    //用户是否勾选
    private Integer isRequired;
    //序号
    private Integer sortId;

    @Override
    public int compareTo(OtherMaterial o) {
        return this.sortId-o.getSortId();
    }
}
