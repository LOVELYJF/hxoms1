package com.hxoms.modules.omsregcadre.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hxoms.common.hxannotation.ColumnAnnotation;
import com.hxoms.common.hxannotation.IdAnnotation;
import com.hxoms.common.hxannotation.TableAnnotation;
import com.hxoms.modules.privateabroad.entity.OmsPriApply;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 出入境记录返回类
 * @author lijiaojiao
 */
public class OmsEntryexitRecordVO extends OmsEntryexitRecord{

    private OmsPriApply priApply;

    public OmsPriApply getPriApply() {
        return priApply;
    }

    public void setPriApply(OmsPriApply priApply) {
        this.priApply = priApply;
    }
}