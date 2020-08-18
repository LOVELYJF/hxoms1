package com.hxoms.modules.passportCard.certificateManage.service;

import com.hxoms.common.utils.PageBean;
import com.hxoms.modules.omsregcadre.entity.OmsRegProcpersoninfo;
import com.hxoms.modules.passportCard.certificateManage.entity.parameterEntity.CerManageQueryParam;
import com.hxoms.modules.passportCard.initialise.entity.CfCertificate;

import java.util.List;

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

    /**
     * @Desc: 查询登记备案人员
     * @Author: wangyunquan     * @Param: [cfCertificate]
     * @Return: java.util.List<com.hxoms.modules.omsregcadre.entity.OmsRegProcpersoninfo>
     * @Date: 2020/8/17
     */
    List<OmsRegProcpersoninfo> selectRegPerson(CfCertificate cfCertificate);

    /**
     * @Desc: 新增证照
     * @Author: wangyunquan
     * @Param: [cfCertificate]
     * @Return: void
     * @Date: 2020/8/17
     */
    void insertCertificate(CfCertificate cfCertificate);
}
