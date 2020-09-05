package com.hxoms.modules.passportCard.counterGet.entity.parameterEntity;

import com.hxoms.common.utils.PageBean;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @Desc：TODO
 * @Author: wangyunquan
 * @Date: 2020/8/18
 */
public class CerGetTaskQueryParam {
    //分页
    @ApiModelProperty(value = "分页")
    private PageBean pageBean;
    //经办人用户Id
    @ApiModelProperty(value = "经办人用户Id")
    private String userId;
    //机构Id
    @ApiModelProperty(value = "机构Id")
    private String rfB0000;
    //领取表Id
    @ApiModelProperty(value = "领取表Id")
    private List<String> getIdList;
    //是否显示过期证照，Y：是，N：否
    @ApiModelProperty(value = "是否显示过期证照，Y：是，N：否")
    private String overFlag;

    public PageBean getPageBean() {
        return pageBean;
    }

    public void setPageBean(PageBean pageBean) {
        this.pageBean = pageBean;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRfB0000() {
        return rfB0000;
    }

    public void setRfB0000(String rfB0000) {
        this.rfB0000 = rfB0000;
    }

    public List<String> getGetIdList() {
        return getIdList;
    }

    public void setGetIdList(List<String> getIdList) {
        this.getIdList = getIdList;
    }

    public String getOverFlag() {
        return overFlag;
    }

    public void setOverFlag(String overFlag) {
        this.overFlag = overFlag;
    }
}
