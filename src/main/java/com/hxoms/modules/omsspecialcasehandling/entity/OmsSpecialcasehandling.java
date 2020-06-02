package com.hxoms.modules.omsspecialcasehandling.entity;

import com.hxoms.common.hxannotation.ColumnAnnotation;
import com.hxoms.common.hxannotation.IdAnnotation;
import com.hxoms.common.hxannotation.IgnoreLogAnnotation;
import com.hxoms.common.hxannotation.TableAnnotation;
import java.util.Date;

/**
 * oms_specialcasehandling
 */
@TableAnnotation(TableName = "oms_specialcasehandling", TableDescription="")
public class OmsSpecialcasehandling {
    /**
     * 特殊情况ID
     */
    @IdAnnotation
    @ColumnAnnotation(FieldName = "ID",  FieldDescription="特殊情况ID")
    private String id;

    /**
     * 单位名称
     */
    @ColumnAnnotation(FieldName = "Company",  FieldDescription="单位名称")
    private String company;

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
    @ColumnAnnotation(FieldName = "Birth",  FieldDescription="出生日期")
    private String birth;

    /**
     * 政治面貌
     */
    @ColumnAnnotation(FieldName = "PoliticsStatus",  FieldDescription="政治面貌")
    private String politicsstatus;

    /**
     * 职务（级）
     */
    @ColumnAnnotation(FieldName = "Duty",  FieldDescription="职务（级）")
    private String duty;

    /**
     * 在职状态
     */
    @ColumnAnnotation(FieldName = "Incumbency_status",  FieldDescription="在职状态")
    private String incumbencyStatus;

    /**
     * 申请开始时间
     */
    @ColumnAnnotation(FieldName = "Start_time",  FieldDescription="申请开始时间")
    private Date startTime;

    /**
     * 申请结束时间
     */
    @ColumnAnnotation(FieldName = "End_time",  FieldDescription="申请结束时间")
    private Date endTime;

    /**
     * 人员主键
     */
    @ColumnAnnotation(FieldName = "A0100",  FieldDescription="人员主键")
    private String a0100;

    /**
     * 单位主键
     */
    @ColumnAnnotation(FieldName = "B0100",  FieldDescription="单位主键")
    private String b0100;

    /**
     * 特殊情况ID
     * @return ID 特殊情况ID
     */
    public String getId() {
        return id;
    }

    /**
     * 特殊情况ID
     * @param id 特殊情况ID
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * 单位名称
     * @return Company 单位名称
     */
    public String getCompany() {
        return company;
    }

    /**
     * 单位名称
     * @param company 单位名称
     */
    public void setCompany(String company) {
        this.company = company == null ? null : company.trim();
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
     * @return Birth 出生日期
     */
    public String getBirth() {
        return birth;
    }

    /**
     * 出生日期
     * @param birth 出生日期
     */
    public void setBirth(String birth) {
        this.birth = birth == null ? null : birth.trim();
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
     * 在职状态
     * @return Incumbency_status 在职状态
     */
    public String getIncumbencyStatus() {
        return incumbencyStatus;
    }

    /**
     * 在职状态
     * @param incumbencyStatus 在职状态
     */
    public void setIncumbencyStatus(String incumbencyStatus) {
        this.incumbencyStatus = incumbencyStatus == null ? null : incumbencyStatus.trim();
    }

    /**
     * 申请开始时间
     * @return Start_time 申请开始时间
     */
    public Date getStartTime() {
        return startTime;
    }

    /**
     * 申请开始时间
     * @param startTime 申请开始时间
     */
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    /**
     * 申请结束时间
     * @return End_time 申请结束时间
     */
    public Date getEndTime() {
        return endTime;
    }

    /**
     * 申请结束时间
     * @param endTime 申请结束时间
     */
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
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
     * 单位主键
     * @return B0100 单位主键
     */
    public String getB0100() {
        return b0100;
    }

    /**
     * 单位主键
     * @param b0100 单位主键
     */
    public void setB0100(String b0100) {
        this.b0100 = b0100 == null ? null : b0100.trim();
    }
}