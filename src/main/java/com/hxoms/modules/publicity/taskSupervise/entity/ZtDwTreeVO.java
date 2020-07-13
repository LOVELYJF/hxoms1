package com.hxoms.modules.publicity.taskSupervise.entity;


import java.util.List;

/**
 * @Desc：组团单位
 * @Author: wangyunquan
 * @Date: 2020/6/23
 */
public class ZtDwTreeVO {
    //年份
    private String year;
    //组团集合
    private List<ZtDwVo> ztDwList;

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public List<ZtDwVo> getZtDwList() {
        return ztDwList;
    }

    public void setZtDwList(List<ZtDwVo> ztDwList) {
        this.ztDwList = ztDwList;
    }
}
