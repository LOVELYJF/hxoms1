package com.hxoms.modules.passportCard.counterReturn.service;


import com.hxoms.modules.passportCard.counterReturn.entity.parameterEntity.ReturnCertificateInfo;
import com.hxoms.modules.passportCard.initialise.entity.CfCertificate;
import com.hxoms.modules.passportCard.initialise.entity.CfCertificateSeeRes;
import com.hxoms.modules.privateabroad.entity.OmsPriApply;

public interface OmsCounterReturnService {


    /**
     * @Desc: 读取证照信息
     * @Author: wangyunquan
     * @Param: [cfCertificate]
     * @Return: com.hxoms.modules.passportCard.counterReturn.entity.parameterEntity.ReturnCertificateInfo
     * @Date: 2020/8/20
     */
    ReturnCertificateInfo readCerInfo(CfCertificate cfCertificate);

    /**
     * @Desc: 查询因私出国申请信息
     * @Author: wangyunquan
     * @Param: [cfCertificate]
     * @Return: com.hxoms.modules.privateabroad.entity.OmsPriApply
     * @Date: 2020/8/20
     */
    OmsPriApply selectPriApplyInfo(CfCertificate cfCertificate);

    /**
     * @Desc: 填写因私有关情况报告
     * @Author: wangyunquan
     * @Param: [omsPriApply]
     * @Return: void
     * @Date: 2020/8/20
     */
    void updatePriForFillReport(OmsPriApply omsPriApply);

    /**
     * @Desc: 归还证照
     * @Author: wangyunquan
     * @Param: [cfCertificate]
     * @Return: void
     * @Date: 2020/8/20
     */
    void returnCertificate(CfCertificate cfCertificate);
    
    
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
