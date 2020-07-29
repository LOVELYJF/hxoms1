package com.hxoms.modules.leaderSupervision.entity;

import com.hxoms.common.hxannotation.ColumnAnnotation;
import com.hxoms.common.hxannotation.IdAnnotation;
import com.hxoms.common.hxannotation.TableAnnotation;

@TableAnnotation(TableName = "oms_batch_applybusiness", TableDescription="批次和业务申请的中间表 ")
public class OmsBatchApplybusiness {
    @IdAnnotation
    @ColumnAnnotation(FieldName = "id",   FieldDescription="批次业务中间表ID")
    private String id;

    @ColumnAnnotation(FieldName = "batch_id",   FieldDescription="批次id")
    private String batchId;

    @ColumnAnnotation(FieldName = "business_id",   FieldDescription="业务id")
    private String businessId;

    @ColumnAnnotation(FieldName = "business_type",   FieldDescription="业务类型比如(因私，因公，延期，等)")
    private String businessType;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getBatchId() {
        return batchId;
    }

    public void setBatchId(String batchId) {
        this.batchId = batchId == null ? null : batchId.trim();
    }

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId == null ? null : businessId.trim();
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType == null ? null : businessType.trim();
    }
}