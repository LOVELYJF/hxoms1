package com.hxoms.modules.passportCard.printGetQrCode.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.hxoms.common.utils.PageBean;
import com.hxoms.modules.passportCard.printGetQrCode.entity.OmsCerPrintQrCode;
import com.hxoms.modules.passportCard.printGetQrCode.entity.parameterEntity.CanGetCerInfo;
import com.hxoms.modules.passportCard.printGetQrCode.entity.parameterEntity.CreateQrCodeApply;
import com.hxoms.modules.passportCard.printGetQrCode.entity.parameterEntity.QrCode;

import java.io.IOException;
import java.util.List;

public interface OmsPrintGetQrCodeService extends IService<OmsCerPrintQrCode> {

    /**
     * @Desc: 查询可领取证照
     * @Author: wangyunquan
     * @Param: [pageBean, overFlag]
     * @Return: com.hxoms.common.utils.PageBean<com.hxoms.modules.passportCard.printGetQrCode.entity.parameterEntity.CanGetCerInfo>
     * @Date: 2020/8/20
     */
    PageBean<CanGetCerInfo> selectCanGetCer(PageBean pageBean, String overFlag);

    /**
     * @Desc: 生成打印二维码
     * @Author: wangyunquan
     * @Param: [list]
     * @Return: java.lang.String
     * @Date: 2020/8/21
     */
    QrCode createPrintQrCode(List<CreateQrCodeApply> omsCerPrintQrCodeList) throws IOException;
}
