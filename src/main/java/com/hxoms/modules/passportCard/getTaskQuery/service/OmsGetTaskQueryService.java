package com.hxoms.modules.passportCard.getTaskQuery.service;


import com.hxoms.common.utils.PageBean;
import com.hxoms.modules.passportCard.getTaskQuery.entity.parameterEntity.*;

import java.util.List;

public interface OmsGetTaskQueryService {

    /**
     * @Desc: 查询领取证照
     * @Author: wangyunquan
     * @Param: [pageBean, cerGetTaskQueryParam]
     * @Return: com.hxoms.common.utils.PageBean<com.hxoms.modules.passportCard.getTaskQuery.entity.parameterEntity.CerGetTaskInfo>
     * @Date: 2020/8/18
     */

    PageBean<CerGetTaskInfo> selectGetCer(PageBean pageBean, CerGetTaskQueryParam cerGetTaskQueryParam);

    /**
     * @Desc: 获取发通知内容模板
     * @Author: wangyunquan
     * @Param: [sendNoticeParamList]
     * @Return: com.hxoms.modules.passportCard.getTaskQuery.entity.parameterEntity.NoticeContent
     * @Date: 2020/9/5
     */
    NoticeContent getSendNoticeContent(List<SendNoticeContentParam> sendNoticeContentParamList);

    /**
     * @Desc: 发通知
     * @Author: wangyunquan
     * @Param: [sendNotice]
     * @Return: void
     * @Date: 2020/9/5
     */
    void sendNotice(SendNotice sendNotice);
}
