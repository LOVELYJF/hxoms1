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
}
