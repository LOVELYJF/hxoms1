package com.hxoms.modules.passportCard.initialise.entity.exportExcel;

import java.util.Date;

/**
 * 导出证照查询表 模板
 */
public class ExportExceptionCer {

    private String name;//姓名
    private String emptyField;//造假空字段
    private String  idnumberGb;//以干部的身份证为准  身份证号
    private String sex;//性别
    private Date birthDate;//出生日期 身份证的
    private String exitAndEntryDate;//出入境起始时间

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmptyField() {
        return emptyField;
    }

    public void setEmptyField(String emptyField) {
        this.emptyField = emptyField;
    }

    public String getIdnumberGb() {
        return idnumberGb;
    }

    public void setIdnumberGb(String idnumberGb) {
        this.idnumberGb = idnumberGb;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getExitAndEntryDate() {
        return exitAndEntryDate;
    }

    public void setExitAndEntryDate(String exitAndEntryDate) {
        this.exitAndEntryDate = exitAndEntryDate;
    }
}
