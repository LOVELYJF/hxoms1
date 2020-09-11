package com.hxoms.modules.omsoperator.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @desc: 交接表扩展
 * @author: lijing
 * @date: 2020-06-03
 */
public class OmsOperatorHandoverSubformVO extends OmsOperatorHandoverSubform{


    /** 交接人姓名*/
    private String operatorName;
    /** 接手人姓名*/
    private String handoverName;
    /** 交接状态*/
    private String handoverStatus;
    /** 交接时间*/
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date jjTime;

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public String getHandoverName() {
        return handoverName;
    }

    public void setHandoverName(String handoverName) {
        this.handoverName = handoverName;
    }

    public String getHandoverStatus() {
        return handoverStatus;
    }

    public void setHandoverStatus(String handoverStatus) {
        this.handoverStatus = handoverStatus;
    }

    public Date getJjTime() {
        return jjTime;
    }

    public void setJjTime(Date jjTime) {
        this.jjTime = jjTime;
    }
}
