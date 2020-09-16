package com.hxoms.modules.omsoperator.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hxoms.common.hxannotation.ColumnAnnotation;
import com.hxoms.common.hxannotation.IdAnnotation;
import com.hxoms.common.hxannotation.TableAnnotation;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@TableAnnotation(TableName = "oms_operator_approval", TableDescription="经办人审批表")
@ApiModel(value = "经办人审批表")
public class OmsOperatorApproval {
    @IdAnnotation
    @ColumnAnnotation(FieldName = "ID",   FieldDescription="经办人审批表主键")
    @ApiModelProperty(value="经办人审批表主键")
    private String id;

    @ColumnAnnotation(FieldName = "OperatorId",   FieldDescription="经办人主键")
    @ApiModelProperty(value="经办人主键")
    private String operatorid;

    @ColumnAnnotation(FieldName = "StepName",   FieldDescription="步骤名称（1.干部监督处审批 2.部长审批,3.上报）")
    @ApiModelProperty(value="步骤名称（1.干部监督处审批 2.部长审批,3.上报）")
    private String stepname;

    @ColumnAnnotation(FieldName = "SubmissionTime",   FieldDescription="提交时间")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    @ApiModelProperty(value="提交时间")
    private Date submissiontime;

    @ColumnAnnotation(FieldName = "Submitter",   FieldDescription="提交人")
    @ApiModelProperty(value="提交人")
    private String submitter;

    @ColumnAnnotation(FieldName = "SubmitterId",   FieldDescription="提交人id")
    @ApiModelProperty(value="提交人id")
    private String submitterid;

    @ColumnAnnotation(FieldName = "ApprovalResult",   FieldDescription="审批结论")
    @ApiModelProperty(value="审批结论")
    private String approvalresult;

    @ColumnAnnotation(FieldName = "ApprovalOpinion",   FieldDescription="审批意见")
    @ApiModelProperty(value="审批意见")
    private String approvalopinion;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getOperatorid() {
        return operatorid;
    }

    public void setOperatorid(String operatorid) {
        this.operatorid = operatorid == null ? null : operatorid.trim();
    }

    public String getStepname() {
        return stepname;
    }

    public void setStepname(String stepname) {
        this.stepname = stepname == null ? null : stepname.trim();
    }

    public Date getSubmissiontime() {
        return submissiontime;
    }

    public void setSubmissiontime(Date submissiontime) {
        this.submissiontime = submissiontime;
    }

    public String getSubmitter() {
        return submitter;
    }

    public void setSubmitter(String submitter) {
        this.submitter = submitter == null ? null : submitter.trim();
    }

    public String getSubmitterid() {
        return submitterid;
    }

    public void setSubmitterid(String submitterid) {
        this.submitterid = submitterid == null ? null : submitterid.trim();
    }

    public String getApprovalresult() {
        return approvalresult;
    }

    public void setApprovalresult(String approvalresult) {
        this.approvalresult = approvalresult == null ? null : approvalresult.trim();
    }

    public String getApprovalopinion() {
        return approvalopinion;
    }

    public void setApprovalopinion(String approvalopinion) {
        this.approvalopinion = approvalopinion == null ? null : approvalopinion.trim();
    }
}