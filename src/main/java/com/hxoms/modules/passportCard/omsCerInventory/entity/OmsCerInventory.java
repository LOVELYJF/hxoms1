package com.hxoms.modules.passportCard.omsCerInventory.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hxoms.common.hxannotation.ColumnAnnotation;
import com.hxoms.common.hxannotation.IdAnnotation;
import com.hxoms.common.hxannotation.TableAnnotation;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

@TableAnnotation(TableName = "oms_cer_inventory", TableDescription="证照盘点表")
public class OmsCerInventory {
    @IdAnnotation
    @ColumnAnnotation(FieldName = "ID",   FieldDescription="主键")
    private String id;

    @ColumnAnnotation(FieldName = "CF_ID",   FieldDescription="证照表主键")
    private String cfId;

    @ColumnAnnotation(FieldName = "OMS_ID",   FieldDescription="登记备案ID主键")
    private String omsId;

    @ColumnAnnotation(FieldName = "NAME",   FieldDescription="姓名")
    private String name;

    @ColumnAnnotation(FieldName = "A0100",   FieldDescription="人员主键")
    private String a0100;

    @ColumnAnnotation(FieldName = "CSRQ",   FieldDescription="出生日期")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date csrq;

    @ColumnAnnotation(FieldName = "ZJLX",   FieldDescription="证件类型")
    private String zjlx;

    @ColumnAnnotation(FieldName = "ZJHM",   FieldDescription="证件号码")
    private String zjhm;

    @ColumnAnnotation(FieldName = "YXQZ",   FieldDescription="有效期至")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date yxqz;

    @ColumnAnnotation(FieldName = "CARD_STATUS",   FieldDescription="证照状态(0:正常,1:过期,2:注销,3:验证失败,4:已验证,5:待验证,6:借出,7:待领取,8:其它)")
    private String cardStatus;

    @ColumnAnnotation(FieldName = "BEFORE_INVENTORY_SAVE_STATUS",   FieldDescription="盘点前证照保管状态(0:正常保管,1:已取出,2:未上缴)")
    private String beforeInventorySaveStatus;

    @ColumnAnnotation(FieldName = "AFTER_INVENTORY_SAVE_STATUS",   FieldDescription="盘点后证照保管状态(0:正常保管,1:已取出,2:未上缴)")
    private String afterInventorySaveStatus;

    @ColumnAnnotation(FieldName = "CABINET_NUM",   FieldDescription="机柜编号")
    private String cabinetNum;

    @ColumnAnnotation(FieldName = "PLACE",   FieldDescription="机柜位置")
    private String place;

    @ColumnAnnotation(FieldName = "COUNTER_NUM",   FieldDescription="柜台编号")
    private Integer counterNum;

    @ColumnAnnotation(FieldName = "COUNTER_START_QUERY",   FieldDescription="柜台编号查询开始编号")
    private Integer counterStartQuery;

    @ColumnAnnotation(FieldName = "COUNTER_END_QUERY",   FieldDescription="柜台编号查询结束编号")
    private Integer counterEndQuery;

    @ColumnAnnotation(FieldName = "INVENTORY_RESULT",   FieldDescription="盘点结果")
    private String inventoryResult;

    @ColumnAnnotation(FieldName = "INVENTORY_DATE",   FieldDescription="盘点年月")
    private String inventoryDate;

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }


    public String getCfId() {
        return cfId;
    }

    public void setCfId(String cfId) {
        this.cfId = cfId == null ? null : cfId.trim();
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

    public String getA0100() {
        return a0100;
    }

    public void setA0100(String a0100) {
        this.a0100 = a0100 == null ? null : a0100.trim();
    }

    public Date getCsrq() {
        return csrq;
    }

    public void setCsrq(Date csrq) {
        this.csrq = csrq;
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

    public String getCardStatus() {
        return cardStatus;
    }

    public void setCardStatus(String cardStatus) {
        this.cardStatus = cardStatus == null ? null : cardStatus.trim();
    }

    public String getBeforeInventorySaveStatus() {
        return beforeInventorySaveStatus;
    }

    public void setBeforeInventorySaveStatus(String beforeInventorySaveStatus) {
        this.beforeInventorySaveStatus = beforeInventorySaveStatus == null ? null : beforeInventorySaveStatus.trim();
    }

    public String getAfterInventorySaveStatus() {
        return afterInventorySaveStatus;
    }

    public void setAfterInventorySaveStatus(String afterInventorySaveStatus) {
        this.afterInventorySaveStatus = afterInventorySaveStatus == null ? null : afterInventorySaveStatus.trim();
    }

    public String getCabinetNum() {
        return cabinetNum;
    }

    public void setCabinetNum(String cabinetNum) {
        this.cabinetNum = cabinetNum == null ? null : cabinetNum.trim();
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place == null ? null : place.trim();
    }

    public Integer getCounterNum() {
        return counterNum;
    }

    public void setCounterNum(Integer counterNum) {
        this.counterNum = counterNum;
    }

    public Integer getCounterStartQuery() {
        return counterStartQuery;
    }

    public void setCounterStartQuery(Integer counterStartQuery) {
        this.counterStartQuery = counterStartQuery;
    }

    public Integer getCounterEndQuery() {
        return counterEndQuery;
    }

    public void setCounterEndQuery(Integer counterEndQuery) {
        this.counterEndQuery = counterEndQuery;
    }

    public String getInventoryResult() {
        return inventoryResult;
    }

    public void setInventoryResult(String inventoryResult) {
        this.inventoryResult = inventoryResult == null ? null : inventoryResult.trim();
    }

    public String getInventoryDate() {
        return inventoryDate;
    }

    public void setInventoryDate(String inventoryDate) {
        this.inventoryDate = inventoryDate == null ? null : inventoryDate.trim();
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