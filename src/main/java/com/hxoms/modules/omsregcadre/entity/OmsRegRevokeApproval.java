package com.hxoms.modules.omsregcadre.entity;

import com.hxoms.common.hxannotation.ColumnAnnotation;
import com.hxoms.common.hxannotation.IdAnnotation;
import com.hxoms.common.hxannotation.TableAnnotation;

import java.util.Date;

/**
 * oms_reg_revokeapproval
 */
@TableAnnotation(TableName = "oms_reg_revokeapproval", TableDescription="撤销登记备案审批表")
public class OmsRegRevokeApproval {
    /**
     * 主键
     */
    @IdAnnotation
    @ColumnAnnotation(FieldName = "ID",  FieldDescription="主键")
    private String id;

    /**
     * 申请表主键
     */
    @ColumnAnnotation(FieldName = "APPLY_ID",  FieldDescription="申请表主键")
    private String applyId;

    /**
     * 步骤名称
     */
    @ColumnAnnotation(FieldName = "STEP_NAME",  FieldDescription="步骤名称")
    private String stepName;

    /**
     * 提交时间
     */
    @ColumnAnnotation(FieldName = "SUBMIT_TIME",  FieldDescription="提交时间")
    private Date submitTime;

    /**
     * 提交人
     */
    @ColumnAnnotation(FieldName = "SUBMIT_USER",  FieldDescription="提交人")
    private String submitUser;

    /**
     * 审批时间
     */
    @ColumnAnnotation(FieldName = "APPROVAL_TIME",  FieldDescription="审批时间")
    private Date approvalTime;

    /**
     * 审批人
     */
    @ColumnAnnotation(FieldName = "APPROVAL_USER",  FieldDescription="审批人")
    private String approvalUser;

    /**
     * 审批结论
     */
    @ColumnAnnotation(FieldName = "APPROVAL_CONCLUSION",  FieldDescription="审批结论 1同意  2不同意")
    private String approvalConclusion;

    /**
     * 审批意见
     */
    @ColumnAnnotation(FieldName = "APPROVAL_OPINION",  FieldDescription="审批意见")
    private String approvalOpinion;

    /**
     * 主键
     * @return ID 主键
     */
    public String getId() {
        return id;
    }

    /**
     * 主键
     * @param id 主键
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * 申请表主键
     * @return APPLY_ID 申请表主键
     */
    public String getApplyId() {
        return applyId;
    }

    /**
     * 申请表主键
     * @param applyId 申请表主键
     */
    public void setApplyId(String applyId) {
        this.applyId = applyId == null ? null : applyId.trim();
    }

    /**
     * 步骤名称
     * @return STEP_NAME 步骤名称
     */
    public String getStepName() {
        return stepName;
    }

    /**
     * 步骤名称
     * @param stepName 步骤名称
     */
    public void setStepName(String stepName) {
        this.stepName = stepName == null ? null : stepName.trim();
    }

    /**
     * 提交时间
     * @return SUBMIT_TIME 提交时间
     */
    public Date getSubmitTime() {
        return submitTime;
    }

    /**
     * 提交时间
     * @param submitTime 提交时间
     */
    public void setSubmitTime(Date submitTime) {
        this.submitTime = submitTime;
    }

    /**
     * 提交人
     * @return SUBMIT_USER 提交人
     */
    public String getSubmitUser() {
        return submitUser;
    }

    /**
     * 提交人
     * @param submitUser 提交人
     */
    public void setSubmitUser(String submitUser) {
        this.submitUser = submitUser == null ? null : submitUser.trim();
    }

    /**
     * 审批时间
     * @return APPROVAL_TIME 审批时间
     */
    public Date getApprovalTime() {
        return approvalTime;
    }

    /**
     * 审批时间
     * @param approvalTime 审批时间
     */
    public void setApprovalTime(Date approvalTime) {
        this.approvalTime = approvalTime;
    }

    /**
     * 审批人
     * @return APPROVAL_USER 审批人
     */
    public String getApprovalUser() {
        return approvalUser;
    }

    /**
     * 审批人
     * @param approvalUser 审批人
     */
    public void setApprovalUser(String approvalUser) {
        this.approvalUser = approvalUser == null ? null : approvalUser.trim();
    }

    /**
     * 审批结论
     * @return APPROVAL_CONCLUSION 审批结论
     */
    public String getApprovalConclusion() {
        return approvalConclusion;
    }

    /**
     * 审批结论
     * @param approvalConclusion 审批结论
     */
    public void setApprovalConclusion(String approvalConclusion) {
        this.approvalConclusion = approvalConclusion == null ? null : approvalConclusion.trim();
    }

    /**
     * 审批意见
     * @return APPROVAL_OPINION 审批意见
     */
    public String getApprovalOpinion() {
        return approvalOpinion;
    }

    /**
     * 审批意见
     * @param approvalOpinion 审批意见
     */
    public void setApprovalOpinion(String approvalOpinion) {
        this.approvalOpinion = approvalOpinion == null ? null : approvalOpinion.trim();
    }
}