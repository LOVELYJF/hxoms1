package com.hxoms.modules.passportCard.counterGet.entity.parameterEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Desc：TODO
 * @Author: wangyunquan
 * @Date: 2020/9/7
 */
@ApiModel(value = "指纹")
public class FingerMark {
    //左手指纹
    @ApiModelProperty(value = "左手指纹")
    private String leftFingerMark;
    //右手指纹
    @ApiModelProperty(value = "右手指纹")
    private String rightFingerMark;

    public String getLeftFingerMark() {
        return leftFingerMark;
    }

    public void setLeftFingerMark(String leftFingerMark) {
        this.leftFingerMark = leftFingerMark;
    }

    public String getRightFingerMark() {
        return rightFingerMark;
    }

    public void setRightFingerMark(String rightFingerMark) {
        this.rightFingerMark = rightFingerMark;
    }
}
