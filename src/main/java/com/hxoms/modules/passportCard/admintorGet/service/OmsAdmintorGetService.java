package com.hxoms.modules.passportCard.admintorGet.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.hxoms.common.utils.PageBean;
import com.hxoms.modules.passportCard.admintorGet.entity.OmsCerAdmintorGetApply;
import com.hxoms.modules.passportCard.admintorGet.entity.parameterEntiry.*;

import java.util.List;

public interface OmsAdmintorGetService extends IService<OmsCerAdmintorGetApply> {


    /**
     * @Desc: 查询证照信息
     * @Author: wangyunquan
     * @Param: [pageBean, admintorGetQueryParam]
     * @Return: com.hxoms.common.utils.PageBean
     * @Date: 2020/8/18
     */
    PageBean<AdmintorGetCerInfo> selectCerInfo(PageBean pageBean, AdmintorGetQueryParam admintorGetQueryParam);

    /**
     * @Desc: 保存管理员取证申请并打印二维码
     * @Author: wangyunquan
     * @Param: [admintorGetApplyList]
     * @Return: void
     * @Date: 2020/8/18
     */
    GetCerInfoAndQrCode insertAdmintorGetApply(List<AdminGetCerApply> adminGetCerApplyList);

    /**
     * @Desc: 查询人员证照
     * @Author: wangyunquan
     * @Param: [omsId]
     * @Return: com.hxoms.modules.passportCard.admintorGet.entity.parameterEntiry.PersonLicenceInfo
     * @Date: 2020/10/19
     */
    PersonLicenceInfo selectInfoByOmsId(String omsId);

    /**
     * @Desc: 打印二维码
     * @Author: wangyunquan
     * @Param: [printQrCodeParamsList]
     * @Return: com.hxoms.modules.passportCard.admintorGet.entity.parameterEntiry.GetCerInfoAndQrCode
     * @Date: 2020/9/15
     */
    GetCerInfoAndQrCode createPrintQrCode(List<PrintQrCodeParams> printQrCodeParamsList);
}
