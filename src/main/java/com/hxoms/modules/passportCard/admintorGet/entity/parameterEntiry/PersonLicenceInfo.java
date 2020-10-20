package com.hxoms.modules.passportCard.admintorGet.entity.parameterEntiry;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @Desc：TODO
 * @Author: wangyunquan
 * @Date: 2020/10/19
 */
@ApiModel(value = "管理员取证干部和持有证件信息")
public class PersonLicenceInfo {
    @ApiModelProperty(value = "管理员取证干部基本信息")
    private PersonInfo personInfo;
    @ApiModelProperty(value = "证件信息集合")
    private List<CerInfo> cerInfoList;

    public PersonInfo getPersonInfo() {
        return personInfo;
    }

    public void setPersonInfo(PersonInfo personInfo) {
        this.personInfo = personInfo;
    }

    public List<CerInfo> getCerInfoList() {
        return cerInfoList;
    }

    public void setCerInfoList(List<CerInfo> cerInfoList) {
        this.cerInfoList = cerInfoList;
    }
}
