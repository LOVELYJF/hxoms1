package com.hxoms.modules.omsregcadre.entity;/*
 * @description:
 * @author 杨波
 * @date:2020-09-28
 */

public class OmsRegRevokeapplyV0 extends OmsRegRevokeapply{
    private String sexName;

    public String getSexName() {
        return sexName;
    }

    public void setSexName(String sexName) {
        this.sexName = sexName;
    }

    public String getExitTypeName() {
        return exitTypeName;
    }

    public void setExitTypeName(String incumbencyStatusName) {
        this.exitTypeName = incumbencyStatusName;
    }

    public String getSecretLevelName() {
        return secretLevelName;
    }

    public void setSecretLevelName(String secretLevelName) {
        this.secretLevelName = secretLevelName;
    }

    public String getDataTypeName() {
        return dataTypeName;
    }

    public void setDataTypeName(String dataTypeName) {
        this.dataTypeName = dataTypeName;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getPoliticalAffiname() {
        return politicalAffiname;
    }

    public void setPoliticalAffiname(String politicalAffiname) {
        this.politicalAffiname = politicalAffiname;
    }

    public String getHealth() {
        return health;
    }

    public void setHealth(String health) {
        this.health = health;
    }

    private String exitTypeName;
    private String secretLevelName;
    private String dataTypeName;
    private String statusName;
    private String politicalAffiname;
    private String health;
}
