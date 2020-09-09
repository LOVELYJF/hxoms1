package com.hxoms.modules.passportCard.certificateCollect.entity.parameterEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;

/**
 * @Desc：TODO
 * @Author: wangyunquan
 * @Date: 2020/9/4
 */
@ApiModel(value = "保存催缴结果")
public class SaveCjResult {
    //id
    @NotBlank(message = "id不能为空")
    @ApiModelProperty(value = "数据列表id",required = true)
    private String id;

    //登陆用户id
    @NotBlank(message = "登陆用户id不能为空")
    @ApiModelProperty(value = "登陆用户id",required = true)
    private String userId;

    //登陆用户姓名
    @NotBlank(message = "登陆用户姓名不能为空")
    @ApiModelProperty(value = "登陆用户姓名",required = true)
    private String userName;

    //催缴情况
    @NotBlank(message = "催缴情况不能为空")
    @ApiModelProperty(value = "催缴情况",required = true)
    private String cjResult;

    //催缴结果
    @NotBlank(message = "催缴结果不能为空")
    @ApiModelProperty(value = "催缴结果",required = true)
    private String cjResultAdd;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public String getCjResult() {
        return cjResult;
    }

    public void setCjResult(String cjResult) {
        this.cjResult = cjResult;
    }

    public String getCjResultAdd() {
        return cjResultAdd;
    }

    public void setCjResultAdd(String cjResultAdd) {
        this.cjResultAdd = cjResultAdd;
    }

}
