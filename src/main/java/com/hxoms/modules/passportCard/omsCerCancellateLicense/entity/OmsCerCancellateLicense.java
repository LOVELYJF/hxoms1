package com.hxoms.modules.passportCard.omsCerCancellateLicense.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hxoms.common.hxannotation.ColumnAnnotation;
import com.hxoms.common.hxannotation.IdAnnotation;
import com.hxoms.common.hxannotation.TableAnnotation;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

@TableAnnotation(TableName = "oms_cer_cancellate_license", TableDescription="注销证照信息表")
public class OmsCerCancellateLicense {
    @IdAnnotation
    @ColumnAnnotation(FieldName = "ID",   FieldDescription="注销证照信息主键")
    private String id;

    @IdAnnotation
    @ColumnAnnotation(FieldName = "CANCELLATE_APPLY_ID",   FieldDescription="注销证照申请主键")
    private String cancellateApplyId;

    @ColumnAnnotation(FieldName = "OMS_ID",   FieldDescription="备案信息表ID主键")
    private String omsId;

    @ColumnAnnotation(FieldName = "NAME",   FieldDescription="姓名")
    private String name;

    @ColumnAnnotation(FieldName = "SEX",   FieldDescription="性别")
    private String sex;

    @ColumnAnnotation(FieldName = "CSRQ",   FieldDescription="出生日期")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date csrq;

    @ColumnAnnotation(FieldName = "B0100",   FieldDescription="单位主键")
    private String b0100;

    @ColumnAnnotation(FieldName = "WORK_UNIT",   FieldDescription="工作单位")
    private String workUnit;

    @ColumnAnnotation(FieldName = "INCUMBENCY_STATUS",   FieldDescription="任职状态")
    private String incumbencyStatus;

    @ColumnAnnotation(FieldName = "POST",   FieldDescription="职务")
    private String post;

    @ColumnAnnotation(FieldName = "ZJLX",   FieldDescription="证件类型")
    private Integer zjlx;

    @ColumnAnnotation(FieldName = "ZJHM",   FieldDescription="证件号码")
    private String zjhm;

    @ColumnAnnotation(FieldName = "YXQZ",   FieldDescription="有效期至")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date yxqz;

    @ColumnAnnotation(FieldName = "CARD_STATUS",   FieldDescription="证照状态")
    private String cardStatus;

    @ColumnAnnotation(FieldName = "SAVE_STATUS",   FieldDescription="证照保管状态")
    private String saveStatus;

    @ColumnAnnotation(FieldName = "QFJG",   FieldDescription="签发机关")
    private String qfjg;

    @ColumnAnnotation(FieldName = "QFRQ",   FieldDescription="签发日期")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date qfrq;

    @ColumnAnnotation(FieldName = "CSDD",   FieldDescription="出生地点")
    private String csdd;

    @ColumnAnnotation(FieldName = "ZHZXZT",   FieldDescription="证照申请注销状态 0-填写申请 1-生成材料 2-打印材料清单 3-提交干部监督处 4-受理 5-处领导审批 6-部领导审批 7-已审批 8-拒绝 9-撤销")
    private String zhzxzt;

    @ColumnAnnotation(FieldName = "ZXFS",   FieldDescription="注销方式 0-自行注销 1-委托")
    private String zxfs;

    @ColumnAnnotation(FieldName = "ZXYY",   FieldDescription="注销原因 1-遗失 2-被盗 3-损毁 4-其他")
    private String zxyy;

    @ColumnAnnotation(FieldName = "ZXSM",   FieldDescription="注销说明")
    private String zxsm;

    @ColumnAnnotation(FieldName = "APPEND_PLACE",   FieldDescription="发生地 1-国内 2-国外")
    private String appendPlace;

    @ColumnAnnotation(FieldName = "CREATE_TIME",   FieldDescription="创建时间")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date createTime;

    @ColumnAnnotation(FieldName = "CREATE_USER",   FieldDescription="创建人")
    private String createUser;

    @ColumnAnnotation(FieldName = "MODIFY_TIME",   FieldDescription="修改时间")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date modifyTime;

    @ColumnAnnotation(FieldName = "MODIFY_USER",   FieldDescription="修改人")
    private String modifyUser;

    @ColumnAnnotation(FieldName = "APPLY_QUERY_START_TIME",   FieldDescription="注销申请查询开始时间")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date applyQueryStartTime;

    @ColumnAnnotation(FieldName = "APPLY_QUERY_END_TIME",   FieldDescription="注销申请查询结束时间")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date applyQueryEndTime;

    @ColumnAnnotation(FieldName = "CXLY",   FieldDescription="撤销理由")
    private String cxly;

    @ColumnAnnotation(FieldName = "LSZJHM",   FieldDescription="临时证件号码")
    private String lszjhm;

    @ColumnAnnotation(FieldName = "CLDYJ",   FieldDescription="处领导意见 1-通过 0-不通过")
    private String cldyj;

    @ColumnAnnotation(FieldName = "BLDYJ",   FieldDescription="部领导意见 1-通过 0-不通过")
    private String bldyj;

    @ColumnAnnotation(FieldName = "CLDYJLY",   FieldDescription="处领导意见理由")
    private String cldyjly;

    @ColumnAnnotation(FieldName = "BLDYJLY",   FieldDescription="部领导意见理由")
    private String bldyjly;

    @ColumnAnnotation(FieldName = "GATSHYJ",   FieldDescription="公安厅审核意见")
    private String gatshyj;

    @ColumnAnnotation(FieldName = "GATYJLY",   FieldDescription="公安厅意见理由")
    private String gatyjly;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getCancellateApplyId() {
        return cancellateApplyId;
    }

    public void setCancellateApplyId(String cancellateApplyId) {
        this.cancellateApplyId = cancellateApplyId == null ? null : cancellateApplyId.trim();
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

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex == null ? null : sex.trim();
    }

    public Date getCsrq() {
        return csrq;
    }

    public void setCsrq(Date csrq) {
        this.csrq = csrq;
    }

    public String getB0100() {
        return b0100;
    }

    public void setB0100(String b0100) {
        this.b0100 = b0100 == null ? null : b0100.trim();
    }

    public String getWorkUnit() {
        return workUnit;
    }

    public void setWorkUnit(String workUnit) {
        this.workUnit = workUnit == null ? null : workUnit.trim();
    }

    public String getIncumbencyStatus() {
        return incumbencyStatus;
    }

    public void setIncumbencyStatus(String incumbencyStatus) {
        this.incumbencyStatus = incumbencyStatus == null ? null : incumbencyStatus.trim();
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post == null ? null : post.trim();
    }

    public Integer getZjlx() {
        return zjlx;
    }

    public void setZjlx(Integer zjlx) {
        this.zjlx = zjlx;
    }

    public String getZjhm() {
        return zjhm;
    }

    public void setZjhm(String zjhm) {
        this.zjhm = zjhm == null ? null : zjhm.trim();
    }

    public Date getYxqz() {
        return yxqz;
    }

    public void setYxqz(Date yxqz) {
        this.yxqz = yxqz;
    }

    public String getCardStatus() {
        return cardStatus;
    }

    public void setCardStatus(String cardStatus) {
        this.cardStatus = cardStatus == null ? null : cardStatus.trim();
    }

    public String getSaveStatus() {
        return saveStatus;
    }

    public void setSaveStatus(String saveStatus) {
        this.saveStatus = saveStatus == null ? null : saveStatus.trim();
    }

    public String getQfjg() {
        return qfjg;
    }

    public void setQfjg(String qfjg) {
        this.qfjg = qfjg == null ? null : qfjg.trim();
    }

    public Date getQfrq() {
        return qfrq;
    }

    public void setQfrq(Date qfrq) {
        this.qfrq = qfrq;
    }

    public String getCsdd() {
        return csdd;
    }

    public void setCsdd(String csdd) {
        this.csdd = csdd == null ? null : csdd.trim();
    }

    public String getZhzxzt() {
        return zhzxzt;
    }

    public void setZhzxzt(String zhzxzt) {
        this.zhzxzt = zhzxzt == null ? null : zhzxzt.trim();
    }

    public String getZxfs() {
        return zxfs;
    }

    public void setZxfs(String zxfs) {
        this.zxfs = zxfs == null ? null : zxfs.trim();
    }

    public String getZxyy() {
        return zxyy;
    }

    public void setZxyy(String zxyy) {
        this.zxyy = zxyy == null ? null : zxyy.trim();
    }

    public String getZxsm() {
        return zxsm;
    }

    public void setZxsm(String zxsm) {
        this.zxsm = zxsm == null ? null : zxsm.trim();
    }

    public String getAppendPlace() {
        return appendPlace;
    }

    public void setAppendPlace(String appendPlace) {
        this.appendPlace = appendPlace == null ? null : appendPlace.trim();
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

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getModifyUser() {
        return modifyUser;
    }

    public void setModifyUser(String modifyUser) {
        this.modifyUser = modifyUser == null ? null : modifyUser.trim();
    }

    public Date getApplyQueryStartTime() {
        return applyQueryStartTime;
    }

    public void setApplyQueryStartTime(Date applyQueryStartTime) {
        this.applyQueryStartTime = applyQueryStartTime;
    }

    public Date getApplyQueryEndTime() {
        return applyQueryEndTime;
    }

    public void setApplyQueryEndTime(Date applyQueryEndTime) {
        this.applyQueryEndTime = applyQueryEndTime;
    }

    public String getCxly() {
        return cxly;
    }

    public void setCxly(String cxly) {
        this.cxly = cxly == null ? null :cxly.trim();
    }

    public String getLszjhm() {
        return lszjhm;
    }

    public void setLszjhm(String lszjhm) {
        this.lszjhm = lszjhm == null ? null : lszjhm.trim();
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
        this.cldyjly = cldyjly;
    }

    public String getBldyjly() {
        return bldyjly;
    }

    public void setBldyjly(String bldyjly) {
        this.bldyjly = bldyjly;
    }

    public String getGatshyj() {
        return gatshyj;
    }

    public void setGatshyj(String gatshyj) {
        this.gatshyj = gatshyj;
    }

    public String getGatyjly() {
        return gatyjly;
    }

    public void setGatyjly(String gatyjly) {
        this.gatyjly = gatyjly;
    }
}