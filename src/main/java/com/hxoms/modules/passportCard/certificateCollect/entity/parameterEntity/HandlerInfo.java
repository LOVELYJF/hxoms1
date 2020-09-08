package com.hxoms.modules.passportCard.certificateCollect.entity.parameterEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;

/**
 * @Desc：TODO
 * @Author: wangyunquan
 * @Date: 2020/8/31
 */
@ApiModel(value = "经办人信息")
public class HandlerInfo {
    @NotBlank(message = "经办人id不能为空")
    @ApiModelProperty(value = "用户id")
    private String userId;

    @NotBlank(message = "经办人真实姓名不能为空")
    @ApiModelProperty("经办人真实姓名")
    private String userName;

    @NotBlank(message = "经办人电话不能为空")
    @ApiModelProperty("经办人电话")
    private String userMobile;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserMobile() {
        return userMobile;
    }

    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile;
    }
}
