package com.hxoms.modules.passportCard.counterGet.entity.parameterEntity;

import com.hxoms.modules.passportCard.counterGet.entity.OmsCerGetTask;

import java.util.List;

/**
 * @Desc：TODO
 * @Author: wangyunquan
 * @Date: 2020/8/18
 */
public class OmsCerGetTaskListParam {
    //领取信息集合
    private List<OmsCerGetTask> omsCerGetTaskList;

    public List<OmsCerGetTask> getOmsCerGetTaskList() {
        return omsCerGetTaskList;
    }

    public void setOmsCerGetTaskList(List<OmsCerGetTask> omsCerGetTaskList) {
        this.omsCerGetTaskList = omsCerGetTaskList;
    }
}
