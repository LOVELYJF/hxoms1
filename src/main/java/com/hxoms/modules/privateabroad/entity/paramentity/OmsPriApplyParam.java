package com.hxoms.modules.privateabroad.entity.paramentity;

import com.hxoms.modules.privateabroad.entity.OmsPriApply;
import com.hxoms.modules.privateabroad.entity.OmsPriTogetherperson;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @desc: 因私出国申请接收参数
 * @author: lijing
 * @date: 2020-05-18
 */
@ApiModel(value = "因私出国申请接收参数")
public class OmsPriApplyParam {
    //因私出国基本信息
    @ApiModelProperty(value = "因私出国基本信息")
    private OmsPriApply omsPriApply;
    //随行人员
    @ApiModelProperty(value = "随行人员")
    private List<OmsPriTogetherperson> omsPriTogetherperson;
    //type 1、保存   2、下一步
    @ApiModelProperty(value = "类型  1、保存   2、下一步")
    private String type;

    public OmsPriApply getOmsPriApply() {
        return omsPriApply;
    }

    public void setOmsPriApply(OmsPriApply omsPriApply) {
        this.omsPriApply = omsPriApply;
    }

    public List<OmsPriTogetherperson> getOmsPriTogetherperson() {
        return omsPriTogetherperson;
    }

    public void setOmsPriTogetherperson(List<OmsPriTogetherperson> omsPriTogetherperson) {
        this.omsPriTogetherperson = omsPriTogetherperson;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
