package com.hxoms.modules.passportCard.counterGet.entity.parameterEntity;

import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * @Desc：TODO
 * @Author: wangyunquan
 * @Date: 2020/8/14
 */
public class IdentityParam {
    //姓名
    @ApiModelProperty(value="姓名")
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
    @ApiModelProperty(value="身份证号")
    private String idNo;
    //户口所在地
    @ApiModelProperty(value="户口所在地")
    private String registeResidence;
    //签发机关
    @ApiModelProperty(value="签发机关")
    private String qrjg;
    //有效期
    @ApiModelProperty(value="有效期")
    private Date yxqz;
    //左手指纹
    @ApiModelProperty(value="左手指纹")
    private String leftFingerMark;
    //右手指纹
    @ApiModelProperty(value="右手指纹")
    private String rightFingerMark;
    //二维码
    @ApiModelProperty(value="二维码")
    private String qRCode;

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

    public String getLeftFingerMark() {
        return leftFingerMark;
    }

    public void setLeftFingerMark(String leftFingerMark) {
        this.leftFingerMark = leftFingerMark;
    }

    public String getRightFingerMark() {
        return rightFingerMark;
    }

    public void setRightFingerMark(String rightFingerMark) {
        this.rightFingerMark = rightFingerMark;
    }

    public String getqRCode() {
        return qRCode;
    }

    public void setqRCode(String qRCode) {
        this.qRCode = qRCode;
    }
}
