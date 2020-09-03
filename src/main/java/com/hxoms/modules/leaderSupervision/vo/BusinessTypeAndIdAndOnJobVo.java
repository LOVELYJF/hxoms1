package com.hxoms.modules.leaderSupervision.vo;

/**
 * @authore:wjf
 * @data 2020/8/10 10:51
 * @Description:
 ***/
public class BusinessTypeAndIdAndOnJobVo {

    private  String bussinessId;   /** 业务 id **/

    private  String bussinessName; /** 业务 名称 **/

    private  String incumbencyStatusArrays; /** 在职转态 **/

    private  String jwjl; /** 纪委建议 **/

    private  String procpersonId; /** 登记备案人员id **/

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

    public String getIncumbencyStatusArrays() {
        return incumbencyStatusArrays;
    }

    public void setIncumbencyStatusArrays(String incumbencyStatusArrays) {
        this.incumbencyStatusArrays = incumbencyStatusArrays;
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
}
