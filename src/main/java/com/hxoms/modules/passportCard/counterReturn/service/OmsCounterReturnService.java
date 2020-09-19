package com.hxoms.modules.passportCard.counterReturn.service;


import com.hxoms.modules.passportCard.counterReturn.entity.parameterEntity.*;
import com.hxoms.modules.passportCard.initialise.entity.CfCertificateSeeRes;

public interface OmsCounterReturnService {


    /**
     * @Desc: 读取证照信息
     * @Author: wangyunquan
     * @Param: [readCerInfo]
     * @Return: com.hxoms.modules.passportCard.counterReturn.entity.parameterEntity.ReturnCertificateInfo
     * @Date: 2020/8/20
     */
    CerAndPersonInfo readCerInfo(ReadCerInfo readCerInfo);

    /**
     * @Desc: 查询因私出国申请信息
     * @Author: wangyunquan
     * @Param: [priApplyQueryParams]
     * @Return: com.hxoms.modules.passportCard.counterReturn.entity.parameterEntity.PriApplyInfo
     * @Date: 2020/8/20
     */
    PriApplyInfo selectPriApplyInfo(PriApplyQueryParams priApplyQueryParams);

    /**
     * @Desc: 填写因私有关情况报告
     * @Author: wangyunquan
     * @Param: [priApplyInfo]
     * @Return: void
     * @Date: 2020/8/20
     */
    void updatePriForFillReport(PriApplyInfo priApplyInfo);

    /**
     * @Desc: 归还证照
     * @Author: wangyunquan
     * @Param: [returnCerInfo]
     * @Return: void
     * @Date: 2020/8/20
     */
    void returnCertificate(ReturnCerInfo returnCerInfo);
    
    
    /**
     * 根据证件号查询证件信息
     * @param passportNum
     *  @param a0100
     * @return
     * @Author: wuyezhen
     * @Date: 2020/9/04
     */
    CfCertificateSeeRes examineCertificate(String passportNum,String a0100);
}
