package com.hxoms.modules.leaderSupervision.vo;

/**
 * @authore:wjf
 * @data 2020/8/7 10:04
 * @Description:
 ***/
public class BussinessTypeAndIdVo {

    private  String bussinessId;   /** 业务 id **/

    private  String bussinessName; /** 业务 名称 **/

    private  String incumbencyStatus; /**  业务申请流程中 人员 在职状态  1在职 2辞职 3退休 4去世 5开除 6调出 7.省管变中管 8 未匹配 9其它**/

    private  String cadresupervisionOpinion;/** 干部监督处建议 **/

    private  String jwjl; /** 纪委建议 **/

    private  String procpersonId; /** 登记备案人员id **/

    private String  bussinessOccureStpet;  /** 发生在业务的哪一步code **/

    private String  bussinessOccureStpetName; /**  发生在业务的哪一步code的名称 **/


    public String getBussinessId() {
        return bussinessId;
    }

    public void setBussinessId(String bussinessId) {
        this.bussinessId = bussinessId;
    }

    public String getBussinessName() {
        return bussinessName;
    }

    public void setBussinessName(String bussinessName) {
        this.bussinessName = bussinessName;
    }

    public String getIncumbencyStatus() {
        return incumbencyStatus;
    }

    public void setIncumbencyStatus(String incumbencyStatus) {
        this.incumbencyStatus = incumbencyStatus;
    }

    public String getCadresupervisionOpinion() {
        return cadresupervisionOpinion;
    }

    public void setCadresupervisionOpinion(String cadresupervisionOpinion) {
        this.cadresupervisionOpinion = cadresupervisionOpinion;
    }

    public String getJwjl() {
        return jwjl;
    }

    public void setJwjl(String jwjl) {
        this.jwjl = jwjl;
    }

    public String getProcpersonId() {
        return procpersonId;
    }

    public void setProcpersonId(String procpersonId) {
        this.procpersonId = procpersonId;
    }

    public String getBussinessOccureStpet() {
        return bussinessOccureStpet;
    }

    public void setBussinessOccureStpet(String bussinessOccureStpet) {
        this.bussinessOccureStpet = bussinessOccureStpet;
    }

    public String getBussinessOccureStpetName() {
        return bussinessOccureStpetName;
    }

    public void setBussinessOccureStpetName(String bussinessOccureStpetName) {
        this.bussinessOccureStpetName = bussinessOccureStpetName;
    }
}
