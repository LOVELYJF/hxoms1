package com.hxoms.modules.omsmobilizingcadres.entity;

import java.util.List;

/**
 * 功能描述: <br>
 * 〈接收参数类〉
 * @Param:
 * @Return:
 * @Author: 李逍遥
 * @Date: 2020/9/15 16:03
 */
public class OmsMobilizingVO {
    private List<String> orgIds;
    private String name;
    private String status;

    public List<String> getOrgIds() {
        return orgIds;
    }

    public void setOrgIds(List<String> orgIds) {
        this.orgIds = orgIds;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
