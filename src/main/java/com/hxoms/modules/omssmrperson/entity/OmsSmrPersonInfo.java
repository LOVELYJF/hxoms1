package com.hxoms.modules.omssmrperson.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hxoms.common.hxannotation.ColumnAnnotation;
import com.hxoms.common.hxannotation.IdAnnotation;
import com.hxoms.common.hxannotation.TableAnnotation;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

@TableAnnotation(TableName = "oms_smr_personinfo", TableDescription="涉密人员信息表")
public class OmsSmrPersonInfo {
    @IdAnnotation
    @ColumnAnnotation(FieldName = "ID",   FieldDescription="主键")
    private String id;

    @ColumnAnnotation(FieldName = "A0100",   FieldDescription="人员主键")
    private String a0100;

    @ColumnAnnotation(FieldName = "B0100",   FieldDescription="单位主键")
    private String b0100;

    @ColumnAnnotation(FieldName = "B0101",   FieldDescription="单位名称")
    private String b0101;

    @ColumnAnnotation(FieldName = "NAME",   FieldDescription="姓名")
    private String name;

    @ColumnAnnotation(FieldName = "SEX",   FieldDescription="性别")
    private String sex;

    @ColumnAnnotation(FieldName = "ID_CARD_NUMBER",   FieldDescription="身份证号码")
    private String idCardNumber;

    @ColumnAnnotation(FieldName = "A0141",   FieldDescription="政治面貌")
    private String a0141;

    @ColumnAnnotation(FieldName = "POST",   FieldDescription="职务职级")
    private String post;

    @ColumnAnnotation(FieldName = "PERSON_STATE",   FieldDescription="管理状态")
    private String personState;

    @ColumnAnnotation(FieldName = "SECRET_RELATED_POST",   FieldDescription="涉密岗位")
    private String secretRelatedPost;

    @ColumnAnnotation(FieldName = "SECRET_RELATED_LEVEL",   FieldDescription="涉密等级")
    private String secretRelatedLevel;

    @ColumnAnnotation(FieldName = "SECRET_REVIEW_DATE",   FieldDescription="保密复审时间")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date secretReviewDate;

    @ColumnAnnotation(FieldName = "SECRET_RELATED_ORG",   FieldDescription="涉密定级单位")
    private String secretRelatedOrg;

    @ColumnAnnotation(FieldName = "MAX_SECRET_RELATED_ORG",   FieldDescription="最高等级定级单位")
    private String maxSecretRelatedOrg;

    @ColumnAnnotation(FieldName = "MAX_SECRET_RELATED_LEVEL",   FieldDescription="最高涉密等级")
    private String maxSecretRelatedLevel;

    @ColumnAnnotation(FieldName = "MAX_FINISH_DATE",   FieldDescription="最长脱密结束日期")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date maxFinishDate;

    @ColumnAnnotation(FieldName = "START_DATE",   FieldDescription="脱密期管理开始日期")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date startDate;

    @ColumnAnnotation(FieldName = "FINISH_DATE",   FieldDescription="脱密期管理终止日期")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date finishDate;

    @ColumnAnnotation(FieldName = "UPDATE_USER_ID",   FieldDescription="更新人")
    private String updateUserId;

    @ColumnAnnotation(FieldName = "UPDATE_TIME",   FieldDescription="更新时间")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date updateTime;

    @ColumnAnnotation(FieldName = "IMPORT_YEAR",   FieldDescription="导入年度")
    private String importYear;

    @ColumnAnnotation(FieldName = "DATASOURCE",   FieldDescription="数据来源")
    private String dataSource;

    @ColumnAnnotation(FieldName = "BIRTHDAY",   FieldDescription="出生日期")
    private String birthDay;

    @ColumnAnnotation(FieldName = "NATION",   FieldDescription="民族")
    private String nation;

    @ColumnAnnotation(FieldName = "REMARK",   FieldDescription="备注")
    private String remark;

    @ColumnAnnotation(FieldName = "MSG",   FieldDescription="返回消息")
    private String msg;

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

    public String getB0101() {
        return b0101;
    }

    public void setB0101(String b0101) {
        this.b0101 = b0101;
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
        this.sex = sex;
    }

    public String getIdCardNumber() {
        return idCardNumber;
    }

    public void setIdCardNumber(String idCardNumber) {
        this.idCardNumber = idCardNumber == null ? null : idCardNumber.trim();
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

    public String getPersonState() {
        return personState;
    }

    public void setPersonState(String personState) {
        this.personState = personState == null ? null : personState.trim();
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

    public String getSecretRelatedOrg() {
        return secretRelatedOrg;
    }

    public void setSecretRelatedOrg(String secretRelatedOrg) {
        this.secretRelatedOrg = secretRelatedOrg == null ? null : secretRelatedOrg.trim();
    }

    public String getMaxSecretRelatedOrg() {
        return maxSecretRelatedOrg;
    }

    public void setMaxSecretRelatedOrg(String maxSecretRelatedOrg) {
        this.maxSecretRelatedOrg = maxSecretRelatedOrg == null ? null : maxSecretRelatedOrg.trim();
    }

    public String getMaxSecretRelatedLevel() {
        return maxSecretRelatedLevel;
    }

    public void setMaxSecretRelatedLevel(String maxSecretRelatedLevel) {
        this.maxSecretRelatedLevel = maxSecretRelatedLevel == null ? null : maxSecretRelatedLevel.trim();
    }

    public Date getMaxFinishDate() {
        return maxFinishDate;
    }

    public void setMaxFinishDate(Date maxFinishDate) {
        this.maxFinishDate = maxFinishDate;
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

    public String getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(String updateUserId) {
        this.updateUserId = updateUserId == null ? null : updateUserId.trim();
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getImportYear() {
        return importYear;
    }

    public void setImportYear(String importYear) {
        this.importYear = importYear == null ? null : importYear.trim();
    }

    public String getDataSource() {
        return dataSource;
    }

    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
    }

    public String getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(String birthDay) {
        this.birthDay = birthDay;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}