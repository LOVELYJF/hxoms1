package com.hxoms.modules.passportCard.certificateManage.service;

import com.hxoms.common.utils.PageBean;
import com.hxoms.modules.passportCard.certificateManage.entity.parameterEntity.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Desc：TODO
 * @Author: wangyunquan
 * @Date: 2020/8/13
 */
public interface OmsCerManageService {

    /**
     * @Desc: 证照管理-证照信息管理-导出
     * @Author: wuqingfan
     * @Param: ids
     * @Return: excel
     * @Date: 2020/9/11
     */
    void exportSelectCerInfo(List<String> ids, HttpServletResponse response);



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
