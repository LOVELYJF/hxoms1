package com.hxoms.modules.passportCard.certificateCollect.entity.parameterEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import java.util.Date;

/**
 * @Desc：TODO
 * @Author: wangyunquan
 * @Date: 2020/8/31
 */
@ApiModel(value = "解除催缴申请")
public class RemoveCjApply {
    @NotBlank(message = "id不能为空")
    @ApiModelProperty(value="主键",required = true)
    private String id;

    @NotBlank(message = "催缴状态不能为空")
    @ApiModelProperty(value="催缴状态(0:手动解除,1:已上缴,2:未上缴,3:自动解除)",required = true)
    private String cjStatus;

    @NotBlank(message = "解除催缴说明不能为空")
    @ApiModelProperty(value="解除催缴说明",required = true)
    private String removeCjDesc;

    @NotBlank(message = "解除催缴时间不能为空")
    @ApiModelProperty(value="解除催缴时间",required = true)
    private Date updatetime;

    @NotBlank(message = "解除人不能为空")
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
