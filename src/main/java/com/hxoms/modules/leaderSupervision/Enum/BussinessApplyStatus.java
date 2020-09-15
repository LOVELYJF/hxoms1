package com.hxoms.modules.leaderSupervision.Enum;

import com.hxoms.common.utils.Constants;

/**
 * @authore:wjf
 * @data 2020/6/12 15:35
 * @Description:
 ***/
public enum BussinessApplyStatus {

//    pubBussinessStatus("oms_pub_apply","SQZT", Constants.public_business[Constants.public_business.length-1]),
//
//    priBussinessStatus("oms_pri_apply","APPLY_STATUS",Constants.private_business[4]),
//    delBussinessStatus("oms_pri_delay_apply","APPLY_STATUS",Constants.delayed_business[4]);


    pubBussinessStatus("oms_pub_apply","SQZT", Constants.leader_business[0]),

    priBussinessStatus("oms_pri_apply","APPLY_STATUS", Constants.leader_business[0]),
    delBussinessStatus("oms_pri_delay_apply","APPLY_STATUS", Constants.leader_business[0]);

    private String tableName;

    private String applySatus;

    private int    leaderNeedInitializeStatus;




    BussinessApplyStatus(String tableName,String applySatus,int leaderNeedInitializeStatus){
        this.tableName = tableName;

        this.applySatus = applySatus;

        this.leaderNeedInitializeStatus = leaderNeedInitializeStatus;

    }


    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getApplySatus() {
        return applySatus;
    }

    public void setApplySatus(String applySatus) {
        this.applySatus = applySatus;
    }


    public int getLeaderNeedInitializeStatus() {
        return leaderNeedInitializeStatus;
    }

    public void setLeaderNeedInitializeStatus(int leaderNeedInitializeStatus) {
        this.leaderNeedInitializeStatus = leaderNeedInitializeStatus;
    }
}
