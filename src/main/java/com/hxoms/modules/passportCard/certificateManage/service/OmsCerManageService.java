package com.hxoms.modules.passportCard.certificateManage.service;

import com.hxoms.common.utils.PageBean;
import com.hxoms.modules.passportCard.certificateManage.entity.parameterEntity.CerManageQueryParam;

/**
 * @Desc：TODO
 * @Author: wangyunquan
 * @Date: 2020/8/13
 */
public interface OmsCerManageService {
    
    /**
     * @Desc: 查询证照信息
     * @Author: wangyunquan
     * @Param: [pageBean, cerManageQueryParam]
     * @Return: com.hxoms.common.utils.PageBean
     * @Date: 2020/8/13
     */
    PageBean selectCerInfo(PageBean pageBean, CerManageQueryParam cerManageQueryParam);
}
