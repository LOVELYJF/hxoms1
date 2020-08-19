package com.hxoms.modules.passportCard.getTaskQuery.service;


import com.hxoms.common.utils.PageBean;
import com.hxoms.modules.passportCard.getTaskQuery.entity.parameterEntity.CerGetTaskQueryParam;

public interface OmsGetTaskQueryService {

    /**
     * @Desc: 查询领取证照
     * @Author: wangyunquan
     * @Param: [pageBean, cerGetTaskQueryParam]
     * @Return: com.hxoms.common.utils.PageBean
     * @Date: 2020/8/18
     */
    PageBean selectGetCer(PageBean pageBean,CerGetTaskQueryParam cerGetTaskQueryParam);


}
