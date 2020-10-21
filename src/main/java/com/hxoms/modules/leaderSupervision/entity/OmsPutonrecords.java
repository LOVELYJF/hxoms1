package com.hxoms.modules.leaderSupervision.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hxoms.common.hxannotation.ColumnAnnotation;
import com.hxoms.common.hxannotation.IdAnnotation;
import com.hxoms.common.hxannotation.TableAnnotation;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

@TableAnnotation(TableName = "oms_putonrecords", TableDescription="备案表下载记录表")
@ApiModel(value = "备案表下载记录表")
public class OmsPutonrecords {
    @IdAnnotation
    @ColumnAnnotation(FieldName = "id",   FieldDescription="备案表下载记录表主键 ")
    @ApiModelProperty(value="备案表下载记录表主键 ")
    private String id;

    @ColumnAnnotation(FieldName = "CREATE_DATE",   FieldDescription="创建时间")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    @ApiModelProperty(value="创建时间")
    private Date createDate;

    @ColumnAnnotation(FieldName = "orgName",   FieldDescription="单位")
    @ApiModelProperty(value="单位id")
    private String orgName;

    @ColumnAnnotation(FieldName = "userName",   FieldDescription="人员姓名")
    @ApiModelProperty(value="备案表")
    private String userName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}