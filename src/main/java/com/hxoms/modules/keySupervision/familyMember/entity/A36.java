package com.hxoms.modules.keySupervision.familyMember.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hxoms.common.hxannotation.ColumnAnnotation;
import com.hxoms.common.hxannotation.IdAnnotation;
import com.hxoms.common.hxannotation.TableAnnotation;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * <b>家庭成员信息表</b>
 * @author luoshuai
 * @date 2020/5/10 18:29
 */
@TableAnnotation(TableName = "a36", TableDescription="家庭成员信息表")
public class A36 {
    @IdAnnotation
    @ColumnAnnotation(FieldName = "a3600",   FieldDescription="")
    private String a3600;

    @ColumnAnnotation(FieldName = "a0100",   FieldDescription="")
    private String a0100;

    @ColumnAnnotation(FieldName = "a3601",   FieldDescription="")
    private String a3601;

    @ColumnAnnotation(FieldName = "a3604a",   FieldDescription="")
    private String a3604a;

    @ColumnAnnotation(FieldName = "a3607",   FieldDescription="")
    private String a3607;

    @ColumnAnnotation(FieldName = "a3611",   FieldDescription="")
    private String a3611;

    @ColumnAnnotation(FieldName = "a3627",   FieldDescription="")
    private String a3627;

    @ColumnAnnotation(FieldName = "id",   FieldDescription="")
    private String id;

    @ColumnAnnotation(FieldName = "is_deleted",   FieldDescription="")
    private String isDeleted;

    @ColumnAnnotation(FieldName = "modify_user",   FieldDescription="")
    private String modifyUser;

    @ColumnAnnotation(FieldName = "modify_time",   FieldDescription="")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date modifyTime;

    @ColumnAnnotation(FieldName = "node_id",   FieldDescription="")
    private String nodeId;

    @ColumnAnnotation(FieldName = "sortid",   FieldDescription="")
    private Integer sortid;

    @ColumnAnnotation(FieldName = "operate_batch",   FieldDescription="")
    private String operateBatch;

    @ColumnAnnotation(FieldName = "is_normal",   FieldDescription="")
    private String isNormal;

    @ColumnAnnotation(FieldName = "nationality",   FieldDescription="国籍")
    private Integer nationality;

    @ColumnAnnotation(FieldName = "live_place",   FieldDescription="现居住地")
    private String livePlace;

    @ColumnAnnotation(FieldName = "lmmigrate_number",   FieldDescription="移居证件号码")
    private String lmmigrateNumber;

    @ColumnAnnotation(FieldName = "lmmigrate_type",   FieldDescription="移居类别")
    private String lmmigrateType;

    @ColumnAnnotation(FieldName = "lmmigrate_time",   FieldDescription="移居时间")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date lmmigrateTime;

    @ColumnAnnotation(FieldName = "chinese_passport_number",   FieldDescription="中国护照号码")
    private String chinesePassportNumber;

    @ColumnAnnotation(FieldName = "id_card",   FieldDescription="身份证号")
    private String idCard;

    @ColumnAnnotation(FieldName = "hukou_location",   FieldDescription="户口所在地")
    private String hukouLocation;

    @ColumnAnnotation(FieldName = "person_status",   FieldDescription="人员现状")
    private String personStatus;

    @ColumnAnnotation(FieldName = "time_stamp",   FieldDescription="")
    private byte[] timeStamp;

    public String getA3600() {
        return a3600;
    }

    public void setA3600(String a3600) {
        this.a3600 = a3600 == null ? null : a3600.trim();
    }

    public String getA0100() {
        return a0100;
    }

    public void setA0100(String a0100) {
        this.a0100 = a0100 == null ? null : a0100.trim();
    }

    public String getA3601() {
        return a3601;
    }

    public void setA3601(String a3601) {
        this.a3601 = a3601 == null ? null : a3601.trim();
    }

    public String getA3604a() {
        return a3604a;
    }

    public void setA3604a(String a3604a) {
        this.a3604a = a3604a == null ? null : a3604a.trim();
    }

    public String getA3607() {
        return a3607;
    }

    public void setA3607(String a3607) {
        this.a3607 = a3607 == null ? null : a3607.trim();
    }

    public String getA3611() {
        return a3611;
    }

    public void setA3611(String a3611) {
        this.a3611 = a3611 == null ? null : a3611.trim();
    }

    public String getA3627() {
        return a3627;
    }

    public void setA3627(String a3627) {
        this.a3627 = a3627 == null ? null : a3627.trim();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(String isDeleted) {
        this.isDeleted = isDeleted == null ? null : isDeleted.trim();
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

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId == null ? null : nodeId.trim();
    }

    public Integer getSortid() {
        return sortid;
    }

    public void setSortid(Integer sortid) {
        this.sortid = sortid;
    }

    public String getOperateBatch() {
        return operateBatch;
    }

    public void setOperateBatch(String operateBatch) {
        this.operateBatch = operateBatch == null ? null : operateBatch.trim();
    }

    public String getIsNormal() {
        return isNormal;
    }

    public void setIsNormal(String isNormal) {
        this.isNormal = isNormal == null ? null : isNormal.trim();
    }

    public Integer getNationality() {
        return nationality;
    }

    public void setNationality(Integer nationality) {
        this.nationality = nationality;
    }

    public String getLivePlace() {
        return livePlace;
    }

    public void setLivePlace(String livePlace) {
        this.livePlace = livePlace == null ? null : livePlace.trim();
    }

    public String getLmmigrateNumber() {
        return lmmigrateNumber;
    }

    public void setLmmigrateNumber(String lmmigrateNumber) {
        this.lmmigrateNumber = lmmigrateNumber == null ? null : lmmigrateNumber.trim();
    }

    public String getLmmigrateType() {
        return lmmigrateType;
    }

    public void setLmmigrateType(String lmmigrateType) {
        this.lmmigrateType = lmmigrateType == null ? null : lmmigrateType.trim();
    }

    public Date getLmmigrateTime() {
        return lmmigrateTime;
    }

    public void setLmmigrateTime(Date lmmigrateTime) {
        this.lmmigrateTime = lmmigrateTime;
    }

    public String getChinesePassportNumber() {
        return chinesePassportNumber;
    }

    public void setChinesePassportNumber(String chinesePassportNumber) {
        this.chinesePassportNumber = chinesePassportNumber == null ? null : chinesePassportNumber.trim();
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard == null ? null : idCard.trim();
    }

    public String getHukouLocation() {
        return hukouLocation;
    }

    public void setHukouLocation(String hukouLocation) {
        this.hukouLocation = hukouLocation == null ? null : hukouLocation.trim();
    }

    public String getPersonStatus() {
        return personStatus;
    }

    public void setPersonStatus(String personStatus) {
        this.personStatus = personStatus == null ? null : personStatus.trim();
    }

    public byte[] getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(byte[] timeStamp) {
        this.timeStamp = timeStamp;
    }
}