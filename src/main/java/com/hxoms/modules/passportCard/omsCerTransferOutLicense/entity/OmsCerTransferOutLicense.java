package com.hxoms.modules.passportCard.omsCerTransferOutLicense.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hxoms.common.hxannotation.ColumnAnnotation;
import com.hxoms.common.hxannotation.IdAnnotation;
import com.hxoms.common.hxannotation.TableAnnotation;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.format.annotation.DateTimeFormat;

@TableAnnotation(TableName = "oms_cer_transfer_out_license", TableDescription="转出证照申请表")
public class OmsCerTransferOutLicense {
    @IdAnnotation
    @ColumnAnnotation(FieldName = "ID",   FieldDescription="主键")
    private String id;

    @ColumnAnnotation(FieldName = "OMS_ID",   FieldDescription="备案信息表ID主键")
    private String omsId;

    @ColumnAnnotation(FieldName = "B0100",   FieldDescription="名称主键")
    private String b0100;

    @ColumnAnnotation(FieldName = "WORK_UNIT",   FieldDescription="单位名称")
    private String workUnit;

    @ColumnAnnotation(FieldName = "NAME",   FieldDescription="姓名")
    private String name;

    @ColumnAnnotation(FieldName = "HANDOVER_TIME",   FieldDescription="交接时间")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date handoverTime;

    @ColumnAnnotation(FieldName = "EXIT_TIME",   FieldDescription="退出时间")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date exitTime;

    @ColumnAnnotation(FieldName = "EXIT_WAY",   FieldDescription="退出方式")
    private String exitWay;

    @ColumnAnnotation(FieldName = "ZJLX",   FieldDescription="证件类型")
    private String zjlx;

    @ColumnAnnotation(FieldName = "ZJHM",   FieldDescription="证件号码")
    private String zjhm;

    @ColumnAnnotation(FieldName = "YXQZ",   FieldDescription="有效期至")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date yxqz;

    @ColumnAnnotation(FieldName = "HANDOVER_START_QUERY_TIME",   FieldDescription="交接时间查询开始时间")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date handoverStartQueryTime;

    @ColumnAnnotation(FieldName = "HANDOVER_END_QUERY_TIME",   FieldDescription="交接时间查询结束时间")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date handoverEndQueryTime;

    @ColumnAnnotation(FieldName = "EXIT_START_QUERY_TIME",   FieldDescription="推出查询开始时间")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date exitStartQueryTime;

    @ColumnAnnotation(FieldName = "EXIT_END_QUERY_TIME",   FieldDescription="退出查询结束时间")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date exitEndQueryTime;

    @ColumnAnnotation(FieldName = "TRANSFEROR",   FieldDescription="移交人")
    private String transferor;

    @ColumnAnnotation(FieldName = "RECEIVER",   FieldDescription="接收人")
    private String receiver;

    @ColumnAnnotation(FieldName = "YEAR",   FieldDescription="年份")
    private String year;

    @ColumnAnnotation(FieldName = "BATCH_NUM",   FieldDescription="批次号")
    private String batchNum;

    @ColumnAnnotation(FieldName = "CREATE_USER",   FieldDescription="创建人")
    private String createUser;

    @ColumnAnnotation(FieldName = "CREATE_TIME",   FieldDescription="创建时间")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date createTime;

    @ColumnAnnotation(FieldName = "MODIFY_USER",   FieldDescription="修改人")
    private String modifyUser;

    @ColumnAnnotation(FieldName = "MODIFY_TIME",   FieldDescription="修改时间")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date modifyTime;

    private List<OmsCerTransferOutLicense> list;

    public List<OmsCerTransferOutLicense> getList() {
        return list;
    }

    public void setList(List<OmsCerTransferOutLicense> list) {
        this.list = list;
    }

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Date getHandoverTime() {
        return handoverTime;
    }

    public void setHandoverTime(Date handoverTime) {
        this.handoverTime = handoverTime;
    }

    public Date getExitTime() {
        return exitTime;
    }

    public void setExitTime(Date exitTime) {
        this.exitTime = exitTime;
    }

    public String getExitWay() {
        return exitWay;
    }

    public void setExitWay(String exitWay) {
        this.exitWay = exitWay == null ? null : exitWay.trim();
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

    public Date getYxqz() {
        return yxqz;
    }

    public void setYxqz(Date yxqz) {
        this.yxqz = yxqz;
    }

    public Date getHandoverStartQueryTime() {
        return handoverStartQueryTime;
    }

    public void setHandoverStartQueryTime(Date handoverStartQueryTime) {
        this.handoverStartQueryTime = handoverStartQueryTime;
    }

    public Date getHandoverEndQueryTime() {
        return handoverEndQueryTime;
    }

    public void setHandoverEndQueryTime(Date handoverEndQueryTime) {
        this.handoverEndQueryTime = handoverEndQueryTime;
    }

    public Date getExitStartQueryTime() {
        return exitStartQueryTime;
    }

    public void setExitStartQueryTime(Date exitStartQueryTime) {
        this.exitStartQueryTime = exitStartQueryTime;
    }

    public Date getExitEndQueryTime() {
        return exitEndQueryTime;
    }

    public void setExitEndQueryTime(Date exitEndQueryTime) {
        this.exitEndQueryTime = exitEndQueryTime;
    }

    public String getTransferor() {
        return transferor;
    }

    public void setTransferor(String transferor) {
        this.transferor = transferor == null ? null : transferor.trim();
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver == null ? null : receiver.trim();
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year == null ? null : year.trim();
    }

    public String getBatchNum() {
        return batchNum;
    }

    public void setBatchNum(String batchNum) {
        this.batchNum = batchNum == null ? null : batchNum.trim();
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser == null ? null : createUser.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getModifyUser() {
        return modifyUser;
    }

    public void setModifyUser(String modifyUser) {
        this.modifyUser = modifyUser == null ? null : modifyUser.trim();
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

}