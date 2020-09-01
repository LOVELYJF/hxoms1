package com.hxoms.modules.publicity.entity;

import java.util.List;

/**
 * 功能描述: <br>
 * 〈返回前端的人员台办批文号实体类〉
 * @Param:
 * @Return:
 * @Author: 李逍遥
 * @Date: 2020/8/31 10:33
 */
public class PWHTreeVO {
    //年份
    private String year;
    //批文号集合
    private List<PWHVO> pwhs ;
    //批文号
    private String pwh;

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }



    public String getPwh() {
        return pwh;
    }

    public void setPwh(String pwh) {
        this.pwh = pwh;
    }

    public List<PWHVO> getPwhs() {
        return pwhs;
    }

    public void setPwhs(List<PWHVO> pwhs) {
        this.pwhs = pwhs;
    }
}
