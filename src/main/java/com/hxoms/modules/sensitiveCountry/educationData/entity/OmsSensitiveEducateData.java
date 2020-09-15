package com.hxoms.modules.sensitiveCountry.educationData.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hxoms.common.hxannotation.ColumnAnnotation;
import com.hxoms.common.hxannotation.IdAnnotation;
import com.hxoms.common.hxannotation.TableAnnotation;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

@TableAnnotation(TableName = "oms_sensitive_educate_data", TableDescription="行前保密资料表")
@ApiModel(value = "行前保密资料表")
public class OmsSensitiveEducateData {
    @IdAnnotation
    @ColumnAnnotation(FieldName = "ID",   FieldDescription="主键")
    @ApiModelProperty(value="主键")
    private String id;

    @ColumnAnnotation(FieldName = "COUNTRY_ID",   FieldDescription="国家id")
    @ApiModelProperty(value="国家id")
    private String countryId;

    @ColumnAnnotation(FieldName = "DATA_TYPE",   FieldDescription="资料类别 1-视频 2-文档")
    @ApiModelProperty(value="资料类别 1-视频 2-文档")
    private String dataType;

    @ColumnAnnotation(FieldName = "DATA_NAME",   FieldDescription="资料名称")
    @ApiModelProperty(value="资料名称")
    private String dataName;

    @ColumnAnnotation(FieldName = "DATA_SIZE",   FieldDescription="资料大小")
    @ApiModelProperty(value="资料大小")
    private String dataSize;

    @ColumnAnnotation(FieldName = "FILE_PATH",   FieldDescription="文件路径")
    @ApiModelProperty(value="文件路径")
    private String filePath;

    @ColumnAnnotation(FieldName = "UPLOAD_TIME",   FieldDescription="上传时间")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    @ApiModelProperty(value="上传时间")
    private Date uploadTime;

    @ColumnAnnotation(FieldName = "UPLOAD_PEOPLE",   FieldDescription="上传人")
    @ApiModelProperty(value="上传人")
    private String uploadPeople;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getCountryId() {
        return countryId;
    }

    public void setCountryId(String countryId) {
        this.countryId = countryId == null ? null : countryId.trim();
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType == null ? null : dataType.trim();
    }

    public String getDataName() {
        return dataName;
    }

    public void setDataName(String dataName) {
        this.dataName = dataName == null ? null : dataName.trim();
    }

    public String getDataSize() {
        return dataSize;
    }

    public void setDataSize(String dataSize) {
        this.dataSize = dataSize == null ? null : dataSize.trim();
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath == null ? null : filePath.trim();
    }

    public Date getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(Date uploadTime) {
        this.uploadTime = uploadTime;
    }

    public String getUploadPeople() {
        return uploadPeople;
    }

    public void setUploadPeople(String uploadPeople) {
        this.uploadPeople = uploadPeople == null ? null : uploadPeople.trim();
    }
}