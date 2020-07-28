package com.hxoms.modules.omsregcadre.entity;

import com.hxoms.common.hxannotation.ColumnAnnotation;
import com.hxoms.common.hxannotation.IdAnnotation;
import com.hxoms.common.hxannotation.TableAnnotation;


import java.util.Date;
/**
 * oms_reg_procbatch
 */
@TableAnnotation(TableName = "oms_reg_procbatch", TableDescription="登记备案批次表")
public class OmsRegProcbatch {
    /**
     * 主键
     */
    @IdAnnotation
    @ColumnAnnotation(FieldName = "ID",  FieldDescription="主键")
    private String id;

    /**
     * 创建时间
     */
    @ColumnAnnotation(FieldName = "CREATE_DATE",  FieldDescription="创建时间")
    private Date createDate;

    /**
     * 创建人
     */
    @ColumnAnnotation(FieldName = "CREATE_USER",  FieldDescription="创建人")
    private String createUser;

    /**
     * 批次号组织机构代码加yyyymmdd
     */
    @ColumnAnnotation(FieldName = "BATCH_NO",  FieldDescription="批次号组织机构代码加yyyymmdd")
    private String batchNo;


    @ColumnAnnotation(FieldName = "RF_B0000",   FieldDescription="备案机构")
    private String rfB0000;

    /**
     * 登记备案单位
     */
    @ColumnAnnotation(FieldName = "RF_UNNIT",  FieldDescription="登记备案单位")
    private String rfUnnit;

    /**
     * 登记备案单位联系人
     */
    @ColumnAnnotation(FieldName = "RF_UCONTACTS",  FieldDescription="登记备案单位联系人")
    private String rfUcontacts;

    /**
     * 登记备案登记备案单位联系电话
     */
    @ColumnAnnotation(FieldName = "RF_UPHONE",  FieldDescription="登记备案登记备案单位联系电话")
    private String rfUphone;

    /**
     * 报送单位组织机构代码
     */
    @ColumnAnnotation(FieldName = "SUBMIT_UB0000",  FieldDescription="报送单位组织机构代码")
    private String submitUb0000;

    /**
     * 报送单位名称
     */
    @ColumnAnnotation(FieldName = "SUBMIT_UNAME",  FieldDescription="报送单位名称")
    private String submitUname;

    /**
     * 报送单位类别
     */
    @ColumnAnnotation(FieldName = "SUBMIT_UCATEGORY",  FieldDescription="报送单位类别")
    private String submitUcategory;

    /**
     * 报送单位联系人
     */
    @ColumnAnnotation(FieldName = "SUBMIT_UCONTACTS",  FieldDescription="报送单位联系人")
    private String submitUcontacts;

    /**
     * 联系电话
     */
    @ColumnAnnotation(FieldName = "SUBMIT_PHONE",  FieldDescription="联系电话")
    private String submitPhone;

    /**
     * 报送时间
     */
    @ColumnAnnotation(FieldName = "SUBMIT_TIME",  FieldDescription="报送时间")
    private Date submitTime;

    /**
     * 报送人
     */
    @ColumnAnnotation(FieldName = "SUBMIT_PERSON",  FieldDescription="报送人")
    private String submitPerson;

    /**
     * 状态
     */
    @ColumnAnnotation(FieldName = "STATUS",  FieldDescription="状态")
    private String status;

    /**
     * 状态
     */
    @ColumnAnnotation(FieldName = "REMARK",  FieldDescription="备注")
    private String remark;
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
     * 创建时间
     * @return CREATE_DATE 创建时间
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * 创建时间
     * @param createDate 创建时间
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * 创建人
     * @return CREATE_USER 创建人
     */
    public String getCreateUser() {
        return createUser;
    }

    /**
     * 创建人
     * @param createUser 创建人
     */
    public void setCreateUser(String createUser) {
        this.createUser = createUser == null ? null : createUser.trim();
    }

    /**
     * 批次号组织机构代码加yyyymmdd
     * @return BATCH_NO 批次号组织机构代码加yyyymmdd
     */
    public String getBatchNo() {
        return batchNo;
    }

    /**
     * 批次号组织机构代码加yyyymmdd
     * @param batchNo 批次号组织机构代码加yyyymmdd
     */
    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo == null ? null : batchNo.trim();
    }

    /**
     * 登记备案单位
     * @return RF_UNNIT 登记备案单位
     */
    public String getRfUnnit() {
        return rfUnnit;
    }

    /**
     * 登记备案单位
     * @param rfUnnit 登记备案单位
     */
    public void setRfUnnit(String rfUnnit) {
        this.rfUnnit = rfUnnit == null ? null : rfUnnit.trim();
    }

    /**
     * 登记备案单位联系人
     * @return RF_UCONTACTS 登记备案单位联系人
     */
    public String getRfUcontacts() {
        return rfUcontacts;
    }

    /**
     * 登记备案单位联系人
     * @param rfUcontacts 登记备案单位联系人
     */
    public void setRfUcontacts(String rfUcontacts) {
        this.rfUcontacts = rfUcontacts == null ? null : rfUcontacts.trim();
    }

    /**
     * 登记备案登记备案单位联系电话
     * @return RF_UPHONE 登记备案登记备案单位联系电话
     */
    public String getRfUphone() {
        return rfUphone;
    }

    /**
     * 登记备案登记备案单位联系电话
     * @param rfUphone 登记备案登记备案单位联系电话
     */
    public void setRfUphone(String rfUphone) {
        this.rfUphone = rfUphone == null ? null : rfUphone.trim();
    }

    /**
     * 报送单位组织机构代码
     * @return SUBMIT_UB0000 报送单位组织机构代码
     */
    public String getSubmitUb0000() {
        return submitUb0000;
    }

    /**
     * 报送单位组织机构代码
     * @param submitUb0000 报送单位组织机构代码
     */
    public void setSubmitUb0000(String submitUb0000) {
        this.submitUb0000 = submitUb0000 == null ? null : submitUb0000.trim();
    }

    /**
     * 报送单位名称
     * @return SUBMIT_UNAME 报送单位名称
     */
    public String getSubmitUname() {
        return submitUname;
    }

    /**
     * 报送单位名称
     * @param submitUname 报送单位名称
     */
    public void setSubmitUname(String submitUname) {
        this.submitUname = submitUname == null ? null : submitUname.trim();
    }

    /**
     * 报送单位类别
     * @return SUBMIT_UCATEGORY 报送单位类别
     */
    public String getSubmitUcategory() {
        return submitUcategory;
    }

    /**
     * 报送单位类别
     * @param submitUcategory 报送单位类别
     */
    public void setSubmitUcategory(String submitUcategory) {
        this.submitUcategory = submitUcategory == null ? null : submitUcategory.trim();
    }

    /**
     * 报送单位联系人
     * @return SUBMIT_UCONTACTS 报送单位联系人
     */
    public String getSubmitUcontacts() {
        return submitUcontacts;
    }

    /**
     * 报送单位联系人
     * @param submitUcontacts 报送单位联系人
     */
    public void setSubmitUcontacts(String submitUcontacts) {
        this.submitUcontacts = submitUcontacts == null ? null : submitUcontacts.trim();
    }

    /**
     * 联系电话
     * @return SUBMIT_PHONE 联系电话
     */
    public String getSubmitPhone() {
        return submitPhone;
    }

    /**
     * 联系电话
     * @param submitPhone 联系电话
     */
    public void setSubmitPhone(String submitPhone) {
        this.submitPhone = submitPhone == null ? null : submitPhone.trim();
    }

    /**
     * 报送时间
     * @return SUBMIT_TIME 报送时间
     */
    public Date getSubmitTime() {
        return submitTime;
    }

    /**
     * 报送时间
     * @param submitTime 报送时间
     */
    public void setSubmitTime(Date submitTime) {
        this.submitTime = submitTime;
    }

    /**
     * 报送人
     * @return SUBMIT_PERSON 报送人
     */
    public String getSubmitPerson() {
        return submitPerson;
    }

    /**
     * 报送人
     * @param submitPerson 报送人
     */
    public void setSubmitPerson(String submitPerson) {
        this.submitPerson = submitPerson == null ? null : submitPerson.trim();
    }

    /**
     * 状态
     * @return STATUS 状态
     */
    public String getStatus() {
        return status;
    }

    /**
     * 状态
     * @param status 状态
     */
    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getRfB0000() {
        return rfB0000;
    }

    public void setRfB0000(String rfB0000) {
        this.rfB0000 = rfB0000;
    }
}