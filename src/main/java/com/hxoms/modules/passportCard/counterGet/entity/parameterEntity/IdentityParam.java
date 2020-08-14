package com.hxoms.modules.passportCard.counterGet.entity.parameterEntity;

import java.util.Date;

/**
 * @Desc：TODO
 * @Author: wangyunquan
 * @Date: 2020/8/14
 */
public class IdentityParam {
    private String name;
    private String sex;
    private String nation;
    private Date csrq;
    private String idNo;
    private String registeResidence;
    private String qrjg;
    private Date yxqz;
    private String leftFingerMark;
    private String rightFingerMark;
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
