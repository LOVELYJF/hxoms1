package com.hxoms.modules.passportCard.certificateManage.service;

import com.hxoms.common.utils.PageBean;
import com.hxoms.modules.passportCard.certificateManage.entity.parameterEntity.*;

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
    PageBean<CerManageInfo> selectCerInfo(PageBean pageBean, CerManageQueryParam cerManageQueryParam);



    /**
     * @Desc: 新领证照录入
     * @Author: wangyunquan
     * @Param: [readCerInfo]
     * @Return: com.hxoms.modules.passportCard.certificateManage.entity.parameterEntity.CerAndPerson
     * @Date: 2020/8/17
     */
    CerAndPerson insertCertificate(ReadCerInfo readCerInfo);

    /**
     * @Desc: 保存证照
     * @Author: wangyunquan
     * @Param: [cerInfoSave]
     * @Return: void
     * @Date: 2020/9/9
     */
    void saveCertificate(CerInfoSave cerInfoSave);
}
