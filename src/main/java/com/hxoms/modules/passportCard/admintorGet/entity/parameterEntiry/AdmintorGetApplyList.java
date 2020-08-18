package com.hxoms.modules.passportCard.admintorGet.entity.parameterEntiry;

import com.hxoms.modules.passportCard.admintorGet.entity.OmsCerAdmintorGetApply;

import java.util.List;

/**
 * @Desc：TODO
 * @Author: wangyunquan
 * @Date: 2020/8/18
 */
public class AdmintorGetApplyList {
    //管理员取证申请集合
    private List<OmsCerAdmintorGetApply> omsCerAdmintorGetApplyList;

    public List<OmsCerAdmintorGetApply> getOmsCerAdmintorGetApplyList() {
        return omsCerAdmintorGetApplyList;
    }

    public void setOmsCerAdmintorGetApplyList(List<OmsCerAdmintorGetApply> omsCerAdmintorGetApplyList) {
        this.omsCerAdmintorGetApplyList = omsCerAdmintorGetApplyList;
    }
}
