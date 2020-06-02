package com.hxoms.modules.keySupervision.majorLeader.entity;

import com.hxoms.common.hxannotation.ColumnAnnotation;
import com.hxoms.common.hxannotation.IdAnnotation;
import com.hxoms.common.hxannotation.TableAnnotation;

/**
 * <b>人员信息排序实体类</b>
 * @author luoshuai
 * @date 2020/5/26 10:50
 */
@TableAnnotation(TableName = "person_org_order", TableDescription="")
public class PersonOrgOrder {
    @IdAnnotation
    @ColumnAnnotation(FieldName = "id",   FieldDescription="主键")
    private String id;

    @ColumnAnnotation(FieldName = "a01000",   FieldDescription="人员id")
    private String a01000;

    @ColumnAnnotation(FieldName = "a0201b",   FieldDescription="机构id")
    private String a0201b;

    @ColumnAnnotation(FieldName = "orderindex",   FieldDescription="")
    private Integer orderindex;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getA01000() {
        return a01000;
    }

    public void setA01000(String a01000) {
        this.a01000 = a01000 == null ? null : a01000.trim();
    }

    public String getA0201b() {
        return a0201b;
    }

    public void setA0201b(String a0201b) {
        this.a0201b = a0201b == null ? null : a0201b.trim();
    }

    public Integer getOrderindex() {
        return orderindex;
    }

    public void setOrderindex(Integer orderindex) {
        this.orderindex = orderindex;
    }
}