package com.hxoms.modules.passportCard.certificateManage.entity.parameterEntity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @Desc：TODO
 * @Author: wangyunquan
 * @Date: 2020/8/4
 */
@ApiModel(value = "证件读取返回信息")
public class CerAndPerson {
    //证照信息
    @ApiModelProperty(value = "证照信息")
    private ReadCerInfo readCerInfo;
    //登记备案人员信息
    @ApiModelProperty(value = "登记备案人员信息")
    private List<RegProcpersoninfo> regProcpersoninfoList;
    @JsonIgnore
    private String message;
    public ReadCerInfo getReadCerInfo() {
        return readCerInfo;
    }

    public void setReadCerInfo(ReadCerInfo readCerInfo) {
        this.readCerInfo = readCerInfo;
    }

    public List<RegProcpersoninfo> getRegProcpersoninfoList() {
        return regProcpersoninfoList;
    }

    public void setRegProcpersoninfoList(List<RegProcpersoninfo> regProcpersoninfoList) {
        this.regProcpersoninfoList = regProcpersoninfoList;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
