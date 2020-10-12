package com.hxoms.modules.passportCard.deviceInteraction.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hxoms.common.hxannotation.ColumnAnnotation;
import com.hxoms.common.hxannotation.IdAnnotation;
import com.hxoms.common.hxannotation.TableAnnotation;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

@TableAnnotation(TableName = "oms_cer_device_info", TableDescription="证照机设备信息表")
@ApiModel(value = "证照机设备信息表")
public class OmsCerDeviceInfo {
    @IdAnnotation
    @ColumnAnnotation(FieldName = "ID",   FieldDescription="主键")
    @ApiModelProperty(value="主键")
    private String id;

    @ColumnAnnotation(FieldName = "DEVICE_SN",   FieldDescription="设备编号")
    @ApiModelProperty(value="设备编号")
    private String deviceSn;

    @ColumnAnnotation(FieldName = "DEVICE_NAME",   FieldDescription="设备名称")
    @ApiModelProperty(value="设备名称")
    private String deviceName;

    @ColumnAnnotation(FieldName = "STATUS",   FieldDescription="设备状态(0:停用,1:正常,2:故障)")
    @ApiModelProperty(value="设备状态(0:停用,1:正常,2:故障)")
    private String status;

    @ColumnAnnotation(FieldName = "SURELY_UNIT",   FieldDescription="保管单位(0:干部监督处,1:省委统战部(台办))")
    @ApiModelProperty(value="保管单位(0:干部监督处,1:省委统战部(台办))")
    private String surelyUnit;

    @ColumnAnnotation(FieldName = "POSITION",   FieldDescription="位置名称")
    @ApiModelProperty(value="位置名称")
    private String position;

    @ColumnAnnotation(FieldName = "TEMPERATURE",   FieldDescription="设备当前温度")
    @ApiModelProperty(value="设备当前温度")
    private String temperature;

    @ColumnAnnotation(FieldName = "UPPER_TEMPERATURE",   FieldDescription="设备报警上限温度")
    @ApiModelProperty(value="设备报警上限温度")
    private String upperTemperature;

    @ColumnAnnotation(FieldName = "LOWER_TEMPERATURE",   FieldDescription="设备报警下限温度")
    @ApiModelProperty(value="设备报警下限温度")
    private String lowerTemperature;

    @ColumnAnnotation(FieldName = "HUMIDITY",   FieldDescription="设备当前湿度")
    @ApiModelProperty(value="设备当前湿度")
    private String humidity;

    @ColumnAnnotation(FieldName = "UPPER_HUMIDITY",   FieldDescription="设备报警上限湿度")
    @ApiModelProperty(value="设备报警上限湿度")
    private String upperHumidity;

    @ColumnAnnotation(FieldName = "LOWER_HUMIDITY",   FieldDescription="设备报警下限湿度")
    @ApiModelProperty(value="设备报警下限湿度")
    private String lowerHumidity;

    @ColumnAnnotation(FieldName = "EMPTY_BOX_NUM",   FieldDescription="空格数")
    @ApiModelProperty(value="空格数")
    private Integer emptyBoxNum;

    @ColumnAnnotation(FieldName = "CARD_BOX_NUM",   FieldDescription="卡盒总数")
    @ApiModelProperty(value="卡盒总数")
    private Integer cardBoxNum;

    @ColumnAnnotation(FieldName = "CARD_EMPTY_NUM",   FieldDescription="卡盒空格数")
    @ApiModelProperty(value="卡盒空格数")
    private Integer cardEmptyNum;

    @ColumnAnnotation(FieldName = "CARD_SAVE_NUM",   FieldDescription="卡盒已存数")
    @ApiModelProperty(value="卡盒已存数")
    private Integer cardSaveNum;

    @ColumnAnnotation(FieldName = "CARD_ROW_COUNT",   FieldDescription="卡盒行数")
    @ApiModelProperty(value="卡盒行数")
    private Integer cardRowCount;

    @ColumnAnnotation(FieldName = "CARD_COLUMN_COUNT",   FieldDescription="卡盒列数")
    @ApiModelProperty(value="卡盒列数")
    private Integer cardColumnCount;

    @ColumnAnnotation(FieldName = "BOOK_BOX_NUM",   FieldDescription="本盒总数")
    @ApiModelProperty(value="本盒总数")
    private Integer bookBoxNum;

    @ColumnAnnotation(FieldName = "BOOK_EMPTY_NUM",   FieldDescription="本盒空格数")
    @ApiModelProperty(value="本盒空格数")
    private Integer bookEmptyNum;

    @ColumnAnnotation(FieldName = "BOOK_SAVE_NUM",   FieldDescription="本盒已存数")
    @ApiModelProperty(value="本盒已存数")
    private Integer bookSaveNum;

    @ColumnAnnotation(FieldName = "BOOK_ROW_COUNT",   FieldDescription="本盒行数")
    @ApiModelProperty(value="本盒行数")
    private Integer bookRowCount;

    @ColumnAnnotation(FieldName = "BOOK_COLUMN_COUNT",   FieldDescription="本盒列数")
    @ApiModelProperty(value="本盒列数")
    private Integer bookColumnCount;

    @ColumnAnnotation(FieldName = "CREATE_TIME",   FieldDescription="创建时间")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    @ApiModelProperty(value="创建时间")
    private Date createTime;

    @ColumnAnnotation(FieldName = "UPDATE_TIME",   FieldDescription="修改时间")
    @JsonFormat(pattern = "yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    @ApiModelProperty(value="修改时间")
    private Date updateTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getDeviceSn() {
        return deviceSn;
    }

    public void setDeviceSn(String deviceSn) {
        this.deviceSn = deviceSn == null ? null : deviceSn.trim();
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName == null ? null : deviceName.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getSurelyUnit() {
        return surelyUnit;
    }

    public void setSurelyUnit(String surelyUnit) {
        this.surelyUnit = surelyUnit == null ? null : surelyUnit.trim();
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position == null ? null : position.trim();
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature == null ? null : temperature.trim();
    }

    public String getUpperTemperature() {
        return upperTemperature;
    }

    public void setUpperTemperature(String upperTemperature) {
        this.upperTemperature = upperTemperature == null ? null : upperTemperature.trim();
    }

    public String getLowerTemperature() {
        return lowerTemperature;
    }

    public void setLowerTemperature(String lowerTemperature) {
        this.lowerTemperature = lowerTemperature == null ? null : lowerTemperature.trim();
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity == null ? null : humidity.trim();
    }

    public String getUpperHumidity() {
        return upperHumidity;
    }

    public void setUpperHumidity(String upperHumidity) {
        this.upperHumidity = upperHumidity == null ? null : upperHumidity.trim();
    }

    public String getLowerHumidity() {
        return lowerHumidity;
    }

    public void setLowerHumidity(String lowerHumidity) {
        this.lowerHumidity = lowerHumidity == null ? null : lowerHumidity.trim();
    }

    public Integer getEmptyBoxNum() {
        return emptyBoxNum;
    }

    public void setEmptyBoxNum(Integer emptyBoxNum) {
        this.emptyBoxNum = emptyBoxNum;
    }

    public Integer getCardBoxNum() {
        return cardBoxNum;
    }

    public void setCardBoxNum(Integer cardBoxNum) {
        this.cardBoxNum = cardBoxNum;
    }

    public Integer getCardEmptyNum() {
        return cardEmptyNum;
    }

    public void setCardEmptyNum(Integer cardEmptyNum) {
        this.cardEmptyNum = cardEmptyNum;
    }

    public Integer getCardSaveNum() {
        return cardSaveNum;
    }

    public void setCardSaveNum(Integer cardSaveNum) {
        this.cardSaveNum = cardSaveNum;
    }

    public Integer getCardRowCount() {
        return cardRowCount;
    }

    public void setCardRowCount(Integer cardRowCount) {
        this.cardRowCount = cardRowCount;
    }

    public Integer getCardColumnCount() {
        return cardColumnCount;
    }

    public void setCardColumnCount(Integer cardColumnCount) {
        this.cardColumnCount = cardColumnCount;
    }

    public Integer getBookBoxNum() {
        return bookBoxNum;
    }

    public void setBookBoxNum(Integer bookBoxNum) {
        this.bookBoxNum = bookBoxNum;
    }

    public Integer getBookEmptyNum() {
        return bookEmptyNum;
    }

    public void setBookEmptyNum(Integer bookEmptyNum) {
        this.bookEmptyNum = bookEmptyNum;
    }

    public Integer getBookSaveNum() {
        return bookSaveNum;
    }

    public void setBookSaveNum(Integer bookSaveNum) {
        this.bookSaveNum = bookSaveNum;
    }

    public Integer getBookRowCount() {
        return bookRowCount;
    }

    public void setBookRowCount(Integer bookRowCount) {
        this.bookRowCount = bookRowCount;
    }

    public Integer getBookColumnCount() {
        return bookColumnCount;
    }

    public void setBookColumnCount(Integer bookColumnCount) {
        this.bookColumnCount = bookColumnCount;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}