package com.hxoms.modules.publicity.taskSupervise.entity;

import java.util.List;

/**
 * @Desc：TODO
 * @Author: wangyunquan
 * @Date: 2020/7/7
 */
public class DownloadBabParam {
    //id集合
    private List<String> list;
    //查询条件
    ZtDwPersionQuery ztDwPersionQuery;

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }

    public ZtDwPersionQuery getZtDwPersionQuery() {
        return ztDwPersionQuery;
    }

    public void setZtDwPersionQuery(ZtDwPersionQuery ztDwPersionQuery) {
        this.ztDwPersionQuery = ztDwPersionQuery;
    }
}
