package com.hxoms.modules.leaderSupervision.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
 * @authore:wjf
 * @data 2020/6/22 15:17
 * @Description:
 ***/
public class OmsJiweiOpinionVo {


    // 被选中 的 人员Id
    private String[] bussinessId; /**业务id 数组**/

    private String[] bussinessName; /**业务名称 数组**/

    private String  id;

   // "反馈方式 1(口头反馈),2(书面反馈)"
    private String feedbackType;

   //"意见")
    private String opinion;

   // "反馈人")
    private String feedbackUser;

    // "电话号码")
    private String telephoneNumber;

   //"反馈结论1(同意),2(不同意),3(不回复)")
    private String feedbackVerdict;

   //   FieldDescription="反馈时间")

   @DateTimeFormat(pattern = "yyyy.MM.dd")
   @JsonFormat(pattern = "yyyy.MM.dd")
    private Date feedbackDate;

   //  FieldDescription="备注")
    private String remark;

    private List<BussinessTypeAndIdVo> bussinessTypeAndIdVos; // 封装 业务 id 与 业务类型

    public OmsJiweiOpinionVo() {
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

    public String getFeedbackType() {
        return feedbackType;
    }

    public void setFeedbackType(String feedbackType) {
        this.feedbackType = feedbackType;
    }

    public String getOpinion() {
        return opinion;
    }

    public void setOpinion(String opinion) {
        this.opinion = opinion;
    }

    public String getFeedbackUser() {
        return feedbackUser;
    }

    public void setFeedbackUser(String feedbackUser) {
        this.feedbackUser = feedbackUser;
    }

    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    public String getFeedbackVerdict() {
        return feedbackVerdict;
    }

    public void setFeedbackVerdict(String feedbackVerdict) {
        this.feedbackVerdict = feedbackVerdict;
    }

    public Date getFeedbackDate() {
        return feedbackDate;
    }

    public void setFeedbackDate(Date feedbackDate) {
        this.feedbackDate = feedbackDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<BussinessTypeAndIdVo> getBussinessTypeAndIdVos() {
        return bussinessTypeAndIdVos;
    }

    public void setBussinessTypeAndIdVos(List<BussinessTypeAndIdVo> bussinessTypeAndIdVos) {
        this.bussinessTypeAndIdVos = bussinessTypeAndIdVos;
    }
}
