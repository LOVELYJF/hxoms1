package com.hxoms.modules.passportCard.counterGet.entity.parameterEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import java.util.Date;

/**
 * @Desc：TODO
 * @Author: wangyunquan
 * @Date: 2020/8/14
 */
@ApiModel(value = "身份验证")
public class IdentityParam {
    //姓名
    @NotBlank(message = "姓名不能为空")
    @ApiModelProperty(value="姓名",required = true)
    private String name;
    //性别
    @ApiModelProperty(value="性别")
    private String sex;
    //国籍
    @ApiModelProperty(value="国籍")
    private String nation;
    //出生日期
    @ApiModelProperty(value="出生日期")
    private Date csrq;
    //身份证号
    @NotBlank(message = "身份证号不能为空")
    @ApiModelProperty(value="身份证号",required = true)
    private String idNo;
    //户口所在地
    @ApiModelProperty(value="户口所在地")
    private String registeResidence;
    //签发机关
    @ApiModelProperty(value="签发机关")
    private String qrjg;
    //有效期至
    @ApiModelProperty(value="有效期至")
    private Date yxqz;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public Date getCsrq() {
        return csrq;
    }

    public void setCsrq(Date csrq) {
        this.csrq = csrq;
    }

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    public String getRegisteResidence() {
        return registeResidence;
    }

    public void setRegisteResidence(String registeResidence) {
        this.registeResidence = registeResidence;
    }

    public String getQrjg() {
        return qrjg;
    }

    public void setQrjg(String qrjg) {
        this.qrjg = qrjg;
    }

    public Date getYxqz() {
        return yxqz;
    }

    public void setYxqz(Date yxqz) {
        this.yxqz = yxqz;
    }

}
