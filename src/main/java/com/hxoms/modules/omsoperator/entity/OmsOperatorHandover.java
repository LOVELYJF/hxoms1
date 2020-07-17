package com.hxoms.modules.omsoperator.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hxoms.common.hxannotation.ColumnAnnotation;
import com.hxoms.common.hxannotation.IdAnnotation;
import com.hxoms.common.hxannotation.TableAnnotation;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * oms_operator_handover
 */
@TableAnnotation(TableName = "oms_operator_handover", TableDescription="")
public class OmsOperatorHandover {
    /**
     * 经办人交接主表ID
     */
    @IdAnnotation
    @ColumnAnnotation(FieldName = "ID",  FieldDescription="经办人交接主表ID")
    private String id;

    /**
     * 经办人主键
     */
    @ColumnAnnotation(FieldName = "OperatorId",  FieldDescription="经办人主键")
    private String operatorid;

    /**
     * 交接人ID
     */
    @ColumnAnnotation(FieldName = "HandoverId",  FieldDescription="交接人ID")
    private String handoverid;

    /**
     * 交接时间
     */
    @ColumnAnnotation(FieldName = "HandoverTime",  FieldDescription="交接时间")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date handovertime;

    /**
     * 交接状态（0待办、1完成、2撤消）
     */
    @ColumnAnnotation(FieldName = "HandoverStatus",  FieldDescription="交接状态（0待办、1完成、2撤消）")
    private String handoverstatus;

    /**
     * 经办人交接主表ID
     * @return ID 经办人交接主表ID
     */
    public String getId() {
        return id;
    }

    /**
     * 经办人交接主表ID
     * @param id 经办人交接主表ID
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * 经办人主键
     * @return OperatorId 经办人主键
     */
    public String getOperatorid() {
        return operatorid;
    }

    /**
     * 经办人主键
     * @param operatorid 经办人主键
     */
    public void setOperatorid(String operatorid) {
        this.operatorid = operatorid == null ? null : operatorid.trim();
    }

    /**
     * 交接人ID
     * @return HandoverId 交接人ID
     */
    public String getHandoverid() {
        return handoverid;
    }

    /**
     * 交接人ID
     * @param handoverid 交接人ID
     */
    public void setHandoverid(String handoverid) {
        this.handoverid = handoverid == null ? null : handoverid.trim();
    }

    /**
     * 交接时间
     * @return HandoverTime 交接时间
     */
    public Date getHandovertime() {
        return handovertime;
    }

    /**
     * 交接时间
     * @param handovertime 交接时间
     */
    public void setHandovertime(Date handovertime) {
        this.handovertime = handovertime;
    }

    /**
     * 交接状态（1待办、2完成、3撤消）
     * @return HandoverStatus 交接状态（1待办、2完成、3撤消）
     */
    public String getHandoverstatus() {
        return handoverstatus;
    }

    /**
     * 交接状态（1待办、2完成、3撤消）
     * @param handoverstatus 交接状态（1待办、2完成、3撤消）
     */
    public void setHandoverstatus(String handoverstatus) {
        this.handoverstatus = handoverstatus == null ? null : handoverstatus.trim();
    }
}