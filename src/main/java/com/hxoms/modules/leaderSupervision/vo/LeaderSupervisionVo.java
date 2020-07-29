package com.hxoms.modules.leaderSupervision.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class LeaderSupervisionVo {


    private Integer pageNum;  /**页码*/

    private Integer pageSize;   /**分页大小*/

    private String[] bussinessId; /**业务id 数组**/

    private String[] bussinessName; /**业务名称 数组**/

    private String  userName; /**人员名称 **/

    private String  bussinessType; /**申请类型**/

    private String  batchName; /**批次号**/

    private String  leaderBtachId; /** 批次id 用于选择人员纳入批次中**/

    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date accpetDate; /**受理时间**/

    private String incumbencyStatus; /** 业务申请流程中 人员 在职状态  1在职 2辞职 3退休 4去世 5开除 6调出 7.省管变中管 8 未匹配 9其它   **/




    public LeaderSupervisionVo(){

    }


    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String[] getBussinessId() {
        return bussinessId;
    }

    public void setBussinessId(String[] bussinessId) {
        this.bussinessId = bussinessId;
    }

    public String[] getBussinessName() {
        return bussinessName;
    }

    public void setBussinessName(String[] bussinessName) {
        this.bussinessName = bussinessName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getBussinessType() {
        return bussinessType;
    }

    public void setBussinessType(String bussinessType) {
        this.bussinessType = bussinessType;
    }

    public String getBatchName() {
        return batchName;
    }

    public void setBatchName(String batchName) {
        this.batchName = batchName;
    }

    public Date getAccpetDate() {
        return accpetDate;
    }

    public void setAccpetDate(Date accpetDate) {
        this.accpetDate = accpetDate;
    }

    public String getLeaderBtachId() {
        return leaderBtachId;
    }

    public void setLeaderBtachId(String leaderBtachId) {
        this.leaderBtachId = leaderBtachId;
    }

    public String getIncumbencyStatus() {
        return incumbencyStatus;
    }

    public void setIncumbencyStatus(String incumbencyStatus) {
        this.incumbencyStatus = incumbencyStatus;
    }



}
