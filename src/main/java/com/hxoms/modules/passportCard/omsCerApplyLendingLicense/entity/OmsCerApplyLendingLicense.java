package com.hxoms.modules.passportCard.omsCerApplyLendingLicense.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hxoms.common.hxannotation.ColumnAnnotation;
import com.hxoms.common.hxannotation.IdAnnotation;
import com.hxoms.common.hxannotation.TableAnnotation;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

@TableAnnotation(TableName = "oms_cer_apply_lending_license", TableDescription="借出证照信息表")
public class OmsCerApplyLendingLicense {
    @IdAnnotation
    @ColumnAnnotation(FieldName = "ID",   FieldDescription="主键")
    private String id;

    @ColumnAnnotation(FieldName = "OMS_ID",   FieldDescription="备案信息表ID主键")
    private String omsId;

    @ColumnAnnotation(FieldName = "NAME",   FieldDescription="姓名")
    private String name;

    @ColumnAnnotation(FieldName = "SQJCZT",   FieldDescription="申请借出状态 0-申请 1-审批 2-已审批 3-已拒绝 4-撤销")
    private String sqjczt;

    @ColumnAnnotation(FieldName = "ZJLX",   FieldDescription="证件类型")
    private String zjlx;

    @ColumnAnnotation(FieldName = "ZJHM",   FieldDescription="证件号码")
    private String zjhm;

    @ColumnAnnotation(FieldName = "JBR",   FieldDescription="经办人")
    private String jbr;

    @ColumnAnnotation(FieldName = "RETURN_TIME",   FieldDescription="归还时间")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date returnTime;

    @ColumnAnnotation(FieldName = "LEND_REASON",   FieldDescription="借出理由")
    private String lendReason;

    @ColumnAnnotation(FieldName = "DOCUMENT_NUM",   FieldDescription="文书号")
    private String documentNum;

    @ColumnAnnotation(FieldName = "CLDYJ",   FieldDescription="处领导意见1-通过 0-不通过")
    private String cldyj;

    @ColumnAnnotation(FieldName = "BLDYJ",   FieldDescription="部领导意见")
    private String bldyj;

    @ColumnAnnotation(FieldName = "CLDYJLY",   FieldDescription="处领导意见理由")
    private String cldyjly;

    @ColumnAnnotation(FieldName = "BLDYJLY",   FieldDescription="部领导意见理由")
    private String bldyjly;

    @ColumnAnnotation(FieldName = "CREATE_TIME",   FieldDescription="创建时间")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date createTime;

    @ColumnAnnotation(FieldName = "CRAETE_USER",   FieldDescription="创建人")
    private String createUser;

    @ColumnAnnotation(FieldName = "MODIFY_USER",   FieldDescription="修改人")
    private String modifyUser;

    @ColumnAnnotation(FieldName = "MODYFY_TIME",   FieldDescription="修改时间")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date modyfyTime;

    @ColumnAnnotation(FieldName = "LENDING_QUERY_START_TIME",   FieldDescription="借出申请查询开始日期")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date lendingQueryStartTime;

    @ColumnAnnotation(FieldName = "LENDING_QUERY_END_TIME",   FieldDescription="借出申请查询结束日期")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date lendingQueryEndTime;

    @ColumnAnnotation(FieldName = "IS_COMMIT",   FieldDescription="是否提交1-是 0-否")
    private String isCommit;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getOmsId() {
        return omsId;
    }

    public void setOmsId(String omsId) {
        this.omsId = omsId == null ? null : omsId.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getSqjczt() {
        return sqjczt;
    }

    public void setSqjczt(String sqjczt) {
        this.sqjczt = sqjczt == null ? null : sqjczt.trim();
    }

    public String getZjlx() {
        return zjlx;
    }

    public void setZjlx(String zjlx) {
        this.zjlx = zjlx == null ? null : zjlx.trim();
    }

    public String getZjhm() {
        return zjhm;
    }

    public void setZjhm(String zjhm) {
        this.zjhm = zjhm == null ? null : zjhm.trim();
    }

    public String getJbr() {
        return jbr;
    }

    public void setJbr(String jbr) {
        this.jbr = jbr == null ? null : jbr.trim();
    }

    public Date getReturnTime() {
        return returnTime;
    }

    public void setReturnTime(Date returnTime) {
        this.returnTime = returnTime;
    }

    public String getLendReason() {
        return lendReason;
    }

    public void setLendReason(String lendReason) {
        this.lendReason = lendReason == null ? null : lendReason.trim();
    }

    public String getDocumentNum() {
        return documentNum;
    }

    public void setDocumentNum(String documentNum) {
        this.documentNum = documentNum == null ? null : documentNum.trim();
    }

    public String getCldyj() {
        return cldyj;
    }

    public void setCldyj(String cldyj) {
        this.cldyj = cldyj == null ? null : cldyj.trim();
    }

    public String getBldyj() {
        return bldyj;
    }

    public void setBldyj(String bldyj) {
        this.bldyj = bldyj == null ? null : bldyj.trim();
    }

    public String getCldyjly() {
        return cldyjly;
    }

    public void setCldyjly(String cldyjly) {
        this.cldyjly = cldyjly == null ? null : cldyjly.trim();
    }

    public String getBldyjly() {
        return bldyjly;
    }

    public void setBldyjly(String bldyjly) {
        this.bldyjly = bldyjly == null ? null : bldyjly.trim();
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

    public String getModifyUser() {
        return modifyUser;
    }

    public void setModifyUser(String modifyUser) {
        this.modifyUser = modifyUser == null ? null : modifyUser.trim();
    }

    public Date getModyfyTime() {
        return modyfyTime;
    }

    public void setModyfyTime(Date modyfyTime) {
        this.modyfyTime = modyfyTime;
    }

    public Date getLendingQueryStartTime() {
        return lendingQueryStartTime;
    }

    public void setLendingQueryStartTime(Date lendingQueryStartTime) {
        this.lendingQueryStartTime = lendingQueryStartTime;
    }

    public Date getLendingQueryEndTime() {
        return lendingQueryEndTime;
    }

    public void setLendingQueryEndTime(Date lendingQueryEndTime) {
        this.lendingQueryEndTime = lendingQueryEndTime;
    }

    public String getIsCommit() {
        return isCommit;
    }

    public void setIsCommit(String isCommit) {
        this.isCommit = isCommit == null ? null : isCommit.trim();
    }
}