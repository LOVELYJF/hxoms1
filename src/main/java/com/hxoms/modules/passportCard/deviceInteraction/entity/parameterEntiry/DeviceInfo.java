package com.hxoms.modules.passportCard.deviceInteraction.entity.parameterEntiry;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @Desc：TODO
 * @Author: wangyunquan
 * @Date: 2020/10/10
 */
@ApiModel(value = "设备信息")
public class DeviceInfo {
    @NotBlank(message = "设备编号不能为空")
    @ApiModelProperty(value = "设备编号",required = true)
    private String deviceSn;
    @NotBlank(message = "设备名称不能为空")
    @ApiModelProperty(value = "设备名称",required = true)
    private String deviceName;
    @NotBlank(message = "设备状态不能为空")
    @ApiModelProperty(value = "设备状态",required = true)
    private String status;
    @NotBlank(message = "保管单位不能为空")
    @ApiModelProperty(value = "保管单位",required = true)
    private String surelyUnit;
    @NotBlank(message = "位置名称不能为空")
    @ApiModelProperty(value = "位置名称",required = true)
    private String position;
    @NotBlank(message = "设备当前温度不能为空")
    @ApiModelProperty(value = "设备当前温度",required = true)
    private String temperature;
    @NotBlank(message = "设备报警上限温度不能为空")
    @ApiModelProperty(value = "设备报警上限温度",required = true)
    private String upperTemperature;
    @NotBlank(message = "设备报警下限温度不能为空")
    @ApiModelProperty(value = "设备报警下限温度",required = true)
    private String lowerTemperature;
    @NotBlank(message = "设备当前湿度不能为空")
    @ApiModelProperty(value = "设备当前湿度",required = true)
    private String humidity;
    @NotBlank(message = "设备报警上限湿度不能为空")
    @ApiModelProperty(value = "设备报警上限湿度",required = true)
    private String upperHumidity;
    @NotBlank(message = "设备报警下限湿度不能为空")
    @ApiModelProperty(value = "设备报警下限湿度",required = true)
    private String lowerHumidity;
    @NotNull(message = "空格数不能为空")
    @ApiModelProperty(value = "空格数",required = true)
    private Integer emptyBoxNum;
    @NotNull(message = "卡盒总数不能为空")
    @ApiModelProperty(value = "卡盒总数",required = true)
    private Integer cardBoxNum;
    @NotNull(message = "卡盒空格数不能为空")
    @ApiModelProperty(value = "卡盒空格数",required = true)
    private Integer cardEmptyNum;
    @NotNull(message = "卡盒已存数不能为空")
    @ApiModelProperty(value = "卡盒已存数",required = true)
    private Integer cardSaveNum;
    @NotNull(message = "本盒总数不能为空")
    @ApiModelProperty(value = "本盒总数",required = true)
    private Integer bookBoxNum;
    @NotNull(message = "本盒已存数不能为空")
    @ApiModelProperty(value = "本盒已存数",required = true)
    private Integer bookSaveNum;
    @NotNull(message = "本盒空格数不能为空")
    @ApiModelProperty(value = "本盒空格数",required = true)
    private Integer bookEmptyNum;
    @NotNull(message = "行列信息不能为空")
    @ApiModelProperty(value = "行列信息",required = true)
    @Valid
    private StorageInfo storageInfo;

    public String getDeviceSn() {
        return deviceSn;
    }

    public void setDeviceSn(String deviceSn) {
        this.deviceSn = deviceSn;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSurelyUnit() {
        return surelyUnit;
    }

    public void setSurelyUnit(String surelyUnit) {
        this.surelyUnit = surelyUnit;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getUpperTemperature() {
        return upperTemperature;
    }

    public void setUpperTemperature(String upperTemperature) {
        this.upperTemperature = upperTemperature;
    }

    public String getLowerTemperature() {
        return lowerTemperature;
    }

    public void setLowerTemperature(String lowerTemperature) {
        this.lowerTemperature = lowerTemperature;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getUpperHumidity() {
        return upperHumidity;
    }

    public void setUpperHumidity(String upperHumidity) {
        this.upperHumidity = upperHumidity;
    }

    public String getLowerHumidity() {
        return lowerHumidity;
    }

    public void setLowerHumidity(String lowerHumidity) {
        this.lowerHumidity = lowerHumidity;
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

    public Integer getBookBoxNum() {
        return bookBoxNum;
    }

    public void setBookBoxNum(Integer bookBoxNum) {
        this.bookBoxNum = bookBoxNum;
    }

    public Integer getBookSaveNum() {
        return bookSaveNum;
    }

    public void setBookSaveNum(Integer bookSaveNum) {
        this.bookSaveNum = bookSaveNum;
    }

    public Integer getBookEmptyNum() {
        return bookEmptyNum;
    }

    public void setBookEmptyNum(Integer bookEmptyNum) {
        this.bookEmptyNum = bookEmptyNum;
    }

    public StorageInfo getStorageInfo() {
        return storageInfo;
    }

    public void setStorageInfo(StorageInfo storageInfo) {
        this.storageInfo = storageInfo;
    }
}
