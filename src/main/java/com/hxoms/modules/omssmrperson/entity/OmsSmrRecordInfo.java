package com.hxoms.modules.omssmrperson.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.hxoms.common.hxannotation.ColumnAnnotation;
import com.hxoms.common.hxannotation.IdAnnotation;
import com.hxoms.common.hxannotation.TableAnnotation;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

@TableAnnotation(TableName = "oms_smr_recordinfo", TableDescription="省国家保密局备案涉密人员")
@TableName("oms_smr_recordinfo")
public class OmsSmrRecordInfo {
    @IdAnnotation
    @ColumnAnnotation(FieldName = "ID",   FieldDescription="主键")
    private String id;

    @ColumnAnnotation(FieldName = "B0100",   FieldDescription="单位主键")
    private String b0100;

    @ColumnAnnotation(FieldName = "NAME",   FieldDescription="姓名")
    private String name;

    @ColumnAnnotation(FieldName = "SEX",   FieldDescription="性别")
    private String sex;

    @ColumnAnnotation(FieldName = "BIRTH_DATE",   FieldDescription="出生年月")
    private String birthDate;

    @ColumnAnnotation(FieldName = "ID_CARD_NUMBER",   FieldDescription="身份证号码")
    private String idCardNumber;

    @ColumnAnnotation(FieldName = "NATION",   FieldDescription="民族")
    private String nation;

    @ColumnAnnotation(FieldName = "A0141",   FieldDescription="政治面貌")
    private String a0141;

    @ColumnAnnotation(FieldName = "POST",   FieldDescription="职务职级")
    private String post;

    @ColumnAnnotation(FieldName = "SECRET_RELATED_POST",   FieldDescription="涉密岗位")
    private String secretRelatedPost;

    @ColumnAnnotation(FieldName = "SECRET_RELATED_LEVEL",   FieldDescription="涉密等级")
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

    @ColumnAnnotation(FieldName = "IMPORT_USER_ID",   FieldDescription="导入人")
    private String importUserId;

    @ColumnAnnotation(FieldName = "IMPORT_DATE",   FieldDescription="导入日期（不带时间）")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date importDate;

    @ColumnAnnotation(FieldName = "IMPORT_YEAR",   FieldDescription="导入年度")
    private String importYear;

    @ColumnAnnotation(FieldName = "IS_MATCHING",   FieldDescription="是否匹配（0未匹配，1已匹配）")
    private String isMatching;

    @ColumnAnnotation(FieldName = "PERSON_STATE",   FieldDescription="人员类型")
    private String personState;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getB0100() {
        return b0100;
    }

    public void setB0100(String b0100) {
        this.b0100 = b0100 == null ? null : b0100.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex == null ? null : sex.trim();
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate == null ? null : birthDate.trim();
    }

    public String getIdCardNumber() {
        return idCardNumber;
    }

    public void setIdCardNumber(String idCardNumber) {
        this.idCardNumber = idCardNumber == null ? null : idCardNumber.trim();
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation == null ? null : nation.trim();
    }

    public String getA0141() {
        return a0141;
    }

    public void setA0141(String a0141) {
        this.a0141 = a0141 == null ? null : a0141.trim();
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post == null ? null : post.trim();
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

    public String getImportUserId() {
        return importUserId;
    }

    public void setImportUserId(String importUserId) {
        this.importUserId = importUserId == null ? null : importUserId.trim();
    }

    public Date getImportDate() {
        return importDate;
    }

    public void setImportDate(Date importDate) {
        this.importDate = importDate;
    }

    public String getImportYear() {
        return importYear;
    }

    public void setImportYear(String importYear) {
        this.importYear = importYear == null ? null : importYear.trim();
    }

    public String getIsMatching() {
        return isMatching;
    }

    public void setIsMatching(String isMatching) {
        this.isMatching = isMatching == null ? null : isMatching.trim();
    }

    public String getPersonState() {
        return personState;
    }

    public void setPersonState(String personState) {
        this.personState = personState;
    }
}