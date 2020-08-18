package com.hxoms.modules.passportCard.getTaskQuery.mapper;


import com.hxoms.modules.passportCard.getTaskQuery.entity.parameterEntity.CerGetTaskInfo;
import com.hxoms.modules.passportCard.getTaskQuery.entity.parameterEntity.CerGetTaskQueryParam;

import java.util.List;

public interface OmsGetTaskQueryMapper{

    /**
     * @Desc: 查询领取证照
     * @Author: wangyunquan
     * @Param: [cerGetTaskQueryParam]
     * @Return: java.util.List<com.hxoms.modules.passportCard.counterGet.entity.parameterEntity.CerGetTaskInfo>
     * @Date: 2020/8/18
     */
    List<CerGetTaskInfo> selectGetCer(CerGetTaskQueryParam cerGetTaskQueryParam);
}