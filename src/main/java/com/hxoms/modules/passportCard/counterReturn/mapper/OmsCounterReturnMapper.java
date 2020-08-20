package com.hxoms.modules.passportCard.counterReturn.mapper;


import com.hxoms.modules.passportCard.initialise.entity.CfCertificate;
import com.hxoms.modules.privateabroad.entity.OmsPriApply;

public interface OmsCounterReturnMapper {

    /**
     * @Desc: 读取证照信息
     * @Author: wangyunquan
     * @Param: [cfCertificate]
     * @Return: com.hxoms.modules.passportCard.initialise.entity.CfCertificate
     * @Date: 2020/8/20
     */
    CfCertificate selectCerByQua(CfCertificate cfCertificate);

    /**
     * @Desc: 查询因私出国申请信息
     * @Author: wangyunquan
     * @Param: [cfCertificate]
     * @Return: com.hxoms.modules.privateabroad.entity.OmsPriApply
     * @Date: 2020/8/20
     */
    OmsPriApply selectPriApplyInfo(CfCertificate cfCertificate);
}