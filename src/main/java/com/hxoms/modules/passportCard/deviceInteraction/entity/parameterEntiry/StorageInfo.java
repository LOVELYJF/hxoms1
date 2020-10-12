package com.hxoms.modules.passportCard.deviceInteraction.entity.parameterEntiry;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

/**
 * @Desc：TODO
 * @Author: wangyunquan
 * @Date: 2020/10/10
 */
@ApiModel(value = "行列信息")
public class StorageInfo {
    @NotNull(message = "卡盒列数不能为空")
    @ApiModelProperty(value = "卡盒列数",required = true)
    private Integer cardColumnCount;
    @NotNull(message = "卡盒行数不能为空")
    @ApiModelProperty(value = "卡盒行数",required = true)
    private Integer cardRowCount;
    @NotNull(message = "本盒列数不能为空")
    @ApiModelProperty(value = "本盒列数",required = true)
    private Integer bookColumnCount;
    @NotNull(message = "本盒行数不能为空")
    @ApiModelProperty(value = "本盒行数",required = true)
    private Integer bookRowCount;

    public Integer getCardColumnCount() {
        return cardColumnCount;
    }

    public void setCardColumnCount(Integer cardColumnCount) {
        this.cardColumnCount = cardColumnCount;
    }

    public Integer getCardRowCount() {
        return cardRowCount;
    }

    public void setCardRowCount(Integer cardRowCount) {
        this.cardRowCount = cardRowCount;
    }

    public Integer getBookColumnCount() {
        return bookColumnCount;
    }

    public void setBookColumnCount(Integer bookColumnCount) {
        this.bookColumnCount = bookColumnCount;
    }

    public Integer getBookRowCount() {
        return bookRowCount;
    }

    public void setBookRowCount(Integer bookRowCount) {
        this.bookRowCount = bookRowCount;
    }
}
