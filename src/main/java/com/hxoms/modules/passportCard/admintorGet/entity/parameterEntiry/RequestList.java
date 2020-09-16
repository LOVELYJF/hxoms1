package com.hxoms.modules.passportCard.admintorGet.entity.parameterEntiry;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * @Desc：TODO
 * @Author: wangyunquan
 * @Date: 2020/8/19
 */
@ApiModel(value = "数据集合")
public class RequestList<T> {
    @NotEmpty(message = "数据集合Size不能为0")
    @Valid
    @ApiModelProperty(value = "数据集合信息",required = true)
    private List<T> list;

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}
