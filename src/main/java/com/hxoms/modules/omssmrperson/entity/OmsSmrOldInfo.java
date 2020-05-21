package com.hxoms.modules.omssmrperson.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hxoms.common.hxannotation.ColumnAnnotation;
import com.hxoms.common.hxannotation.IdAnnotation;
import com.hxoms.common.hxannotation.TableAnnotation;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

@TableAnnotation(TableName = "oms_smr_oldinfo", TableDescription="涉密人员原涉密信息表")
public class OmsSmrOldInfo {
    @IdAnnotation
    @ColumnAnnotation(FieldName = "ID",   FieldDescription="主键")
    private String id;

    @ColumnAnnotation(FieldName = "A0100",   FieldDescription="人员主键")
    private String a0100;

    @ColumnAnnotation(FieldName = "B0100",   FieldDescription="单位主键")
    private String b0100;

    @ColumnAnnotation(FieldName = "SECRET_RELATED_POST",   FieldDescription="涉密岗位")
    private String secretRelatedPost;

    @ColumnAnnotation(FieldName = "SECRET_RELATED_LEVEL",   FieldDescription="涉密等级（0非涉密，1一般涉密，2重要涉密，3核心涉密）")
    private String secretRelatedLevel;

    @ColumnAnnotation(FieldName = "SECRET_REVIEW_DATE",   FieldDescription="保密复审时间")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date secretReviewDate;

    @ColumnAnnotation(FieldName = "START_DATE",   FieldDescription="脱密期管理开始日期")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date startDate;

    @ColumnAnnotation(FieldName = "FINISH_DATE",   FieldDescription="脱密期管理终止日期")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date finishDate;

    @ColumnAnnotation(FieldName = "QR_SECRET_RELATED_POST",   FieldDescription="确认后涉密岗位")
    private String qrSecretRelatedPost;

    @ColumnAnnotation(FieldName = "QR_SECRET_RELATED_LEVEL",   FieldDescription="确认后涉密等级（0非涉密，1一般涉密，2重要涉密，3核心涉密）")
    private String qrSecretRelatedLevel;

    @ColumnAnnotation(FieldName = "QR_START_DATE",   FieldDescription="确认后脱密开始日期")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date qrStartDate;

    @ColumnAnnotation(FieldName = "QR_FINISH_DATE",   FieldDescription="确认后脱密终止日期")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date qrFinishDate;

    @ColumnAnnotation(FieldName = "QR_USER_ID",   FieldDescription="确认人")
    private String qrUserId;

    @ColumnAnnotation(FieldName = "QR_TIME",   FieldDescription="确认时间")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date qrTime;

    @ColumnAnnotation(FieldName = "IMPORT_YEAR",   FieldDescription="导入年度")
    private String importYear;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getA0100() {
        return a0100;
    }

    public void setA0100(String a0100) {
        this.a0100 = a0100 == null ? null : a0100.trim();
    }

    public String getB0100() {
        return b0100;
    }

    public void setB0100(String b0100) {
        this.b0100 = b0100 == null ? null : b0100.trim();
    }

    public String getSecretRelatedPost() {
        return secretRelatedPost;
    }

    public void setSecretRelatedPost(String secretRelatedPost) {
        this.secretRelatedPost = secretRelatedPost == null ? null : secretRelatedPost.trim();
    }

    public String getSecretRelatedLevel() {
        return secretRelatedLevel;
    }

    public void setSecretRelatedLevel(String secretRelatedLevel) {
        this.secretRelatedLevel = secretRelatedLevel == null ? null : secretRelatedLevel.trim();
    }

    public Date getSecretReviewDate() {
        return secretReviewDate;
    }

    public void setSecretReviewDate(Date secretReviewDate) {
        this.secretReviewDate = secretReviewDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(Date finishDate) {
        this.finishDate = finishDate;
    }

    public String getQrSecretRelatedPost() {
        return qrSecretRelatedPost;
    }

    public void setQrSecretRelatedPost(String qrSecretRelatedPost) {
        this.qrSecretRelatedPost = qrSecretRelatedPost == null ? null : qrSecretRelatedPost.trim();
    }

    public String getQrSecretRelatedLevel() {
        return qrSecretRelatedLevel;
    }

    public void setQrSecretRelatedLevel(String qrSecretRelatedLevel) {
        this.qrSecretRelatedLevel = qrSecretRelatedLevel == null ? null : qrSecretRelatedLevel.trim();
    }

    public Date getQrStartDate() {
        return qrStartDate;
    }

    public void setQrStartDate(Date qrStartDate) {
        this.qrStartDate = qrStartDate;
    }

    public Date getQrFinishDate() {
        return qrFinishDate;
    }

    public void setQrFinishDate(Date qrFinishDate) {
        this.qrFinishDate = qrFinishDate;
    }

    public String getQrUserId() {
        return qrUserId;
    }

    public void setQrUserId(String qrUserId) {
        this.qrUserId = qrUserId == null ? null : qrUserId.trim();
    }

    public Date getQrTime() {
        return qrTime;
    }

    public void setQrTime(Date qrTime) {
        this.qrTime = qrTime;
    }

    public String getImportYear() {
        return importYear;
    }

    public void setImportYear(String importYear) {
        this.importYear = importYear == null ? null : importYear.trim();
    }
}