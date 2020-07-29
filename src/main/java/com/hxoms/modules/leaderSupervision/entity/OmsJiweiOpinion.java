package com.hxoms.modules.leaderSupervision.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hxoms.common.hxannotation.ColumnAnnotation;
import com.hxoms.common.hxannotation.IdAnnotation;
import com.hxoms.common.hxannotation.TableAnnotation;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@TableAnnotation(TableName = "oms_jiwei_opinion", TableDescription="纪委意见记录表")
public class OmsJiweiOpinion {
    @IdAnnotation
    @ColumnAnnotation(FieldName = "id",   FieldDescription="纪委意见记录表主键")
    private String id;

    @ColumnAnnotation(FieldName = "applyId",   FieldDescription="业务流程申请id")
    private String applyid;

    @ColumnAnnotation(FieldName = "feedback_type",   FieldDescription="反馈方式 1(口头反馈),2(书面反馈)")
    private String feedbackType;

    @ColumnAnnotation(FieldName = "opinion",   FieldDescription="意见")
    private String opinion;

    @ColumnAnnotation(FieldName = "feedback_user",   FieldDescription="反馈人")
    private String feedbackUser;

    @ColumnAnnotation(FieldName = "telephone_number",   FieldDescription="电话号码")
    private String telephoneNumber;

    @ColumnAnnotation(FieldName = "feedback_verdict",   FieldDescription="反馈结论1(同意),2(不同意),3(不回复)")
    private String feedbackVerdict;

    @ColumnAnnotation(FieldName = "feedback_date",   FieldDescription="反馈时间")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    @JsonFormat(pattern = "yyyy.MM.dd")
    private Date feedbackDate;

    @ColumnAnnotation(FieldName = "remark",   FieldDescription="备注")
    private String remark;

    @ColumnAnnotation(FieldName = "official_feedback_type",   FieldDescription="反馈方式 1(口头反馈),2(书面反馈) 正式")
    private String officialFeedbackType;

    @ColumnAnnotation(FieldName = "official_opinion",   FieldDescription="意见 正式")
    private String officialOpinion;

    @ColumnAnnotation(FieldName = "official_feedback_verdict",   FieldDescription="反馈结论1(同意),2(不同意),3(不回复)  正式")
    private String officialFeedbackVerdict;

    @ColumnAnnotation(FieldName = "official_remark",   FieldDescription="备注 正式")
    private String officialRemark;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getApplyid() {
        return applyid;
    }

    public void setApplyid(String applyid) {
        this.applyid = applyid == null ? null : applyid.trim();
    }

    public String getFeedbackType() {
        return feedbackType;
    }

    public void setFeedbackType(String feedbackType) {
        this.feedbackType = feedbackType == null ? null : feedbackType.trim();
    }

    public String getOpinion() {
        return opinion;
    }

    public void setOpinion(String opinion) {
        this.opinion = opinion == null ? null : opinion.trim();
    }

    public String getFeedbackUser() {
        return feedbackUser;
    }

    public void setFeedbackUser(String feedbackUser) {
        this.feedbackUser = feedbackUser == null ? null : feedbackUser.trim();
    }

    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber == null ? null : telephoneNumber.trim();
    }

    public String getFeedbackVerdict() {
        return feedbackVerdict;
    }

    public void setFeedbackVerdict(String feedbackVerdict) {
        this.feedbackVerdict = feedbackVerdict == null ? null : feedbackVerdict.trim();
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
        this.remark = remark == null ? null : remark.trim();
    }

    public String getOfficialFeedbackType() {
        return officialFeedbackType;
    }

    public void setOfficialFeedbackType(String officialFeedbackType) {
        this.officialFeedbackType = officialFeedbackType == null ? null : officialFeedbackType.trim();
    }

    public String getOfficialOpinion() {
        return officialOpinion;
    }

    public void setOfficialOpinion(String officialOpinion) {
        this.officialOpinion = officialOpinion == null ? null : officialOpinion.trim();
    }

    public String getOfficialFeedbackVerdict() {
        return officialFeedbackVerdict;
    }

    public void setOfficialFeedbackVerdict(String officialFeedbackVerdict) {
        this.officialFeedbackVerdict = officialFeedbackVerdict == null ? null : officialFeedbackVerdict.trim();
    }

    public String getOfficialRemark() {
        return officialRemark;
    }

    public void setOfficialRemark(String officialRemark) {
        this.officialRemark = officialRemark == null ? null : officialRemark.trim();
    }
}