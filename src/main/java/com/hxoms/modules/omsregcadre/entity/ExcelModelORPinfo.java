package com.hxoms.modules.omsregcadre.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
/**
 * 国家工作人员登记备案表导出类
 * @author ljj
 */
public class ExcelModelORPinfo extends BaseRowModel {
    @ExcelProperty(value = "序号" ,index = 0)
    private int no;
    @ExcelProperty(value = "中文姓" ,index = 1)
    private String surname;
    @ExcelProperty(value = "中文名" ,index = 2)
    private String name;
    @ExcelProperty(value = "性别" ,index = 3)
    private String sex;
    @ExcelProperty(value = "身份证号" ,index = 4)
    private String idnumberGb;
    @ExcelProperty(value = "户口所在地" ,index = 5)
    private String registeResidenceCode;
    @ExcelProperty(value = "入库标识" ,index = 6)
    private String inboundFlag;
    @ExcelProperty(value = "工作单位" ,index = 7)
    private String workUnit;
    @ExcelProperty(value = "职务（级）或职称" ,index = 8)
    private String postCode;
    @ExcelProperty(value = "人事主管单位" ,index = 9)
    private String personManager;
    @ExcelProperty(value = "报送单位组织机构代码" ,index = 10)
    private String submitUb0000;
    @ExcelProperty(value = "报送单位名称" ,index = 11)
    private String submitUname;
    @ExcelProperty(value = "报送单位类别" ,index = 12)
    private String submitUcategory;
    @ExcelProperty(value = "报送单位联系人" ,index = 13)
    private String submitUcontacts;
    @ExcelProperty(value = "联系电话 " ,index = 14)
    private String submitPhone;
    @ExcelProperty(value = "入库批号" ,index = 15)
    private String rkph;

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }


    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getIdnumberGb() {
        return idnumberGb;
    }

    public void setIdnumberGb(String idnumberGb) {
        this.idnumberGb = idnumberGb;
    }

    public String getRegisteResidenceCode() {
        return registeResidenceCode;
    }

    public void setRegisteResidenceCode(String registeResidenceCode) {
        this.registeResidenceCode = registeResidenceCode;
    }

    public String getInboundFlag() {
        return inboundFlag;
    }

    public void setInboundFlag(String inboundFlag) {
        this.inboundFlag = inboundFlag;
    }

    public String getWorkUnit() {
        return workUnit;
    }

    public void setWorkUnit(String workUnit) {
        this.workUnit = workUnit;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getPersonManager() {
        return personManager;
    }

    public void setPersonManager(String personManager) {
        this.personManager = personManager;
    }

    public String getSubmitUb0000() {
        return submitUb0000;
    }

    public void setSubmitUb0000(String submitUb0000) {
        this.submitUb0000 = submitUb0000;
    }

    public String getSubmitUname() {
        return submitUname;
    }

    public void setSubmitUname(String submitUname) {
        this.submitUname = submitUname;
    }

    public String getSubmitUcategory() {
        return submitUcategory;
    }

    public void setSubmitUcategory(String submitUcategory) {
        this.submitUcategory = submitUcategory;
    }

    public String getSubmitUcontacts() {
        return submitUcontacts;
    }

    public void setSubmitUcontacts(String submitUcontacts) {
        this.submitUcontacts = submitUcontacts;
    }

    public String getSubmitPhone() {
        return submitPhone;
    }

    public void setSubmitPhone(String submitPhone) {
        this.submitPhone = submitPhone;
    }

    public String getRkph() {
        return rkph;
    }

    public void setRkph(String rkph) {
        this.rkph = rkph;
    }
}