package com.hxoms.modules.omsoperator.entity;

import com.hxoms.common.hxannotation.ColumnAnnotation;
import com.hxoms.common.hxannotation.IdAnnotation;
import com.hxoms.common.hxannotation.TableAnnotation;

import java.util.Date;

/**
 * oms_operator_approval
 */
@TableAnnotation(TableName = "oms_operator_approval", TableDescription="")
public class OmsOperatorApproval {
    /**
     * 经办人审批表主键
     */
    @IdAnnotation
    @ColumnAnnotation(FieldName = "ID",  FieldDescription="经办人审批表主键")
    private String id;

    /**
     * 经办人主键
     */
    @ColumnAnnotation(FieldName = "OperatorId",  FieldDescription="经办人主键")
    private String operatorid;

    /**
     * 步骤名称（1.征求意见 2.干部监督处审批）
     */
    @ColumnAnnotation(FieldName = "StepName",  FieldDescription="步骤名称（1.征求意见 2.干部监督处审批）")
    private String stepname;

    /**
     * 提交时间
     */
    @ColumnAnnotation(FieldName = "SubmissionTime",  FieldDescription="提交时间")
    private Date submissiontime;

    /**
     * 提交人
     */
    @ColumnAnnotation(FieldName = "Submitter",  FieldDescription="提交人")
    private String submitter;

    /**
     * 审批时间
     */
    @ColumnAnnotation(FieldName = "ApprovalDate",  FieldDescription="审批时间")
    private Date approvaldate;

    /**
     * 审批人
     */
    @ColumnAnnotation(FieldName = "Approver",  FieldDescription="审批人")
    private String approver;

    /**
     * 审批结论
     */
    @ColumnAnnotation(FieldName = "ApprovalResult",  FieldDescription="审批结论")
    private String approvalresult;

    /**
     * 审批意见
     */
    @ColumnAnnotation(FieldName = "ApprovalOpinion",  FieldDescription="审批意见")
    private String approvalopinion;

    /**
     * 经办人审批表主键
     * @return ID 经办人审批表主键
     */
    public String getId() {
        return id;
    }

    /**
     * 经办人审批表主键
     * @param id 经办人审批表主键
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * 经办人主键
     * @return OperatorId 经办人主键
     */
    public String getOperatorid() {
        return operatorid;
    }

    /**
     * 经办人主键
     * @param operatorid 经办人主键
     */
    public void setOperatorid(String operatorid) {
        this.operatorid = operatorid == null ? null : operatorid.trim();
    }

    /**
     * 步骤名称（1.征求意见 2.干部监督处审批）
     * @return StepName 步骤名称（1.征求意见 2.干部监督处审批）
     */
    public String getStepname() {
        return stepname;
    }

    /**
     * 步骤名称（1.征求意见 2.干部监督处审批）
     * @param stepname 步骤名称（1.征求意见 2.干部监督处审批）
     */
    public void setStepname(String stepname) {
        this.stepname = stepname == null ? null : stepname.trim();
    }

    /**
     * 提交时间
     * @return SubmissionTime 提交时间
     */
    public Date getSubmissiontime() {
        return submissiontime;
    }

    /**
     * 提交时间
     * @param submissiontime 提交时间
     */
    public void setSubmissiontime(Date submissiontime) {
        this.submissiontime = submissiontime;
    }

    /**
     * 提交人
     * @return Submitter 提交人
     */
    public String getSubmitter() {
        return submitter;
    }

    /**
     * 提交人
     * @param submitter 提交人
     */
    public void setSubmitter(String submitter) {
        this.submitter = submitter == null ? null : submitter.trim();
    }

    /**
     * 审批时间
     * @return ApprovalDate 审批时间
     */
    public Date getApprovaldate() {
        return approvaldate;
    }

    /**
     * 审批时间
     * @param approvaldate 审批时间
     */
    public void setApprovaldate(Date approvaldate) {
        this.approvaldate = approvaldate;
    }

    /**
     * 审批人
     * @return Approver 审批人
     */
    public String getApprover() {
        return approver;
    }

    /**
     * 审批人
     * @param approver 审批人
     */
    public void setApprover(String approver) {
        this.approver = approver == null ? null : approver.trim();
    }

    /**
     * 审批结论
     * @return ApprovalResult 审批结论
     */
    public String getApprovalresult() {
        return approvalresult;
    }

    /**
     * 审批结论
     * @param approvalresult 审批结论
     */
    public void setApprovalresult(String approvalresult) {
        this.approvalresult = approvalresult == null ? null : approvalresult.trim();
    }

    /**
     * 审批意见
     * @return ApprovalOpinion 审批意见
     */
    public String getApprovalopinion() {
        return approvalopinion;
    }

    /**
     * 审批意见
     * @param approvalopinion 审批意见
     */
    public void setApprovalopinion(String approvalopinion) {
        this.approvalopinion = approvalopinion == null ? null : approvalopinion.trim();
    }
}