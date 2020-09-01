package com.hxoms.modules.passportCard.initialise.entity.parameterEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Desc：TODO
 * @Author: wangyunquan
 * @Date: 2020/8/30
 */
@ApiModel(value = "证照催缴申请信息")
public class CertificateCollectionApply {

    @ApiModelProperty(value="备案信息表id",required = true)
    private String omsId;

    @ApiModelProperty(value="数据列表id",required = true)
    private String cerId;

    @ApiModelProperty(value="姓名",required = true)
    private String name;

    @ApiModelProperty(value="机构",required = true)
    private String rfB0000;

    @ApiModelProperty(value="工作单位",required = true)
    private String workUnit;

    @ApiModelProperty(value="证件类型(1:护照,2:港澳通行证,4:台湾通行证)",required = true)
    private Integer zjlx;

    @ApiModelProperty(value="证件号码",required = true)
    private String zjhm;

    @ApiModelProperty(value="数据来源(0:登记备案,1:因私出国(境),2:证照借出,3:撤销出国申请)",required = true)
    private String dataSource;

    public String getOmsId() {
        return omsId;
    }

    public void setOmsId(String omsId) {
        this.omsId = omsId;
    }

    public String getCerId() {
        return cerId;
    }

    public void setCerId(String cerId) {
        this.cerId = cerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRfB0000() {
        return rfB0000;
    }

    public void setRfB0000(String rfB0000) {
        this.rfB0000 = rfB0000;
    }

    public String getWorkUnit() {
        return workUnit;
    }

    public void setWorkUnit(String workUnit) {
        this.workUnit = workUnit;
    }

    public Integer getZjlx() {
        return zjlx;
    }

    public void setZjlx(Integer zjlx) {
        this.zjlx = zjlx;
    }

    public String getZjhm() {
        return zjhm;
    }

    public void setZjhm(String zjhm) {
        this.zjhm = zjhm;
    }

    public String getDataSource() {
        return dataSource;
    }

    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
    }
}