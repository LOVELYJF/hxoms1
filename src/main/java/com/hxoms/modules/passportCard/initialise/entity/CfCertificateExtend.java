package com.hxoms.modules.passportCard.initialise.entity;/*
 * @description:
 * @author 杨波
 * @date:2020-10-12
 */

public class CfCertificateExtend extends CfCertificate {
    public String getSaveStatusName() {
        return saveStatusName;
    }

    public void setSaveStatusName(String saveStatusName) {
        this.saveStatusName = saveStatusName;
    }

    public String getCardStatusName() {
        return cardStatusName;
    }

    public void setCardStatusName(String cardStatusName) {
        this.cardStatusName = cardStatusName;
    }

    private String saveStatusName;
    private String cardStatusName;
}
