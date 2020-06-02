package com.hxoms.modules.omsoperator.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hxoms.common.hxannotation.ColumnAnnotation;
import com.hxoms.common.hxannotation.IdAnnotation;
import com.hxoms.common.hxannotation.TableAnnotation;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * oms_operator_handover_subform
 */
@TableAnnotation(TableName = "oms_operator_handover_subform", TableDescription="")
public class OmsOperatorHandoverSubform {
    /**
     * 交接子表主键
     */
    @IdAnnotation
    @ColumnAnnotation(FieldName = "ID",  FieldDescription="交接子表主键")
    private String id;

    /**
     * 交接表主表主键
     */
    @ColumnAnnotation(FieldName = "HandoverFormID",  FieldDescription="交接表主表主键")
    private String handoverformid;

    /**
     * 业务主键
     */
    @ColumnAnnotation(FieldName = "BusinessId",  FieldDescription="业务主键")
    private String businessid;

    /**
     * 业务类别（1.证照领取 2.因公出国（境） 3.因私出国（境））
     */
    @ColumnAnnotation(FieldName = "BusinessType",  FieldDescription="业务类别（1.证照领取 2.因公出国（境） 3.因私出国（境））")
    private String businesstype;

    /**
     * 人员主键
     */
    @ColumnAnnotation(FieldName = "A0100",  FieldDescription="人员主键")
    private String a0100;

    /**
     * 姓名
     */
    @ColumnAnnotation(FieldName = "Name",  FieldDescription="姓名")
    private String name;

    /**
     * 性别
     */
    @ColumnAnnotation(FieldName = "Sex",  FieldDescription="性别")
    private String sex;

    /**
     * 出生日期
     */
    @ColumnAnnotation(FieldName = "Birthday",  FieldDescription="出生日期")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date birthday;

    /**
     * 身份证号
     */
    @ColumnAnnotation(FieldName = "IDCard",  FieldDescription="身份证号")
    private String idcard;

    /**
     * 政治面貌
     */
    @ColumnAnnotation(FieldName = "PoliticsStatus",  FieldDescription="政治面貌")
    private String politicsstatus;

    /**
     * 工作单位
     */
    @ColumnAnnotation(FieldName = "Company",  FieldDescription="工作单位")
    private String company;

    /**
     * 职务（级）
     */
    @ColumnAnnotation(FieldName = "Duty",  FieldDescription="职务（级）")
    private String duty;

    /**
     * 交接时间
     */
    @ColumnAnnotation(FieldName = "HandoverTime",  FieldDescription="交接时间")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date handovertime;

    /**
     * 交接人ID
     */
    @ColumnAnnotation(FieldName = "HandoverId",  FieldDescription="交接人ID")
    private String handoverid;

    /**
     * 出国（境）时间
     */
    @ColumnAnnotation(FieldName = "ExitDate",  FieldDescription="出国（境）时间")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date exitdate;

    /**
     * 入境时间
     */
    @ColumnAnnotation(FieldName = "EntryDate",  FieldDescription="入境时间")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date entrydate;

    /**
     * 说明
     */
    @ColumnAnnotation(FieldName = "Explain",  FieldDescription="说明")
    private String explain;

    /**
     * 备注
     */
    @ColumnAnnotation(FieldName = "Remark",  FieldDescription="备注")
    private String remark;

    /**
     * 交接子表主键
     * @return ID 交接子表主键
     */
    public String getId() {
        return id;
    }

    /**
     * 交接子表主键
     * @param id 交接子表主键
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * 交接表主表主键
     * @return HandoverFormID 交接表主表主键
     */
    public String getHandoverformid() {
        return handoverformid;
    }

    /**
     * 交接表主表主键
     * @param handoverformid 交接表主表主键
     */
    public void setHandoverformid(String handoverformid) {
        this.handoverformid = handoverformid == null ? null : handoverformid.trim();
    }

    /**
     * 业务主键
     * @return BusinessId 业务主键
     */
    public String getBusinessid() {
        return businessid;
    }

    /**
     * 业务主键
     * @param businessid 业务主键
     */
    public void setBusinessid(String businessid) {
        this.businessid = businessid == null ? null : businessid.trim();
    }

    /**
     * 业务类别（1.证照领取 2.因公出国（境） 3.因私出国（境））
     * @return BusinessType 业务类别（1.证照领取 2.因公出国（境） 3.因私出国（境））
     */
    public String getBusinesstype() {
        return businesstype;
    }

    /**
     * 业务类别（1.证照领取 2.因公出国（境） 3.因私出国（境））
     * @param businesstype 业务类别（1.证照领取 2.因公出国（境） 3.因私出国（境））
     */
    public void setBusinesstype(String businesstype) {
        this.businesstype = businesstype == null ? null : businesstype.trim();
    }

    /**
     * 人员主键
     * @return A0100 人员主键
     */
    public String getA0100() {
        return a0100;
    }

    /**
     * 人员主键
     * @param a0100 人员主键
     */
    public void setA0100(String a0100) {
        this.a0100 = a0100 == null ? null : a0100.trim();
    }

    /**
     * 姓名
     * @return Name 姓名
     */
    public String getName() {
        return name;
    }

    /**
     * 姓名
     * @param name 姓名
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * 性别
     * @return Sex 性别
     */
    public String getSex() {
        return sex;
    }

    /**
     * 性别
     * @param sex 性别
     */
    public void setSex(String sex) {
        this.sex = sex == null ? null : sex.trim();
    }

    /**
     * 出生日期
     * @return Birthday 出生日期
     */
    public Date getBirthday() {
        return birthday;
    }

    /**
     * 出生日期
     * @param birthday 出生日期
     */
    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    /**
     * 身份证号
     * @return IDCard 身份证号
     */
    public String getIdcard() {
        return idcard;
    }

    /**
     * 身份证号
     * @param idcard 身份证号
     */
    public void setIdcard(String idcard) {
        this.idcard = idcard == null ? null : idcard.trim();
    }

    /**
     * 政治面貌
     * @return PoliticsStatus 政治面貌
     */
    public String getPoliticsstatus() {
        return politicsstatus;
    }

    /**
     * 政治面貌
     * @param politicsstatus 政治面貌
     */
    public void setPoliticsstatus(String politicsstatus) {
        this.politicsstatus = politicsstatus == null ? null : politicsstatus.trim();
    }

    /**
     * 工作单位
     * @return Company 工作单位
     */
    public String getCompany() {
        return company;
    }

    /**
     * 工作单位
     * @param company 工作单位
     */
    public void setCompany(String company) {
        this.company = company == null ? null : company.trim();
    }

    /**
     * 职务（级）
     * @return Duty 职务（级）
     */
    public String getDuty() {
        return duty;
    }

    /**
     * 职务（级）
     * @param duty 职务（级）
     */
    public void setDuty(String duty) {
        this.duty = duty == null ? null : duty.trim();
    }

    /**
     * 交接时间
     * @return HandoverTime 交接时间
     */
    public Date getHandovertime() {
        return handovertime;
    }

    /**
     * 交接时间
     * @param handovertime 交接时间
     */
    public void setHandovertime(Date handovertime) {
        this.handovertime = handovertime;
    }

    /**
     * 交接人ID
     * @return HandoverId 交接人ID
     */
    public String getHandoverid() {
        return handoverid;
    }

    /**
     * 交接人ID
     * @param handoverid 交接人ID
     */
    public void setHandoverid(String handoverid) {
        this.handoverid = handoverid == null ? null : handoverid.trim();
    }

    /**
     * 出国（境）时间
     * @return ExitDate 出国（境）时间
     */
    public Date getExitdate() {
        return exitdate;
    }

    /**
     * 出国（境）时间
     * @param exitdate 出国（境）时间
     */
    public void setExitdate(Date exitdate) {
        this.exitdate = exitdate;
    }

    /**
     * 入境时间
     * @return EntryDate 入境时间
     */
    public Date getEntrydate() {
        return entrydate;
    }

    /**
     * 入境时间
     * @param entrydate 入境时间
     */
    public void setEntrydate(Date entrydate) {
        this.entrydate = entrydate;
    }

    /**
     * 说明
     * @return Explain 说明
     */
    public String getExplain() {
        return explain;
    }

    /**
     * 说明
     * @param explain 说明
     */
    public void setExplain(String explain) {
        this.explain = explain == null ? null : explain.trim();
    }

    /**
     * 备注
     * @return Remark 备注
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 备注
     * @param remark 备注
     */
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}