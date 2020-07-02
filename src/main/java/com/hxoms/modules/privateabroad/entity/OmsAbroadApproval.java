package com.hxoms.modules.privateabroad.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hxoms.common.hxannotation.ColumnAnnotation;
import com.hxoms.common.hxannotation.IdAnnotation;
import com.hxoms.common.hxannotation.TableAnnotation;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

@TableAnnotation(TableName = "oms_abroad_approval", TableDescription="出国境审批表")
public class OmsAbroadApproval {
    @IdAnnotation
    @ColumnAnnotation(FieldName = "ID",   FieldDescription="主键")
    private String id;

    @ColumnAnnotation(FieldName = "APPLY_ID",   FieldDescription="申请ID")
    private String applyId;

    @ColumnAnnotation(FieldName = "STEP_CODE",   FieldDescription="步骤编码")
    private Integer stepCode;

    @ColumnAnnotation(FieldName = "STEP_NAME",   FieldDescription="步骤名称")
    private String stepName;

    @ColumnAnnotation(FieldName = "APPROVAL_TIME",   FieldDescription="审批时间")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date approvalTime;

    @ColumnAnnotation(FieldName = "APPROVAL_USER",   FieldDescription="审批人")
    private String approvalUser;

    @ColumnAnnotation(FieldName = "SUBMIT_TIME",   FieldDescription="提交时间")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date submitTime;

    @ColumnAnnotation(FieldName = "SUBMIT_USER",   FieldDescription="提交人")
    private String submitUser;

    @ColumnAnnotation(FieldName = "APPROVAL_RESULT",   FieldDescription="审批结论")
    private String approvalResult;

    @ColumnAnnotation(FieldName = "APPROVAL_ADVICE",   FieldDescription="审批意见")
    private String approvalAdvice;

    @ColumnAnnotation(FieldName = "TYPE",   FieldDescription="类型(因公   因私  延期出国)")
    private String type;

    @ColumnAnnotation(FieldName = "CREATE_TIME",   FieldDescription="创建时间")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date createTime;

    @ColumnAnnotation(FieldName = "CREATE_USER",   FieldDescription="创建人")
    private String createUser;

    @ColumnAnnotation(FieldName = "MODIFY_TIME",   FieldDescription="修改时间")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date modifyTime;

    @ColumnAnnotation(FieldName = "MODIFY_USER",   FieldDescription="修改人")
    private String modifyUser;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getApplyId() {
        return applyId;
    }

    public void setApplyId(String applyId) {
        this.applyId = applyId == null ? null : applyId.trim();
    }

    public Integer getStepCode() {
        return stepCode;
    }

    public void setStepCode(Integer stepCode) {
        this.stepCode = stepCode;
    }

    public String getStepName() {
        return stepName;
    }

    public void setStepName(String stepName) {
        this.stepName = stepName == null ? null : stepName.trim();
    }

    public Date getApprovalTime() {
        return approvalTime;
    }

    public void setApprovalTime(Date approvalTime) {
        this.approvalTime = approvalTime;
    }

    public String getApprovalUser() {
        return approvalUser;
    }

    public void setApprovalUser(String approvalUser) {
        this.approvalUser = approvalUser == null ? null : approvalUser.trim();
    }

    public Date getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(Date submitTime) {
        this.submitTime = submitTime;
    }

    public String getSubmitUser() {
        return submitUser;
    }

    public void setSubmitUser(String submitUser) {
        this.submitUser = submitUser == null ? null : submitUser.trim();
    }

    public String getApprovalResult() {
        return approvalResult;
    }

    public void setApprovalResult(String approvalResult) {
        this.approvalResult = approvalResult == null ? null : approvalResult.trim();
    }

    public String getApprovalAdvice() {
        return approvalAdvice;
    }

    public void setApprovalAdvice(String approvalAdvice) {
        this.approvalAdvice = approvalAdvice == null ? null : approvalAdvice.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser == null ? null : createUser.trim();
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getModifyUser() {
        return modifyUser;
    }

    public void setModifyUser(String modifyUser) {
        this.modifyUser = modifyUser == null ? null : modifyUser.trim();
    }
}