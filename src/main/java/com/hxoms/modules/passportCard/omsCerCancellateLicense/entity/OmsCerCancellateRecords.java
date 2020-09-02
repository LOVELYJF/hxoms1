package com.hxoms.modules.passportCard.omsCerCancellateLicense.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hxoms.common.hxannotation.ColumnAnnotation;
import com.hxoms.common.hxannotation.IdAnnotation;
import com.hxoms.common.hxannotation.TableAnnotation;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

@TableAnnotation(TableName = "oms_cer_cancellate_records", TableDescription="注销证照审批记录表")
@ApiModel(value = "注销证照审批记录表")
public class OmsCerCancellateRecords {
    @IdAnnotation
    @ColumnAnnotation(FieldName = "ID",   FieldDescription="审批记录主键")
    @ApiModelProperty(value="审批记录主键")
    private String id;

    @ColumnAnnotation(FieldName = "CANCELLATE_ID",   FieldDescription="注销证照主键")
    @ApiModelProperty(value="注销证照主键")
    private String cancellateId;

    @ColumnAnnotation(FieldName = "ZHZXZT",   FieldDescription="证照申请注销状态 0-填写申请 1-生成材料 2-打印材料清单 3-提交干部监督处 4-受理 5-处领导审批 6-部领导审批 7-生成注销函 8-拒绝 9-撤销 10-完成注销 11-已办结")
    @ApiModelProperty(value="证照申请注销状态 0-填写申请 1-生成材料 2-打印材料清单 3-提交干部监督处 4-受理 5-处领导审批 6-部领导审批 7-生成注销函 8-拒绝 9-撤销 10-完成注销 11-已办结")
    private String zhzxzt;

    @ColumnAnnotation(FieldName = "SPERATOR",   FieldDescription="处理人")
    @ApiModelProperty(value="处理人")
    private String sperator;

    @ColumnAnnotation(FieldName = "SPERATOR_TIME",   FieldDescription="处理时间")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    @ApiModelProperty(value="处理时间")
    private Date speratorTime;

    @ColumnAnnotation(FieldName = "RESULT",   FieldDescription="结果")
    @ApiModelProperty(value="结果")
    private String result;

    @ColumnAnnotation(FieldName = "REMARKS",   FieldDescription="备注")
    @ApiModelProperty(value="备注")
    private String remarks;

    @ColumnAnnotation(FieldName = "CREATE_USER",   FieldDescription="创建人")
    @ApiModelProperty(value="创建人")
    private String createUser;

    @ColumnAnnotation(FieldName = "CREATE_TIME",   FieldDescription="创建时间")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    @ApiModelProperty(value="创建时间")
    private Date createTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getCancellateId() {
        return cancellateId;
    }

    public void setCancellateId(String cancellateId) {
        this.cancellateId = cancellateId == null ? null : cancellateId.trim();
    }

    public String getZhzxzt() {
        return zhzxzt;
    }

    public void setZhzxzt(String zhzxzt) {
        this.zhzxzt = zhzxzt == null ? null : zhzxzt.trim();
    }

    public String getSperator() {
        return sperator;
    }

    public void setSperator(String sperator) {
        this.sperator = sperator == null ? null : sperator.trim();
    }

    public Date getSperatorTime() {
        return speratorTime;
    }

    public void setSperatorTime(Date speratorTime) {
        this.speratorTime = speratorTime;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result == null ? null : result.trim();
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks == null ? null : remarks.trim();
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser == null ? null : createUser.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}