package com.hxoms.modules.omsregcadre.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hxoms.common.hxannotation.ColumnAnnotation;
import com.hxoms.common.hxannotation.IdAnnotation;
import com.hxoms.common.hxannotation.TableAnnotation;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 省管干部登记备案人员信息扩展类
 * @author lijiaojiao
 */
public class OmsRegProcpersoninfoVO extends OmsRegProcpersoninfo{
    //机构名称
    private String b0101;
    private String sexName;

    public String getSexName() {
        return sexName;
    }

    public void setSexName(String sexName) {
        this.sexName = sexName;
    }

    public String getIncumbencyStatusName() {
        return incumbencyStatusName;
    }

    public void setIncumbencyStatusName(String incumbencyStatusName) {
        this.incumbencyStatusName = incumbencyStatusName;
    }

    public String getSecretLevelName() {
        return secretLevelName;
    }

    public void setSecretLevelName(String secretLevelName) {
        this.secretLevelName = secretLevelName;
    }

    public String getDataTypeName() {
        return dataTypeName;
    }

    public void setDataTypeName(String dataTypeName) {
        this.dataTypeName = dataTypeName;
    }

    private String incumbencyStatusName;
    private String secretLevelName;
    private String dataTypeName;


    public String getB0101() {
        return b0101;
    }

    public void setB0101(String b0101) {
        this.b0101 = b0101;
    }
}