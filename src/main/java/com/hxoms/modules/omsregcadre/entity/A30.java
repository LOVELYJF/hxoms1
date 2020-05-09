package com.hxoms.modules.omsregcadre.entity;

import com.hxoms.common.hxannotation.ColumnAnnotation;
import com.hxoms.common.hxannotation.IdAnnotation;
import com.hxoms.common.hxannotation.IgnoreLogAnnotation;
import com.hxoms.common.hxannotation.TableAnnotation;

import java.util.Date;
/**
 * a30
 */
@TableAnnotation(TableName = "a30", TableDescription="退出管理信息集")
public class A30 {
    /**
     * 主键
     */
    @IdAnnotation
    @ColumnAnnotation(FieldName = "A3000",  FieldDescription="主键")
    private String a3000;

    /**
     *
     */
    @ColumnAnnotation(FieldName = "id",  FieldDescription="")
    private String id;

    /**
     *
     */
    @ColumnAnnotation(FieldName = "is_deleted",  FieldDescription="")
    private String isDeleted;

    /**
     *
     */
    @ColumnAnnotation(FieldName = "modify_user",  FieldDescription="")
    private String modifyUser;

    /**
     *
     */
    @ColumnAnnotation(FieldName = "modify_time",  FieldDescription="")
    private Date modifyTime;

    /**
     *
     */
    @ColumnAnnotation(FieldName = "node_id",  FieldDescription="")
    private String nodeId;

    /**
     *
     */
    @ColumnAnnotation(FieldName = "a0100",  FieldDescription="")
    private String a0100;

    /**
     *
     */
    @ColumnAnnotation(FieldName = "a3001",  FieldDescription="")
    private String a3001;

    /**
     *
     */
    @ColumnAnnotation(FieldName = "a3004",  FieldDescription="")
    private String a3004;

    /**
     *
     */
    @ColumnAnnotation(FieldName = "a3007a",  FieldDescription="")
    private String a3007a;

    /**
     *
     */
    @ColumnAnnotation(FieldName = "a3034",  FieldDescription="")
    private String a3034;

    /**
     *
     */
    @ColumnAnnotation(FieldName = "a3038",  FieldDescription="")
    private String a3038;

    /**
     *
     */
    @ColumnAnnotation(FieldName = "is_normal",  FieldDescription="")
    private String isNormal;

    /**
     *
     */
    @ColumnAnnotation(FieldName = "operate_batch",  FieldDescription="")
    private String operateBatch;

    /**
     *
     */
    @ColumnAnnotation(FieldName = "a3117a",  FieldDescription="")
    private String a3117a;

    /**
     *
     */
    @ColumnAnnotation(FieldName = "a3137",  FieldDescription="")
    private String a3137;

    /**
     *
     */
    @ColumnAnnotation(FieldName = "a3101",  FieldDescription="")
    private String a3101;

    /**
     *
     */
    @IgnoreLogAnnotation
    @ColumnAnnotation(FieldName = "time_stamp",  FieldDescription="")
    private byte[] timeStamp;

    /**
     * 主键
     * @return A3000 主键
     */
    public String getA3000() {
        return a3000;
    }

    /**
     * 主键
     * @param a3000 主键
     */
    public void setA3000(String a3000) {
        this.a3000 = a3000 == null ? null : a3000.trim();
    }

    /**
     *
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     *
     * @param id
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     *
     * @return is_deleted
     */
    public String getIsDeleted() {
        return isDeleted;
    }

    /**
     *
     * @param isDeleted
     */
    public void setIsDeleted(String isDeleted) {
        this.isDeleted = isDeleted == null ? null : isDeleted.trim();
    }

    /**
     *
     * @return modify_user
     */
    public String getModifyUser() {
        return modifyUser;
    }

    /**
     *
     * @param modifyUser
     */
    public void setModifyUser(String modifyUser) {
        this.modifyUser = modifyUser == null ? null : modifyUser.trim();
    }

    /**
     *
     * @return modify_time
     */
    public Date getModifyTime() {
        return modifyTime;
    }

    /**
     *
     * @param modifyTime
     */
    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    /**
     *
     * @return node_id
     */
    public String getNodeId() {
        return nodeId;
    }

    /**
     *
     * @param nodeId
     */
    public void setNodeId(String nodeId) {
        this.nodeId = nodeId == null ? null : nodeId.trim();
    }

    /**
     *
     * @return a0100
     */
    public String getA0100() {
        return a0100;
    }

    /**
     *
     * @param a0100
     */
    public void setA0100(String a0100) {
        this.a0100 = a0100 == null ? null : a0100.trim();
    }

    /**
     *
     * @return a3001
     */
    public String getA3001() {
        return a3001;
    }

    /**
     *
     * @param a3001
     */
    public void setA3001(String a3001) {
        this.a3001 = a3001 == null ? null : a3001.trim();
    }

    /**
     *
     * @return a3004
     */
    public String getA3004() {
        return a3004;
    }

    /**
     *
     * @param a3004
     */
    public void setA3004(String a3004) {
        this.a3004 = a3004 == null ? null : a3004.trim();
    }

    /**
     *
     * @return a3007a
     */
    public String getA3007a() {
        return a3007a;
    }

    /**
     *
     * @param a3007a
     */
    public void setA3007a(String a3007a) {
        this.a3007a = a3007a == null ? null : a3007a.trim();
    }

    /**
     *
     * @return a3034
     */
    public String getA3034() {
        return a3034;
    }

    /**
     *
     * @param a3034
     */
    public void setA3034(String a3034) {
        this.a3034 = a3034 == null ? null : a3034.trim();
    }

    /**
     *
     * @return a3038
     */
    public String getA3038() {
        return a3038;
    }

    /**
     *
     * @param a3038
     */
    public void setA3038(String a3038) {
        this.a3038 = a3038 == null ? null : a3038.trim();
    }

    /**
     *
     * @return is_normal
     */
    public String getIsNormal() {
        return isNormal;
    }

    /**
     *
     * @param isNormal
     */
    public void setIsNormal(String isNormal) {
        this.isNormal = isNormal == null ? null : isNormal.trim();
    }

    /**
     *
     * @return operate_batch
     */
    public String getOperateBatch() {
        return operateBatch;
    }

    /**
     *
     * @param operateBatch
     */
    public void setOperateBatch(String operateBatch) {
        this.operateBatch = operateBatch == null ? null : operateBatch.trim();
    }

    /**
     *
     * @return a3117a
     */
    public String getA3117a() {
        return a3117a;
    }

    /**
     *
     * @param a3117a
     */
    public void setA3117a(String a3117a) {
        this.a3117a = a3117a == null ? null : a3117a.trim();
    }

    /**
     *
     * @return a3137
     */
    public String getA3137() {
        return a3137;
    }

    /**
     *
     * @param a3137
     */
    public void setA3137(String a3137) {
        this.a3137 = a3137 == null ? null : a3137.trim();
    }

    /**
     *
     * @return a3101
     */
    public String getA3101() {
        return a3101;
    }

    /**
     *
     * @param a3101
     */
    public void setA3101(String a3101) {
        this.a3101 = a3101 == null ? null : a3101.trim();
    }

    /**
     *
     * @return time_stamp
     */
    public byte[] getTimeStamp() {
        return timeStamp;
    }

    /**
     *
     * @param timeStamp
     */
    public void setTimeStamp(byte[] timeStamp) {
        this.timeStamp = timeStamp;
    }


}