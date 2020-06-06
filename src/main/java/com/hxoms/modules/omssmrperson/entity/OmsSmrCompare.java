package com.hxoms.modules.omssmrperson.entity;

import com.hxoms.common.hxannotation.ColumnAnnotation;
import com.hxoms.common.hxannotation.IdAnnotation;
import com.hxoms.common.hxannotation.TableAnnotation;

@TableAnnotation(TableName = "oms_smr_compare", TableDescription="保密局涉密人员身份证对照表")
public class OmsSmrCompare {
    @IdAnnotation
    @ColumnAnnotation(FieldName = "ID",   FieldDescription="主键")
    private String id;

    @ColumnAnnotation(FieldName = "ID_CARD_NUMBER_ERROR",   FieldDescription="错误身份证号")
    private String idCardNumberError;

    @ColumnAnnotation(FieldName = "ID_CARD_NUMBER_RIGHT",   FieldDescription="正确身份证号")
    private String idCardNumberRight;

    @ColumnAnnotation(FieldName = "NAME",   FieldDescription="姓名")
    private String name;

    @ColumnAnnotation(FieldName = "SEX",   FieldDescription="性别")
    private String sex;

    @ColumnAnnotation(FieldName = "BIRTH_DATE",   FieldDescription="出生年月")
    private String birthDate;

    @ColumnAnnotation(FieldName = "WORK_UNIT",   FieldDescription="工作单位")
    private String workUnit;

    @ColumnAnnotation(FieldName = "POST",   FieldDescription="职务职级")
    private String post;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getIdCardNumberError() {
        return idCardNumberError;
    }

    public void setIdCardNumberError(String idCardNumberError) {
        this.idCardNumberError = idCardNumberError == null ? null : idCardNumberError.trim();
    }

    public String getIdCardNumberRight() {
        return idCardNumberRight;
    }

    public void setIdCardNumberRight(String idCardNumberRight) {
        this.idCardNumberRight = idCardNumberRight == null ? null : idCardNumberRight.trim();
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

    public String getWorkUnit() {
        return workUnit;
    }

    public void setWorkUnit(String workUnit) {
        this.workUnit = workUnit == null ? null : workUnit.trim();
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post == null ? null : post.trim();
    }
}