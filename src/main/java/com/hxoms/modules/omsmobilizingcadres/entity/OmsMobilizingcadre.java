package com.hxoms.modules.omsmobilizingcadres.entity;

import com.hxoms.common.hxannotation.ColumnAnnotation;
import com.hxoms.common.hxannotation.IdAnnotation;
import com.hxoms.common.hxannotation.IgnoreLogAnnotation;
import com.hxoms.common.hxannotation.TableAnnotation;
import java.util.Date;

/**
 * oms_mobilizingcadre
 */
@TableAnnotation(TableName = "oms_mobilizingcadre", TableDescription="")
public class OmsMobilizingcadre {
    /**
     * 主键
     */
    @IdAnnotation
    @ColumnAnnotation(FieldName = "ID",  FieldDescription="主键")
    private String id;

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
     * 调整时间
     */
    @ColumnAnnotation(FieldName = "Adjustment_time",  FieldDescription="调整时间")
    private Date adjustmentTime;

    /**
     * 完成时间
     */
    @ColumnAnnotation(FieldName = "Completion_time",  FieldDescription="完成时间")
    private Date completionTime;

    /**
     * 创建日期
     */
    @ColumnAnnotation(FieldName = "Create_date",  FieldDescription="创建日期")
    private Date createDate;

    /**
     * 创建人
     */
    @ColumnAnnotation(FieldName = "Create_user",  FieldDescription="创建人")
    private String createUser;

    /**
     * 状态（0调整期、1调整完成）
     */
    @ColumnAnnotation(FieldName = "status",  FieldDescription="状态（0调整期、1调整完成）")
    private String status;

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

    /**
     * 调整时间
     * @return Adjustment_time 调整时间
     */
    public Date getAdjustmentTime() {
        return adjustmentTime;
    }

    /**
     * 调整时间
     * @param adjustmentTime 调整时间
     */
    public void setAdjustmentTime(Date adjustmentTime) {
        this.adjustmentTime = adjustmentTime;
    }

    /**
     * 完成时间
     * @return Completion_time 完成时间
     */
    public Date getCompletionTime() {
        return completionTime;
    }

    /**
     * 完成时间
     * @param completionTime 完成时间
     */
    public void setCompletionTime(Date completionTime) {
        this.completionTime = completionTime;
    }

    /**
     * 创建日期
     * @return Create_date 创建日期
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * 创建日期
     * @param createDate 创建日期
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * 创建人
     * @return Create_user 创建人
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
     * 状态（0调整期、1调整完成）
     * @return status 状态（0调整期、1调整完成）
     */
    public String getStatus() {
        return status;
    }

    /**
     * 状态（0调整期、1调整完成）
     * @param status 状态（0调整期、1调整完成）
     */
    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }
}