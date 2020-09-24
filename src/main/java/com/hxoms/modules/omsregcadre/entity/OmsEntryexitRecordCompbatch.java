package com.hxoms.modules.omsregcadre.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hxoms.common.hxannotation.ColumnAnnotation;
import com.hxoms.common.hxannotation.IdAnnotation;
import com.hxoms.common.hxannotation.TableAnnotation;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

@TableAnnotation(TableName = "oms_entryexit_record_compbatch", TableDescription="出入境记录比对批次表")
public class OmsEntryexitRecordCompbatch {
    @IdAnnotation
    @ColumnAnnotation(FieldName = "ID",   FieldDescription="主键")
    private String id;

    @ColumnAnnotation(FieldName = "BATCH_NO",   FieldDescription="批次号")
    private String batchNo;

    @ColumnAnnotation(FieldName = "START_DATE",   FieldDescription="批次开始时间")
    @JsonFormat(pattern = "yyyy.MM.dd",timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date startDate;

    @ColumnAnnotation(FieldName = "FINISH_DATE",   FieldDescription="实际完成时间")
    @JsonFormat(pattern = "yyyy.MM.dd",timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date finishDate;

    @ColumnAnnotation(FieldName = "CREATE_USER",   FieldDescription="创建人")
    private String createUser;

    @ColumnAnnotation(FieldName = "COMPARE_SUM",   FieldDescription="比对总人数")
    private Integer compareSum;

    @ColumnAnnotation(FieldName = "CURRENT_FINISHSUM",   FieldDescription="当前完成人数")
    private Integer currentFinishsum;

    @ColumnAnnotation(FieldName = "STATUS",   FieldDescription="状态 1.进行中  2.已完成 3.已取消")
    private String status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo == null ? null : batchNo.trim();
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(Date finishDate) {
        this.finishDate = finishDate;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser == null ? null : createUser.trim();
    }

    public Integer getCompareSum() {
        return compareSum;
    }

    public void setCompareSum(Integer compareSum) {
        this.compareSum = compareSum;
    }

    public Integer getCurrentFinishsum() {
        return currentFinishsum;
    }

    public void setCurrentFinishsum(Integer currentFinishsum) {
        this.currentFinishsum = currentFinishsum;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }
}