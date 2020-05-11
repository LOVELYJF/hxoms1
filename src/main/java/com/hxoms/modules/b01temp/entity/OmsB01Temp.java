package com.hxoms.modules.b01temp.entity;

import com.hxoms.common.hxannotation.ColumnAnnotation;
import com.hxoms.common.hxannotation.IdAnnotation;
import com.hxoms.common.hxannotation.TableAnnotation;

@TableAnnotation(TableName = "oms_b01_temp", TableDescription="机构临时表（用于业务中关联查询）")
public class OmsB01Temp {
    @IdAnnotation
    @ColumnAnnotation(FieldName = "batch_id",   FieldDescription="批次id")
    private String batchId;

    @IdAnnotation
    @ColumnAnnotation(FieldName = "b0100",   FieldDescription="机构id")
    private String b0100;

    public String getBatchId() {
        return batchId;
    }

    public void setBatchId(String batchId) {
        this.batchId = batchId == null ? null : batchId.trim();
    }

    public String getB0100() {
        return b0100;
    }

    public void setB0100(String b0100) {
        this.b0100 = b0100 == null ? null : b0100.trim();
    }
}