package com.hxoms.modules.passportCard.counterReturn.mapper;


import com.hxoms.modules.passportCard.counterReturn.entity.parameterEntity.PriApplyInfo;
import com.hxoms.modules.passportCard.counterReturn.entity.parameterEntity.PriApplyQueryParams;
import com.hxoms.modules.passportCard.initialise.entity.CfCertificate;

public interface OmsCounterReturnMapper {

    /**
     * @Desc: 读取证照信息
     * @Author: wangyunquan
     * @Param: [zjlx , zjhm]
     * @Return: com.hxoms.modules.passportCard.initialise.entity.CfCertificate
     * @Date: 2020/8/20
     */
    CfCertificate selectCerByQua(Integer zjlx,String zjhm);

    /**
     * @Desc: 查询因私出国申请信息
     * @Author: wangyunquan
     * @Param: [priApplyQueryParams]
     * @Return: com.hxoms.modules.passportCard.counterReturn.entity.parameterEntity.PriApplyInfo
     * @Date: 2020/8/20
     */
    PriApplyInfo  selectPriApplyInfo(PriApplyQueryParams priApplyQueryParams);
}