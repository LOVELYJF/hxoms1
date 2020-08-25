package com.hxoms.modules.passportCard.admintorGet.entity.parameterEntiry;

import com.hxoms.modules.passportCard.admintorGet.entity.OmsCerAdmintorGetApply;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @Desc：TODO
 * @Author: wangyunquan
 * @Date: 2020/8/18
 */
@ApiModel(value = "管理员取证申请")
public class AdmintorGetApplyList {
    //管理员取证申请集合
    @ApiModelProperty(value = "管理员取证申请集合信息")
    private List<OmsCerAdmintorGetApply> omsCerAdmintorGetApplyList;

    public List<OmsCerAdmintorGetApply> getOmsCerAdmintorGetApplyList() {
        return omsCerAdmintorGetApplyList;
    }

    public void setOmsCerAdmintorGetApplyList(List<OmsCerAdmintorGetApply> omsCerAdmintorGetApplyList) {
        this.omsCerAdmintorGetApplyList = omsCerAdmintorGetApplyList;
    }
}
