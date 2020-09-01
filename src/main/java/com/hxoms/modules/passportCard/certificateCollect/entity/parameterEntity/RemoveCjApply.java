package com.hxoms.modules.passportCard.certificateCollect.entity.parameterEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * @Desc：TODO
 * @Author: wangyunquan
 * @Date: 2020/8/31
 */
@ApiModel(value = "解除催缴申请")
public class RemoveCjApply {
    @ApiModelProperty(value="主键",required = true)
    private String id;

    @ApiModelProperty(value="0:手动解除,1:已上缴,2:未上缴,3:自动解除",required = true)
    private String cjStatus;

    @ApiModelProperty(value="解除催缴说明",required = true)
    private String removeCjDesc;

    @ApiModelProperty(value="解除催缴时间",required = true)
    private Date updatetime;

    @ApiModelProperty(value="解除人",required = true)
    private String updator;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCjStatus() {
        return cjStatus;
    }

    public void setCjStatus(String cjStatus) {
        this.cjStatus = cjStatus;
    }

    public String getRemoveCjDesc() {
        return removeCjDesc;
    }

    public void setRemoveCjDesc(String removeCjDesc) {
        this.removeCjDesc = removeCjDesc;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public String getUpdator() {
        return updator;
    }

    public void setUpdator(String updator) {
        this.updator = updator;
    }
}
